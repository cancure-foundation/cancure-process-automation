core.controller("patientRegNextActionController", ['$timeout', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', 'Loader', 'Flash',
                                                   function ($timeout, $scope, $state, $stateParams, apiService, appSettings, Loader, Flash) {

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
	var init = function() {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: 'tasks/' + $stateParams.prn
		}, function (response) {
			$scope.prn = $stateParams.prn;
			$scope.nextTaskObject = response;
			setupPeople(function (data){
				if (data)
					$scope.doctorList = data;
				Loader.destroy();
			});
		});
	};
	/**
	 *  function to set fields/data according to the next task
	 */
	var setupPeople = function(callback){
		if ($scope.nextTaskObject.nextTask == 'MB Doctor Approval' || 
				$scope.nextTaskObject.nextTask == 'Preliminary Examination' || 
				$scope.nextTaskObject.nextTask == 'Preliminary Examination Clarification') {  

			apiService.serviceRequest({
				URL: appSettings.requestURL.hpocDoctors,
				errorMsg: 'Failed to fetch Doctor list. Try Again!'
			}, function (response) {	    	
				callback(response);
				if ($scope.nextTaskObject.nextTask == 'Preliminary Examination') {
					$scope.uploadNeeded = true;
				}
			});

		}		

		if ($scope.nextTaskObject.nextTask == 'Background Check') {
			vm.statuses = [{'id': 'PASS', 'name' : 'Pass'}, {'id': 'FAIL', 'name' : 'Fail'}];
			callback(null);
		}

		if ($scope.nextTaskObject.nextTask == 'Secretary Approval') {
			vm.statuses = [{'id': 'Approved', 'name' : 'Approve'}, {'id': 'Recommend', 'name' : 'Forward to EC'},
			               {'id': 'Reject', 'name' : 'Reject'}, {'id': 'SendBackToPC', 'name' : 'Need background check clarification'},
			               {'id': 'prelimExamClarificationReqd', 'name' : 'Need preliminary exam clarification'}];
			callback(null);
		}

		if ($scope.nextTaskObject.nextTask == 'EC Approval') {
			vm.statuses = [{'id': 'accept/save', 'name' : 'Approve'}, {'id': 'reject/save', 'name' : 'Reject'}];
			callback(null);
		}		
	};
	/**
	 * 
	 */
	vm.FilesSelection = function(input){
		$timeout(function (){
			vm.patientFile = [];
			vm.patientFileNames = [];
			angular.forEach(input.files, function (v, k) {	
				vm.patientFile.push(input.files[k]); 			 
				vm.patientFileNames.push(input.files[k].name); 			 
			});
		});
	};
	/**
	 * 
	 */
	vm.clearForm = function (){
		vm.formData = {};
		vm.patientFile = [];
		vm.patientFileNames = [];
		if (document.getElementById("patientNextTask-file"))
			document.getElementById("patientNextTask-file").value = "";
	};
	/**
	 * 
	 */
	vm.submitTask = function() {		
		Loader.create('Saving Data .. Please wait!');
		
		var url = '',
			prefix = '';
		if ($scope.nextTaskObject.nextTask == 'Preliminary Examination'){
			url = 'patientregistration/preliminaryexamination/save';
			prefix = 'patientInvestigationBean.';
		} else if ($scope.nextTaskObject.nextTask == 'Background Check') {
			url = 'patientregistration/backgroundcheck/save/' + vm.formData.status; //status
		} else if ($scope.nextTaskObject.nextTask == 'MB Doctor Approval') {
			url = 'patientregistration/mbdoctorrecommendation/save';
		} else if ($scope.nextTaskObject.nextTask == 'Secretary Approval') {
			url = 'patientregistration/secretaryrecommendation/save/' + vm.formData.status; //status
		} else if ($scope.nextTaskObject.nextTask == 'Background Clarification') {
			url = 'patientregistration/bgcheckclarification/save/';
		} else if ($scope.nextTaskObject.nextTask == 'Preliminary Examination Clarification') {
			url = 'patientregistration/preliminaryexaminationclarification/save/';
		} else if ($scope.nextTaskObject.nextTask == 'EC Approval') {
			url = 'patientregistration/executiveboardrecommendation/' + vm.formData.status; //accept/save or reject/save
		} else if ($scope.nextTaskObject.nextTask == 'Patient ID Card Generation') {
			url = 'patientregistration/patientidcard'; // + $scope.prn; //prn
		} else {

		}    	
		var fd = new FormData();

		fd.append(prefix + "prn", $scope.prn);
		fd.append(prefix + "investigatorId", vm.formData.people);
		fd.append(prefix + "comments", vm.formData.comments);


		if (vm.patientFile && vm.patientFile.length > 0) {
			for (var i = 0; i < vm.patientFile.length; i++){
				fd.append("patientDocumentBean["+ i +"].prn", $scope.prn); 
				fd.append("patientDocumentBean["+ i +"].docCategory", 'Preliminary Examination');
				fd.append("patientDocumentBean["+ i +"].docType", 'Report');
				fd.append("patientDocumentBean["+ i +"].patientFile",  vm.patientFile[i]);
			}
		}
		
		// making the server call
		apiService.serviceRequest({
			URL: url,
			method: 'POST',
			payLoad: fd,
			transformRequest : angular.identity,
			headers: {
				'Content-Type': undefined
			},
			errorMsg : 'Unable to save data. Try Again!!'
		}, function (response) {
			Loader.destroy();
			Flash.create('success', 'Completed Successfully.', 'large-text');
			vm.formData = {};
			$state.go('app.patientRegHistory',  { prn: $scope.prn} );

		});
	};

	init();

}]);