core.controller("PatientRegHistoryController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings) {

	var vm = this;

	var init = function() {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: 'tasks/history/' + $stateParams.prn
		}, function (response) {
			$scope.taskHistory = response;
			$scope.nextTask = response.nextTask;
			Loader.destroy();
		});
	}

	vm.nextAction = function() {
		$state.go('app.patientRegNextAction', { prn: $stateParams.prn });
	}

	init();

}]);