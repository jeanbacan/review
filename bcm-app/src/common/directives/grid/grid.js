angular
.module('gridDirective', [])
.component('grid', {
    templateUrl : 'directives/grid/grid.tpl.html',
    bindings: {
        formItem: '<'
    },
    
    controller: MyGridComponent
})
.directive('upChange', UploadChangeDirective);

function MyGridComponent(KanbanService, $element) {

    var ctrl = this;
    ctrl.kanbanService = KanbanService;
    var task = ctrl.kanbanService.selectedTask;
    ctrl.task = task;
    
    ctrl.searchTerm = '';

    ctrl.addRow = function () {
        ctrl.formItem.gridValues.lines.push(angular.copy(ctrl.formItem.gridDefaultValues.lines[0]));
    };

    ctrl.removeRow = function (index, field) {        
        ctrl.formItem.gridValues.lines.splice(index, 1);
    };

    ctrl.stopPropagation = function (ev) {
        ev.stopPropagation();
    };

    ctrl.deleteGridFile = function (cel) {
        cel.myDocumentVO = null;
    };

    /** FUNCOES COMBO SEARCH */
    ctrl.clearSearchTerm = function() {
        ctrl.searchTerm = '';
    };

    ctrl.downloadFile = function (myDocumentId) {
        generateTokenDTO = {
            documentVO: {
                uid: myDocumentId,
                uploaded: false
            },
            accessType: 'DOWNLOAD'
        };
        ctrl.kanbanService.getTokenDownload(generateTokenDTO);
    };

    ctrl.uploadShowFile = function (event, column) {        

        var file = event.currentTarget;
        var fileList = document.getElementById(file.id).files;            
        var imageData = fileList[0];

        var businessKey = ctrl.task.businessKey;
        var fieldsNames = ctrl.formItem.dataGridDTO.values;
        var fileName = "";

       if (column.key === "LotFace" || column.key === "LoteFace" || column.key === "Arquivo") {
          fileName = column.key;
       }
        
        generateTokenDTO = {
            documentVO: {
                name: imageData.name,
                businessKey: businessKey,
                fileName: fileName,
                uploaded: false
            },
            accessType: 'UPLOAD'
        };

         generateTokenDTO = generateTokenDTO;

        //Monta documentoVO com retorno na coluna de upload
        ctrl.kanbanService.getToken(generateTokenDTO, imageData).then(function () {        
            column.value = ctrl.kanbanService.myDocumentId;
            column.myDocumentVO = {
                uid: ctrl.kanbanService.myDocumentId,
                name: ctrl.kanbanService.newNameFile
            };
        });
    };

}

function UploadChangeDirective() {
    return {
        scope: {
            upChange: "&"
        },
        link: function ($scope, $element, $attrs) {
            $element.on("change", function (event) {
                $scope.upChange({ $event: event });
            });
            $scope.$on("$destroy", function () {
                $element.off();
            });
        }
    };
}