core.controller("myQueueController", ['$scope', '$http', '$location', 

	function ($scope, $http, $location) {
	
		apiService.serviceRequest({
	        URL: '/tasks/role/ROLE_PROGRAM_COORDINATOR'
	    }, function (response) {
	    	$scope.tasks = data;
	    });
		
	    $scope.changeView = function(location){
	    	$location.path(location);
		}
	}
]);
