core.controller("CampSearchController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', 
                                           function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;
	vm.formData = {}
	vm.formData.searchMonthYear = new Date();
	vm.campSearched = false;
	vm.camps = [];
	vm.campPatientsSearched = false;
	vm.campPatients = [];
	vm.selectedCamp = {};
	
	vm.selectCamp = function(id) {
		for (var i=0; i < vm.camps.length; i++) {
			if (vm.camps[i].campId == id) {
				vm.selectedCamp = vm.camps[i];
				vm.campPatientsSearched = true;
				vm.campPatients = [{"id": "20170201001", "name": "John doe", "age": 32, "phone" : "987678678", "address": "Kochi, kerala"},
				                   {"id": "20170201002", "name": "Jane doe", "age": 34, "phone" : "5487678678", "address": "Kochi, kerala"},
				                   {"id": "20170201003", "name": "Joan Doe", "age": 83, "phone" : "677678678", "address": "Kochi, kerala"}];
				break;
			}
		}
	}
	
	vm.searchCamp = function() {
		vm.campSearched = true;
		vm.camps = [{"campId": 1, "campDate":"01-JUN-2017", "campName" : "Thrikkakara", "campPlace" : "Kakkanad", "localPOC": "Ajith"},
		            {"campId": 2, "campDate":"01-MAR-2017", "campName" : "St. Marys school", "campPlace" : "Kochi", "localPOC": "Madhav"},
		            {"campId": 3, "campDate":"01-FEB-2017", "campName" : "Town hall", "campPlace" : "Palakkad", "localPOC": "Fredy"}];
	}
	
	vm.uploadResults = function(id) {
		
	}
	
	vm.reset = function() {
		vm.campSearched = false;
		vm.camps = [];
		vm.campPatientsSearched = false;
		vm.campPatients = [];
	}
	
}]);