core.controller("HospitalListController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', 
                                           function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: appSettings.requestURL.hospitalList,
			errorMsg: 'Failed to fetch hospitals. Try Again!'
		}, function (response) {
			Loader.destroy();
			$scope.hospitalList = response;
		})
	};

	vm.editHospital = function(id){
		$scope.$parent.vm.currentNavItem  = "app.hospital.hospitalCreate";
		$state.go('app.hospital.hospitalCreate', { hospitalId: id });
	}     

	init();
}]);