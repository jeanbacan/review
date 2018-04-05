app = angular.module('bcm.instance', [
    'ui.router'
]);


/**
 * Configuração de rotas
 * */ 
app.config(['$stateProvider', function config($stateProvider) {
    $stateProvider.state('in.instanceListWithId', {
        url: '/instanceList/:id',
        controller: 'InstanceListCtrl',
        templateUrl: 'instance/instance-list.tpl.html',
        data: { pageTitle: 'Lista de Instâncias', requiresLogin: true }
    });
}]).config(['$stateProvider', function config($stateProvider) {
    $stateProvider.state('in.instanceList', {
        url: '/instanceList',
        controller: 'InstanceListCtrl',
        templateUrl: 'instance/instance-list.tpl.html',
        data: { pageTitle: 'Lista de Instâncias', requiresLogin: true }
    });
}]);



/**
 * instance-list.tpl.html
 * Controle para gerenciar lista de instancias
 * */ 
app.controller('InstanceListCtrl', ['$stateParams', '$state', '$scope', 'InstanceService', 'FiltersService', '$mdMedia', '$mdDialog',

    function InstanceListController($stateParams, $state, $scope, InstanceService, FiltersService, $mdMedia, $mdDialog) {
        $scope.businessKey = null;
        
        $scope.service = InstanceService;
        $scope.filterService = FiltersService;
        
        $scope.filterService.loadUFs();

        //Seta filtro combo caso tenha sido utilizado anteriormente
        $scope.service.getGroupsInvestimentList().then(function () {
            if ($scope.service.filterGroupName !== null && $scope.service.filterGroupName.length > 1){
                angular.forEach($scope.service.groupsInvestimentList, function (group, key) {
                    if (group.name === $scope.service.filterGroupName) {
                        $scope.comboGroup.description = group.description;
                        $scope.comboGroup.name = group.name;
                        return;
                    }
                });
            }
        });            
        $scope.ordenar = $scope.service.sortByCreateDate;

        $scope.newInstance = function(){
             $state.go('in.instanceForm');
        };

        $scope.comboGroup = {
            description: "Todos",
            name: "Todos"
        };

        $scope.downloadAllFiles = function(task){

            $scope.service.downloadAllFiles([task.processInstanceId]);
        };

        $scope.openModalComfirmCancelInstance = function (processInstanceId) {
            $scope.service.processInstaceId = processInstanceId;
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
            $mdDialog.show({
                controller: InstanceFormController,
                templateUrl: 'instance/modalConfirmCancelInstance.tpl.html',
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

        /**
         * Filtra lista de instância com parametros
         * */ 
        $scope.sendFilter = function (selectedUF, sortByCreateDate, selectedStatus) {

            $scope.service.tablePage = 1;
            $scope.service.selectedStatus = selectedStatus;
            $scope.service.sortByCreateDate = sortByCreateDate;

            filterDTO = {
                constraints: {},
                sortType: 'DESC'
            };

            filterDTO.constraints.FILTER_PROCESS_STATUS = {
                "value": "ACTIVE",
                "type": "EQUAL"
            };
            if (!angular.isUndefined(sortByCreateDate)) {
                filterDTO.sortType = sortByCreateDate;

            }
            if (!angular.isUndefined(selectedUF) && selectedUF !== 'Todos') {
                filterDTO.constraints.FILTER_SELECTED_UF = {
                    "value": selectedUF,
                    "type": "EQUAL"
                };
            }
            if (!angular.isUndefined(selectedStatus)) {
                filterDTO.constraints.FILTER_PROCESS_STATUS = {
                    "value": selectedStatus,
                    "type": "EQUAL"
                };
            }
            if ($scope.businessKey !== null && $scope.businessKey !== "") {
                filterDTO.constraints.FILTER_BUSINESS_KEY = {
                    value: $scope.businessKey,
                    type: "EQUAL"
                };
            }
            if (!angular.isUndefined( $scope.service.filterGroupName)) {
                filterDTO.constraints.FILTER_SELECTED_GROUP = {
                    "value": $scope.service.filterGroupName,
                    "type": "EQUAL"
                };
            }            
            console.log('filterDoSearch:' + selectedUF + ':' + sortByCreateDate + ':' + selectedStatus);
            $scope.service.getProcessInstances(filterDTO);
        };

        /**
         * Filtra lista de instância quando alterado combo
         * */ 
        $scope.filterGroup = function (selectedUF, sortByCreateDate, selectedStatus, group) {            
            
            $scope.businessKey = null;
            $scope.selectedUF = null;
            $scope.selectedStatus = "ACTIVE";

            if (group === 'ALL') {
                $scope.comboGroup.name = 'Todos';
                $scope.comboGroup.description = 'Todos';
                $scope.service.filterGroupName = null;
            } else {
                $scope.comboGroup.name = group.name;
                $scope.comboGroup.description = group.description;
                $scope.service.filterGroupName = group.name;
            }
            $scope.sendFilter(selectedUF, sortByCreateDate, selectedStatus);
        };

        /**
         * Filtra lista de instância ao alterar paginação
         * */ 
        $scope.onTablePaginate = function (intPage, intLimit) {
            $scope.service.tablePage = intPage;
            $scope.service.tableLimit = intLimit;
            filterDTO = {
                page: {},
                constraints: {}
            };
            filterDTO.page = {
                "offset": (($scope.service.tablePage - 1) * $scope.service.tableLimit),
                "size": $scope.service.tableLimit
            };

            if ($scope.service.filterGroupName !== null && $scope.service.filterGroupName !== "") {
                filterDTO.constraints = {
                    "FILTER_SELECTED_GROUP": {
                        "value": $scope.service.filterGroupName,
                        "type": "EQUAL"
                    }
                };
            }
            if (!angular.isUndefined($scope.service.sortByCreateDate)) {
                filterDTO.sortType = $scope.service.sortByCreateDate;

            }
            if (!angular.isUndefined($scope.service.selectedUF)) {
                filterDTO.constraints.FILTER_SELECTED_UF = {
                    "value": $scope.service.selectedUF,
                    "type": "EQUAL"
                };
            }
            if (!angular.isUndefined($scope.service.selectedStatus)) {
                filterDTO.constraints.FILTER_PROCESS_STATUS = {
                    "value": $scope.service.selectedStatus,
                    "type": "EQUAL"
                };
            }

            $scope.service.getProcessInstances(filterDTO);
        };

        if ($state.current.name === 'in.instanceListWithId') {

            if (!angular.isUndefined($stateParams.id) && $stateParams.id !== "") {
                var groupName = $stateParams.id;
                $scope.service.filterGroupName = groupName;
                $scope.service.tablePage = 1;
                filterDTO = {
                    page: {},
                    constraints: {}
                };
                filterDTO.page = {
                    "offset": (($scope.service.tablePage - 1) * $scope.service.tableLimit),
                    "size": $scope.service.tableLimit
                };

                filterDTO.constraints = {
                    "FILTER_SELECTED_GROUP": {
                        "value": groupName,
                        "type": "EQUAL"
                    },
                    "FILTER_PROCESS_STATUS": {
                        "value": "ACTIVE",
                        "type": "EQUAL"
                    }
                };


                $scope.service.getGroupsInvestimentList().then(function () {

                    angular.forEach($scope.service.groupsInvestimentList, function (group, key) {
                        if (group.name === groupName) {
                            $scope.comboGroup.description = group.description;
                            $scope.comboGroup.name = group.name;
                            return;
                        }

                    });

                });
                $scope.service.getProcessInstances(filterDTO);
            }

        }
        if ($state.current.name === 'in.instanceList') {
            
            //Consulta ou reconsultar (goBack()) com parametros ja utilizados.
            $scope.onTablePaginate($scope.service.tablePage, $scope.service.tableLimit);
        }
    }
]);

function InstanceFormController($rootScope, $scope, $mdDialog, $state) {


    $scope.close = function () {
        $scope.service.assignee = '';
        $mdDialog.hide();

    };

    $scope.saveFieldForm = function () {
        var test = $scope.service.processInstanceStartDTO;
        var isUndefined = false;

        angular.forEach($scope.service.processInstanceStartDTO.formItems, function (field) {
            if (field.required && field.value === undefined) {
                sUndefined = true;
            }
        });


        if (!isUndefined) {
            $scope.service.createInstance().then(function () {
                $mdDialog.hide();
                window.history.back();
              //  $state.go('in.instanceList');
            });
        }
    };

    $scope.cancelProcessIstance = function (reason) {
        cancelProcessDTO = {
            processInstanceId: $scope.service.processInstaceId,
            reason: reason
        };

        $scope.service.cancelInstance(cancelProcessDTO).then(function () {
            $mdDialog.hide();
            $scope.service.getGroupsInvestimentList();
        });


    };
}


