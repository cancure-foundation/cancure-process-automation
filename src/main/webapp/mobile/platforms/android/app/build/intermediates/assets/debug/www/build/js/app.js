(function () {
    "use strict";
    config.$inject = ["$stateProvider", "$urlRouterProvider", "$httpProvider", "$mdGestureProvider"];
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
/**
 * 152HQ
 **/
(function () {
    "use strict";

    mainController.$inject = ["$scope", "$state", "$window", "$timeout", "$transitions", "$http", "appConfig", "apiService", "$rootScope", "$mdDialog"];
    angular
        .module('cancure')
        .controller('mainController', mainController);

    /* ngInject */
    function mainController($scope, $state, $window, $timeout, $transitions, $http, appConfig, apiService, $rootScope, $mdDialog) {
        var vm = this;
        vm.online = true;

        /**
         * exection starts here
         **/
        function init() {

            vm.user = angular.fromJson(window.localStorage.getItem("userdetails"));
            appConfig.rolesList = vm.user.roles;

            vm.token = window.localStorage.getItem('loginToken'); // get username from session             

            /* vm.userName = vm.user.firstName + ' ' + vm.user.lastName;*/
            vm.appTitle = appConfig.title;
            vm.appVersion = appConfig.version;

            if (navigator && navigator.connection && navigator.connection.type == 'none') {
                vm.online = false;
            }

            /** set the center div height **/
            vm.setCenterheight();

            /** set the center div height on resize **/
            angular.element($window).bind('resize', function () {
                if (!vm.adjusting)
                    $timeout(function () {
                        vm.adjusting = true;
                        vm.setCenterheight();
                    }, 100)
            });

            // set UI element visibility on state change
            $transitions.onFinish({}, function (action) {
                if (document.getElementById('center-content'))
                    document.getElementById('center-content').scrollTop = 0; // to scroll new view top
            });
        };
        /**
         * set the center div height
         **/
        vm.setCenterheight = function () {
            if (!document.getElementsByTagName('header'))
                return; // retruns if view is not rendereded(login page)

            var html = document.documentElement,
                hHeight = (document.getElementsByTagName('header')[0]) ? document.getElementsByTagName('header')[0].offsetHeight : 0,
                centerContent = document.getElementById('center-content');

            if (centerContent) {
                centerContent.style.height = html.clientHeight - (hHeight) + "px";
            }

            vm.adjusting = false;
        };

        /**
         * logout action
         **/
        vm.logout = function () {
            apiService.logout();
        };

        /**
         * 
         */
        vm.showAlert = function ($event) {
            DialogController.$inject = ["$scope", "$mdDialog", "appConfig"];
            var parentEl = angular.element(document.body);
            $mdDialog
                .show({
                    parent: parentEl,
                    targetEvent: $event,
                    template: '<md-dialog aria-label="List dialog" class="row roleDialogBx">' +
                        '  <md-dialog-content>' +
                        '  <div class="username"> User Details </div>' +
                        '  <table class="table table-bordered">' +
                        '	<th class="col-xs-4">Name</th>' +
                        '	<th class="col-xs-4">Roles</th>' +
                        '		<tr>' +
                        '		<td class="col-xs-4">' +
                        vm.user.name +
                        '</td>' +
                        '		<td class="col-xs-4">' +
                        '      		<div ng-repeat="item in items">' +
                        '        			{{item.displayName}}' +
                        '             </div>' +
                        '        </td>' +
                        '    </tr>' +
                        '</table>' +
                        '  </md-dialog-content>' +
                        '  <md-dialog-actions>' +
                        '    <md-button ng-click="closeDialog()" class="md-primary">' +
                        '      Close' +
                        '    </md-button>' +
                        '  </md-dialog-actions>' +
                        '</md-dialog>',
                    controller: DialogController
                });

            function DialogController($scope, $mdDialog, appConfig) {
                $scope.items = appConfig.rolesList;
                $scope.closeDialog = function () {
                    $mdDialog.hide();
                }
            }
        };


        init();

    }

})();
//set global configuration of application and it can be accessed by injecting appConstants in any modules

(function () {
    angular
        .module('cancure')
        .constant('appConfig', {

            title: "Cancure", // app name      
            version: "1.1.100", // app version 
            lang: "en", // app default locale format

            baseURL: 'http://cancure.in.net/', // app service URL 
            /*baseURL: 'http://localhost:8080/', // app service URL */
            requestURL: { 
                login: 'oauth/token',
                whoami: 'user/whoami',
                myQueue: 'tasks/my',
                patientHistory: 'tasks/history/',
                hpocDoctors: 'doctor/hpoclist' // to get all doctors under that HPOC
            },
            fileType: ['txt', 'doc', 'docx', 'pdf', 'png', 'jpg', 'jpeg', 'xlsx', 'xlsm'], // file types allowed for upload
            imgFileType: ['png', 'jpg', 'jpeg'], // image types allowed for upload

            rolesList: [] // stores all role names (authority)

        });
})();
(function () {
    apiService.$inject = ["$rootScope", "$http", "$q", "$state", "appConfig", "$mdToast", "$document", "$rootScope", "$timeout"];
    angular.module('cancure').service('apiService', apiService);

    function apiService($rootScope, $http, $q, $state, appConfig, $mdToast, $document, $rootScope,  $timeout) {
        var $self = this;
        /**
         * function to place http request
         */
        $self.serviceRequest = function (config, success, fail) {
            var requestParams = angular.merge({
                method: config.method || "GET"
                , url: appConfig.baseURL + config.url
                , params: config.params || {}
                , data: config.data || {}
                , headers: config.headers || {
                    'Content-Type': 'application/json'
                }
            }, config.addOns);
            var request = $http(requestParams);
            request.then(function successCallback(response) {
                if (response && response.status == 200) {
                    if (success) success(response.data);
                    else {
                        if (fail) fail(response.data);
                    }
                }
                else {
                    if (fail) fail(response.data);
                }
            }, function errorCallback(response) {
                if (fail) fail(response.data);
            });
        };
        /**
         * function to place async service request
         */
        $self.asyncServiceRequest = function (params) {
            var deferred = $q.defer(); // creating the promise object
            serviceRequest(params, function (response) {
                deferred.resolve(response); // resolving the promise
            }, function (response) {
                deferred.reject(response); // rejecting the promise
            });
            return deferred.promise; // returning the promise object
        };
        /**
         * speech to text conversion
         */
        $self.speechToText = function (success, fail, configs) {
            // checks for speech recognition plugin
            if (!window.plugins || !window.plugins.speechRecognition) {
                if (fail) fail();
                return;
            }
            // start listening
            window.plugins.speechRecognition.startListening(function (result) {
                if (result) {
                    var text = result.sort(function (a, b) { // retrieves the best match
                        return b.length - a.length;
                    })[0];
                    if (success) success(text);
                }
                else {
                    if (fail) fail();
                }
            }, function (error) { // in case of error in recording
                if (fail) fail(error);
            });
        };
        /**
         *
         */
        $self.toast = function (text, param) {
            $mdToast.show($mdToast.simple().textContent(text || 'Take2').hideDelay(1500));
        };
        /**
         * function to log user out and clear session settings
         */
        $self.logout = function (param) {
            var storage = window.localStorage;
            // clear all cookies
            angular.forEach(storage, function (value, key) {
                window.localStorage.removeItem(key);
            });
            $state.go('login'); // navigate to login
        };
    }
})();
(function () {
    'use strict';

    angular.module('cancure')
        .service('fs', fs);

    /** @ngInject */
    function fs() {
        /*jshint validthis: true*/
        var $self = this;
        $self.requestFileSystem = undefined;

        /*
         * Creates database
         * dbName {string} database name
         */
        $self.getFilesystem = function (success, fail) {
            $self.requestFileSystem = window.requestFileSystem || window.webkitRequestFileSystem;
            $self.requestFileSystem(PERSISTENT || LocalFileSystem.PERSISTENT, 1024 * 1024, success, fail);
        }

        /*
         * Creates table in db
         * tName {string} table name
         * fName {string} field names
         */
        $self.getDirectory = function (dir, options, success, fail) {

            $self.getFilesystem(function (fileSystem) {

                fileSystem.root.getDirectory(dir, options, gotDirEntry, gotDirFail);

                function gotDirEntry(dirEntry) {
                    if (success)
                        success(dirEntry);
                }

                function gotDirFail(dirEntry) {
                    if (fail)
                        fail(dirEntry);
                }
            }, function (fileSystem) {
                if (fail)
                    fail(fileSystem);
            });
        };

        /*
         * select from table in db
         * config {obj} select criteria
         * success {fn} success function
         * fail {fn} fail function
         */
        $self.moveTo = function (options, actionSuccess, actionFail) {

            var destination = options.destination;
            var fileURI = options.fileURI;
            var fileName = options.fileName;

            $self.getDirectory(destination, {
                create: true,
                exclusive: false
            }, destSuccess, actionFail);

            function destSuccess(destEntry) {
                window.resolveLocalFileSystemURL(fileURI, function (fileEntry) {
                    fileEntry.moveTo(destEntry, fileName, actionSuccess, actionFail);
                }, actionFail);
            }
        };

        /*
         * insert into table in db
         * config {obj} insert criteria
         * success {fn} success function
         * fail {fn} fail function
         */
        $self.copyTo = function (options, actionSuccess, actionFail) {

            var destination = options.destination;
            var fileURI = options.fileURI;
            var fileName = options.fileName;

            $self.getDirectory(destination, {
                create: true,
                exclusive: false
            }, destSuccess, actionFail);

            function destSuccess(destEntry) {
                window.resolveLocalFileSystemURL(fileURI, function (fileEntry) {
                    fileEntry.copyTo(destEntry, fileName, actionSuccess, actionFail);
                }, actionFail);
            }
        };

        /*
         * update a table in db
         * config {obj} insert criteria
         * success {fn} success function
         * fail {fn} fail function
         */
        $self.deleteFile = function (fileURI, actionSuccess, actionFail) {

            if (typeof (fileURI) != 'string') { // if its a list
                for (var i = 0; i < fileURI.length; i++)
                    window.resolveLocalFileSystemURL(fileURI[i], delFile, fail);

            } else {
                window.resolveLocalFileSystemURL(fileURI, delFile, fail);
            }

            function delFile(fileEntry) {
                fileEntry.remove(onDeleteSuccess, fail);
            }

            function onDeleteSuccess(file) {
                if (actionSuccess) {
                    actionSuccess(file);
                }
            }

            function fail(error) {
                if (actionFail) {
                    actionFail(error);
                }
            }
        };
    }
})();
(function () {
    httpInterceptor.$inject = ["$state"];
    angular
        .module('cancure')
        .factory('httpInterceptor', httpInterceptor);

    function httpInterceptor($state) {
        return {
            request: function (config) {
                return config;
            },

            requestError: function (rejection) {
                return rejection;
            },

            response: function (response) {
                if (response.status == 401) { // redirects to login page on unauthorized access
                    $state.go('login');
                }
                return response;
            },

            responseError: function (rejection) {
                if (rejection.status == 401) { // redirects to login page on unauthorized access

                }
                return rejection;
            }
        }
    }
})();
(function () {
    "use strict";
    homeController.$inject = ["$scope", "$state", "$timeout", "fs", "$mdDialog", "appConfig", "$rootScope", "apiService"];
    angular.module('cancure').controller('homeController', homeController);
    /* ngInject */
    function homeController($scope, $state, $timeout, fs, $mdDialog, appConfig, $rootScope, apiService) {
        var vm = this;

        vm.init = function () {
            vm.list = []; // take2 list
            vm.user = angular.fromJson(window.localStorage.getItem('user'));
            vm.appTitle = appConfig.title; // binds app title from config
            vm.showPage = false;
            vm.loadError = false;

            // check connectivity
            if (navigator && navigator.connection && navigator.connection.type == 'none') {
                apiService.toast("No internet connection. Please try again when online.", {
                    type: 'f'
                });
                vm.showPage = true;
                vm.loadError = true;
                $timeout();
                return;
            }
            vm.getQueue();
        };
        /**
         * get the queue details
         */
        vm.getQueue = function () {
            apiService.serviceRequest({
                url: appConfig.requestURL.myQueue
            }, function (response) {
                if (!response) {
                    vm.showPage = true;
                    vm.loadError = true;
                    return;
                }

                if (!response.PATIENT_REG_PROCESS_DEF_KEY) {
                    vm.showPage = true;
                    vm.loadError = true;
                    return;
                }
                 
                var tmp = [];
                tmp.push(response.PATIENT_REG_PROCESS_DEF_KEY);
                tmp.push(response.IN_PATIENT_HOSPITAL_VISIT_DEF_KEY);
                tmp.push(response.PATIENT_HOSPITAL_VISIT_DEF_KEY);

                for (var i = 0; i < tmp.length; i++) {
                    var item = tmp[i];
                    for (var j = 0; j < item.length; j++) {
                        vm.list.push(item[j]);
                        vm.list[i].createTime = new Date(vm.list[i].createTime);
                    }
                } 

                $timeout(function () {
                    vm.showPage = true;
                }, 200);

            }, function () {
                vm.showPage = true;
                vm.loadError = true;
            });
        };

        vm.init();
    }
})();
/**
 * Author:152HQ
 **/
(function () {
    "use strict";
    loginController.$inject = ["$scope", "$state", "appConfig", "$stateParams", "apiService", "$timeout", "$mdDialog", "$http", "$rootScope"];
    angular.module('cancure').controller('loginController', loginController);
    /* ngInject */
    function loginController($scope, $state, appConfig, $stateParams, apiService, $timeout, $mdDialog, $http, $rootScope) {
        var vm = this;

        function init() {
            vm.formData = {
                userId: "cancure",
                password: "cancure"
            }; // form field object
            vm.screenHeight = window.screen.height + "px";
            vm.logging = false; // indicate logging in state
            // binds the resize event
            angular.element(window).bind('resize', function () {
                $timeout(function () {
                    vm.screenHeight = window.screen.height + "px";
                });
                if (document.activeElement) {
                    var tag = document.activeElement.tagName;
                    if (tag && tag.toLocaleLowerCase() != "input" && tag.toLocaleLowerCase() != "textarea") {
                        return;
                    }
                    $timeout(function () {
                        document.activeElement.scrollIntoView(); // to bring the focus element to view when keyboard is on
                    }, 200);
                    $timeout(function () {
                        document.activeElement.scrollIntoView(); // to bring the focus element to view when keyboard is on
                    }, 300);
                    $timeout(function () {
                        document.activeElement.scrollIntoView(); // to bring the focus element to view when keyboard is on
                    }, 400);
                    document.activeElement.scrollIntoView(); // to bring the focus element to view when keyboard is on
                }
            });
            // fires when device back button is clicked
            document.addEventListener("backbutton", function (e) {
                e.preventDefault();
                e.stopPropagation();
                if ($state.current.name == "login") {
                    /* var confirm = $mdDialog.confirm()
                         .title('Exit')
                         .textContent('Do you want to close the application?')
                         .ok('YES')
                         .cancel('NO');
                     $mdDialog.show(confirm).then(function () {
                         navigator.app.exitApp(); // exits the application
                     }, function () {});*/
                    navigator.app.exitApp(); // exits the application
                } else {
                    window.history.back(); // goes to the previous state
                }
            }, false);
            document.addEventListener('deviceready', function () {
                vm.initPushNotification();
            }, false);
        };
        /**
         * login function
         **/
        vm.login = function (ev) {
            if (ev) ev.stopPropagation();
            if (vm.logging) return;
            if (vm.formData && vm.formData.userId && vm.formData.password) {
                vm.logging = true;
                // offline, log in from local db
                if (navigator && navigator.connection && navigator.connection.type == 'none') {
                    apiService.toast("No internet connection. Please try again when online.", {
                        type: 'f'
                    });
                    return;
                }
                var userDetails = angular.copy(vm.formData); // clones form data
                // sent device details to server
                if (typeof (cordova) != 'undefined') {
                    var deviceInfo = {
                        platform: device.platform,
                        version: device.version,
                        uuid: device.uuid,
                        model: device.model,
                        manufacturer: device.manufacturer,
                        serial: device.serial
                    }
                }
                // sent login request to server
                apiService.serviceRequest({
                    method: 'POST',
                    url: appConfig.requestURL.login + '?password=' + userDetails.password + '&username=' + userDetails.userId + '&grant_type=password&scope=read%20write&client_secret=cancure123456&client_id=cancureapp',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
                        'Authorization': 'Basic Y2FuY3VyZWFwcDpjYW5jdXJlMTIzNDU2'
                    }
                }, function (data) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + data.access_token; // sets the access token for all http request
                    // sent login request to server
                    apiService.serviceRequest({
                        method: 'POST',
                        url: appConfig.requestURL.whoami
                    }, function (success) {
                        window.localStorage.setItem("lastUser", userDetails.email); // sets the last logged in user email to localstorage
                        window.localStorage.setItem("lastUserPassword", userDetails.password); // sets the last logged in user password to localstorage
                        window.localStorage.setItem("userdetails", angular.toJson(success)); // set loginToken to localstorage
                        $state.go('app.home');
                    }, function (fail) { // service fails
                        vm.logging = false;
                        console.log("Login error due to service issue.");
                        apiService.toast("Action Failed. Try again!", {
                            type: 'f'
                        });
                    });

                }, function (fail) { // service fails
                    vm.logging = false;
                    console.log("Login error due to service issue.");
                    apiService.toast("Action Failed. Try again!", {
                        type: 'f'
                    });
                });
            } else {
                apiService.toast("Please fill all details.", {
                    type: 'f'
                });
            }
        };
        /**
         * function to handle push notification
         */
        vm.initPushNotification = function () {
            if (typeof (cordova) == "undefined") return;

            var notificationOpenedCallback = function (jsonData) { // handles callback when notification received background
                console.log(jsonData);
            };

            var notificationReceivedCallback = function (jsonData) { // handles callback when notification received foreground
                console.log(jsonData);
            };

            window.plugins.OneSignal.startInit("93ba3689-a206-45ae-abd4-96d6123c9c59").handleNotificationOpened(notificationOpenedCallback) // handles callback when notification received background
                .handleNotificationOpened(notificationOpenedCallback) // handles callback when notification received background
                .handleNotificationReceived(notificationReceivedCallback) // handles callback when notification received foreground
                .inFocusDisplaying(window.plugins.OneSignal.OSInFocusDisplayOption.None) // restrict plugin alert box when app is in foreground
                .endInit();
        };
        init(); 
    }
})();
(function () {
    "use strict";
    PatientRegHistoryController.$inject = ["$scope", "$state", "$timeout", "$stateParams", "$mdDialog", "appConfig", "$rootScope", "apiService"];
    angular.module('cancure').controller('PatientRegHistoryController', PatientRegHistoryController);
    /* ngInject */
    function PatientRegHistoryController($scope, $state, $timeout, $stateParams, $mdDialog, appConfig, $rootScope, apiService) {
        var vm = this;

        var init = function () {
            vm.showPage = false;
            vm.loadError = false;

            // check connectivity
            if (navigator && navigator.connection && navigator.connection.type == 'none') {
                apiService.toast("No internet connection. Please try again when online.", {
                    type: 'f'
                });
                vm.showPage = true;
                vm.loadError = true;
                $timeout();
                return;
            }

            apiService.serviceRequest({
                url: appConfig.requestURL.patientHistory + $stateParams.prn
            }, function (response) {

                if (!response) {
                    vm.showPage = true;
                    vm.loadError = true;
                    return;
                }
                vm.tasks = [];

                vm.nextTask = {
                    name: response.nextTask,
                    roles: response.Owner
                };

                // iteration for first task
                if (response.tasks[0]) {
                    vm.regDetails = response.tasks[0];
                    vm.regDetails.createTime = new Date(vm.regDetails.createTime);
                    vm.patientDetails = vm.regDetails.patient;

                    if (!vm.nextTask.name && !vm.patientDetails.pidn) {
                        vm.patientReject = true;
                    }

                    vm.regDocument = vm.patientDetails.document;
                    vm.patientFamily = [];
                    vm.supportOrg = [];
                    vm.otherDouments = [];
                    // iterating through patient documents
                    for (var i = 0; i < vm.regDocument.length; i++) {
                        if (vm.regDocument[i].docCategory == "Profile Image") {
                            vm.profileSrc = appConfig.baseURL + 'files/' + vm.regDocument[i].docId;
                        } else if (vm.regDocument[i].docCategory == "Diagnosis File") {
                            vm.otherDouments.push({
                                docCategory: vm.regDocument[i].docCategory,
                                docType: vm.regDocument[i].docType,
                                docSrc: appConfig.baseURL + 'files/' + vm.regDocument[i].docId
                            });
                        } else if (vm.regDocument[i].docCategory == "Income Proof") {
                            vm.otherDouments.push({
                                docCategory: vm.regDocument[i].docCategory,
                                docType: vm.regDocument[i].docType,
                                docSrc: appConfig.baseURL + 'files/' + vm.regDocument[i].docId
                            });
                        } else if (vm.regDocument[i].docCategory == "Aadhar Card") {
                            vm.otherDouments.push({
                                docCategory: vm.regDocument[i].docCategory,
                                docType: vm.regDocument[i].docType,
                                docSrc: appConfig.baseURL + 'files/' + vm.regDocument[i].docId
                            });
                        }
                    }
                    // iterating through patient details
                    vm.patientFamily = vm.patientDetails.patientFamily;

                    // iterating through support organisation details
                    vm.supportOrg = vm.patientDetails.organisation;
                }
                // iteration for rest tasks
                for (var i = 1; i < response.tasks.length; i++) {
                    var currentTask = response.tasks[i];

                    vm.tasks.push({
                        description: currentTask.description,
                        nextTask: currentTask.nextTask,
                        endTime: currentTask.endTime ? new Date(currentTask.endTime) : null,
                        documents: currentTask.documents,
                        investigation: (typeof (currentTask.investigation) == "undefined") ? null : currentTask.investigation
                    });

                    if (vm.tasks[i - 1].investigation)
                        vm.tasks[i - 1].investigation.investigationDate = angular.copy(new Date(vm.tasks[i - 1].investigation.investigationDate));

                    vm.nextTask.description = currentTask.description;
                }

                if (!vm.tasks[vm.tasks.length - 1].endTime)
                    vm.tasks.pop();

                $timeout(function () {
                    vm.showPage = true;
                });
            }, function () {
                vm.showPage = true;
                vm.loadError = true;
                $timeout();
            });
        };
        /**
         *  function to proceed to next task
         */
        vm.nextAction = function (checkAccess) {
            var hasAccess = false; // flag to indicate if access to next step is there
            for (var i = 0; i < appConfig.rolesList.length; i++) { // checks if access to next step is there
                var role = appConfig.rolesList[i];
                if (vm.nextTask.roles.indexOf(role.authority) >= 0) {
                    hasAccess = true;
                    break;
                }
            };
            if (checkAccess) {
                return hasAccess;
            }
            if (hasAccess)
                $state.go('app.patientRegNextAction', {
                    prn: $stateParams.prn
                });
            else
                apiService.toast('You do not have access to proceed.');
        };

        init();
    }
})();
(function () {
    "use strict";
    patientRegNextActionController.$inject = ["$scope", "$state", "$timeout", "fs", "$mdDialog", "appConfig", "$rootScope", "apiService", "$stateParams"];
    angular.module('cancure').controller('patientRegNextActionController', patientRegNextActionController);
    /* ngInject */
    function patientRegNextActionController($scope, $state, $timeout, fs, $mdDialog, appConfig, $rootScope, apiService, $stateParams) {
        var vm = this;
        vm.formData = {};

        /**
         *	/patientregistration/preliminaryexamination/save ROLE_HOSPITAL_POC
         *  /patientregistration/backgroundcheck/save/ ROLE_PROGRAM_COORDINATOR
         *  /patientregistration/mbdoctorrecommendation/save ROLE_HOSPITAL_POC
         *  /patientregistration/secretaryrecommendation/save/ ROLE_SECRETARY
         *  /patientregistration/executiveboardrecommendation/accept/save ROLE_EXECUTIVE_COMMITTEE
         *  /patientregistration/executiveboardrecommendation/reject/save ROLE_EXECUTIVE_COMMITTEE
         *  /patientregistration/Patientidcard/ ROLE_PROGRAM_COORDINATOR
         */
        var init = function () {
            vm.showPage = false;
            vm.loadError = false;

            // check connectivity
            if (navigator && navigator.connection && navigator.connection.type == 'none') {
                apiService.toast("No internet connection. Please try again when online.", {
                    type: 'f'
                });
                vm.showPage = true;
                vm.loadError = true;
                $timeout();
                return;
            }

            apiService.serviceRequest({
                url: 'tasks/' + $stateParams.prn
            }, function (response) {
                if (!response) {
                    vm.showPage = true;
                    vm.loadError = true;
                    return;
                }

                $scope.prn = $stateParams.prn;
                $scope.nextTaskObject = response;
                if ($scope.nextTaskObject && $scope.nextTaskObject.hospitalCostApproved && $scope.nextTaskObject.medicalCostApproved) {
                    if ($scope.nextTaskObject.hospitalCostApproved == "0")
                        $scope.nextTaskObject.hospitalCostApproved = null;
                    if ($scope.nextTaskObject.medicalCostApproved == "0")
                        $scope.nextTaskObject.medicalCostApproved = null;
                }
                if ($scope.nextTaskObject && $scope.nextTaskObject.hospitalCostEstimate && $scope.nextTaskObject.medicalCostEstimate) {
                    if ($scope.nextTaskObject.hospitalCostEstimate == "0")
                        $scope.nextTaskObject.hospitalCostEstimate = null;
                    if ($scope.nextTaskObject.medicalCostEstimate == "0")
                        $scope.nextTaskObject.medicalCostEstimate = null;
                }
                // checks for patient type
                if ($scope.nextTaskObject.patientType == "inPatient")
                    vm.inPatient = true;
                else if ($scope.nextTaskObject.patientType == "outPatient")
                    vm.outPatient = true;

                setupPeople(function (data) {
                    if (data)
                        $scope.doctorList = data;

                    $timeout(function () {
                        vm.showPage = true; 
                    });
                });
            }, function () {
                vm.showPage = true;
                vm.loadError = true;
                $timeout();
            });
        };
        /**
         *  function to set fields/data according to the next task
         */
        var setupPeople = function (callback) {
            if ($scope.nextTaskObject.nextTaskKey == 'mbDoctorApproval' ||
                $scope.nextTaskObject.nextTaskKey == 'preliminaryExamination' ||
                $scope.nextTaskObject.nextTaskKey == 'preliminaryExaminationClarification') {

                // check connectivity
                if (navigator && navigator.connection && navigator.connection.type == 'none') {
                    apiService.toast("No internet connection. Please try again when online.", {
                        type: 'f'
                    });
                    vm.showPage = true;
                    vm.loadError = true;
                    $timeout();
                    return;
                }

                apiService.serviceRequest({
                    url: appConfig.requestURL.hpocDoctors,
                    errorMsg: 'Failed to fetch Doctor list. Try Again!'
                }, function (response) {
                    callback(response);
                    if ($scope.nextTaskObject.nextTaskKey == 'preliminaryExamination') {
                        $scope.uploadNeeded = true;
                        vm.isPrelimEx = true;
                        vm.patientType = [{
                            'id': 'inPatient',
                            'name': 'In-Patient'
                        }, {
                            'id': 'outPatient',
                            'name': 'Out-Patient'
                        }];
                    }
                    if ($scope.nextTaskObject.nextTaskKey == 'mbDoctorApproval') {
                        vm.isMBDoc = true;
                        vm.formData.people = response[0].doctorId.toString();
                    }
                });

            } else if ($scope.nextTaskObject.nextTaskKey == 'backgroundCheck') {
                vm.statuses = [{
                    'id': 'PASS',
                    'name': 'Pass'
                }, {
                    'id': 'FAIL',
                    'name': 'Fail'
                }];
                callback(null);
            } else if ($scope.nextTaskObject.nextTaskKey == 'secretaryApproval') {
                vm.statuses = [];
                // checks if estimated cost is greater than 20,000 to route to EC
                if ((vm.inPatient && parseInt($scope.nextTaskObject.hospitalCostEstimate) < 20000) ||
                    (vm.outPatient && parseInt($scope.nextTaskObject.medicalCostEstimate) < 20000)) {
                    vm.statuses.push({
                        'id': 'Approved',
                        'name': 'Approve'
                    });
                }
                vm.statuses.push({
                    'id': 'Recommend',
                    'name': 'Forward to EC'
                }, {
                    'id': 'Reject',
                    'name': 'Reject'
                }, {
                    'id': 'SendBackToPC',
                    'name': 'Need background check clarification'
                }, {
                    'id': 'prelimExamClarificationReqd',
                    'name': 'Need preliminary exam clarification'
                });

                vm.isSecrtry = true; // flag to indicate secretary step

                // watch secretary entered cost medical cost to see if it exceeds estimates
                $scope.$watch('vm.formData.medicalCostApproved', function (newValue, oldValue, scope) {
                    vm.costValidator();
                });
                $scope.$watch('vm.formData.hospitalCostApproved', function (newValue, oldValue, scope) {
                    vm.costValidator();
                });
                callback(null);
            } else if ($scope.nextTaskObject.nextTaskKey == 'ecApproval') {
                vm.statuses = [{
                    'id': 'accept/save/Approve',
                    'name': 'Approve'
                }, {
                    'id': 'reject/save/Reject',
                    'name': 'Reject'
                }];
                callback(null);
            } else if ($scope.nextTaskObject.nextTaskKey == 'confirmApprovedAmounts') {
                vm.isSecrtryCnfrm = true; // flag to indicate secretary confirm step
                vm.formData.hospitalCostApproved = $scope.nextTaskObject.hospitalCostApproved; // pre-populate values
                vm.formData.medicalCostApproved = $scope.nextTaskObject.medicalCostApproved; // pre-populate values
                // watch secretary entered cost medical cost to see if it exceeds estimates
                $scope.$watch('vm.formData.medicalCostApproved', function (newValue, oldValue, scope) {
                    vm.costValidator();
                });
                $scope.$watch('vm.formData.hospitalCostApproved', function (newValue, oldValue, scope) {
                    vm.costValidator();
                });
                callback(null);
            } else {
                callback(null);
            }
        };
        /**
         *  handles the file selection for the page
         */
        vm.FilesSelection = function (input) {
            $timeout(function () {
                vm.patientFile = [];
                vm.patientFileNames = [];
                angular.forEach(input.files, function (v, k) {
                    vm.patientFile.push(input.files[k]);
                    vm.patientFileNames.push(input.files[k].name);
                });
            });
        };
        /**
         *  to clear all fields
         */
        vm.clearForm = function () {
            vm.formData = {};
            vm.patientFile = [];
            vm.patientFileNames = [];
            if (document.getElementById("patientNextTask-file"))
                document.getElementById("patientNextTask-file").value = "";
        };
        /**
         *  function to submit formdata
         */
        vm.submitTask = function () {
            var url = '',
                prefix = '';
            if ($scope.nextTaskObject.nextTaskKey == 'preliminaryExamination') {
                url = 'patientregistration/preliminaryexamination/save';
                prefix = 'patientInvestigationBean.';
            } else if ($scope.nextTaskObject.nextTaskKey == 'backgroundCheck') {
                url = 'patientregistration/backgroundcheck/save/' + vm.formData.status; //status
            } else if ($scope.nextTaskObject.nextTaskKey == 'mbDoctorApproval') {
                url = 'patientregistration/mbdoctorrecommendation/save';
            } else if ($scope.nextTaskObject.nextTaskKey == 'secretaryApproval') {
                url = 'patientregistration/secretaryrecommendation/save/' + vm.formData.status; //status
            } else if ($scope.nextTaskObject.nextTaskKey == 'backgroundClarification') {
                url = 'patientregistration/bgcheckclarification/save/';
            } else if ($scope.nextTaskObject.nextTaskKey == 'preliminaryExaminationClarification') {
                url = 'patientregistration/preliminaryexaminationclarification/save/';
            } else if ($scope.nextTaskObject.nextTaskKey == 'ecApproval') {
                url = 'patientregistration/executiveboardrecommendation/' + vm.formData.status; //accept/save or reject/save
            } else if ($scope.nextTaskObject.nextTaskKey == 'confirmApprovedAmounts') {
                url = 'patientregistration/confirmamount'; // + $scope.prn; //prn
            } else {

            }
            var fd = new FormData();

            fd.append(prefix + "prn", $scope.prn);
            fd.append(prefix + "investigatorId", vm.formData.people);
            fd.append(prefix + "comments", vm.formData.comments);

            // checks if the step is preliminary Examination
            if (vm.isPrelimEx) {
                fd.append(prefix + "medicalCostEstimate", vm.formData.medicalCostEstimate || "0");
                fd.append(prefix + "hospitalCostEstimate", vm.formData.hospitalCostEstimate || "0");
                fd.append(prefix + "patientType", vm.formData.patientType);
            }

            // checks if the step needs approved costs to be captured
            if (vm.formData.medicalCostApproved || vm.formData.hospitalCostApproved) {
                fd.append(prefix + "medicalCostApproved", vm.formData.medicalCostApproved || "0");
                fd.append(prefix + "hospitalCostApproved", vm.formData.hospitalCostApproved || "0");
            }

            if (vm.patientFile && vm.patientFile.length > 0) {
                for (var i = 0; i < vm.patientFile.length; i++) {
                    fd.append("patientDocumentBean[" + i + "].prn", $scope.prn);
                    fd.append("patientDocumentBean[" + i + "].docCategory", 'Preliminary Examination');
                    fd.append("patientDocumentBean[" + i + "].docType", 'Report');
                    fd.append("patientDocumentBean[" + i + "].patientFile", vm.patientFile[i]);
                }
            }

            // making the server call
            apiService.serviceRequest({
                url: url,
                method: 'POST',
                payLoad: fd,
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                },
                errorMsg: 'Unable to save data. Try Again!!'
            }, function (response) {
                apiService.toast('Completed Successfully.');
                vm.formData = {};
                $state.go('app.patientRegHistory', {
                    prn: $scope.prn
                });

            });
        };
        /**
         * 
         */
        vm.costValidator = function () {
            var errFlag;
            if (parseInt(vm.formData.medicalCostApproved) > parseInt($scope.nextTaskObject.medicalCostEstimate)) {
                errFlag = true;
                document.getElementById("medicalCostApproved").value = "";
            } else if (parseInt(vm.formData.hospitalCostApproved) > parseInt($scope.nextTaskObject.hospitalCostEstimate)) {
                errFlag = true;
                document.getElementById("hospitalCostApproved").value = "";
            }
            if (errFlag)
                vm.costErr = "Approved cost is greater than Estimated Cost.";
            else
                vm.costErr = null;
        };
        /**
         *  checks for patient type and makes changes accordingly
         */
        vm.patientTypeChange = function () {
            if (vm.formData.patientType == "inPatient") {
                vm.inPatient = true;
                vm.outPatient = false;
                vm.formData.medicalCostEstimate = 0;
                vm.formData.hospitalCostEstimate = null;
            } else if (vm.formData.patientType == "outPatient") {
                vm.inPatient = false;
                vm.outPatient = true;
                vm.formData.hospitalCostEstimate = 0;
                vm.formData.medicalCostEstimate = null;
            } else {
                vm.inPatient = false;
                vm.outPatient = false;
                vm.formData.medicalCostEstimate = null;
                vm.formData.hospitalCostEstimate = null;
            }
        }
        init();

    }
})();