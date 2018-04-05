app = angular.module('bcm.appinfo.appinfo-service', [
]);

app.factory("UserApplications", ['$resource', function ($resource) {
    return $resource(API + '/userApplications/:username/', { uid: '@uid' }, {
        list: {
            method: 'GET',
            isArray: true
        }
    });
}]);

app.factory("ListApplications", ['$resource', function ($resource) {
    return $resource(API + '/applications/', { uid: '@uid' }, {
        list: {
            method: 'GET',
            isArray: true
        }
    });
}]);

app.factory("ApplicationConfigurations", ['$resource', function ($resource) {
    return $resource(API + '/applications/:applicationKey/configurations', { uid: '@uid' }, {
       list: {
            method: 'GET',
            isArray: true
        }
    });
}]);

/**
 * AppInfoService 
 */
app.service('AppInfoService', ['$http', '$rootScope', '$state', '$q', 'UserApplications','ListApplications', 'ApplicationConfigurations', 
    function ($http, $rootScope, $state, $q, UserApplications, ListApplications, ApplicationConfigurations) {
    var self = {
        'userApplications': function (user) {
            var d = $q.defer();
            UserApplications.list({ 'username': user }, function (data) {
                d.resolve(data);
            }, function (e) {
                $rootScope.toastError(e, 'Erro ao consultar das apps do usuario.');
            });
            return d.promise;
        },
         'listAplications': function () {
            var d = $q.defer();
            ListApplications.list(function (data) {
                d.resolve(data);
            }, function (e) {
                $rootScope.toastError(e, 'Erro ao efetuar a consulta das aplicacões.');
            });
            return d.promise;
        },
         'listAplicationConfigurations': function (applicationKey) {
             var params = {
                'applicationKey': applicationKey
            };
            var d = $q.defer();
            ApplicationConfigurations.list(params, function (data) {
                d.resolve(data);
            }, function (e) {
                $rootScope.toastError(e, 'Erro ao efetuar a consulta das configurações de aplicacões.');
            });
            return d.promise;
        }
        
    };

    return self;

}]);