core.controller("patientRegNextActionController", ['$rootScope', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', 'Loader', 'Flash',
function ($rootScope, $scope, $state, $stateParams, apiService, appSettings, Loader, Flash) {
    
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
	    
	    
	    if ($scope.nextTaskObject.nextTask == 'BackgroundCheck') {
	    	vm.statuses = [{'id': 'PASS', 'name' : 'Pass'}, {'id': 'FAIL', 'name' : 'Fail'}];
	    }
	    
	    if ($scope.nextTaskObject.nextTask == 'SecretaryApproval') {
	    	vm.statuses = [{'id': 'Recommend', 'name' : 'Forward to EC'}, {'id': 'Approved', 'name' : 'Approve'},
	    	               {'id': 'Reject', 'name' : 'Reject'}, {'id': 'SendBackToPC', 'name' : 'Need Clarification'}];
	    }
	    
	    if ($scope.nextTaskObject.nextTask == 'ECApproval') {
	    	vm.statuses = [{'id': 'accept/save', 'name' : 'Approve'}, {'id': 'Rejected', 'name' : 'Reject'}];
	    }
    };
    
    
     vm.submitTask = function() {
    	/*var serverData = angular.copy(vm.formData);
    	serverData.patientInvestigationBean = {};
    	serverData.patientInvestigationBean.prn = $scope.prn;
    	//serverData.patientInvestigationBean.investigatorType = 'Program Coordinator'; //Secretary, Executive Committee, Doctor
    	serverData.patientInvestigationBean.investigatorId = serverData.people;
    	//serverData.patientInvestigationBean.status = '';
    	serverData.patientInvestigationBean.comments = serverData.comments;
    	*/
    	Loader.create('Please wait ...');
    
    	var url = '';
    	/*if (!vm.formData.status) {
    		alert('Please select a status');
    	}*/
    	//return;
    	
    	//alert(JSON.stringify(serverData));
    	
    	var prefix = '';
    	if ($scope.nextTaskObject.nextTask == 'PreliminaryExamination'){
    		
    		url = 'patientregistration/preliminaryexamination/save';
    		prefix = 'patientInvestigationBean.';
    		
    	} else if ($scope.nextTaskObject.nextTask == 'BackgroundCheck') {
    		url = 'patientregistration/backgroundcheck/save/' + vm.formData.status; //status
    	} else if ($scope.nextTaskObject.nextTask == 'MBDoctorApproval') {
    		url = 'patientregistration/mbdoctorrecommendation/save';
    	} else if ($scope.nextTaskObject.nextTask == 'SecretaryApproval') {
    		url = 'patientregistration/secretaryrecommendation/save/' + vm.formData.status; //status
    	} else if ($scope.nextTaskObject.nextTask == 'ECApproval') {
    		url = 'patientregistration/executiveboardrecommendation/' + vm.formData.status; //accept/save or reject/save
    	} else if ($scope.nextTaskObject.nextTask == 'PatientIDCardGeneration') {
    		url = 'patientregistration/Patientidcard/' + $scope.prn; //prn
    	} else {
    		
    	}
    	
    	/*console.log($scope);
    	console.log($scope.patientFile);
    	return;
    	*/
    	return
    	multipartSubmit(url, prefix);
    	
    	//JSON.stringify().
    	//alert(url);
    	//alert(JSON.stringify(serverData));
    	
    }
    
    var multipartSubmit = function(url, prefix) {
    	var fd = new FormData();
    	
    	fd.append(prefix + "prn", $scope.prn);
    	fd.append(prefix + "investigatorId", vm.formData.people);
    	fd.append(prefix + "comments", vm.formData.comments);
    	
    	
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

