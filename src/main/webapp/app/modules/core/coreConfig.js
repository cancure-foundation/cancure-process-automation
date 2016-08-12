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

    // Search page state
    $stateProvider.state('app.manageUser', {
        url: '/manageUser',
        templateUrl: 'app/modules/core/manageUser/manageUser.html',
        controller: 'ManageUserController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Manage User'
        }
    });
    //  Create User
    $stateProvider.state('app.manageUser.createUser', {
        url: '/createUser',
        templateUrl: 'app/modules/core/createUser/createUser.html',
        controller: 'CreateUserController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Create User'
        }
    });
    // User List
    $stateProvider.state('app.manageUser.userList', {
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
    
    $stateProvider.state('app.patientRegNextAction', {
        url: '/patientRegNextAction/:prn',
        templateUrl: 'app/modules/core/patientRegNextAction/patientRegNextAction.html',
        controller: 'patientRegNextActionController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Patient Registration Next Action'
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

    // patient registration page state
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

    // Search page state
    $stateProvider.state('app.doctor', {
        url: '/doctor',
        templateUrl: 'app/modules/core/doctor/doctor.html',
        controller: 'doctorController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Doctors'
        }
    });
    
}]);