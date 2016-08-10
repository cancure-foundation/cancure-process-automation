core.controller("patientRegNextActionController", ['$rootScope', '$scope', '$state', '$stateParams', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $stateParams, apiService, appSettings) {
    
	var vm = this;
	vm.formData = {};
	 
	/*
	 *	/patientregistration/preliminaryexamination/save ROLE_HOSPITAL_POC
	 *  /patientregistration/backgroundcheck/save/ ROLE_PROGRAM_COORDINATOR
	 *  /patientregistration/mbdoctorrecommendation/save ROLE_HOSPITAL_POC
	 *  /patientregistration/secretaryrecommendation/save/ ROLE_SECRETARY
	 *  /patientregistration/executiveboardrecommendation/accept/save ROLE_EXECUTIVE_COMMITTEE
	 *  /patientregistration/executiveboardrecommendation/reject/save ROLE_EXECUTIVE_COMMITTEE
	 *  /patientregistration/Patientidcard/ ROLE_PROGRAM_COORDINATOR
	 */
    var init = function() {
	    apiService.serviceRequest({
	        URL: 'tasks/' + $stateParams.prn
	    }, function (response) {
	    	$scope.prn = $stateParams.prn;
	    	$scope.nextTaskObject = response;
	    	//getInvestigatorTypes();
	    	setupPeople();
	    });
    }
    
    /*var getInvestigatorTypes = function() {
	    apiService.serviceRequest({
	        URL: 'common/investigatorTypes'
	    }, function (response) {
	    	$scope.investigatorTypes = response;
	    	setupPeople();
	    });
    }*/
    
    var getDoctors = function() {
	    apiService.serviceRequest({
	        URL: 'doctor/list',
	        errorMsg: 'Failed to fetch user roles. Try Again!'
	    }, function (response) {
	    	$scope.doctorList = response;
	    });
    }
    
    var setupPeople = function(){
    	if ($scope.nextTaskObject.nextTask == 'MBDoctorApproval' || 
	    		$scope.nextTaskObject.nextTask == 'PreliminaryExamination') {
    		
    		getDoctors();
	    	
	    }
	    
	    if ($scope.nextTaskObject.nextTask == 'PreliminaryExamination' || 
	    		$scope.nextTaskObject.nextTask == 'BackgroundCheck') {
	    	$scope.uploadNeeded = true;
	    }
    } 
    
    
     vm.submitTask = function() {
    	var url = '';
    	if ($scope.nextTaskObject.nextTask == 'PreliminaryExamination'){
    		url = '/patientregistration/preliminaryexamination/save';
    	} else if ($scope.nextTaskObject.nextTask == 'BackgroundCheck') {
    		url = '/patientregistration/backgroundcheck/save/'; //status
    	} else if ($scope.nextTaskObject.nextTask == 'MBDoctorApproval') {
    		url = '/patientregistration/mbdoctorrecommendation/save';
    	} else if ($scope.nextTaskObject.nextTask == 'SecretaryApproval') {
    		url = '/patientregistration/secretaryrecommendation/save/'; //status
    	} else if ($scope.nextTaskObject.nextTask == 'ECApproval') {
    		url = '/patientregistration/executiveboardrecommendation/'; //accept/save or reject/save
    	} else if ($scope.nextTaskObject.nextTask == 'PatientIDCardGeneration') {
    		url = '/patientregistration/Patientidcard/'; //prn
    	} else {
    		
    	}
    	
    	var serverData = angular.copy(vm.formData);
    	serverData.patientInvestigationBean = {}
    	serverData.patientInvestigationBean.prn = $scope.prn;
    	serverData.patientInvestigationBean.investigatorType = 'Program Coordinator'; //Secretary, Executive Committee, Doctor
    	serverData.patientInvestigationBean.investigatorId = serverData.people;
    	serverData.patientInvestigationBean.status = '';
    	serverData.patientInvestigationBean.comments = serverData.comments;
    	//JSON.stringify().
    	alert(url);
    	alert(JSON.stringify(serverData));
    	
    }
    
    init();
    
}]);

