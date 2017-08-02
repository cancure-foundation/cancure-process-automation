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
			fetchCamp();
		});
	}
	
	var fetchCamp = function() {
		var id = $stateParams.campId;
		if (id) {
			Loader.create('Please wait .. Loading Camp Data ...');
			vm.editMode = true;
			apiService.serviceRequest({
				URL: 'camp/' + id,
				method: 'GET'
			}, function (response) {
				Loader.destroy();
				var parts =response.campDate.split('-');
				var mydate = new Date(parts[0],parts[1]-1,parts[2]);
				vm.formData = response;
				vm.formData.campDate = mydate;
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