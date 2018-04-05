app = angular.module('bcm.openTask', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {

    $stateProvider.state('in.openTask', {
        url: '/openTask/:id',
        controller: 'OpenTaskCtrl',
        templateUrl: 'kanban/openTask.tpl.html',

        data: { pageTitle: 'Abrir Tarefa', requiresLogin: true }
    });
}]);

app.controller('OpenTaskCtrl', ['$mdDialog', '$stateParams', '$timeout', '$rootScope', '$http', '$state', '$scope', 'KanbanService', '$mdMedia', 'FiltersService',

    function OpenTaskController($mdDialog, $stateParams, $timeout, $rootScope, $http, $state, $scope, KanbanService, $mdMedia, FiltersService) {

        $scope.service = KanbanService;
        $scope.fileName = null;
        $scope.documentRequired = false;
        $scope.filterService = FiltersService;

        $scope.listening = function (formIten) {
            var fileList = formIten;
            var islazy = false;
            angular.forEach($scope.selectedTask.formItens, function (field) {

                if (field.listening === formIten.id && field.lazy) {
                    islazy = true;
                    comboBoxDTO = {
                        value: formIten.value,
                        dataSourceName: field.dataSourceName
                    };
                }
            });

            if (islazy) {
                $scope.filterService.getFilter(comboBoxDTO).then(function () {
                    angular.forEach($scope.selectedTask.formItens, function (field) {
                        if (field.listening === formIten.id) {
                            field.possibleValues = $scope.filterService.ufs;
                        }
                    });

                    $timeout(function () { $scope.messageFeed = ''; }, 3000);

                });
            }
        };

        $scope.unassign = function (taskId) {

            if (angular.isUndefined(taskId)) {
                taskId = $scope.service.selectedTask.taskId;
            }
            $scope.service.unassign(taskId).then(function(){
                $state.go($state.current, {}, {reload: true});
            });

        };

        $scope.assignToMe = function (taskId) {
            $scope.service.assignToMe([taskId]).then(function () {
                $state.go($state.current, {}, {reload: true});
            });
        };

        $scope.manageTask = function (task) {
            $scope.service.selectedTask = task;

            $scope.service.getUsersGroup(task.candidateGroup).then(function () {
            });

            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
            $mdDialog.show({
                controller: KanbanFormController,
                templateUrl: 'kanban/fwd-task-for-user.tpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: true,
                scope: $scope,
                preserveScope: true
            })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
            $scope.$watch(function () {
                return $mdMedia('xs') || $mdMedia('sm');
            }, function (wantsFullScreen) {
                $scope.customFullscreen = (wantsFullScreen === true);
            });
        };



        $scope.confirmCloseTask = function (ev, taskForm) {

            var isValid = true;
            angular.forEach($scope.service.selectedTask.formItens, function (field, key) {
                if (field.required && angular.isUndefined(field.value)) {
                    if (field.type === 'DOCUMENT' && angular.isUndefined(field.value)) {
                        $scope.documentRequired = true;
                        isValid = false;
                    }
                }
            });

            if (isValid) {
                if (taskForm.$valid) {
                    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
                    $mdDialog.show({
                        controller: KanbanFormController,
                        templateUrl: 'kanban/modalConfirmCloseTask.tpl.html',
                        parent: angular.element(document.body),
                        targetEvent: ev,
                        clickOutsideToClose: false,
                        fullscreen: true,
                        scope: $scope,
                        preserveScope: true
                    })
                        .then(function (answer) {
                            $scope.status = 'You said the information was "' + answer + '".';
                        }, function () {
                            $scope.status = 'You cancelled the dialog.';
                        });
                    $scope.$watch(function () {
                        return $mdMedia('xs') || $mdMedia('sm');
                    }, function (wantsFullScreen) {
                        $scope.customFullscreen = (wantsFullScreen === true);
                    });
                }
            }
        };

        $scope.goBack = function () {
            $scope.service.selectedTask = '';
            window.history.back();
        };

        $scope.closeTask = function () {
            $scope.service.closeTask();
        };


        if ($state.current.name === 'in.openTask') {
            var taskId = $stateParams.id;

            $scope.service.openTask(taskId).then(function () {
                var islazy = false;
                for (var k = 0; k < $scope.service.selectedTask.headers.length; k++) {
                    if ($scope.service.selectedTask.headers[k].lazy) {
                        for (var l = 0; l < $scope.service.selectedTask.headers.length; l++) {
                            if ($scope.service.selectedTask.headers[l].id === $scope.service.selectedTask.headers[k].listening) {
                                comboBoxDTO = {
                                    value: $scope.service.selectedTask.headers[l].value,
                                    dataSourceName: $scope.service.selectedTask.headers[k].dataSourceName
                                };
                                $scope.findLazy(comboBoxDTO, $scope.service.selectedTask.headers[k]);
                            }
                        }
                    }
                }

                for (var i = 0; i < $scope.service.selectedTask.formItens.length; i++) {
                    if ($scope.service.selectedTask.formItens[i].type === 'CHECKLIST' && !angular.isUndefined($scope.service.selectedTask.formItens[i].value) && $scope.service.selectedTask.formItens[i].value !== null) {
                        $scope.service.selectedTask.formItens[i].value = JSON.parse($scope.service.selectedTask.formItens[i].value);
                    }
                    if ($scope.service.selectedTask.formItens[i].lazy) {
                        for (var j = 0; j < $scope.service.selectedTask.headers.length; j++) {
                            if ($scope.service.selectedTask.headers[j].id === $scope.service.selectedTask.formItens[i].listening) {
                                comboBoxDTO = {
                                    value: $scope.service.selectedTask.headers[j].value,
                                    dataSourceName: $scope.service.selectedTask.formItens[i].dataSourceName
                                };
                                $scope.findLazy(comboBoxDTO, $scope.service.selectedTask.formItens[i]);
                            }
                        }
                    }

                    if ($scope.service.selectedTask.formItens[i].type === 'DATE' && !angular.isUndefined($scope.service.selectedTask.formItens[i].value) && $scope.service.selectedTask.formItens[i].value !== null) {
                        var stringDate = $scope.service.selectedTask.formItens[i].value;
                        var day = stringDate.slice(0, 2);
                        var month = stringDate.slice(3, 5);
                        var year = stringDate.slice(6, 10);

                        var formatDate = year + '-' + month + '-' + day;
                        $scope.service.selectedTask.formItens[i].value = new Date(formatDate + "T12:00:00-06:30");
                    }

                    if ($scope.service.selectedTask.formItens[i].type === 'DATE' && angular.isUndefined($scope.service.selectedTask.formItens[i].value)) {
                        $scope.service.selectedTask.formItens[i].value = {};
                    }

                    if ($scope.service.selectedTask.formItens[i].type === 'DATAGRID') {

                        for (var gridPosition = 0; gridPosition < $scope.service.selectedTask.formItens[i].gridValues.lines.length; gridPosition++) {
                            for (var valuesPosition = 0; valuesPosition < $scope.service.selectedTask.formItens[i].gridValues.lines[gridPosition].values.length; valuesPosition++) {
                                if ($scope.service.selectedTask.formItens[i].gridValues.lines[gridPosition].values[valuesPosition].metadata !== null) {
                                    var metadata = $scope.service.selectedTask.formItens[i].gridValues.lines[gridPosition].values[valuesPosition].metadata;

                                    if (metadata.lazy) {
                                        for (var header = 0; header < $scope.service.selectedTask.headers.length; header++) {
                                            if ($scope.service.selectedTask.headers[header].id === metadata.listening) {
                                                comboBoxDTO = {
                                                    value: $scope.service.selectedTask.headers[header].value,
                                                    dataSourceName: metadata.beanName
                                                };
                                                $scope.findLazy(comboBoxDTO, $scope.service.selectedTask.formItens[i].dataGridDTO.values[gridPosition]);
                                            }
                                        }
                                    }
                                }
                            }


                            for (var gridValue = 0; gridValue < $scope.service.selectedTask.formItens[i].dataGridDTO.values.length; gridValue++) {

                                if ($scope.service.selectedTask.formItens[i].dataGridDTO.values[gridValue].type === 'DATE' && $scope.service.selectedTask.formItens[i].dataGridDTO.values[gridValue].readable) {

                                    for (var lineValue = 0; lineValue < $scope.service.selectedTask.formItens[i].gridValues.lines.length; lineValue++) {
                                        var celValue = $scope.service.selectedTask.formItens[i].gridValues.lines[lineValue].values[gridValue].value;

                                        if (celValue !== null && celValue.valueOf !== undefined) {

                                            if (!(celValue instanceof Date) && typeof celValue == "string") {
                                                var m = moment(celValue);
                                                $scope.service.selectedTask.formItens[i].gridValues.lines[lineValue].values[gridValue].value = m._d;
                                            }
                                        }else{
                                            $scope.service.selectedTask.formItens[i].gridValues.lines[lineValue].values[gridValue].value = {};
                                        }

                                    }
                                }
                            }

                        }
                    }
                }
            });
        }

        $scope.changeQuantidadeArea = function (value){
            if (value > 15){
                $scope.taskForm.quantidadeArea.$dirty = true;
                $scope.taskForm.quantidadeArea.$setValidity('size', false);
            } else {
                $scope.taskForm.quantidadeArea.$dirty = false;
                $scope.taskForm.quantidadeArea.$setValidity('size', true);
            }

        };

        $scope.findLazy = function (comboBoxDTO, formItens) {

            $scope.filterService.getFilter(comboBoxDTO).then(function () {
                formItens.comboBoxPossibleValues = $scope.filterService.result;
            });
        };

        $scope.downloadAllFiles = function(task){

            $scope.service.downloadAllFiles([task.processInstanceId]);
        };

        $scope.downloadFile = function (myDocumentId) {
            generateTokenDTO = {
                documentVO: {
                    uid: myDocumentId,
                    uploaded: false
                },
                accessType: 'DOWNLOAD'
            };
            $scope.service.getTokenDownload(generateTokenDTO);
        };

        $scope.deleteFile = function (field) {
            var log = [];
            $scope.fileName = null;
            angular.forEach($scope.service.selectedTask.formItens, function (value, key) {
                if (value.id === field.id) {
                    field.value = undefined;
                    field.myDocumentVO = null;
                    $scope.fileName = null;
                }
            }, log);

            document.getElementById(field.id).value = '';
        };

        $scope.uploadShowFile = function (file, formIten) {
            var fileList = [];
            var imageData = '';
            $scope.fileName = null;
            var businessKey = $scope.service.selectedTask.businessKey;
            fileList = document.getElementById(file.id).files;

            imageData = fileList[0];

            var fileName = "";

            $scope.fileName = null;
            angular.forEach($scope.service.selectedTask.formItens, function (value, key) {
                if (value.id === file.id) {
                    fileName = value.id;
                }
            });

            $scope.fileName = fileList[0].name;
            generateTokenDTO = {
                documentVO: {
                    name: imageData.name,
                    businessKey: businessKey,
                    fileName: fileName,
                    uploaded: false
                },
                accessType: 'UPLOAD'
            };

            $scope.service.getToken(generateTokenDTO, imageData).then(function () {

                angular.forEach($scope.service.selectedTask.formItens, function (value, key) {

                    if (value.id === file.id) {
                        value.value = $scope.service.myDocumentId;
                        $scope.service.myDocumentId = '';
                        $scope.documentRequired = false;

                        myDocumentVO = {
                            name: myDocumentVO = $scope.service.newNameFile
                        };

                        value.myDocumentVO = myDocumentVO;
                    } else if (value.type === 'DATAGRID' && value.gridValues.lines.length > 0) {
                        console.log(value.gridValues.lines);
                        angular.forEach(value.gridValues.lines, function (cells, key) {
                            var index = key;

                            angular.forEach(cells, function (cell) {

                                angular.forEach(cell, function (col) {
                                    var valueKey = col.key + index;

                                    if (valueKey === file.id) {
                                        col.value = $scope.service.myDocumentId;

                                        if (col.myDocumentVO == null) {
                                            myDocumentVO = {
                                                uid: $scope.service.myDocumentId,
                                                name: $scope.service.newNameFile
                                                //name: imageData.name
                                            };
                                            col.myDocumentVO = myDocumentVO;

                                        } else {
                                            col.myDocumentVO.name = imageData.name;
                                        }
                                    }
                                });
                            });
                        });
                    }
                });

                formIten.value = $scope.service.myDocumentId;
                $timeout(function () { $scope.messageFeed = ''; }, 3000);

            });
        };

        $scope.saveTaskDraft = function () {
            var save = false;
            angular.forEach($scope.service.selectedTask.formItens, function (field, key) {
                if (field.type === 'DATE' && !angular.isUndefined(field.value) && field.value !== {}) {
                    var date = field.value;

                    if (typeof (date.valueOf()) === 'number') {

                        var ano = date.getFullYear();
                        var mes = date.getMonth() + 1;
                        var dia = date.getDate();
                        if (mes < 10) {
                            mes = '0'.concat(mes.toString());
                        }
                        if (dia < 10) {
                            dia = '0'.concat(dia.toString());
                        }

                        var formatDate = dia + '/' + mes + '/' + ano;

                        field.value = formatDate;

                    } else {
                        field.value = null;
                    }
                }
                if (field.type === 'CHECKLIST' && !angular.isUndefined(field.value)) {
                    field.value = angular.toJson(field.value);
                }
            });


            for (var i = 0; i < $scope.service.selectedTask.formItens.length; i++) {
                if ($scope.service.selectedTask.formItens[i].type == 'DATAGRID') {

                    for (var gridValue = 0; gridValue < $scope.service.selectedTask.formItens[i].dataGridDTO.values.length; gridValue++) {

                        if ($scope.service.selectedTask.formItens[i].dataGridDTO.values[gridValue].type === 'DATE' && $scope.service.selectedTask.formItens[i].dataGridDTO.values[gridValue].readable) {

                            for (var lineValue = 0; lineValue < $scope.service.selectedTask.formItens[i].gridValues.lines.length; lineValue++) {
                                var celValue = $scope.service.selectedTask.formItens[i].gridValues.lines[lineValue].values[gridValue].value;

                                if (celValue !== null && celValue.valueOf !== undefined) {

                                    if (!(celValue instanceof Date) && !(celValue instanceof String && celValue !== {})) {
                                        $scope.service.selectedTask.formItens[i].gridValues.lines[lineValue].values[gridValue].value = null;
                                    }

                                }

                            }
                        }
                    }

                    $scope.service.selectedTask.formItens[i].value = angular.toJson($scope.service.selectedTask.formItens[i].value);
                }

            }
            $scope.service.saveTaskDraft();

        };
    }
]);


function KanbanFormController($rootScope, $scope, $mdDialog, $state, $window) {

    $scope.completeTask = function () {
        var isNull = false;
        angular.forEach($scope.service.selectedTask.formItens, function (field, key) {
            if (field.required && angular.isUndefined(field.value)) {
                if (field.type === 'DOCUMENT' && angular.isUndefined(field.value)) {
                    $scope.documentRequired = true;
                }
                isNull = true;
            }

        });

        if (!isNull) {
            angular.forEach($scope.service.selectedTask.formItens, function (field, key) {
                if (field.type === 'DATE' && !angular.isUndefined(field.value)) {
                    var date = field.value;
                    if (field.value !== null && typeof (date.valueOf()) === 'number') {
                        var ano = date.getFullYear();
                        var mes = date.getMonth() + 1;
                        var dia = date.getDate();
                        if (mes < 10) {
                            mes = '0'.concat(mes.toString());
                        }
                        if (dia < 10) {
                            dia = '0'.concat(dia.toString());
                        }

                        var formatDate = dia + '/' + mes + '/' + ano;

                        field.value = formatDate;
                    } else {
                        field.value = null;
                    }
                }
                if (field.type === 'CHECKLIST' && !angular.isUndefined(field.value)) {
                    field.value = angular.toJson(field.value);
                }
            });
            $scope.service.completeTask();
        } else {
            $rootScope.toastError('A tarefa não pode ser finalizada', 'Você deve preencher os campos obrigatórios');
            $mdDialog.hide();
        }
    };

    $scope.close = function () {
        $scope.service.assignee = '';
        $mdDialog.hide();

    };

    $scope.fowardTaskToUser = function (userId) {

        assignTaskToUserDTO = {
            taskId: '',
            userId: ''
        };

        assignTaskToUserDTO.taskId = $scope.service.selectedTask.taskId;
        assignTaskToUserDTO.userId = userId;
        $scope.service.saveTask(assignTaskToUserDTO);
        $mdDialog.hide();
    };

    $scope.createUserFilterFor = self.createUserFilterFor;
    $scope.selectedUserItem = self.selectedUserItem;
    $scope.searchUserText = self.searchUserText;
    $scope.queryUserSearch = self.queryUserSearch;
    $scope.searchUserTextChange = self.searchUserTextChange;
    $scope.selectedUserItemChange = self.selectedUserItemChange;
}