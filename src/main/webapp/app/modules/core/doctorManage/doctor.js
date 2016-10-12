core.controller("DoctorListController", [ '$scope', '$state', 'Loader', 'apiService', 'appSettings', '$q',
                                          function ($scope, $state, Loader, apiService, appSettings, $q) {
	var vm = this;

	var init = function (token) {
		Loader.create(token? 'Refreshing Data.. Please wait...' : 'Fetching Data.. Please wait...');
		
		var doctorList = apiService.asyncServiceRequest({URL: appSettings.requestURL.doctorList});
		var hospitalList = apiService.asyncServiceRequest({URL: 'hospital/list'});
		var reqlist = [doctorList, hospitalList];

		$q.all(reqlist).then(function (response){
			$scope.doctorList = response[0];
			vm.hospitalList = response[1];
			Loader.destroy();
		});
	};
	/**
	 * 
	 */
	vm.editDoctor = function(id){
		vm.editMode = true;
		Loader.create('Fetching Data.. Please wait...');
		apiService.serviceRequest({
			URL: 'doctor/' + id,
			errorMsg: 'Failed to fetch Doctor Details. Try Again!'
		}, function (response) {			
			vm.formData = response;
			vm.formData.hospital = {hospitalId:response.hospital.hospitalId.toString()};
			Loader.destroy();
		});
	};
	/**
	 * function to handle save button click
	 */
	vm.createDoctor = function () { 
		Loader.create('Saving Data.. Please wait...');

		var serverData = angular.copy(vm.formData);

		// making the server call
		apiService.serviceRequest({
			URL: 'doctor/save',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			vm.editMode = false;			
			Loader.destroy();	
			init(1);
		});
	};

	init();
}]);