core.controller("PatientHospitalVisitController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	vm.documentTypes = ['Lab Test Prescription', 'Medicine Prescription'];
	var init = function() {
		vm.noSearchResult = false;
	};
	
	vm.searchUser = function(){
		Flash.create('info', 'Please wait while we Search patient.', 'large-text');
		vm.patient = null;
		if (!vm.pidn){
			alert('Please enter a PIDN');
			return;
		}
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
	
	init();

}]);