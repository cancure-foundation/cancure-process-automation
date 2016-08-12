core.controller("SearchPatientController", ['$rootScope', '$scope', '$state', '$stateParams', 'Flash', 'apiService', 'appSettings',
                                            function ($rootScope, $scope, $state, $stateParams, Flash, apiService, appSettings) {

	var vm = this;	
	var init = function () {
		vm.formData = {};
	};

	// init function, execution starts here
	init();

	//function to handle save button click
	vm.submitForm = function () {

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
			URL: url
		}, function (response) {
			Flash.create('success', 'Search completed with ' + response.length + ' results', 'large-text');
			vm.patientList = response;
			setTimeout(function (){
				var centerContent = document.getElementById('center-content-wrapper');
				if (centerContent)
					centerContent.scrollTop += 500; // scrolls view to search result area
			}, 100);
		});
	};     
	//
	vm.changeView = function(prn){
		$state.go('app.patientRegHistory', { prn: prn });
	}

}]);