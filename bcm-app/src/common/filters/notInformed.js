var app = angular.module('notInformed', []);

//filtro para mostrar como 'Não informado' quando o campo é null
app.filter('notInformed', function () {
    return function (value) {      
        if (angular.isUndefined(value)) {
            return 'Não informado';
        } else {
            return value;
        }
    };
});