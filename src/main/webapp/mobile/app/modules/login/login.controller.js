/**
 * Author:152HQ
 **/
(function () {
    "use strict";
    angular.module('cancure').controller('loginController', loginController);
    /* ngInject */
    function loginController($scope, $state, appConfig, $stateParams, apiService, $timeout, $mdDialog, $http, $rootScope) {
        var vm = this;

        function init() {
            vm.formData = {
                userId: "cancure",
                password: "cancure"
            }; // form field object
            vm.screenHeight = $('body').height() + "px";
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
                        apiService.updatePushId(success.pushId, success.id);
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