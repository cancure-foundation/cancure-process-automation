core.controller("HpocMappingController", ['$q', '$timeout', '$scope', '$state', 'Loader', 'apiService', 'appSettings', 'Flash',
                                          function ($q, $timeout, $scope, $state, Loader, apiService, appSettings, Flash) {
	var vm = this;

	var init = function () {
		Loader.create('Fetching Data. Please wait');

		var hpocMappedList = apiService.asyncServiceRequest({URL : appSettings.requestURL.hospitalHpoc}); // for getting already mapped data

		$q.all([hpocMappedList]).then(function (success){
			vm.hospitalHpocList = success[0];
			Loader.destroy();		
		});
	};   
	init();
}]);