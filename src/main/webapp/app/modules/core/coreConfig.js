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
        url: '/createUser/:user',
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
    
    $stateProvider.state('app.patientHospitalVisit', {
        url: '/patientHospitalVisit/:pidn/:id',
        templateUrl: 'app/modules/core/patientHospitalVisit/patientHospitalVisit.html',
        controller: 'PatientHospitalVisitController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Patient Hospital Visit'
        }
    });
    
    $stateProvider.state('app.pharmacyForwards', {
        url: '/pharmacyForwards/:pidn',
        templateUrl: 'app/modules/core/pharmacyDispatch/pharmacyForwards.html',
        controller: 'PharmacyForwardsController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Pharmacy Forwards'
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

    // patient registration page state
    $stateProvider.state('app.patientRegistration', {
        url: '/patientRegistration/:prn',
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
    
    $stateProvider.state('app.doctor', {
        url: '/doctorList',
        templateUrl: 'app/modules/core/doctorManage/doctor.html',
        controller: 'DoctorListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Doctors'
        }
    });
    
    $stateProvider.state('app.lab', {
        url: '/lab',
        templateUrl: 'app/modules/core/labManage/labManage.html',
        controller: 'LabManageController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Labs'
        }
    });
    
    $stateProvider.state('app.lab.labCreate', {
        url: '/labcreate/:labId',
        templateUrl: 'app/modules/core/labManage/labCreate/labCreate.html',
        controller: 'LabCreateController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Labs'
        }
    });
    $stateProvider.state('app.lab.labList', {
        url: '/labList',
        templateUrl: 'app/modules/core/labManage/labList/labList.html',
        controller: 'LabListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Labs'
        }
    });
    
    $stateProvider.state('app.settingsList', {
        url: '/settingsList',
        templateUrl: 'app/modules/core/settingsManage/settingsList.html',
        controller: 'SettingsListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Settings'
        }
    });
    $stateProvider.state('app.pharmacy.pharmacyCreate', {
        url: '/pharmacyCreate/:pharmacyId',
        templateUrl: 'app/modules/core/pharmacyManage/pharmacyCreate/pharmacyCreate.html',
        controller: 'PharmacyCreateController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hospitals'
        }
    });
    
    $stateProvider.state('app.pharmacy.pharmacyList', {
        url: '/pharmacyList',
        templateUrl: 'app/modules/core/pharmacyManage/pharmacyList/pharmacyList.html',
        controller: 'PharmacyListController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hospitals'
        }
    });
    
    $stateProvider.state('app.pharmacy', {
        url: '/pharmacy',
        templateUrl: 'app/modules/core/pharmacyManage/pharmacyManage.html',
        controller: 'PharmacyManageController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Hospitals'
        }
    });
    
    $stateProvider.state('app.payments', {
        url: '/payments',
        templateUrl: 'app/modules/core/payments/payments.html',
        controller: 'PaymentsController',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Payments'
        }
    });
    
}]);