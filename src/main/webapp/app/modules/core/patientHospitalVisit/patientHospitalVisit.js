core.controller("PatientHospitalVisitController", ['Loader', '$timeout', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $timeout, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	vm.documentTypes = ['Lab Test Prescription', 'Medicine Prescription'];
	var init = function() {
		vm.noSearchResult = false;
	};
	
	vm.searchUser = function(){
		vm.patient = null;
		if (!vm.pidn){
			alert('Please enter a PIDN');
			return;
		}
		
		Flash.create('info', 'Please wait while we Search patient.', 'large-text');
		
		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit/patient/' + vm.pidn,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			if (response == null) {
				vm.noSearchResult = true;
			} else {
				vm.patient = response;
				vm.noSearchResult = false;
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
		
	};
	
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
	
	vm.sendToSecretary = function() {
		alert('Secretary');
		vm.submit('TRUE');
	};
	
	
	vm.selectPartners = function() {
		alert('Toptup');
		vm.submit('FALSE');
	};
	
	vm.submit = function(flag) {
		
		if (!vm.patient) {
			alert('Nothing to submit');
			return;
		}
		
		
		var fd = new FormData();
		fd.append("pidn", vm.patient.patientBean.pidn);
		fd.append("topupNeeded", flag);
		
		if (vm.patientFile && vm.patientFile.length > 0) {
			for (var i = 0; i < vm.patientFile.length; i++){
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].docType", 'Prescription');
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].accountTypeId", 3); //Pharmacy
				fd.append("patientHospitalVisitDocumentBeanList["+ i +"].patientVisitFile",  vm.patientFile[i]);
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
			Flash.create('success', 'Completed Successfully.', 'large-text');
			vm.formData = {};
			alert('Saved successfully ' + response);
			//$state.go('app.patientRegHistory',  { prn: $scope.prn} );

		});
		
	};
	
	init();

}]);