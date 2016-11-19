core.controller("PatientHospitalVisitController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	
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
			URL: '/patient/search/pidn/' + vm.pidn,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			if (response.length == 0) {
				vm.noSearchResult = true;
			} else {
				vm.patient = response[0];
				vm.noSearchResult = false;
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
		
	};
	
	init();

}]);