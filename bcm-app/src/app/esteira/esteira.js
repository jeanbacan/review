app = angular.module('bcm.esteira', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {


    $stateProvider.state('in.esteira', {
        url: '/esteira',
        controller: 'EsteiraCtrl',
        templateUrl: 'esteira/esteira.tpl.html',
        data: {pageTitle: 'Visão geral', requiresLogin: true}
    });


}]);


app.config(['$stateProvider', function config($stateProvider) {

    $stateProvider.state('in.openBC', {
        url: '/openBC',
        controller: 'OpenBCCtrl',
        templateUrl: 'esteira/open-BC.tpl.html',
        data: {pageTitle: 'Abertura de BC', requiresLogin: true}
    });
}]);

app.controller('EsteiraCtrl', ['$state','$q', '$rootScope', '$scope', '$location', 'EsteiraService', '$mdDialog', '$mdMedia', 'FiltersService',

    function EsteiraController($state, $q, $rootScope, $scope, $location, EsteiraService, $mdDialog, $mdMedia, FiltersService) {
        
        $scope.EM_ANDAMENTO = 0;
        $scope.EM_ESTUDO = 1;
        $scope.EM_ANALISE = 2;
        $scope.APROVADOS = 3;
        $scope.TOTAL = 1;
        /*var REPROVADOS = 5;
        var CALENDARIZADOS = 6;
        var NAO_CALENDARIZADOS = 7;
        var OBRAS_CONCLUIDAS = 8;*/
        
        $scope.filterService = FiltersService;        
        $scope.service = EsteiraService;

        $scope.groupedAmounts = [];        

        $scope.refresh = function (filterDTO){

            $scope.service.getTotalGroupedAmount(filterDTO).then(function (result){
                $scope.groupedAmounts[$scope.TOTAL].amount = result;
    
                $scope.service.getEsteiraCountGroups(filterDTO).then(function (result){
                    $scope.groupedAmounts[$scope.EM_ANDAMENTO].amount = result.EM_ANDAMENTO;
                });
            });     
        };
   
        //Efetua primeira chamada
        $scope.refresh();
               
        $scope.groupedAmounts[$scope.TOTAL] = {
            name : 'Total',
        };
        $scope.groupedAmounts[$scope.EM_ANDAMENTO] = {
            name : 'Em Andamento',
        };
        /*$scope.groupedAmounts[$scope.EM_ESTUDO] = {
            name : 'Em Estudo',                        
        };
        $scope.groupedAmounts[$scope.EM_ANALISE] = {
            name : 'Em Análise',                        
        };
        $scope.groupedAmounts[$scope.APROVADOS] = {
            name : 'Aprovadas',            
        };*/

        $scope.newInstance = function(){
             $state.go('in.instanceForm');
        };
    }

]);


app.controller('OpenBCCtrl', ['$stateParams', '$state','$scope', 'EsteiraService','$mdExpansionPanel',

    function OpenBCController($stateParams, $state,$scope, EsteiraService, $mdExpansionPanel) {
        $scope.service = EsteiraService;


        $scope.goBack = function () {
           window.history.back();
        };

        if ($state.current.name === 'in.openBC') {
            var taskId = $stateParams.id;
        }

    }
]);
