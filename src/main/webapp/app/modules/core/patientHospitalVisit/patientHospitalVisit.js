core.controller("PatientHospitalVisitController", ['Loader', '$timeout', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash', 
                                                   function (Loader, $timeout, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	vm.documentTypes = ['Lab Test Prescription', 'Medicine Prescription'];

	var init = function() {
		if (appSettings.rolesList.indexOf('ROLE_SECRETARY') >= 0){
			vm.isSecretary = true;
		}
		Loader.create('Fetching data... Please wait..');
		varInit(true);
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
	function varInit(init){
		if (init)
			vm.pidn = null;
		vm.formData = {};
		vm.formData.topUp = false;
		vm.patient = null;
		vm.noSearchResult = false;
		vm.patientFile = [{
			id : 0
		}];
		vm.bill = [{
			id : 0
		}];
		vm.inPatient = false; // resets the flag
		vm.outPatient = false; // resets the flag
	}
	/**
	 *  function to search the particular patient
	 */
	vm.searchUser = function(searchValue){
		varInit();
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
				if (response.workflowExists && !vm.isSecretary){					
					vm.formSubmitted = true;
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

				if (vm.isSecretary){
					vm.patientVisitDocuments = vm.patient.patientVisitDocuments;
				}

				if (vm.patient.patientBean && vm.patient.patientBean.patientType == 'inPatient') {
					vm.inPatient = true;
				} else if (vm.patient.patientBean && vm.patient.patientBean.patientType == 'outPatient') {
					vm.outPatient = true;
				}

				$scope.$watch('vm.formData.amount', function (newValue, oldValue, scope) {
					if (vm.formData.amount && parseInt(vm.formData.amount) > vm.balAmount){
						vm.balErr = true;	
						vm.billErr = false;			
						document.getElementById('cancureRdAmt').value = null;
					} else if (checkBill()) {
						vm.balErr = true;
						vm.billErr = true;			
						document.getElementById('cancureRdAmt').value = null;
					} else
						vm.balErr = false;

					function checkBill(){						
						var billAmt = 0;
						for (var i=0; i<vm.bill.length;i++){
							billAmt = billAmt + parseInt(vm.bill[i].partnerBillAmount);
						}
						return vm.formData.amount > billAmt;
					}
				});

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
	 *  function to handle save request for out-paitent
	 */
	vm.sendToPharma = function() {	

		Loader.create('Sending data... Please wait...');		

		var fd = new FormData();
		fd.append("pidn", vm.patient.patientBean.pidn);
		fd.append("topupNeeded", (vm.formData.topUpSelect) ? 'TRUE' : 'FALSE');

		// checks to inlcude files selected
		if (vm.patientFile && vm.patientFile.length > 0) {
			for (var i = 0; i < vm.patientFile.length; i++){
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].docType", vm.patientFile[i].documentTypes);
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
			apiService.showAlert("Request Placed Successfully !!", function (){
				varInit(init);
			});			
		});

	};
	/**
	 *  function to handle save for in-patient
	 */
	vm.hospitalBill = function (){		
		Loader.create('Saving Data .. Please wait ...');
		var fd = new FormData();

		fd.append("pidn", vm.pidn);
		fd.append("comments", vm.formData.comments);
		fd.append("amount", parseFloat(vm.formData.amount));

		for (var i=0; i<vm.bill.length;i++){
			fd.append("patientBills[" + i + "].partnerBillNo",  vm.bill[i].partnerBillNo);
			fd.append("patientBills[" + i + "].partnerBillAmount",  parseFloat(vm.bill[i].partnerBillAmount));
			fd.append("patientBills[" + i + "].partnerBillFile",  vm.bill[i].file);
		}	

		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit/inpatient',
			method: 'POST',
			payLoad: fd,
			hideErrMsg : true,
			headers: {
				'Content-Type': undefined
			}
		}, function (response) {			
			Loader.destroy();
			apiService.showAlert("Data Saved Successfully !!", function (){
				init(1);
			});	
		}, function (fail){
			Flash.create('danger', 'Action Failed. Try Again!!', 'large-text');
		}); 

	};
	/**
	 *  function to handle file selection
	 */
	vm.billSelection = function(input){
		var index = parseInt(input.attributes.fileid.value);
		vm.bill[index].file = input.files[0];		
		console.log(vm.bill)
	};
	/**
	 *  function to handle save for secretary pop-up
	 */
	vm.doTopup = function(approve){	

		Loader.create('Saving data... Please wait...');		

		var serverData = {};
		serverData.pidn = vm.patient.patientBean.pidn;
		serverData.patientVisitId = $stateParams.id;

		if (approve) {
			serverData.patientApproval = [];
			if (vm.inPatient) {
				var hosAmount = {};
				hosAmount.pidn = vm.patient.patientBean.pidn;
				hosAmount.amount = vm.hospitalAmount; //vm.pharmacyAmount;
				hosAmount.approvedForAccountTypeId = 5; // Hospital
				serverData.patientApproval.push(hosAmount);
			}

			if (vm.outPatient) {
				var pharmaAmount = {};
				pharmaAmount.pidn = vm.patient.patientBean.pidn;
				pharmaAmount.amount = vm.pharmacyAmount;
				pharmaAmount.approvedForAccountTypeId = 3; // Hospital
				serverData.patientApproval.push(pharmaAmount);
			}
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
				Flash.create('danger', 'Failed to complete action. Try again!', 'large-text');
			} else {
				apiService.showAlert("Action Completed Successfully !!", function (){
					$state.go('app.home');
				});	
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});

	}
	init();

}]);