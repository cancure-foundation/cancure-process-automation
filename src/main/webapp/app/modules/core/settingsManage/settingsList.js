core.controller("SettingsListController", [ '$scope', 'Loader', 'apiService', 
                                            function ($scope, Loader, apiService) {
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

	vm.editSettings = function(sett){
		if(!vm.settList.$valid)
			return;
		
		Loader.create('Saving Data .. Please wait ...');
		
		var serverData = angular.copy(sett);		
		
		// making the server call
		apiService.serviceRequest({
			URL: 'common/settings/save',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();  			
			sett.enabled= false;					
		});
	}     

	init();
}]);