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
		Loader.create('Fetching Data... Please wait..');
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
		if ($scope.nextTaskObject.nextTaskKey == 'mbDoctorApproval' || 
				$scope.nextTaskObject.nextTaskKey == 'preliminaryExamination' || 
				$scope.nextTaskObject.nextTaskKey == 'preliminaryExaminationClarification') {  

			apiService.serviceRequest({
				URL: appSettings.requestURL.hpocDoctors,
				errorMsg: 'Failed to fetch Doctor list. Try Again!'
			}, function (response) {	    	
				callback(response);
				if ($scope.nextTaskObject.nextTaskKey == 'preliminaryExamination') {
					$scope.uploadNeeded = true;
					vm.isPrelimEx = true;
				}
				if ($scope.nextTaskObject.nextTaskKey == 'mbDoctorApproval') {					
					vm.isMBDoc = true;
					vm.formData.people = response[0].doctorId.toString();
				}
			});

		} else if ($scope.nextTaskObject.nextTaskKey == 'backgroundCheck') {
			vm.statuses = [{'id': 'PASS', 'name' : 'Pass'}, {'id': 'FAIL', 'name' : 'Fail'}];
			callback(null);
		} else if ($scope.nextTaskObject.nextTaskKey == 'secretaryApproval') {
			vm.statuses = [{'id': 'Approved', 'name' : 'Approve'}, {'id': 'Recommend', 'name' : 'Forward to EC'},
			               {'id': 'Reject', 'name' : 'Reject'}, {'id': 'SendBackToPC', 'name' : 'Need background check clarification'},
			               {'id': 'prelimExamClarificationReqd', 'name' : 'Need preliminary exam clarification'}];
			
			vm.isSecrtry = true; // flag to indicate i
			
			// watch secretary entered cost medical cost to see if it exceeds estimates
			$scope.$watch('vm.formData.medicalCostApproved', function (newValue, oldValue, scope) {
				vm.costValidator();
			});
			$scope.$watch('vm.formData.hospitalCostApproved', function (newValue, oldValue, scope) {
				vm.costValidator();
			});
			callback(null);
		} else if ($scope.nextTaskObject.nextTaskKey == 'ecApproval') {
			vm.statuses = [{'id': 'accept/save/Approve', 'name' : 'Approve'}, {'id': 'reject/save/Reject', 'name' : 'Reject'}];
			callback(null);
		} else {
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
		if ($scope.nextTaskObject.nextTaskKey == 'preliminaryExamination'){
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
		
		// checks if the step needs estimate costs to be captured
		if (vm.formData.medicalCostEstimate && vm.formData.hospitalCostEstimate){
			fd.append(prefix + "medicalCostEstimate", vm.formData.medicalCostEstimate);
			fd.append(prefix + "hospitalCostEstimate", vm.formData.hospitalCostEstimate);
		}

		// checks if the step needs approved costs to be captured
		if (vm.formData.medicalCostApproved && vm.formData.hospitalCostApproved){
			fd.append(prefix + "medicalCostApproved", vm.formData.medicalCostApproved);
			fd.append(prefix + "hospitalCostApproved", vm.formData.hospitalCostApproved);
		}
		
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
	
	vm.costValidator = function (){
		var errFlag;
		if (parseInt(vm.formData.medicalCostApproved) > parseInt($scope.nextTaskObject.medicalCostEstimate)){
			errFlag = true;
			document.getElementById("medicalCostApproved").value="";
		} else if (parseInt(vm.formData.hospitalCostApproved) > parseInt($scope.nextTaskObject.hospitalCostEstimate)){
			errFlag = true;
			document.getElementById("hospitalCostApproved").value="";
		}
		if (errFlag) 
			vm.costErr = "Approved cost should not be greater than Estimated Cost.";			
		else
			vm.costErr = null;
	}
	init();

}]);