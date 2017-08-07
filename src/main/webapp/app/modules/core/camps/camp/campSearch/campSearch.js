core.controller("CampSearchController", ['$scope', '$state', 'Loader', 'apiService', 'appSettings', '$mdDialog', '$timeout', 'Flash', 'NgTableParams',
                                           function ($scope, $state, Loader, apiService, appSettings, $mdDialog, $timeout, Flash, NgTableParams) {
	var vm = this;
	vm.formData = {}
	vm.formData.searchMonthYear = new Date();
	vm.campSearched = false;
	vm.camps = [];
	vm.campPatientsSearched = false;
	vm.campPatients = [];
	vm.selectedCamp = {};
	vm.selectedPatient = {};
	vm.alllabtests = null;
	
	vm.selectCamp = function(id) {
		for (var i=0; i < vm.camps.length; i++) {
			if (vm.camps[i].campId == id) {
				vm.selectedCamp = vm.camps[i];
				
				apiService.serviceRequest({
					URL: 'camp/' + id + '/patients',
					method: 'GET'
				}, function (response) {
					vm.campPatientTableParams = new NgTableParams({sorting: {
			            uid: 'asc'     
			        }}, { dataset: response});
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
	$scope.showAdvanced = function(campPatientId) {
		var campPatient = pickPatient(campPatientId);
		
		apiService.serviceRequest({
			URL: 'camp/patient/' + campPatient.campPatientId + '/testresult',
			method: 'GET'
		}, function (response) {
			vm.selectedPatient = campPatient;
			vm.selectedPatient.CampLabTests = response;
			vm.selectedPatient.unsavedCampTestResults = false;
			vm.selectedPatient.savedCampTestResults = false;
				
			for (var j=0; j<vm.selectedPatient.CampLabTests.length;j++ ) {
				if (vm.selectedPatient.CampLabTests[j].testResultText == null) {
					vm.selectedPatient.unsavedCampTestResults = true;
				}
				
				if (vm.selectedPatient.CampLabTests[j].testResultText) {
					vm.selectedPatient.savedCampTestResults = true;
				}
			}
			
			$mdDialog.show({
		      controller: DialogController,
		      templateUrl: 'app/modules/core/camps/camp/campSearch/campTestResult.html',
		      parent: angular.element(document.body),
//		      targetEvent: ev,
		      clickOutsideToClose:true,
		      fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
		      locals:{dataToPass: vm.selectedPatient}
		    })
		    .then(function(answer) {
		      $scope.status = 'You said the information was "' + answer + '".';
		    }, function() {
		      $scope.status = 'You cancelled the dialog.';
		    });
		});
		
		/*
		vm.selectedPatient = {"id": "20170201001", "name": "John doe", "age": 32, "phone" : "987678678", "address": "Kochi, kerala", "gender": "male", 
				"CampLabTests" : [{"testName": "Pap Smear"} , {"testName": "Ultrasound"}], "campName" : "Lions club" };
		*/
	    
	};
	
	$scope.chooseTests = function(campPatientId) {
		var campPatient = pickPatient(campPatientId);
		getAllTests(campPatient);
	}
	
	var pickPatient = function(campPatientId) {
		for (var i=0; i < vm.campPatients.length; i++) {
			if (vm.campPatients[i].campPatientId == campPatientId) {
				return vm.campPatients[i]; 
			}
		}
	}
	
	var showChooseTestsDialog = function(campPatient, alllabtests, currentlySelectedTests) {
		$mdDialog.show({
		      controller: DialogControllerTestChoose,
		      templateUrl: 'app/modules/core/camps/camp/campSearch/campTestChoose.html',
		      parent: angular.element(document.body),
		      clickOutsideToClose:true,
		      fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
		      locals:{dataToPass: { "selectedPatient" : campPatient, 
		    	  					"alllabtests" : alllabtests, 
		    	  					"currentlySelectedTests" : currentlySelectedTests}
		      }
		    })
		    .then(function(answer) {
		      $scope.status = 'You said the information was "' + answer + '".';
		    }, function() {
		      $scope.status = 'You cancelled the dialog.';
		});
	}
	
	var getAllTests = function(campPatient) {
		if (!vm.alllabtests) {
			apiService.serviceRequest({
				URL: 'common/lov/CampLabTests',
				method: 'GET'
			}, function (response) {
				Loader.destroy();
				vm.alllabtests = response[0].listValues;
				getCurrentlySelectTests(campPatient, vm.alllabtests);
			});
		} else {
			getCurrentlySelectTests(campPatient, vm.alllabtests);
		}
	}
	
	var getCurrentlySelectTests = function(campPatient) {
		apiService.serviceRequest({
			URL: 'camp/patient/' + campPatient.campPatientId + '/testresult',
			method: 'GET'
		}, function (response) {
			showChooseTestsDialog(campPatient, vm.alllabtests, response);
		});
	}
	
	function DialogControllerTestChoose($scope, $mdDialog, dataToPass) {
		var vm= this;
		$scope.selectedPatient = dataToPass.selectedPatient;
		$scope.alllabtests = dataToPass.alllabtests;
		$scope.currentlySelectedTests = dataToPass.currentlySelectedTests;
		$scope.labTests = {};
		
		if (dataToPass.currentlySelectedTests && dataToPass.currentlySelectedTests.length > 0) {
			for (var i=0; i < dataToPass.currentlySelectedTests.length; i ++) {
				$scope.labTests[dataToPass.currentlySelectedTests[i].testName] = true;
			}
		}
		
		$scope.cancel = function() {
	      $mdDialog.cancel();
	    };
	    
	    $scope.answer = function(answer) {
	      $mdDialog.hide(answer);
	    };
	    
	    $scope.saveTests = function() {
	    	$scope.saving = true;
	    	var serverData = {};
	    	serverData.campPatientTestResults = [];
	    	
	    	var i=0;
			for (var key in $scope.labTests) {
			  if ($scope.labTests.hasOwnProperty(key) && $scope.labTests[key]) {
				  serverData.campPatientTestResults[i] = {};
				  serverData.campPatientTestResults[i].testName = key;
				  i++;
			  }
			}
			
			serverData.campPatientId = $scope.selectedPatient.campPatientId;
			
			apiService.serviceRequest({
				URL: 'camp/patient',
				method: 'PUT',
				payLoad: serverData
			}, function (response) {
				Loader.destroy();
				$mdDialog.cancel();
			});
	    }
	}
	
	function DialogController($scope, $mdDialog, dataToPass) {
		var vm= this;
		$scope.selectedPatient = dataToPass;
		$scope.testFormData = {};
		$scope.testFormData.testResult = [];
		
	    $scope.hide = function() {
	      $mdDialog.hide();
	    };

	    $scope.cancel = function() {
	      $mdDialog.cancel();
	    };

	    $scope.answer = function(answer) {
	      $mdDialog.hide(answer);
	    };
	    
	    $scope.deleteTestReport = function(testid) {
	    	if (confirm('Are you sure you want to delete the test report?')) {
	    		
	    		apiService.serviceRequest({
					URL: 'camp/patient/testresult/' + testid,
					method: 'DELETE'
				}, function (response) {
					Flash.create('success', 'Test Result has been deleted successfully', 'large-text');
					$scope.cancel();
				}, function(response) {
					Flash.create('danger', 'Test Results could not be deleted. ' + response, 'large-text');
					$scope.cancel();
				});
	    		
	    	}
	    }
	    	    
	    $scope.saveTestResults = function() {
	    	$scope.saving = true;
			var count=0;
			var fd = new FormData();
			for (var i = 0; i < $scope.selectedPatient.CampLabTests.length; i++){
				if ($scope.testFormData.testResult[i]) {
					fd.append("campPatientTestResultsBeanList[" + count + "].id", $scope.testFormData.testResult[i].testResultId);
					fd.append("campPatientTestResultsBeanList[" + count + "].testResultText", $scope.testFormData.testResult[i].testResultText);
					fd.append("campPatientTestResultsBeanList[" + count + "].testFile", $scope.testFormData.testResult[i].testResultFile);
					fd.append("campPatientTestResultsBeanList[" + count + "].testName", $scope.testFormData.testResult[i].testName);
					fd.append("campPatientTestResultsBeanList[" + count + "].campPatientId", $scope.selectedPatient.campPatientId);
					count++;
				}
			}
			fd.append("campId", $scope.selectedPatient.campId);
			
			apiService.serviceRequest({
				URL: 'camp/patient/testresult',
				method: 'POST',
				payLoad: fd,
				headers: {
					'Content-Type': undefined
				}
			}, function (response) {
				$scope.saving = false;
				Flash.create('success', 'Test Results have been saved successfully', 'large-text');
				$scope.cancel();
			}, function(response) {
				Flash.create('danger', 'Test Results could not be saved. ' + response, 'large-text');
				$scope.cancel();
			});
		}
	    
	    $scope.emailReport = function(patientid) {
	    	apiService.serviceRequest({
				URL: 'camp/patient/' + patientid + '/testresult/email',
				method: 'GET'
			}, function (response) {
				Flash.create('success', 'Test Results have been emailed to Camp POC.', 'large-text');
			});
	    }
	    
	}
	
	vm.downloadReport = function(campId) {
		window.open("camp/report/" + campId);
	}
	
}]);