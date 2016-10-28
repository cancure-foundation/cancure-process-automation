core.controller("SearchPatientController", ['$rootScope', '$scope', '$state', '$stateParams', 'Flash', 'apiService', 'appSettings',
                                            function ($rootScope, $scope, $state, $stateParams, Flash, apiService, appSettings) {

	var vm = this;	
	var init = function () {
		vm.formData = {};
		vm.formData.searchBy = 'prn';
	};

	// init function, execution starts here
	init();

	//function to handle save button click
	vm.search = function () {
		delete vm.patientList; //deletes the patient list so as to hide the search result container

		Flash.create('info', 'Please wait while we Search patient.', 'large-text');    

		apiService.serviceRequest({
			URL: 'patient/search/' + vm.formData.searchBy + '/' +  vm.formData.searchText.toString(),
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