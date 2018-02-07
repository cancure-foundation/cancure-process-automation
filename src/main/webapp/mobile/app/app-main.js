(function () {
    "use strict";
    angular.module('cancure', ['ui.router', 'ngMaterial', 'ngAnimate', 'ngAria']).config(config).run(run);
    /* @ngInject */
    function config($stateProvider, $urlRouterProvider, $httpProvider, $mdGestureProvider) {

        $stateProvider.state('app', {
            url: '/',
            templateUrl: 'modules/base/base.html',
            controller: 'mainController',
            controllerAs: 'vm'
        }).state('login', {
            url: '/login',
            templateUrl: 'modules/login/login.html',
            controller: 'loginController',
            controllerAs: 'vm'
        }).state('app.home', {
            url: 'home',
            templateUrl: 'modules/home/home.html',
            controller: 'homeController',
            controllerAs: 'vm'
        }).state('app.patientRegHistory', {
            url: '/patientRegHistory',
            params: {
                prn: undefined
            },
            templateUrl: 'modules/patientRegHistory/patientRegHistory.html',
            controller: 'PatientRegHistoryController',
            controllerAs: 'vm'
        }).state('app.patientRegNextAction', {
            url: '/patientRegNextAction',
            params: {
                prn: undefined
            },
            templateUrl: 'modules/patientRegNextAction/patientRegNextAction.html',
            controller: 'patientRegNextActionController',
            controllerAs: 'vm'
        });

        $urlRouterProvider.otherwise('login'); // fallback url
        $httpProvider.interceptors.push('httpInterceptor');
        $mdGestureProvider.skipClickHijack(); // fix to mouse 'hijack' issue android

    }
    /* @ngInject */
    function run() {

    }

})();