var app = angular.module('appInfo', [
    'ngResource',
    'toastr'
]);

var template = '<md-menu md-offset="-220 75" target" ng-controller="AppInfoController">' +
                    '<md-button aria-label="Open demo menu" class="md-icon-button" ng-click="$mdMenu.open($event)">' +
                        '<ng-md-icon icon="apps" style="fill: white"></ng-md-icon>' +
                    '</md-button>' +
                    '<md-menu-content style="overflow-y:hidden" width="4" id="liApp">' +                        
                    '</md-menu-content>' +
                '</md-menu>';

app.directive('appInfo', function () {
    return {
        restrict: 'AE',
        template: template,
        replace: true
    };
});

app.controller('AppInfoController', ['$scope', '$rootScope', 'AppInfoService', 'toastr',
    function AppCtrl($scope, $rootScope, AppInfoService, toastr) {
        $scope.appsInfo = null;   
        
        if ($rootScope.me != null && angular.isDefined($rootScope.me)) {
            AppInfoService.userApplications($rootScope.me.username).then(function (resultado) {
                $scope.appsInfo = resultado;
                
                var count = 0;
                jQuery.each($scope.appsInfo, function (i, val) {
                    var nameAP = '';
                    var urlAPP = '';
                    jQuery.each(val, function (a, b) {
                        if (a === 'applicationCode') {
                            nameAPP = b;
                        }
                        if (a === 'applicationHomeUrl') { 
                            urlAPP = b;
                        }

                    });
                    
                    if (nameAPP !== APPLICATION_NAME) {
                        
                        var htmlTemp = '<md-menu-item style="padding:10px;">' +
                                            '<md-button>' +
                                                '<a href="'+urlAPP+'" target="_blank">' +
                                                    '<div layout="row">' +
                                                        '<img src="'+ RUBEUS_LOGO_APPS_DIR + nameAPP + '_logo_horiz.png" alt="" style="width: 190px; height: auto" />' +                                                    
                                                    '</div>' +
                                                '</a>' +
                                            '</md-button>' +
                                        '</md-menu-item>';                        
                        $('#liApp').append(htmlTemp);
                        count++;
                    }
                });

                if (count === 0) {
                    $('#menuNavApp').addClass("ng-hide");
                }

            }, function (reason) {
                $rootScope.toastError(reason, 'Erro ao efetuar login.');
            });
        }

        $rootScope.toastClear = function () {
            $mdToast.cancel();
            toastr.clear([toast]);
        };

        $rootScope.toastSuccess = function (message) {
            if (message != null) {
                toastr.success(message);
            }
        };

        $rootScope.toastError = function (error, message) {
            if (error != null && error.data != null && error.data.description != null) {
                var strMessage = error.data.description.trim();

                if (error.data.comments != null && error.data.comments.length > 0) {
                    strMessage = strMessage + ' ' + error.data.comments;
                }

                toastr.error(strMessage);
            } else {
                toastr.error(message);
            }
        };

    }]);