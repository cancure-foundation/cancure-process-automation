core.controller("CampSearchController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', '$mdDialog',
                                           function ($scope, $state, Loader, apiService, appSettings, $mdDialog) {
	var vm = this;
	vm.formData = {}
	vm.formData.searchMonthYear = new Date();
	vm.campSearched = false;
	vm.camps = [];
	vm.campPatientsSearched = false;
	vm.campPatients = [];
	vm.selectedCamp = {};
	vm.selectedPatient = {};
	
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
		vm.selectedPatient = {};
	}
	
	$scope.status = '  ';
	$scope.customFullscreen = false;
	$scope.showAdvanced = function(ev) {
		vm.selectedPatient = {"id": "20170201001", "name": "John doe", "age": 32, "phone" : "987678678", "address": "Kochi, kerala", "gender": "male", 
				"CampLabTests" : [{"testName": "Pap Smear"} , {"testName": "Ultrasound"}], "campName" : "Lions club" };
		
	    $mdDialog.show({
	      controller: DialogController,
	      templateUrl: 'app/modules/core/camps/camp/campSearch/campTestResult.html',
	      parent: angular.element(document.body),
	      targetEvent: ev,
	      clickOutsideToClose:true,
	      fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
	      locals:{dataToPass: vm.selectedPatient}
	    })
	    .then(function(answer) {
	      $scope.status = 'You said the information was "' + answer + '".';
	    }, function() {
	      $scope.status = 'You cancelled the dialog.';
	    });
	};
	
	function DialogController($scope, $mdDialog, dataToPass) {
		$scope.selectedPatient = dataToPass;
		
		$scope.showPat = function() {
			alert($scope.selectedPatient.name);
		}
		
	    $scope.hide = function() {
	      $mdDialog.hide();
	    };

	    $scope.cancel = function() {
	      $mdDialog.cancel();
	    };

	    $scope.answer = function(answer) {
	      $mdDialog.hide(answer);
	    };
	}
	
}]);