var login = angular.module('login', ['ui.router', 'ngResource', 'ngAnimate', 'ngIdle']);


login.config(["$stateProvider", function ($stateProvider) {

    //login page state
    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'app/modules/login/login.html',
        controller: 'loginCtrl',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Login'
        }
    });

}]);

