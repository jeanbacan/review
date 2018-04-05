app = angular.module('bcm.instance-details', [
    'ui.router'
]);



/**
 * Configuração de rotas
 * */
app.config(['$stateProvider', function config($stateProvider) {
    $stateProvider.state('in.detailsInstance', {
        url: '/detailsInstance/:id',
        controller: 'DetailsInstanceCtrl',
        templateUrl: 'instance/instance-details.tpl.html',
        data: { pageTitle: 'Detalhe da Instância', requiresLogin: true }
    });
}]);


app.controller('DetailsInstanceCtrl', ['$stateParams', '$state', '$q', '$rootScope', '$scope', '$location', 'InstanceService', '$mdMedia', 'KanbanService','FiltersService',

    function DetailsInstanceController($stateParams, $state, $q, $rootScope, $scope, $location, InstanceService, $mdMedia, KanbanService, FiltersService) {

        $scope.service = InstanceService;
        $scope.filterService = FiltersService;
        $scope.kanbanService = KanbanService;
        $scope.goBack = function () {
            window.history.back();
        };

        if ($state.current.name === 'in.detailsInstance') {
            $scope.service.getInstanceDetails($stateParams.id).then(function () {
                for (var j = 0; j < $scope.service.vivoTasks.length; j++) {
                    for (var i = 0; i < $scope.service.vivoTasks[j].formItens.length; i++) {
                        if ($scope.service.vivoTasks[j].formItens[i].type === 'CHECKLIST' && !angular.isUndefined($scope.service.vivoTasks[j].formItens[i].value) && $scope.service.vivoTasks[j].formItens[i].value !== null) {
                           $scope.service.vivoTasks[j].formItens[i].value = JSON.parse($scope.service.vivoTasks[j].formItens[i].value);
                        }

                        if ($scope.service.vivoTasks[j].formItens[i].type === 'DATAGRID' && !angular.isUndefined($scope.service.vivoTasks[j].formItens[i].value) && $scope.service.vivoTasks[j].formItens[i].value !== null) {
                          
                            for(var gridPosition = 0; gridPosition < $scope.service.vivoTasks[j].formItens[i].gridValues.lines.length; gridPosition ++){
                                for(var valuesPosition = 0; valuesPosition < $scope.service.vivoTasks[j].formItens[i].gridValues.lines[gridPosition].values.length;  valuesPosition++){
                                    if($scope.service.vivoTasks[j].formItens[i].gridValues.lines[gridPosition].values[valuesPosition].metadata !== null){
                                        var metadata = $scope.service.vivoTasks[j].formItens[i].gridValues.lines[gridPosition].values[valuesPosition].metadata;

                                        if(metadata.lazy){
                                            for(var header = 0;  header  < $scope.service.vivoTasks[j].headers.length; header++) {
                                                if ($scope.service.vivoTasks[j].headers[header].id === metadata.listening) {
                                                    comboBoxDTO = {
                                                        value: $scope.service.vivoTasks[j].headers[header].value,
                                                        dataSourceName: metadata.beanName
                                                    };
                                                    $scope.findLazy(comboBoxDTO,$scope.service.vivoTasks[j].formItens[i].dataGridDTO.values[gridPosition]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                    $scope.util($scope.service.vivoTasks[j]);
                }


            });

        }


        $scope.util = function(vivoTask){
            var isLazy = false;
            for(var i = 0; i < vivoTask.formItens.length; i++){
                if(vivoTask.formItens[i].lazy){
                    for(var j = 0;  j  < vivoTask.headers.length; j++) {
                        if(vivoTask.headers[j].id === vivoTask.formItens[i].listening){
                            isLazy = true;
                            comboBoxDTO = {
                                value: vivoTask.headers[j].value,
                                dataSourceName: vivoTask.formItens[i].dataSourceName
                            };

                        }

                    }
                    if(isLazy){
                        $scope.findLazy(comboBoxDTO,vivoTask.formItens[i]);
                    }else{
                        for(var k = 0;  k  < vivoTask.formItens.length; k++) {
                            if(vivoTask.formItens[k].id === vivoTask.formItens[i].listening){
                                isLAzy = true;
                                comboBoxDTO = {
                                    value: vivoTask.formItens[k].value,
                                    dataSourceName: vivoTask.formItens[i].dataSourceName
                                };
                                $scope.findLazy(comboBoxDTO,vivoTask.formItens[i]);
                            }

                        }
                    }
                }
            }

        };



        $scope.findLazy = function(comboBoxDTO, formItens){

            $scope.filterService.getFilter(comboBoxDTO).then(function () {
                formItens.comboBoxPossibleValues = $scope.filterService.result;
            });
        };


        $scope.downloadFile = function (myDocumentId) {
            generateTokenDTO = {
                documentVO: {
                    uid: myDocumentId,
                    uploaded: false
                },
                accessType: 'DOWNLOAD'
            };
            $scope.kanbanService.getTokenDownload(generateTokenDTO);
        };

    }

]);