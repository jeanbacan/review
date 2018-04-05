app = angular.module('bcm.dashboard', [
    'ui.router'
]);

app.config(['$stateProvider', function config($stateProvider) {

    $stateProvider.state('in.kanban', {
        url: '/kanban',
        controller: 'KanbanCtrl',
        templateUrl: 'kanban/kanban.tpl.html',
        data: { pageTitle: 'Tarefas', requiresLogin: true }
    });


}]);

app.controller('KanbanCtrl', ['$q','$stateParams', '$timeout', '$rootScope', '$http', '$state', '$scope', '$location', 'KanbanService', '$mdDialog', '$mdMedia', 'moment', 'InstanceService',

    function KanbanController($q, $stateParams, $timeout, $rootScope, $http, $state, $scope, $location, KanbanService, $mdDialog, $mdMedia, moment, InstanceService) {

        $scope.taskComments = '';
        $scope.service = KanbanService;
        $scope.today = new Date();

        $scope.instanceService = InstanceService;

        $scope.showNew = function(createTime){
            var date = new Date(createTime);
            date.setDate(date.getDate() + 1);

            return date > new Date();
        };

        $scope.manageTask = function (task) {
            $scope.service.selectedTask = task;

            $scope.service.getUsersGroup(task.candidateGroup).then(function () {
            });

            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
            $mdDialog.show({
                controller: KanbanFormController,
                templateUrl: 'kanban/fwd-task-for-user.tpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: true,
                scope: $scope,
                preserveScope: true
            })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
            $scope.$watch(function () {
                return $mdMedia('xs') || $mdMedia('sm');
            }, function (wantsFullScreen) {
                $scope.customFullscreen = (wantsFullScreen === true);
            });
        };

        //Mantem filtros na tela
        var filterDTO;
        if($scope.service.filterDTO){
            filterDTO = $scope.service.filterDTO;
        }

        $scope.service.loadKanban(filterDTO);
        $scope.show = false;

        $scope.unassign = function (taskId) {
            if (!angular.isUndefined(taskId)) {
                $scope.service.unassign(taskId);
            } else {
                $scope.service.unassign($scope.service.selectedTask.taskId);
            }
        };

        $scope.downloadAllFiles = function(task){

            $scope.service.downloadAllFiles([task.processInstanceId]);
        };

        $scope.assignToMe = function (taskId) {
            $scope.service.assignToMe([taskId]);
            $state.go('in.kanban');
        };
        $scope.assignAllToMe = function () {

            var allTaskId = [];
            var pendingTasks = $scope.service.kanbanMap.PENDING;

            for (var i=0; i < pendingTasks.length; i++){
                allTaskId.push(pendingTasks[i].taskId);
            }

            $scope.service.assignToMe(allTaskId);
            $state.go('in.kanban');
        };

        $scope.newInstance = function () {

            $state.go('in.instanceForm');
        };
    }
]);


function KanbanFormController($rootScope, $scope, $mdDialog, $state) {

    $scope.completeTask = function () {
        var isNull = false;
        angular.forEach($scope.service.selectedTask.formItens, function (field, key) {
            if (field.required && angular.isUndefined(field.value)) {
                if(field.type === 'DOCUMENT' && angular.isUndefined(field.value) ){
                   $scope.documentRequired = true;
                }
                isNull = true;
            }

        });

        if (!isNull) {
            angular.forEach($scope.service.selectedTask.formItens, function (field, key) {
                if (field.type === 'DATE' && !angular.isUndefined(field.value)) {
                    var date = field.value;

                    var ano = date.getFullYear();
                    var mes = date.getMonth() + 1;
                    var dia = date.getDate();
                    if (mes < 10) {
                        mes = '0'.concat(mes.toString());
                    }
                    if (dia < 10) {
                        dia = '0'.concat(dia.toString());
                    }

                    var formatDate = dia + '/' + mes + '/' + ano;

                    field.value = formatDate;
                }
            });
            $scope.service.completeTask();
        } else {
            $rootScope.toastError('A tarefa não pode ser finalizada', 'Você deve preencher os campos obrigatórios');
            $mdDialog.hide();
        }
    };

    $scope.close = function () {
        $scope.service.assignee = '';
        $mdDialog.hide();

    };

    $scope.fowardTaskToUser = function (userId) {

        assignTaskToUserDTO = {
            taskId: '',
            userId: ''
        };

        assignTaskToUserDTO.taskId = $scope.service.selectedTask.taskId;
        assignTaskToUserDTO.userId = userId;
        $scope.service.saveTask(assignTaskToUserDTO);
        $mdDialog.hide();
    };

    $scope.createUserFilterFor = self.createUserFilterFor;
    $scope.selectedUserItem = self.selectedUserItem;
    $scope.selectedUserItemChange = self.selectedUserItemChange;

}
