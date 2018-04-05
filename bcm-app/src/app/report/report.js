app = angular.module('bcm.report', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {


    $stateProvider.state('in.report', {
        url: '/report',
        controller: 'ReportCtrl',
        templateUrl: 'report/report.tpl.html',
        data: {pageTitle: 'Relatórios', requiresLogin: true}
    });


}]);

app.controller('ReportCtrl', ['$q', '$rootScope', '$scope', '$location', '$mdDialog', '$mdMedia', 'FiltersService','GenerateReport',

    function ReportController($q, $rootScope, $scope, $location, $mdDialog, $mdMedia, FiltersService, GenerateReport) {

        $scope.filterService = FiltersService;

        $scope.filterService.loadUFs().then(function () {

        });

        $scope.sendFilter = function(selectedUF,selectedStatus){

            filterDTO = {
                constraints: {}
            };

            filterDTO.constraints = {
                "FILTER_SELECTED_UF": {
                    "value": selectedUF,
                    "type": "EQUAL"
                },
                "FILTER_PROCESS_STATUS": {
                    "value": selectedStatus,
                    "type": "EQUAL"
                }
            };



            var d = $q.defer();

            GenerateReport.download(filterDTO, function (result) {
                var data = '\ufeff' + result.entity;
                var blob = new Blob([data], {type: 'text/csv', charset: 'utf-8'});

                if (navigator.appVersion.toString().indexOf('.NET') > 0) {
                    window.navigator.msSaveBlob(blob, 'report.csv');
                } else {
                    var urlBlob = window.URL.createObjectURL(blob);
                    var link = document.createElement("a");
                    link.setAttribute("href", urlBlob);
                    link.setAttribute("download", 'reportParcial.csv');
                    var event = document.createEvent('MouseEvents');
                    event.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
                    link.dispatchEvent(event);
                }

                d.resolve();
            }, function (e) {
                d.reject(e);
            });
        };





    }

]);

