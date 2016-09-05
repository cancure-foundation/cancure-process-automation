core.controller("HpocMappingController", ['$q', '$timeout', '$scope', '$state', 'Loader', 'apiService', 'appSettings', 'Flash',
                                          function ($q, $timeout, $scope, $state, Loader, apiService, appSettings, Flash) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');

		var hospitalList = apiService.asyncServiceRequest({URL : appSettings.requestURL.hospitalList});
		var hpocList = apiService.asyncServiceRequest({URL : appSettings.requestURL.hpocList});

		$q.all([hospitalList, hpocList]).then(function (success){
			vm.hospitalList = success[0];
			vm.hpocList = success[1];
			$timeout(function (){
				Loader.destroy();				
			});
		});
	};   

	vm.submitForm = function (){
		if(!vm.hospitalListValue || !vm.hpocListValue || vm.hpocListValue.length == 0) {
			Flash.create('warning', 'Please select all options to procees.', 'large-text');   
			return
		}
		Loader.create('Please wait while we register you...');

		// making the server call
		apiService.serviceRequest({
			URL: appSettings.requestURL.hpocMapping,
			method: 'POST',
			payLoad: {
				hospitalId : vm.hospitalListValue,
				hpocIdList : vm.hpocListValue
			}
		}, function (response) {
			Loader.destroy();
			delete vm.hospitalListValue;
			vm.hpocListValue = [];
			Flash.create('success', 'Mapping Successful.', 'large-text');   
		});
	}
	init();
}]);