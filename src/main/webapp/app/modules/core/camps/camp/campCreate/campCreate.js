core.controller("CampCreateController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.campCreated = false;
	vm.MedicalTeams = [];
	
	var init = function() {
		apiService.serviceRequest({
			URL: 'common/lov/CampMedicalTeam',
			method: 'GET'
		}, function (response) {
			vm.MedicalTeams = response[0].listValues;
		});
	}
	
	// init function, execution starts here
	init();

	/**
	 * function to handle save button click
	 */
	vm.createLab = function () { 			
		Loader.create('Please wait ...');

		var serverData = angular.copy(vm.formData);

		// making the server call
		apiService.serviceRequest({
			URL: 'camp',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			vm.campCreated = true; // to show the user summary div
		});
	};	
	
}]);