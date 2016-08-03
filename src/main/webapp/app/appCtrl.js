app.controller("appCtrl", ['$rootScope', '$scope', '$state', '$location', '$cookies', 'Flash', 'appSettings', 'apiService',
                           function ($rootScope, $scope, $state, $location, $cookies, Flash, appSettings, apiService) {

	var vm = this;  
	/**
	 * exection starts with the init function
	 */
	var init = function (){    	
		if ($cookies.get('userName')) {
			appSettings.loginUserName = $cookies.get('userName'); // sets the userName to app settings
			appSettings.roles = JSON.parse($cookies.get('roles')); // sets the roles to app settings
			appSettings.access_token = $cookies.get('access_token'); // sets the access token to app settings
		} else {
			Flash.create('warning', 'Please log in !','large-text');
			$state.go('login');
			return;
		}

		vm.loginUserName = appSettings.loginUserName;
		vm.roles = appSettings.roles;
		
		$rootScope.theme = appSettings.theme;
		$rootScope.layout = appSettings.layout;

		//avalilable themes
		vm.themes = appSettings.themeList;

		//available layouts
		vm.layouts = appSettings.layoutList;

		//Main menu items of the dashboard
		vm.menuItems = appSettings.menuList;
	}

	//set the theme selected
	vm.setTheme = function (value) {
		$rootScope.theme = value;
	};

	//set the Layout in normal view
	vm.setLayout = function (value) {
		$rootScope.layout = value;
	};
	
	//control sidebar open & close in mobile and normal view
	vm.sideBar = function (value) {
		if ($(window).width() <= 767) {
			if ($("body").hasClass('sidebar-open'))
				$("body").removeClass('sidebar-open');
			else
				$("body").addClass('sidebar-open');
		} else {
			if (value == 1) {
				if ($("body").hasClass('sidebar-collapse'))
					$("body").removeClass('sidebar-collapse');
				else
					$("body").addClass('sidebar-collapse');
			}
		}
	};
	/*
	 *  function to perform logout action
	 */
	vm.logout = function (){
		apiService.logoutAction();
	}   

	init(); //exection starts with the init function

}]);