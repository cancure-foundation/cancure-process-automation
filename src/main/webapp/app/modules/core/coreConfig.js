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
    
    $stateProvider.state('app.patientRegHistory', {
        url: '/patientRegHistory/:prn',
        templateUrl: 'app/modules/core/patientRegHistory/patientRegHistory.html',
        controller: 'PatientRegHistoryController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Patient Registration History'
        }
    });
    
    $stateProvider.state('app.myQueue', {
        url: '/myQueue',
        templateUrl: 'app/modules/core/myQueue/myQueue.html',
        controller: 'myQueueController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'My Queue'
        }
    });

    // Search page state
    $stateProvider.state('app.searchUser', {
        url: '/searchUser',
        templateUrl: 'app/modules/core/searchUser/searchUser.html',
        controller: 'searchUserController',
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
    
    //  S
    $stateProvider.state('app.searchPatient', {
        url: '/searchPatient',
        templateUrl: 'app/modules/core/searchPatient/searchPatient.html',
        controller: 'SearchPatientController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Search Patient'
        }
    });

}]);