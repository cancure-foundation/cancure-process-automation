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
				$('#hpocMapping-panel-expandable').slideToggle(); // toggles the expandable panel
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
		Loader.create('Please wait while HPOC is mapped...');

		// making the server call
		apiService.serviceRequest({
			URL: appSettings.requestURL.hpocMapping,
			method: 'POST',
			payLoad: {
				hospitalId : vm.hospitalListValue,
				hpocIdList : vm.hpocListValue
			},
			errorMsg : 'Error Mapping selected HPOC to hospital. Please try again!'
		}, function (response) {
			Loader.destroy();
			delete vm.hospitalListValue;
			vm.hpocListValue = [];
			Flash.create('success', 'Mapping Successful.', 'large-text');   
			init();
			vm.panelExpanded = false;
		});
	}
	/**
	 * toggles the expandable panel
	 */
	vm.togglePanel = function (action){
		vm.panelExpanded = !vm.panelExpanded; // toggles the flag to indicate panel state
		$('#hpocMapping-panel-expandable').slideToggle(); // toggles the panel
		if (action == "close"){
			vm.editMode = false; // sets off the edit flag
			vm.hospitalListValue = null; // clears the hospital list
			vm.hpocListValue = []; // clears the hpoc list
		}
	};
	/**
	 *  function to handle edit functionality
	 */
	vm.editHpoc = function (values){			
		vm.editMode = true; // sets the edit mode flag
		vm.hospitalListValue = values.hospital.hospitalId; // sets values for hospital drop-down
		vm.hpocListValue = [];
		for (var i=0; i < values.hpocList.length; i++)
			vm.hpocListValue.push(values.hpocList[i].id); // sets values for hpoc drop-down
		if(!vm.panelExpanded) // checks if panel is expanded or not and expands if not
			vm.togglePanel();
	}

	init();
}]);