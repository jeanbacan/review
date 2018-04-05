app = angular.module('bcm.project.report-service', [
    'ui.router'
]);

app.factory('GenerateReport', function ($resource) {

    var url = API + '/report';

    return $resource(url, {}, {
        download: {params: {}, method: 'POST'}
    });
});