core.controller("HospitalHpocController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', 
                                           function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: appSettings.requestURL.hospitalHpoc,
			errorMsg: 'Failed to fetch hospitals. Try Again!'
		}, function (response) {
			Loader.destroy();
			$scope.hospitalHpocList = response;
		})
	};

/*	vm.editHospital = function(id){
		$scope.$parent.vm.currentNavItem  = "app.hospital.hospitalCreate";
		$state.go('app.hospital.hospitalCreate', { hospitalId: id });
	}  */   

	init();
}]);