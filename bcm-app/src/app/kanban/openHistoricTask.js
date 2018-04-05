app = angular.module('bcm.openHistoricTask', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {

    $stateProvider.state('in.openHistoricTask', {
        url: '/openHistoricTask/:id',
        controller: 'OpenHistoricCtrl',
        templateUrl: 'kanban/openHistoricTask.tpl.html',

        data: { pageTitle: 'Abrir Tarefa', requiresLogin: true }
    });
}]);


app.controller('OpenHistoricCtrl', ['$stateParams', '$timeout', '$rootScope', '$http', '$state', '$scope', 'KanbanService', '$mdMedia', 'FiltersService',

    function OpenHistoricController($stateParams, $timeout, $rootScope, $http, $state, $scope, KanbanService, $mdMedia, FiltersService) {

        $scope.service = KanbanService;
        $scope.filterService = FiltersService;

        if ($state.current.name === 'in.openHistoricTask') {
            var taskId = $stateParams.id;

            $scope.service.historicTask(taskId).then(function () {

                for (var i = 0; i < $scope.service.selectedTask.formItens.length; i++) {
                    if ($scope.service.selectedTask.formItens[i].type === 'CHECKLIST' && !angular.isUndefined($scope.service.selectedTask.formItens[i].value) && $scope.service.selectedTask.formItens[i].value !== null) {
                       $scope.service.selectedTask.formItens[i].value = JSON.parse($scope.service.selectedTask.formItens[i].value);
                    }
                    
                    if($scope.service.selectedTask.formItens[i].lazy){
                        for(var j = 0;  j  < $scope.service.selectedTask.headers.length; j++) {
                            if ($scope.service.selectedTask.headers[j].id === $scope.service.selectedTask.formItens[i].listening) {
                                comboBoxDTO = {
                                    value: $scope.service.selectedTask.headers[j].value,
                                    dataSourceName: $scope.service.selectedTask.formItens[i].dataSourceName
                                };
                                $scope.findLazy(comboBoxDTO,$scope.service.selectedTask.formItens[i]);
                            }
                        }
                    }

                    if ($scope.service.selectedTask.formItens[i].type === 'DATAGRID'){

                        for(var gridPosition = 0; gridPosition < $scope.service.selectedTask.formItens[i].gridValues.lines.length; gridPosition ++){
                            for(var valuesPosition = 0; valuesPosition < $scope.service.selectedTask.formItens[i].gridValues.lines[gridPosition].values.length;  valuesPosition++){
                                if($scope.service.selectedTask.formItens[i].gridValues.lines[gridPosition].values[valuesPosition].metadata !== null){
                                    var metadata = $scope.service.selectedTask.formItens[i].gridValues.lines[gridPosition].values[valuesPosition].metadata;

                                    if(metadata.lazy){
                                        for(var header = 0;  header  < $scope.service.selectedTask.headers.length; header++) {
                                            if ($scope.service.selectedTask.headers[header].id === metadata.listening) {
                                                comboBoxDTO = {
                                                    value: $scope.service.selectedTask.headers[header].value,
                                                    dataSourceName: metadata.beanName
                                                };
                                                $scope.findLazy(comboBoxDTO,$scope.service.selectedTask.formItens[i].dataGridDTO.values[gridPosition]);
                                            }
                                        }
                                    }
                                }
                            }
                        }                        
                    }
                }


                $timeout(function () { $scope.messageFeed = ''; }, 3000);
            });
        }

        $scope.findLazy = function(comboBoxDTO, formItens){

            $scope.filterService.getFilter(comboBoxDTO).then(function () {
                formItens.comboBoxPossibleValues = $scope.filterService.result;
            });
        };

        $scope.goBack = function () {
            $scope.service.selectedTask = '';
            window.history.back();
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

    }
]);