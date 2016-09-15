var core = angular.module('core', []);


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
        url: '/createUser/:userId',
        templateUrl: 'app/modules/core/manageUser/createUser/createUser.html',
        controller: 'CreateUserController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Create User'
        }
    });
    // User List
    $stateProvider.state('app.manageUser.userList', {
        url: '/userList',
        templateUrl: 'app/modules/core/manageUser/userList/userList.html',
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
    $stateProvider.state('app.hospital.hospitalCreate', {
        url: '/hospitalcreate/:hospitalId',
        templateUrl: 'app/modules/core/hospitalManage/hospitalCreate/hospitalCreate.html',
        controller: 'HospitalCreateController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hospitals'
        }
    });
    
    $stateProvider.state('app.hospital.hospitalList', {
        url: '/hospitalList',
        templateUrl: 'app/modules/core/hospitalManage/hospitalList/hospitalList.html',
        controller: 'HospitalListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hospitals'
        }
    });
    
    $stateProvider.state('app.hospital.hpocMapping', {
        url: '/hpocMapping',
        templateUrl: 'app/modules/core/hospitalManage/hpocMapping/hpocMapping.html',
        controller: 'HpocMappingController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hospitals'
        }
    });
    
    $stateProvider.state('app.hospital', {
        url: '/hospital',
        templateUrl: 'app/modules/core/hospitalManage/hospitalManage.html',
        controller: 'HospitalManageController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hospitals'
        }
    });
    
    
 // Search page state
    $stateProvider.state('app.doctor.doctorCreate', {
        url: '/doctorCreate/:doctor_id',
        templateUrl: 'app/modules/core/doctorManage/doctorCreate/doctorCreate.html',
        controller: 'DoctorCreateController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Doctors'
        }
    });
    
    $stateProvider.state('app.doctor.doctorList', {
        url: '/doctorList',
        templateUrl: 'app/modules/core/doctorManage/doctorList/doctorList.html',
        controller: 'DoctorListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Doctors'
        }
    });
    
    $stateProvider.state('app.doctor', {
        url: '/doctor',
        templateUrl: 'app/modules/core/doctorManage/doctorManage.html',
        controller: 'DoctorManageController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Doctors'
        }
    });
    
    $stateProvider.state('app.settingsList', {
        url: '/settingsList',
        templateUrl: 'app/modules/core/settingsManage/settingsList/settingsList.html',
        controller: 'SettingsListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Settings'
        }
    });
    
    $stateProvider.state('app.settingsCreate', {
        url: '/settingsCreate/:settingsId',
        templateUrl: 'app/modules/core/settingsManage/settingsCreate/settingsCreate.html',
        controller: 'SettingsCreateController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Settings'
        }
    });
    
}]);