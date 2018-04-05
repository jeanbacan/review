app = angular.module('bcm.project.instance-service', [
    'ui.router'
]);


/**
 * Call backend service to CRUD Investiment
 */
app.factory("Instance",['$resource', function ($resource) {
    return $resource(API + '/',  {uid: '@uid'}, {
        getGroupsInvestimentList:{
            method: 'GET',
            url: (API + '/groups'),
            isArray: true
        },
        getListProcessInstances:{
            method: 'POST',
            url: (API + '/processInstance/processInstanceList')
        },
        cancelInstance:{
            method: 'POST',
            url: (API + '/processInstance/cancel')
        },
        downloadAllFiles: {
            method: 'GET',
            url: (API + '/task/download/?processInstanceId=:uid')
        },
        getProcessInstanceStartDTO:{
            method: 'GET',
            url: (API + '/processDefinition/form')
        },
        saveInstance: {
            method: 'POST',
            url: (API + '/processInstance/saveInstance')
        },
        getInstanceDetails: {
            method: 'GET',
            url: (API + '/processInstance/instanceDetail?processInstanceId=:uid'),
            isArray: true
        }

    });
}]);

app.factory("saveInstance", function ($http) {
    return {
        saveInstance: function (processInstanceStartDTO) {
            return $http.post(API + '/processInstance/saveInstance',  {
                params: {processInstanceStartDTO: processInstanceStartDTO}
            });
        }
    };
});



/**
 * Investiment service
 */
app.service('InstanceService', ['$mdDialog', 'saveInstance', '$http', 'toaster','Instance', '$q', '$rootScope', '$state', 'store', function ($mdDialog, saveInstance, $http, toaster,Instance, $q, $rootScope, $state, store) {
    var self = {
        'offset': 0,
        'hasMore': true,
        'tablePage' : 1,
        'tableLimit': 10,
        'tableTotal': 0,
        'processInstanceStartDTO' : {},
        'groupsInvestimentList' : [],
        'processInstaceList' : [],
        'vivoTasks' : [],
        'selectdGroup': {},
        'processInstaceId' : '',
        'isLoading': false,
        'filterGroupName' : null,
        'sortByCreateDate' : null,
        'selectedUF' : null,
        'selectedStatus' : "ACTIVE",

        'getInstanceDetails'  : function(businessKey) {
            var d = $q.defer();
            if (!self.isLoading) {
               self.isLoading = true;
            }

            self.vivoTasks = [];
            Instance.getInstanceDetails({uid: businessKey}).$promise.then(function (vivoTasks) {
                self.processInstaceId = '';
                self.vivoTasks = vivoTasks;
                self.isLoading = false;
                d.resolve();
            }, function (reason) {
            self.isLoading = false;
                console.log(JSON.stringify(reason));
                $rootScope.toastError(reason, 'Falha ao Cancelar Projeto.');
            });
            return d.promise;
        },
        'cancelInstance'  : function(cancelProcessDTO) {
            var d = $q.defer();
            if (!self.isLoading) {
               self.isLoading = true;
            }
            Instance.cancelInstance({},cancelProcessDTO).$promise.then(function () {
                self.processInstaceId = '';
                d.resolve();
                self.getProcessInstances();
                self.isLoading = false;
            }, function (reason) {
                self.isLoading = false;
                console.log(JSON.stringify(reason));
                $rootScope.toastError(reason, 'Falha ao Cancelar Projeto.');
            });
            return d.promise;
        },
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

        'getProcessInstances'  : function(filterDTO) {

            if (!self.isLoading) {
               self.isLoading = true;
            }

             if (angular.isUndefined(filterDTO)) {
                 filterDTO = {
                   page : {},
                   constraints: {},
                   sortTypes: {}
                 };

                filterDTO.page = {
                    "offset" : ((self.tablePage - 1) * self.tableLimit),
                    "size" : self.tableLimit
                };


                 filterDTO.constraints  = {
                     "FILTER_PROCESS_STATUS": {
                         "value": "ACTIVE",
                         "type": "EQUAL"
                     }
                 };

                 filterDTO.sortType =  "DESC";

                 filterDTO.page = {
                   "offset" : ((self.tablePage - 1) * self.tableLimit),
                   "size" : self.tableLimit
                 };
            }
            self.processInstaceList = [];
            Instance.getListProcessInstances({}, filterDTO, function (resultPageDTO) {

                self.tableTotal = resultPageDTO.resultCount;

                self.processInstaceList = resultPageDTO.listResult;
                self.isLoading = false;
            }, function (reason) {
                 self.isLoading = false;
                console.log(JSON.stringify(reason));
                $rootScope.toastError(reason, 'Falha ao obter lista de Projetos.');
            });
        },
        'getProcessInstanceStartDTO'  : function() {

            if (!self.isLoading) {
               self.isLoading = true;
            }

            Instance.getProcessInstanceStartDTO(function (processInstanceStartDTO) {

                self.processInstanceStartDTO = processInstanceStartDTO;
                 self.isLoading = false;

            }, function (reason) {
                self.isLoading = false;
                console.log(JSON.stringify(reason));
                $rootScope.toastError(reason, 'Falha ao obter lista de campos.');
            });
        },
        'createInstance' : function(){
            var d = $q.defer();
            if (!self.isLoading) {
                self.isLoading = true;
            }

            Instance.saveInstance({}, self.processInstanceStartDTO, function () {

               self.processInstanceStartDTO = {};
               $mdDialog.hide();
               self.isLoading = false;
               self.getProcessInstances();
               d.resolve();
            },function(reason){
            self.isLoading = false;
               $rootScope.toastError(reason, 'Falha ao criar nova instância.');
            });
             return d.promise;
        },
        'getGroupsInvestimentList'  : function() {
            self.groupsInvestimentList =[];
             var d = $q.defer();
            Instance.getGroupsInvestimentList({}).$promise.then(function (groupsInvestimentList) {
                self.groupsInvestimentList = groupsInvestimentList;
                d.resolve();
            }, function (reason) {
                console.log(JSON.stringify(reason));
                $rootScope.toastError(reason, 'Falha ao obter lista Processos.');
            });
              return d.promise;
        }

    };

    return self;

}]);