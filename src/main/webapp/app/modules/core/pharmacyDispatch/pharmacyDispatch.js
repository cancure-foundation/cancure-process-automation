core.controller("PharmacyDispatchController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	var init = function() {
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacyforwards/' + $stateParams.patientVisitId,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			if (response == null) {
				vm.noSearchResult = true;
			} else {
				vm.pharmacyDispatchHistory = response;
				vm.noSearchResult = false;
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
	};
	
	vm.submit = function() {
		
		Loader.create('Saving Data .. Please wait ...');

		vm.formData.pidn = vm.pharmacyDispatchHistory.patient.pidn;
		
		var serverData = angular.copy(vm.formData);
		
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacydispatch',
			method: 'POST',
			payLoad: serverData,
			hideErrMsg : true
		}, function (response) {
			Flash.create('danger', "Saved successfully", 'large-text');
			Loader.destroy();
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		}); 
		
	};
		
	init();

}]);