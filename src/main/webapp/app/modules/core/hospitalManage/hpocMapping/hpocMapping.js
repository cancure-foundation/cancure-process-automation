core.controller("HpocMappingController", ['$q', '$timeout', '$scope', '$state', 'Loader', 'apiService', 'appSettings', 'Flash',
                                          function ($q, $timeout, $scope, $state, Loader, apiService, appSettings, Flash) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');

		var hospitalList = apiService.asyncServiceRequest({URL : appSettings.requestURL.hospitalList}); // for hosptial list
		var hpocList = apiService.asyncServiceRequest({URL : appSettings.requestURL.hpocList}); // for HPOC list
		var hpocMappedList = apiService.asyncServiceRequest({URL : appSettings.requestURL.hospitalHpoc}); // for getting already mapped data

		$q.all([hospitalList, hpocList, hpocMappedList]).then(function (success){
			vm.hospitalList = success[0];
			vm.hpocList = success[1];
			vm.hospitalHpocList = success[2];
			
			$timeout(function (){
				$('#hpocMapping-panel-expandable').slideToggle();
				Loader.destroy();				
			});
		});
	};   
	/**
	 * 
	 */
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
	/**
	 * 
	 */
	vm.togglePanel = function (){
		if(vm.panelExpanded)
			vm.panelExpanded = false;
		else
			vm.panelExpanded = true;
		$('#hpocMapping-panel-expandable').slideToggle();
	};
	
	init();
}]);