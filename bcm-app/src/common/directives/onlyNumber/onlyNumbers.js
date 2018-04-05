angular.module('onlyNumbers', [])

.run(function($rootScope) {
  $rootScope.typeOf = function(value) {
    return typeof value;
  };
})

.directive('onlyNumbers', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind("keydown", function(event) {
                if(event.shiftKey){event.preventDefault(); return false;}
                //console.log(event.which);
                if ([8, 13, 27, 35,36, 37, 38, 39, 40, 46].indexOf(event.which) > -1) {
                    // backspace, home, end, enter, escape, arrows, delete
                    return true;
                } else if (event.which >= 48 && event.which <= 57) {
                    // numbers 0 to 9
                    return true;
                } else if (event.which >= 96 && event.which <= 105) {
                    // numpad number
                    return true;
                } 
                // else if ([110, 190].indexOf(event.which) > -1) {
                //     // dot and numpad dot
                //     return true;
                // }
                else {
                    event.preventDefault();
                    return false;
                }
            });
        }
    };
});