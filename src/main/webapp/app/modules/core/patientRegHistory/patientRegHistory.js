core.controller("PatientRegHistoryController", ['$rootScope', '$scope', '$state', '$stateParams', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $stateParams, apiService, appSettings) {
    
	var vm = this;
    
	var init = function() {
	    apiService.serviceRequest({
	        URL: 'tasks/history/' + $stateParams.prn
	    }, function (response) {
	    	$scope.taskHistory = response;
	    	$scope.nextTask = response.nextTask;
	    });
    }
    
    vm.nextAction = function() {
    	$state.go('app.patientRegNextAction', { prn: $stateParams.prn });
    }
    
    init();
    
}]);

