core.controller("SettingsListController", [ '$scope', '$state', 'Loader', 'apiService', 'appSettings',
                                          function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: 'common/settings',
			errorMsg: 'Failed to fetch Settings. Try Again!'
		}, function (response) {
			Loader.destroy();
			$scope.settingsList = response;
		})
	};

	vm.editSettings = function(id){
		$scope.$parent.vm.currentNavItem  = "app.settings.settingsCreate";
		$state.go('app.settingsCreate', { settingsId: id });
	}     

	init();
}]);