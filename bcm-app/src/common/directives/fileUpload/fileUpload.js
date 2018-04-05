var app = angular.module('fileUpload', []);

var templateFileUpload = '<input id="fileUploadInput" type="file" class="ng-hide">' +
    '<md-button id="uploadButton" class="md-raised md-primary" aria-label="selecionar arquivo">' +
    '{{ buttonLabel }}</md-button><md-input-container md-no-float>' +
    '<input id="textUploadInput" style="width: 250px;" ng-model="fileName" type="text" placeholder="{{ placeholder }}" ng-readonly="true"></md-input-container>';

app.directive('fileUpload', function () {
    return {
        restrict: 'EA',
        template: templateFileUpload,
        scope: {
            buttonLabel: '@buttonLabel',
            placeholder: '@placeholder',
            accept: '@accept',
            acceptErrorMsg: '@acceptErrorMsg'
        },
        link: fileUpload
    };
});

function fileUpload(scope, element, attrs) {
    var input = $(element[0].querySelector('#fileUploadInput'));
    var button = $(element[0].querySelector('#uploadButton'));
    var textInput = $(element[0].querySelector('#textUploadInput'));

    if (input.length && button.length && textInput.length) {
        button.click(function (e) {
            input.click();
        });
        textInput.click(function (e) {
            input.click();
        });
    }

    input.on('change', function (e) {
        var files = e.target.files;
        if (files[0]) {
            var fileName = files[0].name;
            var regex = new RegExp('(.*?)\\.(' + scope.accept + ')$');
            if (!regex.test(fileName)) {
                scope.fileName = null;
                scope.$parent.uploadedFile = null;
                scope.$root.toastError(null, scope.acceptErrorMsg);
            } else {
                scope.fileName = fileName;
                scope.$parent.uploadedFile = files[0];
            }
        } else {
            scope.fileName = null;
            scope.$parent.uploadedFile = null;
        }
        scope.$apply();
    });
}