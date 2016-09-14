core.controller("SettingsCreateController", ['$scope', '$timeout', '$state', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $state, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.settingsCreated = false;
	
	var init = function() {
		fetchSettings();
	}
	
	var fetchSettings = function() {
		var id = $stateParams.settingsId;
		if (id) {
			apiService.serviceRequest({
				URL: 'common/settings/' + id,
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
	vm.createSettings = function () { 	
		Loader.create('Please wait ...');

		var serverData = angular.copy(vm.formData);
		serverData.enabled = true;

		// making the server call
		apiService.serviceRequest({
			URL: 'common/settings/save',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			Flash.create('success', 'Settings successfully saved', 'large-text');   
			vm.settingsCreated = true; // to show the user summary div
		});
	};
	
	vm.cancel = function(){
		$state.go('app.settingsList');
	}
	
	/**
	 * function to show created user
	 */
	vm.createNwDoctorBtn = function (){
		vm.doctorCreated = false; // to hide the user summary div
		$timeout(function (){
			vm.formData = {};      
			vm.formData.roles = [];
		});
		vm.registerForm.$setUntouched();
		vm.registerForm.$setPristine();
	}
}]);