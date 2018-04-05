angular.module('stringToNumber', [])

.run(function($rootScope) {
  $rootScope.typeOf = function(value) {
    return typeof value;
  };
})

.directive('stringToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(value) {
        return '' + (value == null ? '' : value);
      });
      ngModel.$formatters.push(function(value) {
        return parseFloat(value);
      });
    }
  };
});