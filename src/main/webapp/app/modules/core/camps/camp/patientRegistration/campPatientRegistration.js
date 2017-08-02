core.controller("CampPatientRegisterController", ['$scope', '$state', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $state, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.campSearched = false;
	vm.campSearchForm = {};
	vm.campSearchForm.searchMonthYear = new Date();
	vm.selectedCamp = {};
	vm.patientCreated = false;
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
		vm.patientCreated = false;
		vm.formData = {};
	}
	
	vm.createPatient = function() {
		var serverData = angular.copy(vm.formData);
		serverData.campPatientTestResults = [];
		serverData.campId = vm.selectedCamp.campId;
		
		var i=0;
		for (var key in vm.labTests) {
		  if (vm.labTests.hasOwnProperty(key) && vm.labTests[key]) {
			  serverData.campPatientTestResults[i] = {};
			  serverData.campPatientTestResults[i].testName = key;
			  i++;
		  }
		}
		
		apiService.serviceRequest({
			URL: 'camp/patient',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			vm.patientCreated = true;
		});
	}
	
	vm.editCamp = function(id){
		$scope.$parent.vm.currentNavItem  = "app.camp.campCreate";
		$state.go('app.camp.campCreate', { campId: id });
	}
	
	init();
}]);