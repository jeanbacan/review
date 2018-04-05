app = angular.module('bcm.process', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {


    $stateProvider.state('in.processList', {
        url: '/processList',
        controller: 'ProcessCtrl',
        templateUrl: 'process/process-list.tpl.html',
        data: {pageTitle: 'Lista de Processos', requiresLogin: true}
    });


}]);

app.controller('ProcessCtrl', ['$q', '$rootScope', '$scope', '$location', 'ProcessService', '$mdDialog', '$mdMedia',

    function ProcessController($q, $rootScope, $scope, $location, ProcessService, $mdDialog, $mdMedia) {

        $scope.service = ProcessService;
        $scope.service.getProcessList($scope.filterDTO);

        $scope.newProcess = function (ev, atualiza) {

            $scope.atualiza = atualiza;


               var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
               $mdDialog.show({
                   controller: ProcessFormController,
                   templateUrl: 'process/modalUploadProcess.tpl.html',
                   parent: angular.element(document.body),
                   targetEvent: ev,
                   clickOutsideToClose: false,
                   fullscreen: true,
                   scope: $scope,
                   preserveScope: true
               })
                   .then(function (answer) {
                       $mdDialog.hide();
                       $scope.status = 'You said the information was "' + answer + '".';
                   }, function () {
                       $mdDialog.hide();
                       $scope.status = 'You cancelled the dialog.';
                   });
               $scope.$watch(function () {
                   return $mdMedia('xs') || $mdMedia('sm');
               }, function (wantsFullScreen) {
                   $scope.customFullscreen = (wantsFullScreen === true);
               });


        };



    }

]);


function ProcessFormController($scope, $rootScope, $mdDialog, ProcessService, $location) {
    $scope.BPMNName = '';

    $scope.close = function () {
        $mdDialog.hide();

    };



    $scope.importFile = function (form) {

        if (form.$valid) {
            if (angular.isDefined($scope.uploadedFile) && $scope.uploadedFile != null) {

                // exibe o overlay
                angular.element(document.getElementById('overlay')).css('display', 'block');


                $scope.service.upload($scope.uploadedFile, $scope.uploadedFile.name).then(function () {
                    $mdDialog.hide();
                    angular.element(document.getElementById('overlay')).css('display', 'none');
                }, function() {
                     //esconde o overlay
                     angular.element(document.getElementById('overlay')).css('display', 'none');
                });

            } else {
                    $rootScope.toastError(null, "Selecione o arquivo a ser importado.");
            }
        }

    };


}