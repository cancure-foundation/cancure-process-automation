core.controller("SearchPatientController", ['$rootScope', '$scope', '$state', '$stateParams', 'Flash', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $stateParams, Flash, apiService, appSettings) {
    
	var vm = this;
	 vm.formData = {};

     var init = function () {
       vm.formData = {};
     };

     // init function, execution starts here
     init();

     //function to handle save button click
     vm.submitForm = function () {
    	 
    	 var url = '';
    	 Flash.create('info', 'Please wait while we Search patient.', 'large-text');    
         
    	 if (vm.formData.searchBy == 'prn') {
        	 if (isNaN(vm.formData.searchText)) {
        		 Flash.create('danger', 'PRN should be numeric. Please enter again.', 'large-text');
        		 return;
        	 } else {
        		 url = 'patient/' +  vm.formData.searchText;
        	 }
        	 
         } else if (vm.formData.searchBy == 'name') {
        	 url = 'patient/search/' +  vm.formData.searchText;
         }
         
         apiService.serviceRequest({
        	 URL: url,
        	 method:'GET',
        }, function (response) {
        	vm.patientList = response;
        });
     }
     
     
     vm.changeView = function(prn){
	    $state.go('app.patientRegHistory', { prn: prn });
	 }
     
     
}]);