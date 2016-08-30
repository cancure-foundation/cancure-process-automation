core.controller("SearchPatientController", ['$rootScope', '$scope', '$state', '$stateParams', 'Flash', 'apiService', 'appSettings',
                                            function ($rootScope, $scope, $state, $stateParams, Flash, apiService, appSettings) {

	var vm = this;	
	var init = function () {
		vm.formData = {};
	};

	// init function, execution starts here
	init();

	//function to handle save button click
	vm.search = function () {

		if (!vm.formData.searchBy || !vm.formData.searchText) {
			Flash.create('warning', 'Please enter the search criteria.', 'large-text');    
			return;
		}

		var url = 'patient/search/' +  vm.formData.searchText;
		delete vm.patientList; //deletes the patient list so as to hide the search result container

		Flash.create('info', 'Please wait while we Search patient.', 'large-text');    

		if (vm.formData.searchBy == 'prn') {
			if (isNaN(vm.formData.searchText)) {
				Flash.create('danger', 'PRN should be numeric. Please enter again.', 'large-text');
				return;
			} else 
				url = 'patient/' +  vm.formData.searchText;        	         	 
		} 

		apiService.serviceRequest({
			URL: url,
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			if (response.length == 0) {
				vm.noSearchResult = true;
			} else {
				vm.patientList = response;
				vm.noSearchResult = false;
			}
		},function (){
			Flash.dismiss();
			vm.noSearchResult = true;
		});
	};    
}]);