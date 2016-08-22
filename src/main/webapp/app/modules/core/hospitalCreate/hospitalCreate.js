core.controller("HospitalCreateController", ['$scope', '$timeout', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.hospitalCreated = false;
	
	// init function, execution starts here
	//init();

	/**
	 * function to handle save button click
	 */
	vm.createHospital = function () { 	
		var formState = formValidator(); // function to validate form fields
		if(!formState.valid){
			Flash.create('warning', formState.errMsg, 'large-text');
			return;
		}
		
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
			Flash.create('success', 'Hospital Successfully Created.', 'large-text');   
			vm.hospitalCreated = true; // to show the user summary div
		});
	};
	/**
	 * function to validate form fields
	 */
	var formValidator = function (){
		var formState = {
				valid : true
		};
				
		return formState;
	}
	
	/**
	 * function to show created user
	 */
	vm.createNwHospitalBtn = function (){
		vm.userCreated = false; // to hide the user summary div
		$timeout(function (){
			vm.formData = {};      
			vm.formData.roles = [];
		});
		vm.registerForm.$setUntouched();
		vm.registerForm.$setPristine();
	}
}]);