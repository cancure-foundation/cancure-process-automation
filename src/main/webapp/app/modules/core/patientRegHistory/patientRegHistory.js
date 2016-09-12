core.controller("PatientRegHistoryController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout) {

	var vm = this;

	var init = function() {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: 'tasks/history/' + $stateParams.prn
		}, function (response) {
			vm.tasks = [];
			vm.nextTask = {
					name : response.nextTask
			};
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
					} else if (vm.regDocument[i].docCategory == "Age Proof") {
						vm.otherDouments.push({
							docCategory : vm.regDocument[i].docCategory,
							docType : vm.regDocument[i].docType,
							docSrc : appSettings.baseURL + 'files/' + vm.regDocument[i].docId
						});
					}
				}
				// iterating through patient details
				for(var i = 0; i<vm.patientDetails.patientFamily.length; i++) {
					vm.patientFamily.push({
						relation : vm.patientDetails.patientFamily[i].relation,
						age : vm.patientDetails.patientFamily[i].age,
						status : vm.patientDetails.patientFamily[i].status,
						income : vm.patientDetails.patientFamily[i].income,
						otherIncome : vm.patientDetails.patientFamily[i].otherIncome
					});
				}
				// iterating through support organisation details
				for(var i = 0; i<vm.patientDetails.organisation.length; i++) {
					vm.supportOrg.push({
						name : vm.patientDetails.organisation[i].name,
						amountRec : vm.patientDetails.organisation[i].amountRec
					});
				}
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
			//vm.tasks.pop();
			$timeout(function (){
				vm.viewMorePtDetails();
				Loader.destroy();
			}, 400);
		});
	};
	/**
	 * 
	 */
	vm.nextAction = function() {
		$state.go('app.patientRegNextAction', { prn: $stateParams.prn });
	};
	/**
	 *  function to set additional details section toggle collapse
	 */
	vm.viewMorePtDetails = function (){
		if(vm.isExpandedInfo)
			vm.isExpandedInfo = false;
		else
			vm.isExpandedInfo = true;
		$('#patientInfo-expandable').slideToggle();
	};

	init();

}]);