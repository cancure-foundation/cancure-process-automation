(function () {
    "use strict";
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
                data: fd,  
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