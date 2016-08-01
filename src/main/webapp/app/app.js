var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'flash',
    //main modules
    'login', 'core']);

app.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', function ($stateProvider, $locationProvider, $urlRouterProvider, $modalInstance) {

    //IdleScreenList
    $stateProvider
        .state('app', {
            url: '/app',
            templateUrl: 'app/baseLayout.html',
            controller: 'appCtrl',
            controllerAs: 'vm',
            data: {
                pageTitle: 'BaseLayout'
            }
        });

    $urlRouterProvider.otherwise('login');
}]);

// set global configuration of application and it can be accessed by injecting appSettings in any modules
var appConfig = {
    title: "Cancure", // app name
    lang: "en", // app default locale format
    dateFormat: "mm/dd/yy", // app date format
    baseURL: 'http://localhost:8080/', // app service URL
    requestURL: {
        authRequest: 'oauth/token',
        userRoles : 'roles',
        createUser: 'user/save',
        userList: 'user/list'
    },
    theme: 'skin-yellow', // app default theme
    layout: "" // app default layout
};

app.constant('appSettings', appConfig);