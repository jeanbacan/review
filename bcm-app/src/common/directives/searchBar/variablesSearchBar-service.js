app = angular.module('bcm.searchBar.variables-service', [
    'ui.router'
]);


app.service('VariablesSearchBarService', ['$state', 
function ($state) {

        listVariables = {
            cbTipoObra: "Todos",
            ownerName: "",
            cbGrupoOwner: "Todos",
            businessKey: "",
            cbTarefas: "Todas",
            cbTipoTecnlogia: "Todos",
            cbTipoFluxo: "Todos",
            selectedDiretoria: "Todos",
            selectedUF: "Todos",
            selectedCidade: "Todos"
        };

        var self = {

            'loadVariables': function (filterDTO) {

                listVariables.businessKey = "";
                listVariables.cbGrupoOwner = "Todos";
                listVariables.ownerName = "";
                listVariables.cbTipoObra = "Todos";
                listVariables.cbTarefas = "Todas";
                listVariables.cbTipoTecnlogia = "Todos";
                listVariables.cbTipoFluxo = "Todos";
                listVariables.selectedDiretoria = "Todos";
                listVariables.selectedUF = "Todos";
                listVariables.selectedCidade = "Todos";

                if (filterDTO) {
                    listVariables.businessKey       = typeof filterDTO.constraints.FILTER_BUSINESSKEY != "undefined" ? filterDTO.constraints.FILTER_BUSINESSKEY.value : listVariables.businessKey;
                    listVariables.cbGrupoOwner      = typeof filterDTO.constraints.FILTER_OWNER_GROUP != "undefined" ? filterDTO.constraints.FILTER_OWNER_GROUP.value : listVariables.cbGrupoOwner;
                    listVariables.ownerName         = typeof filterDTO.constraints.FILTER_OWNER != "undefined" ? filterDTO.constraints.FILTER_OWNER.value : listVariables.ownerName;
                    listVariables.cbTipoObra        = typeof filterDTO.constraints.FILTER_OBRA != "undefined" ? filterDTO.constraints.FILTER_OBRA.value : listVariables.cbTipoObra;
                    listVariables.cbTarefas         = typeof filterDTO.constraints.FILTER_TASK_NAME != "undefined" ? filterDTO.constraints.FILTER_TASK_NAME.value : listVariables.cbTarefas;
                    listVariables.cbTipoTecnlogia   = typeof filterDTO.constraints.FILTER_TECNOLOGIA != "undefined" ? filterDTO.constraints.FILTER_TECNOLOGIA.value : listVariables.cbTipoTecnlogia;
                    listVariables.cbTipoFluxo       = typeof filterDTO.constraints.FILTER_FLUXO != "undefined" ? filterDTO.constraints.FILTER_FLUXO.value : listVariables.cbTipoFluxo;
                    listVariables.selectedDiretoria = typeof filterDTO.constraints.FILTER_DIRETORIA != "undefined" ? filterDTO.constraints.FILTER_DIRETORIA.value : listVariables.selectedDiretoria;
                    listVariables.selectedUF        = typeof filterDTO.constraints.FILTER_UF != "undefined" ? filterDTO.constraints.FILTER_UF.value : listVariables.selectedUF;
                    listVariables.selectedCidade    = typeof filterDTO.constraints.FILTER_CIDADE != "undefined" ? filterDTO.constraints.FILTER_CIDADE.value : listVariables.selectedCidade;
                }

                return listVariables;
            },
             //carrega state atual
             'loadState': function () {
                  return $state.current.name;
             }

        };

        return self;
    }]);