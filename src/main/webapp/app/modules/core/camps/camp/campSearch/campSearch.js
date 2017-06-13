core.controller("CampSearchController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', 
                                           function ($scope, $state, Loader, apiService, appSettings) {
	var vm = this;
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
				vm.campPatients = [{"name": "John doe", "age": 32, "phone" : "987678678", "address": "Kochi, kerala"},
				                   {"name": "Jane doe", "age": 34, "phone" : "5487678678", "address": "Kochi, kerala"},
				                   {"name": "Joan Doe", "age": 83, "phone" : "677678678", "address": "Kochi, kerala"}];
				break;
			}
		}
	}
	
	vm.searchCamp = function() {
		vm.campSearched = true;
		vm.camps = [{"campId": 1, "campName" : "Thrikkakara", "campPlace" : "Kakkanad", "localPOC": "Ajith"},
		            {"campId": 2, "campName" : "St. Marys school", "campPlace" : "Kochi", "localPOC": "Madhav"},
		            {"campId": 3, "campName" : "Town hall", "campPlace" : "Palakkad", "localPOC": "Fredy"}];
	}
	
	vm.reset = function() {
		vm.campSearched = false;
		vm.camps = [];
		vm.campPatientsSearched = false;
		vm.campPatients = [];
	}
	
}]);