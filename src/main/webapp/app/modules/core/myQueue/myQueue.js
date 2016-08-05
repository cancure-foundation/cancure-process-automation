core.controller("myQueueController", ['$scope', '$http', '$location', '$state', 'apiService', 'appSettings',

	function ($scope, $http, $location, $state, apiService, appSettings) {
		var roleStr = '';
		for (var i=0; i < appSettings.roles.length; i++){
			roleStr += (appSettings.roles[i].name + '__');
		}
		apiService.serviceRequest({
			URL: 'tasks/role/' + roleStr
	    }, function (response) {
	    	$scope.tasks = response;
	    });
		
	    $scope.changeView = function(prn){
	    	$state.go('app.patientRegHistory', { prn: prn });
		}
	}
]);
