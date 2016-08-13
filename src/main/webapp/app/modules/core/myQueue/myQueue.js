core.controller("myQueueController", ['$scope', '$http', '$location', '$state', 'apiService', 'appSettings',
                                      function ($scope, $http, $location, $state, apiService, appSettings) {

	var init = function (){
		apiService.serviceRequest({
			URL: 'tasks/my'
		}, function (response) {
			$scope.tasks = response;
		});
	};
	
	init();

	$scope.changeView = function(prn){
		$state.go('app.patientRegHistory', { prn: prn });
	}
}]);