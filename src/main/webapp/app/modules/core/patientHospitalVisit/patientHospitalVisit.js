core.controller("PatientHospitalVisitController", ['Loader', '$timeout', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash', 
                                                function (Loader, $timeout, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	vm.documentTypes = ['Lab Test Prescription', 'Medicine Prescription'];
	
	var init = function() {
		varInit();
	};
	
	function varInit (){
		vm.patient = null;
		vm.noSearchResult = false;
		vm.patientFile = [{
			id : 0
		}];
		vm.formSubmitted = false;
	}
	/**
	 * 
	 */
	vm.searchUser = function(){		
		vm.formSubmitted = false;
		Loader.create('Please wait while we Search patient.');
		
		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit/patient/' + vm.pidn,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {			
			if (response && !response.patientBean) {
				vm.pageMessage="Sorry, no items match your query.";
				vm.noSearchResult = true;
			} else {
				vm.toDay = new Date().toDateString();
				vm.patient = response;
				vm.noSearchResult = false;
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
	vm.submit = function(flag) {
	
		Loader.create('Sending data... Please wait...');
		
		vm.pageMessage = (flag == 'FALSE') ? "Data saved Successfully." : "Topup request send. Data saved successfully."
				
		var fd = new FormData();
		fd.append("pidn", vm.patient.patientBean.pidn);
		fd.append("topupNeeded", flag);
		
		if (vm.patientFile && vm.patientFile.length > 0 && flag == 'FALSE') {
			for (var i = 0; i < vm.patientFile.length; i++){
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].docType", 'Prescription');
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].accountTypeId", 3); //Pharmacy
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].patientVisitFile",  vm.patientFile[i].file);
			}
		}
		
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
			vm.noSearchResult = true;
			vm.pidn = null;
		});
		
	};
	
	init();

}]);