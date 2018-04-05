app = angular.module('bcm.project.esteira-service', [
    'ui.router'
]);


app.factory("Esteira",['$resource', function ($resource) {
    return $resource(API + '/task/countByCandidateGroup',  {uid: '@uid'}, {
        getCount:{
            method: 'POST',
            url: (API + '/count/task')
        },
        getEsteiraCountGroups:{
            method: 'POST',
            url: (API + '/count/groups')
        },
        sendMail:{
            method: 'POST',
            url: (API + '/notify')
        }
    });
}]);


/**
 * Group service
 */
app.service('EsteiraService', ['$http', '$state','toaster','Esteira', '$q', '$rootScope', function ($http, $state,toaster,Esteira, $q, $rootScope) {
    var self = {
        'offset': 0,
        'countTasksDTO' : {},
        'ufs' : [],
        'isLoading': false,

        'sendMail': function() {
            Esteira.sendMail({}, function () {
            }, function (reason) {

            });
        },
        'getTotalGroupedAmount': function(filterDTO) {

            var d = $q.defer();
            
            if (!self.isLoading) {
               self.isLoading = true;
            }

            if (angular.isUndefined(filterDTO)){
                filterDTO = {};
            }

            Esteira.getCount({},filterDTO).$promise.then(function (countTasksDTO) {
                self.isLoading = false;
                self.countTasksDTO = countTasksDTO;
                d.resolve(countTasksDTO);
            }, function (reason) {
                self.isLoading = false;
                $rootScope.toastError(reason, 'Falha ao calcular total.');
                d.reject(reason);
            });
            return d.promise;
        },
        'getEsteiraCountGroups': function(filterDTO) {

            var d = $q.defer();
            
            if (!self.isLoading) {
               self.isLoading = true;
            }

            if (angular.isUndefined(filterDTO)){
                filterDTO = {};
            }

            Esteira.getEsteiraCountGroups({},filterDTO).$promise.then(function (result) {
                self.isLoading = false;
                d.resolve(result);
            }, function (reason) {
                self.isLoading = false;
                $rootScope.toastError(reason, 'Falha ao calcular total.');
                d.reject(reason);
            });
            return d.promise;
        }
    };
    return self;

}]);