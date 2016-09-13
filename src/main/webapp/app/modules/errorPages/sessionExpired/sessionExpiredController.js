errorPage.controller("sessionExpCtrl", ['$rootScope', '$scope', '$state', 'apiService', 'Idle',

                                        function ($rootScope, $scope, $state, apiService, Idle) {
	var vm = this;		
	var init = function (){			
		vm.expiryTime = new Date();
		apiService.logoutAction('sessionExpired');
	};

	init();
}]);