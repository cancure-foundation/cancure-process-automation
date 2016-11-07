core.controller("labListController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', 
                                           function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: appSettings.requestURL.labList,
			errorMsg: 'Failed to fetch Labs. Try Again!'
		}, function (response) {
			Loader.destroy();
			$scope.labList = response;
		})
	};

	vm.editLab = function(id){
		$scope.$parent.vm.currentNavItem  = "app.lab.labCreate";
		$state.go('app.lab.labCreate', { labId: id });
	}     

	init();
}]);