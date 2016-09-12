errorPage.controller("sessionExpCtrl", ['$rootScope', '$scope', '$state', 'apiService',

    function ($rootScope, $scope, $state, apiService) {
		var vm = this;		
		var init = function (){
			vm.expiryTime = new Date();
			apiService.logoutAction('sessionExpired');
		};
		
		init();
}]);