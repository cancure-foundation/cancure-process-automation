core.controller("UserListController", ['$scope', '$state', 'Flash', 'apiService', 'appSettings', 'Loader',
                                       function ($scope, $state, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.userList = [];

	var init = function () {
		Loader.create('Fetching Data. Please wait');
		apiService.serviceRequest({
			URL: appSettings.requestURL.userList,
			errorMsg: 'Failed to fetch user roles. Try Again!'
		}, function (response) {
			$scope.userList = response; // assigns the roles to roles object in the scope
			setTimeout(function () {
				$(document).ready(
						function () {
							$('td div').slideUp();
							$('td role').click(
									function () {
										$(this).siblings('div').slideToggle();
									}
							);
						}
				);
			}, 100);
			Loader.destroy();
		});
	}
	// init function, execution starts here
	init();

	vm.editUser = function(userDetails){
		for (var i = 0; i < userDetails.roles.length; i++) {
			if (userDetails.roles[i].id == 5) {
				userDetails.isDoctor = true; // shows the doctor details tab				
			}
			if (userDetails.roles[i].id == 4) {
				userDetails.isHpoc = true; // shows the hpoc details tab				
			}
		}
		$scope.$parent.vm.currentNavItem  = "app.manageUser.createUser";
		$state.go('app.manageUser.createUser', { user: JSON.stringify(userDetails) });
	}

}]);