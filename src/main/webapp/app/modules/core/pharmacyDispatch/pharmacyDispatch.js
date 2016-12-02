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
	
	vm.saveInvoice = function() {
		
		// Call 
		
	};
		
	init();

}]);