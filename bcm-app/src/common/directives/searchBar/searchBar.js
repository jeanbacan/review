angular
.module('searchBarComponent', [])
.component('searchBar', {
    templateUrl : 'directives/searchBar/searchBar.tpl.html',
    styleUrls: ['directives/searchBar/searchBar.tpl.html'],
    bindings: {
        searchFunction: '&'
    },    
    controller: SearchFiltersComponent
});

function SearchFiltersComponent(KanbanService, FiltersService, $element, VariablesSearchBarService) {

    var ctrl = this;

    ctrl.kanbanService = KanbanService;        
    ctrl.filterService = FiltersService;

    ctrl.state = VariablesSearchBarService.loadState();
    ctrl.filterDTO = ctrl.kanbanService.filterDTO;

    //se tiver filtro da tela Kanban, inicializa sem filtros para tela esteira
    if(ctrl.state == "in.esteira" && ctrl.kanbanService.filterDTO){
        ctrl.filterDTO = null;
    }
        
    ctrl.variablesService = VariablesSearchBarService.loadVariables(ctrl.filterDTO);

    ctrl.ufs = [];
    ctrl.cidades = [];
    ctrl.diretorias = [];
    ctrl.tarefas = [];
    
    ctrl.changeDiretoria = function(){

        if (ctrl.variablesService.selectedDiretoria === 'Todos'){
            return;
        }

        comboBoxDTO = {
            value: ctrl.variablesService.selectedDiretoria,
            dataSourceName: 'listUFDSBusinessOperation'
        };

        ctrl.variablesService.selectedUF = null;
        ctrl.variablesService.selectedCidade = null;
        ctrl.cidades = null;

        ctrl.filterService.getFilter(comboBoxDTO).then(function () {            
            ctrl.ufs = ctrl.filterService.result;
        });
    };

    ctrl.changeUF = function(){

        if (ctrl.variablesService.selectedUF === 'Todos'){
            ctrl.variablesService.selectedCidade = null;
            ctrl.cidades = null;
            return;
        }

        comboBoxDTO = {
            value: ctrl.variablesService.selectedUF,
            dataSourceName: 'listCidadeDSBusinessOperation'
        };

        ctrl.variablesService.selectedCidade = null;
        ctrl.cidades = null;
       

        ctrl.filterService.getFilter(comboBoxDTO).then(function () {            
            ctrl.cidades = ctrl.filterService.result;
        });
    };

    ctrl.loadDiretorias = function(){
        
        comboBoxDTO = {
            dataSourceName: 'listDiretoriaDSBusinessOperation'
        };

        ctrl.filterService.getFilter(comboBoxDTO).then(function () {            
            ctrl.diretorias = ctrl.filterService.result;
        });
    };

    ctrl.loadTarefas = function(){
        
        ctrl.filterService.loadTarefas().then(function () {            
            ctrl.tarefas = ctrl.filterService.tarefas;
        });
    };

    ctrl.cleanFilter = function(){
         ctrl.variablesService = VariablesSearchBarService.loadVariables(null);
         ctrl.sendFilter();
       
    };
   
    ctrl.sendFilter = function () {
        
        filterDTO = {
            constraints: {},
            sortType: 'DESC'
        };

        if (!angular.isUndefined(ctrl.variablesService.cbTarefas) && ctrl.variablesService.cbTarefas !== 'Todas') {
            filterDTO.constraints.FILTER_TASK_NAME = {
                "value": ctrl.variablesService.cbTarefas,
                "type": "EQUAL"
            };
        }

        if (!angular.isUndefined(ctrl.variablesService.selectedDiretoria) && ctrl.variablesService.selectedDiretoria !== 'Todos') {
            filterDTO.constraints.FILTER_DIRETORIA = {
                "value": ctrl.variablesService.selectedDiretoria,
                "type": "EQUAL"
            };
        }

        if (!angular.isUndefined(ctrl.variablesService.selectedCidade) && ctrl.variablesService.selectedCidade !== 'Todos') {
            filterDTO.constraints.FILTER_CIDADE = {
                "value": ctrl.variablesService.selectedCidade,
                "type": "EQUAL"
            };
        }
        
        if (!angular.isUndefined(ctrl.variablesService.selectedUF) && ctrl.variablesService.selectedUF !== 'Todos') {
            filterDTO.constraints.FILTER_UF = {
                "value": ctrl.variablesService.selectedUF,
                "type": "EQUAL"
            };
        }

        if (!angular.isUndefined(ctrl.variablesService.cbTipoFluxo) && ctrl.variablesService.cbTipoFluxo !== 'Todos') {
            filterDTO.constraints.FILTER_FLUXO = {
                "value": ctrl.variablesService.cbTipoFluxo,
                "type": "EQUAL"
            };
        }                

        if (!angular.isUndefined(ctrl.variablesService.cbTipoObra) && ctrl.variablesService.cbTipoObra !== 'Todos') {
            filterDTO.constraints.FILTER_OBRA = {
                "value": ctrl.variablesService.cbTipoObra,
                "type": "EQUAL"
            };
        }
        
        if (!angular.isUndefined(ctrl.variablesService.cbTipoTecnlogia) && ctrl.variablesService.cbTipoTecnlogia !== 'Todos') {
            filterDTO.constraints.FILTER_TECNOLOGIA = {
                "value": ctrl.variablesService.cbTipoTecnlogia,
                "type": "EQUAL"
            };
        }
        if (!angular.isUndefined(ctrl.variablesService.cbGrupoOwner) && ctrl.variablesService.cbGrupoOwner !== 'Todos') {
            filterDTO.constraints.FILTER_OWNER_GROUP = {
                "value": ctrl.variablesService.cbGrupoOwner,
                "type": "EQUAL"
            };
        }

        if (!angular.isUndefined(ctrl.variablesService.businessKey) && ctrl.variablesService.businessKey !== '' && ctrl.variablesService.businessKey.length > 3) {
            filterDTO.constraints.FILTER_BUSINESSKEY = {
                "value": ctrl.variablesService.businessKey,
                "type": "LIKE_IGNORECASE"
            };
        }
        
        if (!angular.isUndefined(ctrl.variablesService.ownerName) && ctrl.variablesService.ownerName !== '' && ctrl.variablesService.ownerName.length > 3) {
            filterDTO.constraints.FILTER_OWNER = {
                "value": ctrl.variablesService.ownerName,
                "type": "LIKE_IGNORECASE"
            };
        }
    
        ctrl.searchFunction({filterDTO : filterDTO});
    };

    if(ctrl.variablesService.selectedDiretoria != "Todos"){
        if (ctrl.variablesService.selectedDiretoria === 'Todos'){
            return;
        }

        comboBoxDTO = {
            value: ctrl.variablesService.selectedDiretoria,
            dataSourceName: 'listUFDSBusinessOperation'
        };

        ctrl.filterService.getFilter(comboBoxDTO).then(function () {            
            ctrl.ufs = ctrl.filterService.result;
        });
    }

    if(ctrl.variablesService.selectedUf != "Todos"){
        var selectedCidade = ctrl.variablesService.selectedCidade;
        var cidade = ctrl.cidades;

        ctrl.changeUF();

        ctrl.variablesService.selectedCidade = selectedCidade;
        ctrl.cidades = cidade;
    }

    //Load de filtros
    ctrl.loadDiretorias();
    ctrl.loadTarefas();
}
