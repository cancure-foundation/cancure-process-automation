core.controller("PharmacyCreateController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.pharmacyCreated = false;
	
	var init = function() {
		var id = $stateParams.pharmacyId;
		if (id) {
			Loader.create('Please wait .. Loading Data ...');
			vm.editMode = true;
			apiService.serviceRequest({
				URL: 'pharmacy/' + id,
				method: 'GET'
			}, function (response) {
				Loader.destroy();
				vm.formData = response;
			});
		}
	}
	
	// init function, execution starts here
	init();

	/**
	 * function to handle save button click
	 */
	vm.createPharamcy = function () { 			
		Loader.create('Please wait ...');

		var serverData = angular.copy(vm.formData);
		serverData.enabled = true;

		// making the server call
		apiService.serviceRequest({
			URL: 'pharmacy',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			vm.pharmacyCreated = true; // to show the user summary div
		});
	};	
	/**
	 * function to show created user
	 */
	
}]);