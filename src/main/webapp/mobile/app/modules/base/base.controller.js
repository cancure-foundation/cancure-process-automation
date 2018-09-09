/**
 * 152HQ
 **/
(function () {
    "use strict";

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
            var confirm = $mdDialog.confirm()
                .title('Confirm logout')
                .textContent('Are you sure to exit session')
                .ok('Yes')
                .cancel('No');

            $mdDialog.show(confirm).then(function () {
                apiService.logout();
            }, function () {

            });
        };

        /**
         * 
         */
        vm.showAlert = function ($event) {
            $mdDialog
                .show({
                    parent: angular.element(document.body),
                    targetEvent: $event,
                    template: '<md-dialog aria-label="List dialog" class="row roleDialogBx" style="padding:15px;">' +
                        '  <md-dialog-content>' +
                        '  <table class="table table-bordered" style="margin-bottom:15px;">' +
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
                        '  <md-dialog-actions style="min-height:auto;">' +
                        '    <md-button ng-click="closeDialog()" class="md-primary" style="border: 1px solid rgb(63,81,181);border-radius:8px;margin:0px;">' +
                        '      Close' +
                        '    </md-button>' +
                        '  </md-dialog-actions>' +
                        '</md-dialog>',
                    controller: DialogController,
                    openFrom: 'top' 
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