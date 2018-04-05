app = angular.module('bcm.access.access-service', [
    'ui.router'
]);

/**
 * Access Service
 */
app.service("AccessService", ['$rootScope',
    function ($rootScope) {

        var self = {
            'getAccess': function () {
                return {
                    ADMIN: $rootScope.hasAuthorization('ADMIN')
                   /** equipments: $rootScope.hasAuthorization('VIEW_EQUIPMENTS_CPE'),
                    orders: $rootScope.hasAuthorization('VIEW_ORDERS_CPE'),
                    reports: $rootScope.hasAuthorization('VIEW_REPORTS_CPE')  **/
                };
            }
        };

        return self;
}]);