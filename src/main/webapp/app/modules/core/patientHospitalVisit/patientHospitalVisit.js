core.controller("PatientHospitalVisitController", ['Loader', '$timeout', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash', 
                                                function (Loader, $timeout, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	vm.documentTypes = ['Lab Test Prescription', 'Medicine Prescription'];
	
	var init = function() {
		if (appSettings.rolesList.indexOf('ROLE_SECRETARY') >= 0){
			vm.isSecretary = true;
		}
		Loader.create('Fetching data... Please wait..');
		varInit();
		if ($stateParams && $stateParams.pidn){			
			vm.searchUser($stateParams.pidn);
			vm.preLoad = true;
		}
		// service call to fetch pharmacy list
		apiService.serviceRequest({
			URL: '/pharmacy/all',
			method: 'GET',
			hideErrMsg : true
		}, function (response) {	
			vm.pharmacyList = response;		
			Loader.destroy();
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});		
	};
	/**
	 * 
	 */
	function varInit (){
		vm.formData = {};
		vm.formData.topUp = false;
		vm.patient = null;
		vm.noSearchResult = false;
		vm.patientFile = [{
			id : 0
		}];
	}
	/**
	 *  function to search the particular patient
	 */
	vm.searchUser = function(searchValue){
		Loader.create('Please wait while we Search patient.');
		
		vm.formSubmitted = false;		
		var pidn = (searchValue) ? searchValue : vm.pidn; 
		
		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit/patient/' + pidn,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {			
			if (response && !response.patientBean) {
				vm.pageMessage ="Sorry, no items match your query.";
				vm.noSearchResult = true;
			} else {
				vm.toDay = new Date().toDateString();
				vm.patient = response;
				vm.noSearchResult = false;
				
				// checks if any requests is pending for the user
				vm.formSubmitted = response.workflowExists;
				if (vm.formSubmitted){
					vm.pageMessage ="Requests pending for the patient. Please contact Sectretary.";
				}
				
				vm.approvedTotal = 0;
				vm.spendTotal = 0;
				if (vm.patient.patientApprovals) {
					for (var i=0; i < vm.patient.patientApprovals.length; i++){
						vm.approvedTotal = vm.approvedTotal + vm.patient.patientApprovals[i].amount;
					}
				}
				
				if (vm.patient.invoicesList){
					for (var i=0; i < vm.patient.invoicesList.length; i++){
						vm.spendTotal = vm.spendTotal + vm.patient.invoicesList[i].amount;
					}
				}
				vm.balAmount = vm.approvedTotal - vm.spendTotal;
			}
			Loader.destroy();
		});
		
	};
	/**
	 *  function to handle file selection
	 */
	vm.FilesSelection = function(input, item){
		var index = parseInt(input.attributes.fileid.value);
		vm.patientFile[index].file = input.files[0];
	};
	/**
	 *  function to add file filed in the UI
	 */
	vm.addPatientFile = function (){
		vm.patientFile.push({
			id : vm.patientFile.length
		});
	};
	/**
	 *  function to handle save request
	 */
	vm.submit = function() {
	
		Loader.create('Sending data... Please wait...');		
				
		var fd = new FormData();
		fd.append("pidn", vm.patient.patientBean.pidn);
		fd.append("topupNeeded", (vm.formData.topUpSelect) ? 'TRUE' : 'FALSE');
		
		// checks to inlcude files selected
		if (vm.patientFile && vm.patientFile.length > 0) {
			for (var i = 0; i < vm.patientFile.length; i++){
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].docType", 'Prescription');
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].accountTypeId", 3); //Pharmacy
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].patientVisitFile",  vm.patientFile[i].file);
			}
		}

		fd.append("forwardList[0].accountTypeId", 3);			
		fd.append("forwardList[0].accountHolderId", vm.formData.pharmacy);				
		
		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit',
			method: 'POST',
			payLoad: fd,
			transformRequest : angular.identity,
			headers: {
				'Content-Type': undefined
			},
			errorMsg : 'Unable to save data. Try Again!!'
		}, function (response) {
			Loader.destroy();	
			vm.pageMessage ="Requests submitted successfully.";
			vm.formSubmitted = true;				
		});
		
	};
	/**
	 * 
	 */
	vm.doTopup = function(approve){
		Loader.create('Saving data... Please wait...');		
		
		var serverData = {};
		serverData.pidn = vm.patient.patientBean.pidn;
		serverData.patientVisitId = $stateParams.id;
		
		if (approve) {
			serverData.patientApproval = [];
			var hosAmount = {};
			hosAmount.pidn = vm.patient.patientBean.pidn;
			hosAmount.amount = vm.hospitalAmount; //vm.pharmacyAmount;
			hosAmount.approvedForAccountTypeId = 5; // Hospital
			serverData.patientApproval.push(hosAmount);
			
			var pharmaAmount = {};
			pharmaAmount.pidn = vm.patient.patientBean.pidn;
			pharmaAmount.amount = vm.pharmacyAmount;
			pharmaAmount.approvedForAccountTypeId = 3; // Hospital
			serverData.patientApproval.push(pharmaAmount);
		}
		
		serverData.status = approve ? "TRUE" : "FALSE";
		
		// Submit /patientvisit/topup
		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit/topup',
			payLoad: serverData,
			method: 'POST',
			hideErrMsg : true
		}, function (response) {
			Loader.destroy();	
			if (response == null) {
				vm.noSearchResult = true;
			} else {
				vm.noSearchResult = false;
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
		
	}
	init();

}]);