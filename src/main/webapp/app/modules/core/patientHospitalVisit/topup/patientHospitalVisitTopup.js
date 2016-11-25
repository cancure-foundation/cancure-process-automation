core.controller("PatientHospitalVisitTopupController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	
	var init = function() {
		
		searchUser();
		
	};
	
	var searchUser = function(){
		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit/' + $stateParams.patientVisitId,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			if (response == null) {
				vm.noSearchResult = true;
			} else {
				vm.patient = response;
				vm.noSearchResult = false;
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
		
	};
	
	vm.doTopup = function(approve){
		alert(approve);
		
		if (approve) {
			if (vm.pharmacyAmount == null || vm.hospitalAmount == null || isNaN(vm.pharmacyAmount) || 
					isNaN(vm.hospitalAmount)) {
				Flash.create('danger', 'Please provide proper amounts.', 'large-text');
				return;
			}
			
			if (parseInt(vm.pharmacyAmount) < 0 || parseInt(vm.pharmacyAmount) < 0) {
				Flash.create('danger', 'Please provide positive amounts.', 'large-text');
				return;
			}
		}
			
		var serverData = {};
		serverData.pidn = vm.patient.patientBean.pidn;
		serverData.patientVisitId = $stateParams.patientVisitId
		
		if (approve) {
			serverData.patientApproval = [];
			var hosAmount = {};
			hosAmount.pidn = vm.patient.patientBean.pidn;
			hosAmount.amount = vm.hospitalAmount; //vm.pharmacyAmount;
			hosAmount.approvedForAccountTypeId = 5; // Hospital
			serverData.patientApproval.push(hosAmount);
			
			var pharmaAmount = {};
			pharmaAmount.pidn = vm.patient.patientBean.pidn;
			pharmaAmount.amount = vm.pharmacyAmount;
			pharmaAmount.approvedForAccountTypeId = 3; // Hospital
			serverData.patientApproval.push(pharmaAmount);
		}
		
		serverData.status = approve ? "TRUE" : "FALSE";
		
		// Submit /patientvisit/topup
		// making the server call
		apiService.serviceRequest({
			URL: '/patientvisit/topup',
			payLoad: serverData,
			method: 'POST',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			alert(response);
			if (response == null) {
				vm.noSearchResult = true;
			} else {
				vm.noSearchResult = false;
			}
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
		
	}
	
	init();

}]);