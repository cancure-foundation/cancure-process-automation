(function () {
    "use strict";
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