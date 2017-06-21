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
				
				apiService.serviceRequest({
					URL: 'camp/' + id + '/patients',
					method: 'GET'
				}, function (response) {
					vm.campPatients = response;
					vm.campPatientsSearched = true;
				});
				
				break;
			}
		}
	}
	
	vm.searchCamp = function() {
		
		var month = vm.formData.searchMonthYear.getMonth() + 1;
		var year = vm.formData.searchMonthYear.getFullYear();
		
		apiService.serviceRequest({
			URL: 'camp/' + month + '/' + year,
			method: 'GET'
		}, function (response) {
			vm.camps = response;
			vm.campSearched = true;
		});
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
	
	vm.downloadReport = function(campId) {
		window.open("camp/report/" + campId);
	}
	
}]);