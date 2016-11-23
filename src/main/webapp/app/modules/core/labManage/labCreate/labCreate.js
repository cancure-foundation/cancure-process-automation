core.controller("LabCreateController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.labCreated = false;
	
	var init = function() {
		var id = $stateParams.labId;
		if (id) {
			Loader.create('Please wait .. Loading Data ...');
			vm.editMode = true;
			apiService.serviceRequest({
				URL: 'lab/' + id,
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
	vm.createLab = function () { 			
		Loader.create('Please wait ...');

		var serverData = angular.copy(vm.formData);
		serverData.enabled = true;

		// making the server call
		apiService.serviceRequest({
			URL: 'lab/save',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			vm.labCreated = true; // to show the user summary div
		});
	};	
	/**
	 * function to show created user
	 */
	vm.createNwLabBtn = function (){
		vm.labCreated = false; // to hide the user summary div
		$timeout(function (){
			vm.formData = {};      
			vm.formData.roles = [];
		});
		vm.registerForm.$setUntouched();
		vm.registerForm.$setPristine();
	}
}]);