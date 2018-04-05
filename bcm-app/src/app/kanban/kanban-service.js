app = angular.module('bcm.dashboard.kanban-service', [
    'ui.router'
]);

/**
 * Call backend services to CRUD Dashboard
 */
app.factory("Kanban", ['$resource', function ($resource) {
    return $resource(API + '/task/:id/', {uid: '@uid'}, {

        update: {
            method: 'POST'
        },
        post: {
            method: 'POST',
            url: (API + '/task')
        },
        remove: {
            method: 'DELETE'
        },
        downloadAllFiles: {
            method: 'GET',
            url: (API + '/task/download/?processInstanceId=:uid')
        },
        assignToMe: {
            method: 'POST',
            url: (API + '/task/assignToMe')
        },
        completeTask: {
            method: 'POST',
            url: (API + '/task/complete')
        },
        saveTaskDraft: {
            method: 'POST',
            url: (API + '/task/saveDraft')
        },
        getTaskById: {
            method: 'POST',
            url: (API + '/task/getTask')
        },
        saveTask: {
            method: 'POST',
            url: (API + '/task/assignToUser')
        },
        historicTask:{
            method: 'GET',
            url: (API + '/task/historicTask?taskId=:uid')
        },
        openTask: {
            method: 'GET',
            url: (API + '/task/openTask?taskId=:uid')
        },
        getUsersGroup: {
            method: 'GET',
            url: (API + '/groups/users?candidateGroup=:uid'),
            isArray: true
        },        
        unassign: {
            method: 'POST',
            url: (API + '/task/unassign')
        }

    });
}]);

app.factory("getToken", function ($http) {
    return {
        getToken: function (GenerateTokenDTO) {
            return $http.post(API + '/upload', GenerateTokenDTO, {
                params: {GenerateTokenDTO: GenerateTokenDTO}
            });
        }
    };
});

app.factory("uploadFile", function ($http) {
    return {
        upload: function (imageData, token) {
            return $http.post(API_DM + '/upload/', imageData, {
                transformRequest: angular.identity,
                params: {token: token},
                headers: {
                    "Content-Type": "text/plain"
                }

            });
        }
    };
});

/**
 * Dashboards services
 */
app.service('KanbanService', ['getToken', '$http', 'uploadFile', '$state', '$location', 'Kanban', '$q', '$rootScope', '$window', 'store', 'VariablesSearchBarService',
function (getToken, $http, uploadFile, $state, $location, Kanban, $q, $rootScope, $window, store, VariablesSearchBarService) {

    var self = {
        'kanbanMap': new Map(),
        'tasks': '',
        'search': null,
        'selectedTask': null,
        'task': '',
        'taskId': '',
        'myDocumentId': '',
        'newNameFile': '',
        'isLoading': false,
        'filterDTO': false,
        'listUsersGroup': [],

        'downloadAllFiles': function(processInstanceIdsArray) {

            var d = $q.defer();

            if (!self.isLoading) {
                self.isLoading = true;
            }

            $http({
                url: API + '/task/download',
                method: 'GET',
                params: {processInstanceId : processInstanceIdsArray[0]},
                headers: {'Authorization': 'Bearer ' + store.get(appTokenKey)},
                responseType:'arraybuffer'
            }).then(function (response) {                

                var header = unescape(response.headers()['content-disposition']);
                var startIndex = header.indexOf('filename=');
                var filename = header.slice(startIndex + 9, header.lenth);

                var file = new Blob([response.data], {type: "application/zip", charset: 'utf-8'});

                var urlBlob = window.URL.createObjectURL(file);
                var link = document.createElement("a");
                link.setAttribute("href", urlBlob);
                link.setAttribute("download", filename);
                var event = document.createEvent('MouseEvents');
                event.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
                link.dispatchEvent(event);

                console.log('Sucesso ao baixar arquivo.');
            }, function (error) {
                $rootScope.toastError(reason, 'Não foram encontrados arquivos para download.');
            });
            self.isLoading = false;
/*
            Kanban.downloadAllFiles({uid:processInstanceIdsArray[0]}, function (response) {                            
                self.isLoading = false;
                
                d.resolve(response);
            }, function (reason) {
                $rootScope.toastError(reason, 'Falha preparar arquivos para download.');
                self.isLoading = false;

                d.reject(reason);
            });

            return d.promise; */
        },
        'getTokenDownload': function (generateTokenDTO) {
            $http({
                url: API + '/upload',
                method: 'POST',
                data: generateTokenDTO
            }).then(function (FileTokenDTO) {
                document.getElementById("iframeFile").src = (API_DM + '/download/' + FileTokenDTO.data.token);
            }, function (error) {
                $rootScope.toastError(reason, 'Falha ao executar consulta.');
            });

        },
        'getToken': function (GenerateTokenDTO, imageData) {
            var d = $q.defer();
            self.myDocumentId = '';

            getToken.getToken(GenerateTokenDTO).then(function (FileTokenDTO) {
                self.uploadFile(FileTokenDTO, imageData);
                //self.callbackUpload(FileTokenDTO.data.myDocumentVO);
                self.myDocumentId = '';
                self.newNameFile = '';

                self.myDocumentId = FileTokenDTO.data.myDocumentVO.documentManagerId;
                self.newNameFile = FileTokenDTO.data.documentVO.name;
                
                d.resolve();
            }, function (reason) {
                $rootScope.toastError(reason, 'Sistema não autorizado a fazer upload');
                self.isLoading = false;

            });
            return d.promise;
        },

        'uploadFile': function (FileTokenDTO, imageData) {
            var d = $q.defer();
            uploadFile.upload(imageData, FileTokenDTO.data.token).then(function () {

                d.resolve();
                // self.callbackUpload(FileTokenDTO.documentVO);
            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao Fazer Upload do arquivo.');
                self.isLoading = false;

            });
            return d.promise;
        },

        'doSearch': function (search, searchTerm, type) {

            self.hasMore = true;
            self.offset = 0;
            self.groups = [];
            self.filterDTO = {
                constraints: {}
            };

            self.filterDTO.constraints[searchTerm] = {
                value: search,
                type: type
            };

            self.loadKanban(self.filterDTO);
            this.filterDTO = {};
        },

        'saveTask': function (assignTaskToUserDTO) {
            Kanban.saveTask({}, assignTaskToUserDTO, function () {
                self.formFields = [];
                self.loadKanban(filterDTO);
                $state.go('in.kanban');
            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao salvar tarefa.');
                self.isLoading = false;

            });

        },

        'getUsersGroup': function (candidateGroup) {

            var d = $q.defer();
            Kanban.getUsersGroup({uid: candidateGroup}, function (listUsers) {
                self.listUsersGroup = [];
                self.listUsersGroup = listUsers;
                d.resolve();
            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao carregar Usuários.');
            });
            return d.promise;

        },

        'historicTask': function (taskiId) {
            self.selectedTask = null;
            var d = $q.defer();
            if (!self.isLoading) {
               self.isLoading = true;
            }

            Kanban.historicTask({uid: taskiId}, function (vivoTask) {

                self.selectedTask = vivoTask;
                self.isLoading = false;
                d.resolve();
            }, function (reason) {
                self.isLoading = false;
                $rootScope.toastError(reason, 'Falha ao carregar a tarefa Solicitada.');
            });
            return d.promise;
        },
        'openTask': function (taskiId) {
            self.selectedTask = null;
            if (!self.isLoading) {
               self.isLoading = true;
            }

            var d = $q.defer();
            Kanban.openTask({uid: taskiId}).$promise.then(function (vivoTask) {
                self.selectedTask = vivoTask;
                 self.isLoading = false;
                d.resolve();
            }, function (reason) {
                self.isLoading = false;
                $rootScope.toastError(reason, 'Falha ao carregar a tarefa Solicitada.');
            });
            return d.promise;
        },
        'completeTask': function () {

           var d = $q.defer();
            if (!self.isLoading) {
               self.isLoading = true;
            }

           Kanban.completeTask({}, self.selectedTask, function () {
               self.selectedTask = null;
                $state.go('in.kanban');
                $rootScope.toastSuccess('Tarefa concluída com sucesso');
                 self.isLoading = false;
               d.resolve();
           }, function (reason) {
                self.isLoading = false;
               $rootScope.toastError(reason, 'Falha ao concluir a tarefa.');
           });
           return d.promise;
        },

        'saveTaskDraft': function () {
            var d = $q.defer();

            if (!self.isLoading) {
                self.isLoading = true;
            }

            Kanban.saveTaskDraft({}, self.selectedTask, function () {
                self.formFields = [];
                $state.go('in.kanban');
                self.selectedTask = null;
                $rootScope.toastSuccess('Tarefa salva com sucesso');
                self.isLoading = false;
                d.resolve();
            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao salvar a tarefa.');
                self.isLoading = false;
            });
            return d.promise;
        },
 
        'assignToMe': function (taskIdArray) {

            if (!self.isLoading) {
                self.isLoading = true;
            }

            var d = $q.defer();
            Kanban.assignToMe(taskIdArray).$promise.then(function () {
            
                $rootScope.toastSuccess('Tarefas assumidas com sucesso.');
                d.resolve();
                self.isLoading = false;

                if ($state.current.name === 'in.kanban'){
                    try {
                         self.loadKanban(filterDTO);
                    } catch (error) {
                         self.loadKanban();
                    }                   
                  }

            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao assinar a tarefa.');
                self.isLoading = false;

            });
            return d.promise;
        },

        'loadKanban': function (filterDTO) {

            var ctrl = this;
            ctrl.variablesService = VariablesSearchBarService.loadVariables(filterDTO);

            self.filterDTO = filterDTO;

            if (angular.isUndefined(filterDTO)) {
                filterDTO = {
                    constraints: {}
                };
            }

            self.isLoading = true;
            Kanban.post({}, filterDTO, function (kanbanMap) {
                self.kanbanMap = kanbanMap;
                self.isLoading = false;

            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao carregar Kanban.');
                self.isLoading = false;
            });

        },
        'unassign': function (taskId) {

          var d = $q.defer();

          Kanban.unassign(taskId).$promise.then(function () {                          
              d.resolve();
              self.isLoading = false;

              if ($state.current.name === 'in.kanban'){
                self.loadKanban();
                $state.go('in.kanban');
              }
          }, function (reason) {
            $rootScope.toastError(reason, 'Falha ao atualizar tarefa.');
            self.isLoading = false;

           });
          return d.promise;

        },
        'closeTask': function () {
            Kanban.completeTask({}, self.selectedTask, function () {
                 self.loadKanban();
                $state.go('in.kanban');
            });
        }
    };


    return self;

}]);

