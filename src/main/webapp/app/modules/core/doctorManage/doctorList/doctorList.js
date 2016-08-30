core.controller("DoctorListController", [ '$scope', '$state', 'Loader', 'apiService', 'appSettings',
                                          function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: appSettings.requestURL.doctorList,
			errorMsg: 'Failed to fetch Doctors. Try Again!'
		}, function (response) {
			Loader.destroy();
			$scope.doctorList = response;
		})
	};

	vm.editDoctor = function(id){
		$state.go('app.doctor.doctorCreate', { doctor_id: id });
	}     

	init();
}]);