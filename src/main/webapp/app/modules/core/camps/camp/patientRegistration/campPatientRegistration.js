core.controller("CampPatientRegisterController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.campSearched = false;
	vm.campSearchForm = {};
	vm.campSearchForm.searchMonthYear = new Date();
	vm.selectedCamp = {};
	vm.formData = {};
	vm.camps = [];
	
	vm.alllabtests = [];
	vm.allgenders = [{
		value : 'Male'
	},{
		value : 'Female'
	},{
		value : 'Transgender'
	}];
	
	var init = function() {
		vm.campSearchForm.searchMonthYear = new Date();
		apiService.serviceRequest({
			URL: 'common/lov/CampLabTests',
			method: 'GET'
		}, function (response) {
			Loader.destroy();
			vm.alllabtests = response[0].listValues;
		});
	}

	vm.searchCamp = function() {
		var month = vm.campSearchForm.searchMonthYear.getMonth() + 1;
		var year = vm.campSearchForm.searchMonthYear.getFullYear();
		
		apiService.serviceRequest({
			URL: 'camp/' + month + '/' + year,
			method: 'GET'
		}, function (response) {
			vm.camps = response;
			vm.campSearched = true;
		});
	}

	vm.selectCamp = function(id) {
		for (var i=0; i < vm.camps.length; i++) {
			if (vm.camps[i].campId == id) {
				vm.selectedCamp = vm.camps[i];
				vm.campSelected = true;
				break;
			}
		}
	}
	
	vm.resetCamp = function() {
		vm.camps = [];
		vm.campSearched = false;
		vm.selectedCamp = {};
		vm.campSelected = false;
		vm.campSearchForm = {};
		vm.formData = {};
	}
	
	vm.createPatient = function() {
		//vm.labTests
		var keys = [];
		for (var key in vm.labTests) {
		  if (vm.labTests.hasOwnProperty(key) && vm.labTests[key]) {
			  keys.push(key);
		  }
		}
		
		/*apiService.serviceRequest({
			URL: 'camp/patient',
			method: 'POST'
		}, function (response) {
			vm.campCreated = true;
		});
		*/
	}
	
	init();
}]);