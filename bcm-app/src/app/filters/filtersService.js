app = angular.module('bcm.filters.filters-service', [
    'ui.router'
]);




/**
 * Call backend service to CRUD Filter
 */
app.factory("LoadUFs",['$resource', function ($resource) {
    return $resource(API + '/',  {uid: '@uid'}, {
        loadUFs:{
            method: 'GET',
            url: (API + '/combo'),
            isArray : true
        }

    });
}]);


app.factory("getListByComboBoxDTO", function ($http) {
    return {
        getList: function (comboBoxDTO) {
            return $http.post(API + '/combo', comboBoxDTO, {
                params: {comboBoxDTO: comboBoxDTO}
            });
        }
    };
});

app.factory("TaskComboBox",['$resource', function ($resource) {
    return $resource(API + '/',  {uid: '@uid'}, {
        findAll:{
            method: 'GET',
            url: (API + '/combo/progress'),
            isArray : true
        }

    });
}]);

/**
 * Filters service
 */
app.service('FiltersService', ['$q', '$log' , '$rootScope', 'LoadUFs', 'getListByComboBoxDTO', 'TaskComboBox',
    function ($q, $log, $rootScope, LoadUFs, getListByComboBoxDTO, TaskComboBox) {
        var self = {                   
            'filterResult': [],
            'ufs' :[],
            'tarefas' :[],
            'cidade' :[],
            'diretorias' :[],
            'result' : [],

            'loadTarefas'  : function() {
                self.groupsInvestimentList =[];
                var d = $q.defer();
                TaskComboBox.findAll({}).$promise.then(function (data) {
                   self.tarefas = data;
                    d.resolve();
                }, function (reason) {
                    console.log(JSON.stringify(reason));
                    $rootScope.toastError(reason, 'Falha ao obter lista de Tarefas.');
                });

                return d.promise;
            },
            'loadUFs'  : function() {
                self.groupsInvestimentList =[];
                var d = $q.defer();
                LoadUFs.loadUFs({}).$promise.then(function (data) {
                   self.ufs = data;
                    d.resolve();
                }, function (reason) {
                    console.log(JSON.stringify(reason));
                    $rootScope.toastError(reason, 'Falha ao obter lista UFs.');
                });

                return d.promise;
            },

            'getFilter'  : function(comboBoxDTO) {
                self.groupsInvestimentList =[];
                var d = $q.defer();
                    getListByComboBoxDTO.getList(comboBoxDTO).then(function (result) {
                        self.result = result.data;
                        d.resolve();
                    }, function (reason) {
                        console.log(JSON.stringify(reason));
                        $rootScope.toastError(reason, 'Falha ao obter lista Processos.');
                    });
                return d.promise;
            }
        };
        
        return self;
    }
]);