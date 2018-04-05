
var principalUsernameClaim = 'principal-user-name';

app = angular.module('bcm', [
    'templates-app',
    'templates-common',

    'bcm.openTask',
    'stringToNumber',
    'onlyNumbers',

    'bcm.openHistoricTask',
    'bcm.instance-form',
    'bcm.instance-details',
    'bcm.report',
    'bcm.project.report-service',
    'bcm.appinfo.appinfo-service',

    'bcm.instance',
    'bcm.project.instance-service',

    'bcm.dashboard',
    'bcm.dashboard.kanban-service',

    'bcm.esteira',
    'bcm.project.esteira-service',

    'bcm.process',
    'bcm.project.process-service',

    'bcm.logout',
    'bcm.logout.logout-service',

    'bcm.filters.filters-service',
    'bcm.access.access-service',

    'angularMoment',
    'toaster',
    'ui.router',
    'ngResource',
    'toastr',    
    'ngAnimate',
    'angular-ladda',
    'jcs-autoValidate',
    'angular-jwt',
    'angular-storage',
    'ngSanitize',
    'ngMaterial',
    'ngMdIcons',
    'md.data.table',    
    'ngMaterialSidemenu',
    'zingchart-angularjs',
    'base64',
    'xdLocalStorage',
    'appInfo',
    'notInformed',
    'errSrc',
    'material.components.expansionPanels',
    'fileUpload',
    'fileFormUpload',
    'angularSpinner',
    'gridDirective',
    'searchBarComponent',

    'bcm.searchBar.variables-service'
]);

app.config(['$locationProvider', '$qProvider', '$stateProvider', '$urlRouterProvider', '$httpProvider', '$resourceProvider', 'jwtInterceptorProvider', 
'$mdThemingProvider','$compileProvider', '$mdDateLocaleProvider', '$mdInkRippleProvider',

    function myAppConfig($locationProvider, $qProvider, $stateProvider, $urlRouterProvider, $httpProvider, $resourceProvider, jwtInterceptorProvider, 
        $mdThemingProvider, $compileProvider, $mdDateLocaleProvider, $mdInkRippleProvider) {
        $qProvider.errorOnUnhandledRejections(false);



        /**
         * Configurações de calendário (pt-BR)
         */
        moment.locale("pt-br");

        //desabilita o efeito de ripple nos botões, pois alguns browser não conseguem reproduzir o efeito corretamente
        $mdInkRippleProvider.disableInkRipple();

        $mdDateLocaleProvider.months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];

        $mdDateLocaleProvider.shortMonths = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
        $mdDateLocaleProvider.days = ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'];
        $mdDateLocaleProvider.shortDays = ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'];

        // Can change week display to start on Monday.
        $mdDateLocaleProvider.firstDayOfWeek = 0;

        // Example uses moment.js to parse and format dates.
        $mdDateLocaleProvider.parseDate = function (dateString) {
            var m = moment(dateString, 'DD/MM/YYYY', true);
            return m.isValid() ? m.toDate() : new Date(NaN);
        };

        $mdDateLocaleProvider.formatDate = function(date) {
            return moment(date).format('DD/MM/YYYY');
        };  
        /*$mdDateLocaleProvider.formatDate = function (date) {
            var m = moment(date);
            return m.isValid() ? m.format('L') : '';
        };*/

        $locationProvider.html5Mode(false);
        $locationProvider.hashPrefix('');

        jwtInterceptorProvider.tokenGetter = function (store) {
            return store.get(appTokenKey);
        };

        $httpProvider.interceptors.push('jwtInterceptor');
        $httpProvider.interceptors.push('updateTokenInterceptor');
        $httpProvider.interceptors.push('ErrorsInterceptor');
        $httpProvider.interceptors.push('validateLoginTokenInterceptor');

        $resourceProvider.defaults.stripTrailingSlashes = false;

        //formato de data para DD/MM/YYYY
        $mdDateLocaleProvider.formatDate = function(date) {
            return moment(date).format('DD/MM/YYYY');
        };        
        
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);                
                             
        $stateProvider
            .state("out", {
                url: "/out",
                templateUrl: "templates/clean.tpl.html"
            })
            .state("in", {
                url: "/in",
                templateUrl: "templates/menu.content.tpl.html",
                data: {requiresLogin: true}
            });

           /** if($rootScope.hasAuthorization('PERMIT_ALL')) {
                $urlRouterProvider.otherwise('/in/esteira');
            }else{**/
                $urlRouterProvider.otherwise('/in/kanban');
            /**}**/
                


        var customPrimary = {
           '50': 'F3E5F5',
           '100': 'FAFAFA', // background
           '200': 'CE93D8',
           '300': 'BA68C8',
           '400': 'AB47BC',
           '500': '#0066cc', //cor azul que ficara na frente
           '600': '0066cc',
           '700': '7B1FA2',
           '800': '0066cc',
           '900': '4A148C',
           'A100': 'EA80FC',
           'A200': 'E040FB',
           'A400': 'D500F9',
           'A700': 'AA00FF',
           'contrastDefaultColor': 'light', // whether, by default, text (contrast) // on this palette should be dark or light
           'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
               '200', '300', '400', 'A100'],
           'contrastLightColors': undefined    // could also specify this if default was 'dark'
        };
        $mdThemingProvider.definePalette('customPrimary', customPrimary);

        var customAccent = {
           '50': 'F3E5F5',
           '100': 'E1BEE7',
           '200': 'CE93D8',
           '300': 'BA68C8',
           '400': 'AB47BC',
           '500': '9C27B0',
           '600': '0066cc',
           '700': '7B1FA2',
           '800': '0066cc',
           '900': '4A148C',
           'A100': 'EA80FC',
           'A200': '#0061CA', //Cor Azul do accent
           'A400': 'D500F9',
           'A700': 'FAFAFA', //background
           'contrastDefaultColor': 'light', // whether, by default, text (contrast) // on this palette should be dark or light
           'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
               '200', '300', '400', 'A100'],
           'contrastLightColors': undefined    // could also specify this if default was 'dark'
        };
        $mdThemingProvider.definePalette('customAccent', customAccent);        

        $mdThemingProvider
            .theme('default')
            .primaryPalette('customPrimary')
            .accentPalette('customAccent');
    }]).filter('nospace', function () {
        return function (value) {
            return (!value) ? '' : value.replace(/ /g, '');
        };
    }).filter('humanizeDoc', function () {
        return function (doc) {
        if (!doc) {
            return;
        }
        if (doc.type === 'directive') {
            return doc.name.replace(/([A-Z])/g, function ($1) {
                return '-' + $1.toLowerCase();
            });
        }
        
        return doc.label || doc.name;
    };
});

app.run(['$rootScope', '$base64', '$state', 'store', 'jwtHelper', 'defaultErrorMessageResolver', 'xdLocalStorage', 'KanbanService',
    function run($rootScope, $base64, $state, store, jwtHelper, defaultErrorMessageResolver, xdLocalStorage, KanbanService) {      
        xdLocalStorage.init({
            iframeUrl: RUBEUS_IFRAME_URL
        }).then(function () {            
            $rootScope.skipValidateLoginToken = false;
            $rootScope.validateLoginToken();
        });        
        
        /**
         * Checks token expiration each request / change state
         */               
        $rootScope.$on('$stateChangeStart', function (event, toState) {            
            if (toState.data && toState.data.requiresLogin) {
                /**validar se o token existe no storage **/
                var appToken = store.get(appTokenKey);
                if (angular.isDefined(appToken) && appToken != null && !jwtHelper.isTokenExpired(appToken)) {
                    $rootScope.hasMenu = true;
                    $rootScope.isAuth = true;
                    $rootScope.processMenu();
                } else {
                    event.preventDefault();
                    $rootScope.redirectToLogin();
                }
            }                      
        });
        
        /**
         * Translates error messages to pt-br
         */
        defaultErrorMessageResolver.setI18nFileRootPath('assets');
        defaultErrorMessageResolver.setCulture('pt-br');

    }]);

app.controller('AppCtrl', ['$scope', '$location', '$base64', 'store', '$state', '$rootScope', '$mdSidenav', '$window', '$log', '$mdToast', 'toastr', 'AccessService', 'LogoutService', 'xdLocalStorage', 'jwtHelper',
    function AppCtrl($scope, $location, $base64, store, $state, $rootScope, $mdSidenav, $window, $log, $mdToast, toastr, AccessService, LogoutService, xdLocalStorage, jwtHelper) {
        $rootScope.toggleMenu = buildTogglerMenu();



        var userData = store.get(userDataKey);
        if (userData != null && angular.isDefined(userData)) {
            $rootScope.me = angular.fromJson(userData);
        }



        function buildTogglerMenu() {
              return function() {
                $mdSidenav("menuLeft")
                  .toggle()
                  .then(function () {
                  });
              };
        }

        $rootScope.back = function() {
            $window.history.back();
        };
        
        $rootScope.skipValidateLoginToken = true;


        $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            /** if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle;
            }**/
            
            if($rootScope.hasAuthorization('PERMIT_ALL')) {
               if(toState.name == 'in.esteira') {
                   $state.go('in.esteira');
               }
            }else{

            if(toState.name == 'in.kanban') {
                   $state.go('in.kanban');
            }
            if(toState.name == 'in.openTask') {
                   $state.go('in.openTask');
            }
            if(toState.name == 'in.openHistoricTask') {
                   $state.go('in.openHistoricTask');
            }

           }

        });
                
        /**
         * Valida se o usuario fez logout
         */
        $rootScope.validateLoginToken = function () {         
            xdLocalStorage.getItem(loginTokenKey).then(function (resp) {                
                if (resp.value == null || !angular.isDefined(resp.value)) {
                    $rootScope.redirectToLogin();                    
                } else {                    
                    //ou fez login com outro usuario                    
                    var tokenUsername = jwtHelper.decodeToken(resp.value)[principalUsernameClaim];
                    if (!angular.equals(tokenUsername, $rootScope.me.username)) {                        
                        $rootScope.redirectToLogin();
                    }                    
                }                                
            });
        };

        /**
         * Redirect To Login
         */
        $rootScope.redirectToLogin = function () {
            $rootScope.me = null;
            store.remove(appTokenKey);
            store.remove(userDataKey);
            $rootScope.skipValidateLoginToken = true;
            $state.go('out.logout');
        };
        

        /**
         * Logout
         */
        $scope.logout = function () {
            xdLocalStorage.getItem(loginTokenKey).then(function (resp) {
                if (resp.value != null && angular.isDefined(resp.value)) {
                    $rootScope.me = null;
                    $rootScope.hasMenu = false;
                    $rootScope.isAuth = false;
                    $rootScope.bodylayout = 'bgout';
                    store.remove(appTokenKey);
                    store.remove(userDataKey);
                    xdLocalStorage.removeItem(loginTokenKey);
                    $rootScope.skipValidateLoginToken = true;
                    LogoutService.logout(resp.value.replace(/"/g, ""));
                } else {
                    $rootScope.redirectToLogin();
                }
            });
        };

        
        /**
         * Valida autorização do usuário pelo code
         */
        $rootScope.hasAuthorization = function () {
            if (angular.isDefined($rootScope.me) && $rootScope.me != null) {                
                for (var i = 0; i < $rootScope.me.authorizations.length; i++) {
                    for (var j = 0; j < arguments.length; j++) {                        
                        if ($rootScope.me.authorizations[i].code === arguments[j]) {                            
                            return true;
                        }
                    }
                }
            }
            return false;
        };

        /**
         * Toggle Menu
         */
        $rootScope.toggleMenu = function () {
            $mdSidenav('menuLeft').toggle();
        };

        $rootScope.toastClear = function () {
            $mdToast.cancel();
            toastr.clear([toast]);
        };

        $rootScope.toastSuccess = function (message) {
            if (message != null) {
                toastr.success(message);
            }
        };

        $rootScope.toastError = function (error, message) {
            if (error != null && error.data != null && error.data.description != null) {
                var strMessage = error.data.description.trim();

                if (strMessage.length != (strMessage.lastIndexOf(".") + 1)) {
                    strMessage = strMessage + '.';
                }
                if (error.data.comments != null) {
                    strMessage = strMessage + ' ' + error.data.comments;
                }
                if (strMessage.length != (strMessage.lastIndexOf(".") + 1)) {
                    strMessage = strMessage + '.';
                }

                toastr.error(strMessage);
            } else {
                toastr.error(message);
            }
        };      


        $rootScope.processMenu = function() {

            var me =  $rootScope.me.authorizations;

            var objMenu = AccessService.getAccess();

            $rootScope.menu_groups = [];

            var group_overview = {"title": "", items:[]};

            group_overview.items.push({title: "Visão Geral", href: "/#/in/esteira", sub_items: null});
            group_overview.items.push({title: "Tarefas", href: "/#/in/kanban", sub_items: null});

            if($rootScope.hasAuthorization('PERMIT_ALL')){                

                group_overview.items.push({title: "Investimentos", href: "/#/in/instanceList", sub_items: null});

                group_overview.items.push({title: "Processos", href: "/#/in/processList", sub_items: null});

                group_overview.items.push({title: "Relatórios", href: "/#/in/report", sub_items: null});
            }

            $rootScope.menu_groups.push(group_overview);   

        };
    }]);

/**
 * Interceptor para validar se usuario fez logout
 */
app.factory('validateLoginTokenInterceptor', ['$rootScope', function ($rootScope) {
    return {
        response: function (response) {
            if (!$rootScope.skipValidateLoginToken) {
                $rootScope.validateLoginToken();
            }
            return response;
        }
    };
}]);


/**
 * Interceptor to update token when AuthToken header apears
 */
app.factory('updateTokenInterceptor', ['store', function (store) {
    return {
        response: function (response) {
            var authToken = response.headers('AuthToken');            
            if (angular.isDefined(authToken) && authToken != null) {
                store.set(appTokenKey, authToken);
            }
            return response;
        }
    };
}]);


/**
 * Interceptor to handle erros
 */
app.factory('ErrorsInterceptor', ['$location', '$q', '$rootScope', function ($location, $q, $rootScope) {
    return {
        'responseError': function (rejection) {
            if (rejection.status !== 200) {
                if (rejection.status === 401) {
                    $rootScope.redirectToLogin();
                } else if (rejection.status === 403) {
                    $rootScope.toastError(null, 'Seu perfil não tem autorização para acessar este conteúdo.');
                }
                return $q.reject(rejection);
            }
            return $q.reject(rejection);
        }
    };
}]);