var dashboard = angular.module('dashboard', ['ui.router', 'ngAnimate', 'ngMaterial']);


dashboard.config(["$stateProvider", function ($stateProvider) {

    //dashboard home page state
    $stateProvider.state('app.dashboard', {
        url: '/dashboard',
        templateUrl: 'app/modules/dashboard/views/home.html',
        controller: 'HomeController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Home'
        }
    });


    //REST page state
    $stateProvider.state('app.hellorest', {
        url: '/hellorest',
        templateUrl: 'app/modules/dashboard/views/hellorest.html',
        controller: 'HelloRestController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hello Rest'
        }
    });

    //Create User
    $stateProvider.state('app.createUser', {
        url: '/createuser',
        templateUrl: 'app/modules/dashboard/views/createUser.html',
        controller: 'CreateUserController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Create User'
        }
    });

    //Contact page state
    $stateProvider.state('app.userlist', {
        url: '/userlist',
        templateUrl: 'app/modules/dashboard/views/userList.html',
        controller: 'UserListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'User List'
        }
    });

    //Search page state
    $stateProvider.state('app.search', {
        url: '/search',
        templateUrl: 'app/modules/dashboard/views/search.html',
        controller: 'appCtrl',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Search'
        }
    });

}]);