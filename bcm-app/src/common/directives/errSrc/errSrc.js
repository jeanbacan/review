var app = angular.module('errSrc', []);

//diretiva para mostrar "imagem não disponivel" quando não houver uma imagem para o equipamento
app.directive('errSrc', function() {
    return {
        link: function(scope, element, attrs) {
            scope.$watch(function() {
                return attrs['ngSrc'];
            }, function (value) {          
                if (!value) {
                    element.attr('src', attrs.errSrc);  
                }
            });

            element.bind('error', function() {
                element.attr('src', attrs.errSrc);
            });
        }
    };
});