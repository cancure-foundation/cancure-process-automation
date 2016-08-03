var core = angular.module('core', ['ui.router', 'ngAnimate', 'ngMaterial']);


core.config(["$stateProvider", function ($stateProvider) {

    //dashboard home page state
    $stateProvider.state('app.home', {
        url: '/home',
        templateUrl: 'app/modules/core/home/home.html',
        controller: 'HomeController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Home'
        }
    });

    //  Create User
    $stateProvider.state('app.createUser', {
        url: '/createUser',
        templateUrl: 'app/modules/core/createUser/createUser.html',
        controller: 'CreateUserController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Create User'
        }
    });

    // User List
    $stateProvider.state('app.userList', {
        url: '/userList',
        templateUrl: 'app/modules/core/userList/userList.html',
        controller: 'UserListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'User List'
        }
    });

    // Search page state
    $stateProvider.state('app.searchUser', {
        url: '/searchUser',
        templateUrl: 'app/modules/core/searchUser/searchUser.html',
        controller: 'searchUser',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Search'
        }
    });
    // Search page state
    $stateProvider.state('app.patientRegistration', {
        url: '/patientRegistration',
        templateUrl: 'app/modules/core/patientRegistration/patientRegistration.html',
        controller: 'PatientRegistrationController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Patient Registration'
        }
    });

}]);