core.controller("patientRegNextActionController", ['$rootScope', '$scope', '$state', '$stateParams', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $stateParams, apiService, appSettings) {
    
	var vm = this;
    
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
	    	getInvestigatorTypes();
	    });
    }
    
    var getInvestigatorTypes = function() {
	    apiService.serviceRequest({
	        URL: 'common/investigatorTypes'
	    }, function (response) {
	    	$scope.investigatorTypes = response;
	    	setupPeople();
	    });
    }
    
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
    
    init();
    
}]);

