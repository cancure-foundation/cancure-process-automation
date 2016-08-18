core.controller("PatientRegistrationController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings', 'Loader', '$mdDialog', '$mdMedia',
                                                  function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings, Loader, $mdDialog, $mdMedia) {
	var vm = this;

	var init = function () {
		vm.formData = {};
		vm.familyDetails = [];
		vm.organisation = [{}];
	};

	// init function, execution starts here
	init();

	//function to handle save button click
	vm.submitForm = function () {
		
		Loader.create('Please wait while we register patient.');
		
		var fd = new FormData();

		if ($scope.ageProof) {
			fd.append("document[0].docCategory", 'Age Proof');
			fd.append("document[0].docType", vm.formData.ageProof);
			fd.append("document[0].patientFile",  $scope.ageProof);
		}

		if ($scope.incomeProof) {
			fd.append("document[1].docCategory", 'Income Proof');
			fd.append("document[1].docType", vm.formData.incomeProof);
			fd.append("document[1].patientFile", $scope.incomeProof);
		}

		if (vm.familyDetails.length > 0){
			for (var i=0; i < vm.familyDetails.length; i++) {
				fd.append("patientFamily[" + i + "].relation", vm.familyDetails[i].relation);
				fd.append("patientFamily[" + i + "].age", vm.familyDetails[i].age);
				fd.append("patientFamily[" + i + "].status", vm.familyDetails[i].status);
				fd.append("patientFamily[" + i + "].income", vm.familyDetails[i].income);
				fd.append("patientFamily[" + i + "].otherIncome", vm.familyDetails[i].otherIncome);
			}
		}        		

		if (vm.organisation.length > 0) {
			for (var i=0; i < vm.organisation.length; i++) {
				if(vm.organisation[i].name)
					fd.append("organisation[" + i + "].name", vm.organisation[i].name);
				if(vm.organisation[i].amountRec)
					fd.append("organisation[" + i + "].amountRec", vm.organisation[i].amountRec);
			}
		}

		angular.forEach(vm.formData, function (v, k) {
			fd.append(k,v);
		});     	

		// making the server call
		apiService.serviceRequest({
			URL: appSettings.requestURL.patientRegistration,
			method: 'POST',
			payLoad: fd,
			headers: {
				'Content-Type': undefined
			}
		}, function (response) {
			Loader.destroy(); // hide the loader			
			showRegistrationDetails(response.prn); // shows the summary dialog box
		});
	};
	/**
	 * 
	 */
	vm.clearOrg = function (){
		vm.organisation = [{}];
	};
	/**
	 *  function to show patient summary on successful registration
	 */
	var showRegistrationDetails = function(prn) {
		$("body").addClass('sidebar-collapse'); // to collapse the sidebar
		var parentEl = angular.element(document.body);
		$mdDialog.show({
			parent: parentEl,
			template:
				'<md-dialog aria-label="List dialog" class="row patientRegDialogBx">' +
				'  <md-dialog-content>'+
				'  <div class="title"> Patient Registration Successful. </div>'+
				'  <div class="subtitle"> Pending Registration Number (PRN) is <b>' + prn + '</b></div>'+
				'  <table class="table table-bordered">'+       
				'	<tr>'+
				'		<td rowspan="7" class="user_img"><i class="fa fa-user" aria-hidden="true"></i></td>'+
				'   </tr>'+
				'	<tr>'+
				'		<td class="fieldName">Name</td>'+
				'		<td class="fieldValue">' + vm.formData.name + '</td>'+
				'   </tr>'+
				'	<tr>'+
				'		<td class="fieldName">Gender</td>'+
				'		<td class="fieldValue">' + vm.formData.gender + '</td>'+
				'   </tr>'+
				'	<tr>'+
				'		<td class="fieldName">DoB</td>'+
				'		<td class="fieldValue">' + vm.formData.dob.toDateString() + '</td>'+
				'   </tr>'+
				'	<tr>'+
				'		<td class="fieldName">Marital Status</td>'+
				'		<td class="fieldValue">' + vm.formData.maritalStatus + '</td>'+
				'   </tr>'+
				'	<tr>'+
				'		<td class="fieldName">Contact No</td>'+
				'		<td class="fieldValue">' + vm.formData.contact + '</td>'+
				'   </tr>'+
				'	<tr>'+
				'		<td class="fieldName">Address</td>'+
				'		<td class="fieldValue">' + vm.formData.address + '</td>'+
				'   </tr>'+
				'</table>'+
				'  </md-dialog-content>' +
				'  <md-dialog-actions>' +
				'    <md-button ng-click="closeDialog(0)" class="md-primary">' +
				'      Register another Patient' +
				'    </md-button>' +
				'    <md-button ng-click="closeDialog(1)" class="md-primary">' +
				'      Go to Homepage' +
				'    </md-button>' +
				'  </md-dialog-actions>' +
				'</md-dialog>',
				controller: DialogController
		});
		function DialogController($scope, $mdDialog) {
			$scope.closeDialog = function(to) {
				$("body").removeClass('sidebar-collapse'); // to expand the sidebar
				$mdDialog.hide(); // hides the dialog box
				vm.formData = {}; // clears the formFields
				vm.patientRegisterForm.$setUntouched();
				vm.patientRegisterForm.$setPristine();
				if (to == 1)
					$state.go('app.home'); // redirects to home page
			}
		}
	};

}]);