core.controller("myQueueController", ['$scope', '$http', '$location', '$state', 'apiService', 'appSettings',

	function ($scope, $http, $location, $state, apiService, appSettings) {
		apiService.serviceRequest({
	        URL: 'tasks/role/ROLE_PROGRAM_COORDINATOR'
	    }, function (response) {
	    	$scope.tasks = response;
	    });
		
	    $scope.changeView = function(prn){
	    	$state.go('app.patientRegHistory', { prn: prn });
		}
	}
]);
