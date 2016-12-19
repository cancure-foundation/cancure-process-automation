core.controller("PharmacyForwardsController", ['Loader', '$scope', '$state', '$stateParams', 'apiService',
                                                function (Loader, $scope, $state, $stateParams, apiService) {

	var vm = this;
	
	var init = function (){
		vm.forwards = null;		
	};
	/**
	 * 
	 */
	vm.searchUser = function() {
		Loader.create('Please wait while we Search patient.');
		
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacydispatch/' + vm.pidn,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {	
			if (response.length == 0) {
				vm.noSearchResult = true;
				vm.pageMessage ="Sorry, no items match your query.";
			} else {
				vm.forwards = response;
				vm.patient = response[0].patient;
				vm.noSearchResult = false;
			}
			Loader.destroy();
		});
	};
	
	init();
	
}]);