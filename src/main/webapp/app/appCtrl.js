app.controller("appCtrl", ['$rootScope', '$scope', '$state', '$http', '$cookies', 'Flash', 'appSettings', 'apiService', '$mdDialog', '$mdMedia',
                           function ($rootScope, $scope, $state, $http, $cookies, Flash, appSettings, apiService, $mdDialog, $mdMedia) {

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
			
			// to scroll the page to top so that previous page scroll is not retained			
			var centerContent = document.getElementById('center-content-wrapper');
			if (centerContent)
				centerContent.scrollTop -= centerContent.scrollTop; // scrolls each view to top on state change

			// to remove any flash messages from the previous screen			
			Flash.dismiss();
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
	/**
	 * 
	 */
	vm.showAlert = function($event) {
		var parentEl = angular.element(document.body);
		$mdDialog.show({
			parent: parentEl,
			targetEvent: $event,
			template:
				  '<md-dialog aria-label="List dialog" class="row roleDialogBx">' +
		           '  <md-dialog-content>'+
		           '  <div class="username"> User Details </div>'+
		           '  <table class="table table-bordered">'+
                   '	<th class="col-xs-4">Name</th>'+          
                   '	<th class="col-xs-4">Roles</th>'+          
                   '		<tr>'+
                   '		<td class="col-xs-4">'+ appSettings.loginUserName +'</td>'+
                   '		<td class="col-xs-4">'+
                   '      		<div ng-repeat="item in items">'+                        
                   '        			{{item.name}}'+                         
                   '             </div>'+
                   '        </td>'+
                   '    </tr>'+
                   '</table>'+
		           '  </md-dialog-content>' +
		           '  <md-dialog-actions>' +
		           '    <md-button ng-click="closeDialog()" class="md-primary">' +
		           '      Close' +
		           '    </md-button>' +
		           '  </md-dialog-actions>' +
		           '</md-dialog>',
				locals: {
					items: appSettings.roles
				},
				controller: DialogController
		});
		
		function DialogController($scope, $mdDialog, items) {
			$scope.items = items;
			$scope.closeDialog = function() {
				$mdDialog.hide();
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