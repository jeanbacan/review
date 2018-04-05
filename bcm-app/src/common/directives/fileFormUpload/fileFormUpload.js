var app = angular.module('fileFormUpload', []);

var templateFileFormUpload = '<input id="fileFormUploadInput" ng-required="isRequired" type="file" class="ng-hide">' +
    '<md-button style="min-height: 0px; min-width: 15px; line-height: 17px;" id="uploaFormdButton" aria-label="selecionar">' +
     '<ng-md-icon icon="file_upload" style="fill: #757575; margin-right: 20px;" ng-click="goBack()">'+
                        '<md-tooltip>Voltar</md-tooltip>'+
                    '</ng-md-icon>'+

    '</md-button>' +
    '<md-input-container md-no-float>' +
    '<input id="textFormUploadInput" style="width: 315px;" ng-required="isRequired" ng-model="fileName" type="text" placeholder="{{ placeholder }}" ng-readonly="true"></md-input-container>' ;

app.directive('fileFormUpload', function () {
    return {
        restrict: 'EA',
        template: templateFileFormUpload,
        scope: {
            buttonLabel: '@buttonLabel',
            placeholder: '@placeholder',
            isRequired: '@isRequired'
        },
        link: fileFormUpload
    };
});

function fileFormUpload(scope, element, attrs) {
    var input = $(element[0].querySelector('#fileFormUploadInput'));
    var button = $(element[0].querySelector('#uploaFormdButton'));
    var textInput = $(element[0].querySelector('#textFormUploadInput'));

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
                scope.fileName = fileName;
                scope.$parent.uploadedFormFile = files[0];
        } else {

            scope.fileName = null;
            scope.$parent.uploadedFormFile = null;
        }
        scope.$apply();
    });
}