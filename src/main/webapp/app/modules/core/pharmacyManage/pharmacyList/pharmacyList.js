core.controller("PharmacyListController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', 
                                           function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: appSettings.requestURL.pharamcyList,
			errorMsg: 'Failed to fetch Pharmacies. Try Again!'
		}, function (response) {
			Loader.destroy();
			$scope.pharamcyList = response;
		})
	};

	vm.editPharamcy = function(id){
		$scope.$parent.vm.currentNavItem  = "app.pharmacy.pharmacyCreate";
		$state.go('app.pharmacy.pharmacyCreate', { pharmacyId: id });
	}     

	init();
}]);