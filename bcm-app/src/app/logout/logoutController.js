app = angular.module('bcm.logout', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {
    $stateProvider.state('out.logout', {
        url: '/logout',
        controller: 'LogoutCtrl'
    });
}]);

app.controller('LogoutCtrl', ['$scope', 'LogoutService',
    function LoginController($scope, LogoutService) {      
        $scope.logoutService = LogoutService;
        $scope.logoutService.redirectRubeus();
    }]);