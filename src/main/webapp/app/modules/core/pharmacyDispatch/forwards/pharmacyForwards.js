core.controller("PharmacyForwardsController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	
	vm.searchUser = function() {
		vm.forwards = null;
		if (!vm.pidn){
			alert('Please enter a PIDN');
			return;
		}
		
		Flash.create('info', 'Please wait while we Search patient.', 'large-text');
		
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacydispatch/' + vm.pidn,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			if (response == null) {
				vm.noSearchResult = true;
			} else {
				vm.forwards = response;
				vm.noSearchResult = false;
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
	};
	
	
}]);