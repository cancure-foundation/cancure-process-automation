core.controller("PatientRegistrationController", ['$q', '$scope', '$state', 'Flash', 'apiService', 'appSettings', 'Loader', '$mdDialog', '$mdMedia', '$timeout',
                                                  function ($q, $scope, $state, Flash, apiService, appSettings, Loader, $mdDialog, $mdMedia, $timeout) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		initializeVars();

		var referenceData = apiService.asyncServiceRequest({URL : appSettings.requestURL.patientRegDrpDwn});
		var hospitalList = apiService.asyncServiceRequest({URL : appSettings.requestURL.hospitalList});
		var doctorList = apiService.asyncServiceRequest({URL : appSettings.requestURL.doctorList});

		$q.all([referenceData, hospitalList, doctorList]).then(function (success){
			angular.forEach(success[0], function (v, k) {
				vm[k] = v[0].listValues;
			});
			vm.hospitalList = success[1];
			vm.doctorMainList = success[2];
			Loader.destroy();
		});
	};
	/**
	 *  function to initalize all variables in the page
	 */
	var initializeVars = function (){
		vm.formData = {};
		// ******** to be removed : start *********//
		vm.formData = {
				name : 'test',
				address : "qew",
				bloodGroup : "B+",
				contact : 123123,
				dob : new Date(),
				employmentStatus : "Employed",
				gender : "Male",
				maritalStatus : "Divorced",
				typeOfSupport :	"Lab Tests"
		}
		// ******** to be removed : end *********//
		vm.formData.profileImage = null;
		vm.formData.profilePicSrc = null;
		vm.formData.familyDetails = [];
		vm.formData.organisation = [{}];
		vm.formData.diagnosisFiles = [];
		vm.formData.diagnosisFilesNames = [];
		document.getElementById("patientReg-dianosis").value = "";
		document.getElementById("patientReg-ageProof").value = "";
		document.getElementById("patientReg-incomeProof").value = "";
	};

	// init function, execution starts here
	init();

	/**
	 *  function to pop up window explorer on "select image" click
	 */
	vm.showFileDialog = function (){
		document.getElementById('patientProfilePic_reg').click();
	};
	/**
	 *  function to select profile image and display it in the div
	 */
	vm.profileImageChange = function (input) {
		var url = input.value,
		ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
		vm.formData.profileImage = input.files[0];
		if (input.files && input.files[0] && (ext == "png" || ext == "jpeg" || ext == "jpg")) {
			var reader = new FileReader();		    
			reader.onload = function (e) {
				$timeout(function (){
					vm.formData.profilePicSrc = e.target.result;
				});		    	
			};
			reader.readAsDataURL(input.files[0]);
		}
		else {
			Flash.create('danger', 'Please select valid image types.(jpg/jpeg/png)' , 'large-text');
		}		
	};
	/**
	 *  function to clear all organisation
	 */
	vm.clearOrg = function (){
		vm.formData.organisation = [{}];
	};
	/**
	 *  function to remove selected profile image
	 */
	vm.removeProfileImg = function () {
		vm.formData.profileImage = null;
		vm.formData.profilePicSrc = null;
	};
	/**
	 *  function to set files selected in diagnosis file select 
	 */
	vm.diagnosisFilesSelection = function(input){
		$timeout(function (){
			vm.formData.diagnosisFiles = [];
			vm.formData.diagnosisFilesNames = [];
			angular.forEach(input.files, function (v, k) {	
				vm.formData.diagnosisFiles.push(input.files[k]); 			 
				vm.formData.diagnosisFilesNames.push(input.files[k].name); 			 
			});
		});
	};
	/**
	 *  function to handle hospital drop-down changes
	 */
	vm.onHospitalChange = function (){
		var hospitalId = parseInt(vm.formData.preliminaryExamHospitalId);
		vm.doctorList = [];
		for (var i = 0; i <  vm.doctorMainList.length;i++)
			if(vm.doctorMainList[i].hospital.hospitalId == hospitalId) 
				vm.doctorList.push({ name : vm.doctorMainList[i].name, id : vm.doctorMainList[i].doctorId});
	};
	/**
	 * function to handle save button click
	 */
	vm.submitForm = function () {		
		Loader.create('Please wait while we register patient.');

		var fd = new FormData(),
		localVm = angular.copy(vm.formData),
		fileCount = 0;

		if (localVm.profileImage && localVm.profilePicSrc) { // get the profile image if available
			fd.append("document[" + fileCount + "].docCategory", 'Profile Image');
			fd.append("document[" + fileCount + "].docType", "profile-image");
			fd.append("document[" + fileCount + "].patientFile",  vm.formData.profileImage);
			fileCount++;
		}

		if (localVm.diagnosisFiles.length > 0) { // get the diagnosis files if available
			for (var i =0;i< localVm.diagnosisFiles.length;i++) {
				fd.append("document[" + fileCount + "].docCategory", 'Diagnosis File');
				fd.append("document[" + fileCount + "].docType", "diagnosis-file");
				fd.append("document[" + fileCount + "].patientFile",  vm.formData.diagnosisFiles[i]);
				fileCount++;
			}
		}

		if (vm.formData.ageProofFile) { // get age proof file 
			fd.append("document[" + fileCount + "].docCategory", 'Age Proof');
			fd.append("document[" + fileCount + "].docType", localVm.ageProof);
			fd.append("document[" + fileCount + "].patientFile",  vm.formData.ageProofFile);
			fileCount++;
		}

		if (vm.formData.incomeProofFile) { // get income tax file 
			fd.append("document[" + fileCount + "].docCategory", 'Income Proof');
			fd.append("document[" + fileCount + "].docType", localVm.incomeProof);
			fd.append("document[" + fileCount + "].patientFile", vm.formData.incomeProofFile);
			fileCount++;
		}

		if (localVm.familyDetails.length > 0) { // get family details if available
			for (var i=0; i < localVm.familyDetails.length; i++) {
				fd.append("patientFamily[" + i + "].relation", localVm.familyDetails[i].relation);
				fd.append("patientFamily[" + i + "].age", localVm.familyDetails[i].age);
				fd.append("patientFamily[" + i + "].status", localVm.familyDetails[i].status);
				fd.append("patientFamily[" + i + "].income", localVm.familyDetails[i].income);
				fd.append("patientFamily[" + i + "].otherIncome", localVm.familyDetails[i].otherIncome);
			}
		}

		if (localVm.organisation.length > 0) { // get family details if available
			for (var i=0; i < localVm.organisation.length; i++) {
				if(localVm.organisation[i].name)
					fd.append("organisation[" + i + "].name", localVm.organisation[i].name);
				if(localVm.organisation[i].amountRec)
					fd.append("organisation[" + i + "].amountRec", localVm.organisation[i].amountRec);
			}
		}

		delete localVm.diagnosisFiles;
		delete localVm.diagnosisFilesNames;
		delete localVm.profilePicSrc;
		delete localVm.profileImage;
		delete localVm.organisation;
		delete localVm.familyDetails;
		delete localVm.ageProof;
		delete localVm.incomeProof;

		angular.forEach(localVm, function (v, k) {
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
			showRegistrationDetails(response.prn, vm.formData.profilePicSrc); // shows the summary dialog box
		});
	};
	/**
	 *  function to show patient summary on successful registration
	 */
	var showRegistrationDetails = function(prn, profileImg) {
		$("body").addClass('sidebar-collapse'); // to collapse the sidebar
		var parentEl = angular.element(document.body),
		patientPic = profileImg ? '<img src= ' + profileImg + ' />' : '<i class="fa fa-user" aria-hidden="true">';
		$mdDialog.show({
			parent: parentEl,
			template:
				'<md-dialog id="patient-reg-card" aria-label="List dialog" class="row patientRegDialogBx">' +
				'  <md-dialog-content>'+
				'  <div class="title"> Patient Registration Successful. </div>'+
				'  <div class="subtitle"> Pending Registration Number (PRN) is <b>' + prn + '</b></div>'+
				'  <table class="table table-bordered">'+       
				'	<tr>'+
				'		<td rowspan="7" class="user_img">' + patientPic + '</td>'+
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
				'    <i class="fa fa-print" aria-hidden="true" ng-click="printMe()"></i>'+
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
				initializeVars(); // clears the formFields
				vm.patientRegisterForm.$setUntouched();
				vm.patientRegisterForm.$setPristine();
				if (to == 1)
					$state.go('app.home'); // redirects to home page
				else {
					// to scroll the page
					var centerContent = document.getElementById('center-content-wrapper');
					centerContent.scrollTop -= centerContent.scrollTop; 
				}
			};
			$scope.printMe = function (){
				apiService.printScreen('patient-reg-card');
			}
		}
	};

}]);