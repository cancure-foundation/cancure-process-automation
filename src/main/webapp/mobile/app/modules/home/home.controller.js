(function () {
    "use strict";
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
                        item[j].createTime = new Date(item[j].createTime);
                        vm.list.push(item[j]);
                    }
                } 

                $timeout(function () {
                    vm.showPage = true;
                }); 

            }, function () {
                vm.showPage = true;
                vm.loadError = true;
            });
        };

        vm.init();
    }
})();