app.controller("appCtrl", ['$rootScope', '$scope', '$state', '$http', '$cookies', 'Flash', 'appSettings', 'apiService',
                           function ($rootScope, $scope, $state, $http, $cookies, Flash, appSettings, apiService) {

	var vm = this;  
	/**
	 * exection starts with the init function
	 */
	var init = function (){  
		if ($cookies.get('userName')) {
			appSettings.loginUserName = $cookies.get('userName'); // sets the userName to app settings
			appSettings.roles = JSON.parse($cookies.get('roles')); // sets the roles to app settings
			appSettings.access_token = $cookies.get('access_token'); // sets the access token to app settings
			$http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get('access_token'); // sets the access token for all http request
		} else {
			Flash.create('warning', 'Please log in !','large-text');
			$state.go('login');
			return;
		}
		
		// to set the height for center content so as to enable the scroll
		angular.element(window).bind('resize', function(){
			apiService.adjustScreenHeight();
		});
		apiService.adjustScreenHeight();
		
		$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){ 
			var centerContent = document.getElementById('center-content-wrapper');
			if (centerContent)
				centerContent.scrollTop -= centerContent.scrollTop; // scrolls each view to top on state change
		});

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

	//set the Layout in normal view
	vm.setLayout = function (value) {
		$rootScope.layout = value;
	};
	
	//set the theme selected
    vm.setTheme = function (value) {
        $rootScope.theme = value;
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