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
	        URL: 'doctor/hpoclist',
	        errorMsg: 'Failed to fetch user roles. Try Again!'
	    }, function (response) {
	    	$scope.doctorList = response;
	    });
    }
    
    var setupPeople = function(){
    	if ($scope.nextTaskObject.nextTask == 'MB Doctor Approval' || 
	    		$scope.nextTaskObject.nextTask == 'Preliminary Examination' || 
	    		$scope.nextTaskObject.nextTask == 'Preliminary Examination Clarification') {
    		
    		getDoctors();
	    	
	    }
	    
	    if ($scope.nextTaskObject.nextTask == 'Preliminary Examination' || 
	    		$scope.nextTaskObject.nextTask == 'Background Check') {
	    	$scope.uploadNeeded = true;
	    }
	    
	    
	    if ($scope.nextTaskObject.nextTask == 'Background Check') {
	    	vm.statuses = [{'id': 'PASS', 'name' : 'Pass'}, {'id': 'FAIL', 'name' : 'Fail'}];
	    }
	    
	    if ($scope.nextTaskObject.nextTask == 'Secretary Approval') {
	    	vm.statuses = [{'id': 'Approved', 'name' : 'Approve'}, {'id': 'Recommend', 'name' : 'Forward to EC'},
	    	               {'id': 'Reject', 'name' : 'Reject'}, {'id': 'SendBackToPC', 'name' : 'Need background check clarification'},
	    	               {'id': 'prelimExamClarificationReqd', 'name' : 'Need preliminary exam clarification'}];
	    }
	    
	    if ($scope.nextTaskObject.nextTask == 'EC Approval') {
	    	vm.statuses = [{'id': 'accept/save', 'name' : 'Approve'}, {'id': 'reject/save', 'name' : 'Reject'}];
	    }
    } 
    
    
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
    	if ($scope.nextTaskObject.nextTask == 'Preliminary Examination'){
    		
    		url = 'patientregistration/preliminaryexamination/save';
    		prefix = 'patientInvestigationBean.';
    		
    	} else if ($scope.nextTaskObject.nextTask == 'Background Check') {
    		url = 'patientregistration/backgroundcheck/save/' + vm.formData.status; //status
    	} else if ($scope.nextTaskObject.nextTask == 'MB Doctor Approval') {
    		url = 'patientregistration/mbdoctorrecommendation/save';
    	} else if ($scope.nextTaskObject.nextTask == 'Secretary Approval') {
    		url = 'patientregistration/secretaryrecommendation/save/' + vm.formData.status; //status
    	} else if ($scope.nextTaskObject.nextTask == 'Background Clarification') {
    		url = 'patientregistration/bgcheckclarification/save/';
    	} else if ($scope.nextTaskObject.nextTask == 'Preliminary Examination Clarification') {
    		url = 'patientregistration/preliminaryexaminationclarification/save/';
    	} else if ($scope.nextTaskObject.nextTask == 'EC Approval') {
    		url = 'patientregistration/executiveboardrecommendation/' + vm.formData.status; //accept/save or reject/save
    	} else if ($scope.nextTaskObject.nextTask == 'Patient ID Card Generation') {
    		url = 'patientregistration/patientidcard'; // + $scope.prn; //prn
    	} else {
    		
    	}
    	
    	/*console.log($scope);
    	console.log(vm.formData.people);
    	return;*/
    	
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
    	
    	
    	if (vm.patientFile) {
	    	fd.append("patientDocumentBean[0].prn", $scope.prn); 
	    	fd.append("patientDocumentBean[0].docCategory", 'Preliminary Examination');
	    	fd.append("patientDocumentBean[0].docType", 'Report');
	    	fd.append("patientDocumentBean[0].patientFile",  vm.patientFile);
    	}
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
            $state.go('app.patientRegHistory',  { prn: $scope.prn} );

        });
    }
     
    init();
    
}]);