core.controller("myQueueController", ['$scope', 'Loader', '$state', 'apiService', 'appSettings',
                                      function ($scope, Loader, $state, apiService, appSettings) {

	var init = function (){
		$scope.tasks = [];
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: appSettings.requestURL.myQueue
		}, function (response) {
			$scope.tasks = response;
			Loader.destroy();
		});
	};

	init();	
}]);