core.controller("PatientRegHistoryController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;

	var init = function() {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: 'tasks/history/' + $stateParams.prn
		}, function (response) {
			vm.tasks = [];
			
			vm.nextTask = {
				name : response.nextTask,
				roles : response.Owner
			};
			if (vm.nextTask.name == "Rejected")
				vm.patientReject = true;
			
			// iteration for first task
			if (response.tasks[0]) {
				vm.regDetails = response.tasks[0];
				vm.patientDetails = vm.regDetails.patient;
				vm.regDocument = vm.patientDetails.document;
				vm.patientFamily = [];
				vm.supportOrg = [];
				vm.otherDouments = [];
				// iterating through patient documents
				for(var i = 0; i<vm.regDocument.length; i++) {
					if (vm.regDocument[i].docCategory == "Profile Image") {
						vm.profileSrc = appSettings.baseURL + 'files/' + vm.regDocument[i].docId;
					} else if (vm.regDocument[i].docCategory == "Diagnosis File") {
						vm.otherDouments.push({
							docCategory : vm.regDocument[i].docCategory,
							docType : vm.regDocument[i].docType,
							docSrc : appSettings.baseURL + 'files/' + vm.regDocument[i].docId
						});
					} else  if (vm.regDocument[i].docCategory == "Income Proof") {
						vm.otherDouments.push({
							docCategory : vm.regDocument[i].docCategory,
							docType : vm.regDocument[i].docType,
							docSrc : appSettings.baseURL + 'files/' + vm.regDocument[i].docId
						});
					} else if (vm.regDocument[i].docCategory == "Aadhar Card") {
						vm.otherDouments.push({
							docCategory : vm.regDocument[i].docCategory,
							docType : vm.regDocument[i].docType,
							docSrc : appSettings.baseURL + 'files/' + vm.regDocument[i].docId
						});
					}
				}
				// iterating through patient details
				vm.patientFamily = vm.patientDetails.patientFamily;
				
				// iterating through support organisation details
				vm.supportOrg = vm.patientDetails.organisation;
			}
			// iteration for rest tasks
			for(var i=1; i< response.tasks.length; i++){
				var currentTask = response.tasks[i];
				vm.tasks.push({
					description : currentTask.description,
					nextTask : currentTask.nextTask,
					endTime : currentTask.endTime,
					documents : currentTask.documents,
					investigation : angular.equals({}, currentTask.investigation) ? null : currentTask.investigation
				});
				vm.nextTask.description = currentTask.description;
			}
			
			if (!vm.tasks[vm.tasks.length-1].endTime)
				vm.tasks.pop();
			
			$timeout(function (){
				vm.viewMorePtDetails();
				Loader.destroy();
			}, 1000);
		});
	};
	/**
	 *  function to proceed to next task
	 */
	vm.nextAction = function(checkAccess) {
		var hasAccess = false; // flag to indicate if access to next step is there
		for (var i=0; i < appSettings.rolesList.length; i++){ // checks if access to next step is there
			var role = appSettings.rolesList[i];
			if (vm.nextTask.roles.indexOf(role) >= 0){
				hasAccess = true;
				break;
			}
		};
		if (checkAccess) {
			return hasAccess;
		}
		if (hasAccess)
			$state.go('app.patientRegNextAction', { prn: $stateParams.prn });
		else
			Flash.create('danger', 'You do not have access to proceed.' , 'large-text');
	};
	/**
	 *  function to set additional details section toggle collapse
	 */
	vm.viewMorePtDetails = function (){
		vm.isExpandedInfo = !vm.isExpandedInfo;
		$('#patientInfo-expandable').slideToggle();
	};

	init();

}]);