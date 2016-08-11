core.controller("patientRegNextActionController", ['$rootScope', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', 'Loader',
function ($rootScope, $scope, $state, $stateParams, apiService, appSettings, Loader) {
    
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
    	var serverData = angular.copy(vm.formData);
    	serverData.patientInvestigationBean = {};
    	serverData.patientInvestigationBean.prn = $scope.prn;
    	//serverData.patientInvestigationBean.investigatorType = 'Program Coordinator'; //Secretary, Executive Committee, Doctor
    	serverData.patientInvestigationBean.investigatorId = serverData.people;
    	//serverData.patientInvestigationBean.status = '';
    	serverData.patientInvestigationBean.comments = serverData.comments;
    	
    	var url = '';
    	
    	//alert(JSON.stringify(serverData));
    	
    	if ($scope.nextTaskObject.nextTask == 'PreliminaryExamination'){
    		url = 'patientregistration/preliminaryexamination/save';
    		multipartSubmit(url);
    	} else if ($scope.nextTaskObject.nextTask == 'BackgroundCheck') {
    		url = 'patientregistration/backgroundcheck/save/'; //status
    		multipartSubmit(url);
    	} else if ($scope.nextTaskObject.nextTask == 'MBDoctorApproval') {
    		url = 'patientregistration/mbdoctorrecommendation/save';
    	} else if ($scope.nextTaskObject.nextTask == 'SecretaryApproval') {
    		url = 'patientregistration/secretaryrecommendation/save/'; //status
    	} else if ($scope.nextTaskObject.nextTask == 'ECApproval') {
    		url = 'patientregistration/executiveboardrecommendation/'; //accept/save or reject/save
    	} else if ($scope.nextTaskObject.nextTask == 'PatientIDCardGeneration') {
    		url = 'patientregistration/Patientidcard/'; //prn
    	} else {
    		
    	}
    	
    	
    	//JSON.stringify().
    	//alert(url);
    	//alert(JSON.stringify(serverData));
    	
    }
    
    var multipartSubmit = function(url) {
    	var fd = new FormData();
    	
    	fd.append("patientInvestigationBean.prn", $scope.prn);
    	fd.append("patientInvestigationBean.investigatorId", vm.formData.people);
    	fd.append("patientInvestigationBean.comments", vm.formData.comments);
    	
    	alert($scope.patientFile);
    	return;
    	if ($scope.patientFile) {
	    	fd.append("patientDocumentBean[0].prn", $scope.prn); 
	    	fd.append("patientDocumentBean[0].docCategory", 'Preliminary Examination');
	    	fd.append("patientDocumentBean[0].docType", 'Report');
	    	fd.append("patientDocumentBean[0].patientFile",  $scope.patientFile);
    	}
    	//alert('patientInvestigationBean.prn ' +  fd.get('patientInvestigationBean.prn'));
    	
    	// making the server call
        apiService.serviceRequest({
            URL: url,
            method: 'POST',
            payLoad: fd,
            transformRequest : angular.identity,
            headers: {
            	'Content-Type': undefined
            }
        }, function (response) {
        	Loader.destroy();
            Flash.create('success', 'Completed Successfully.', 'large-text');
            vm.formData = {};
            
        });
    }
     
    init();
    
}]);

