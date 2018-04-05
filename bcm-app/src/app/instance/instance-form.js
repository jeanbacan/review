app = angular.module('bcm.instance-form', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {

    $stateProvider.state('in.instanceForm', {
        url: '/instance',
        controller: 'InstanceCtrl',
        templateUrl: 'instance/instance-form.tpl.html',
        data: { pageTitle: 'Nova Instância', requiresLogin: true }
    });
}]);


/**
 * Utilizado pelo Form para criar nova instancia.
 */
app.controller('InstanceCtrl', ['$q', '$rootScope', '$scope', '$location', 'InstanceService', '$mdDialog', '$mdMedia', '$state', 'FiltersService',

    function InstanceController($q, $rootScope, $scope, $location, InstanceService, $mdDialog, $mdMedia, $state, FiltersService) {

        $scope.service = InstanceService;
        $scope.filterService = FiltersService;
        $scope.service.getProcessInstanceStartDTO();
        $scope.businessKey = null;

        $scope.listening = function (formIten) {
            var fileList = formIten;
            var islazy = false;
            angular.forEach($scope.service.processInstanceStartDTO.formItems, function (field) {

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
                    angular.forEach($scope.service.processInstanceStartDTO.formItems, function (field) {
                        if (field.listening === formIten.id) {
                            field.comboBoxPossibleValues =  $scope.filterService.result;
                        }                            
                    });

                    $timeout(function () { $scope.messageFeed = ''; }, 3000);

                });
            }
        };

        $scope.goBack = function () {
            window.history.back();
        };

        $scope.openModalCreateInstance = function (instanceForm) {
            var isUndefined = false;

            angular.forEach($scope.service.processInstanceStartDTO.formItems, function (field) {
                if (field.required && field.value === undefined) {
                    sUndefined = true;
                }
            });

            if (!isUndefined) {
                if (instanceForm.$valid) {
                    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
                    $mdDialog.show({
                        controller: InstanceFormController,
                        templateUrl: 'instance/modalConfirmCreateInstance.tpl.html',
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

                }
            }
        };



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
                $state.go('in.instanceList');
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
