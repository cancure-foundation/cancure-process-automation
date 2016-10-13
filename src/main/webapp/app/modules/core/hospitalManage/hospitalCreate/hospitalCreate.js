core.controller("HospitalCreateController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.hospitalCreated = false;
	
	var init = function() {
		var id = $stateParams.hospitalId;
		if (id) {
			vm.editMode = true;
			apiService.serviceRequest({
				URL: 'hospital/' + id,
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
	vm.createHospital = function () { 			
		Loader.create('Please wait ...');

		var serverData = angular.copy(vm.formData);
		serverData.enabled = true;

		// making the server call
		apiService.serviceRequest({
			URL: 'hospital/save',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			Flash.create('success', 'Hospital successfully saved', 'large-text');   
			vm.hospitalCreated = true; // to show the user summary div
		});
	};	
	/**
	 * function to show created user
	 */
	vm.createNwHospitalBtn = function (){
		vm.hospitalCreated = false; // to hide the user summary div
		$timeout(function (){
			vm.formData = {};      
			vm.formData.roles = [];
		});
		vm.registerForm.$setUntouched();
		vm.registerForm.$setPristine();
	}
}]);