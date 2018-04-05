app = angular.module('bcm.project.process-service', [
    'ui.router'
]);


app.factory("Process",['$resource', function ($resource) {
    return $resource(API + '/processDefinition/:id/',  {uid: '@uid'}, {
        getProcessList:{
            method: 'POST',
            url: (API + '/processDefinition'),
            isArray: true
        }
    });
}]);





app.factory("BPMN", function ($http) {
    return {
        upload: function(imageData, label){
            return $http.post(API + '/processDefinition/uploadBPMN', imageData, {
                //headers: {'Content-Type': undefined },
                transformRequest: angular.identity,
                params: {label: label},
                headers: {
                    "Content-Type": "text/plain"
                }
            });
        }
    };
});



/**
 * Group service
 */
app.service('ProcessService', ['BPMN','$http', '$state','toaster','Process', '$q', '$rootScope', function (BPMN, $http, $state,toaster,Process, $q, $rootScope) {
    var self = {
        'offset': 0,
        'hasMore': true,
        'isLoading': false,
        'isSaving': false,
        'search': null,
        'project': null,
        'tasks' : new Map(),
        'processDefinitionList' : [],
        'filterList' : [],
        'filter':'',
        'labels':'',
        'pathBPMN':'',

        'getProcessList'  : function(filterDTO) {

            if (angular.isUndefined(filterDTO)){
                filterDTO = {};
            }
            self.processInstanceVOList = [];
            self.pathBPMN = '';
            Process.getProcessList(filterDTO, function (processDefinitionList) {
                self.processDefinitionList = processDefinitionList;

            }, function (reason) {
                alert('reason: ' + reason);
                console.log(JSON.stringify(reason));
                $rootScope.toastError(reason, 'Falha ao obter lista Processos.');
            });
        },
        'upload': function (imageData, label) {
            var d = $q.defer();
            
            if (!self.isLoading) {
                self.isLoading = true;
            }
            BPMN.upload(imageData, label).then(function () {
                $rootScope.toastSuccess('Upload realizado com Sucesso.');
                self.isLoading = false;
                self.getProcessList();
                d.resolve();
            }, function (reason) {
                self.isLoading = false;
                $rootScope.toastError(reason, 'Falha ao Fazer Upload do novo Processo.');

                self.getProcessList();
            });
            return d.promise;

        }
    };

    return self;

}]);