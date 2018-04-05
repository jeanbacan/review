app = angular.module('bcm.logout.logout-service', [
    'ui.router'
]);

app.factory("RedirectToLogin", ['$resource', function ($resource) {
    return $resource(API + '/redirectToLogin/', {}, {
        doRedirect: {
            method: 'GET'
        }
    });
}]);

app.factory("Logout", ['$resource', function ($resource) {
    return $resource(API + '/logout/', { }, {
        doLogout: {
            method: 'POST'
        }
    });
}]);

app.service('LogoutService', ['RedirectToLogin', 'Logout', '$q', '$rootScope', function (RedirectToLogin, Logout, $q, $rootScope) {
    var self = {
        'redirectRubeus': function () {
            var d = $q.defer();
            self.isUpdating = true;
            RedirectToLogin.doRedirect(function (data) {                
                self.isUpdating = false;
                window.location.href = data.entity;
                d.resolve();
            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao fazer redirect para o Rubeus.');
                self.isUpdating = false;
                d.reject();
            });
            
            return d.promise;
        },
        'logout': function (loginToken) {
            var params = {
                'loginToken' : loginToken
            };
            var d = $q.defer();
            self.isUpdating = true;
            Logout.doLogout(params, function (data) {
                self.isUpdating = false;
                window.location.href = data.entity;
                d.resolve();
            }, function (reason) {
                $rootScope.toastError(reason, 'Falha ao fazer logout no Rubeus.');
                self.isUpdating = false;
                d.reject();
            });            
            return d.promise;
        }

    };
    return self;
}]);