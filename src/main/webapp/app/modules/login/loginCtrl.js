login.controller("loginCtrl", ['$rootScope', '$scope', '$state', '$timeout', '$cookies', 'Flash', 'apiService', 'appSettings', '$http', '$location',

                               function ($rootScope, $scope, $state, $timeout, $cookies, Flash, apiService, appSettings, $http, $location) {
	var vm = this;


	var init = function (){
		vm.initVar();
	};
	/**
	 * 
	 */
	vm.initVar = function (){		
		vm.formData = {};
		vm.resetPass = {};
		vm.loggingIn = false;
		vm.stateLogin = true;
		vm.stateResetPassword = false;		
		vm.stateForgotPassword = false;
	}
	/**
	 * access login
	 */
	vm.login = function (data) {
		vm.loggingIn = true;
		// oauth service
		apiService.serviceRequest({
			method : 'POST',
			URL : appSettings.requestURL.authRequest + '?password='+ data.Password +'&username='+ data.Username +'&grant_type=password&scope=read%20write&client_secret=cancure123456&client_id=cancureapp',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=utf-8',
				'Authorization' : 'Basic Y2FuY3VyZWFwcDpjYW5jdXJlMTIzNDU2'
			},
			errorMsg : 'Unable to Authenticate. Try Again!'
		}, function (success){					
			$http.defaults.headers.common.Authorization = 'Bearer ' + success.access_token; // sets the access token for all http request
			$cookies.put('access_token', success.access_token); // sets the access_token values to the cookies
			// login service
			apiService.serviceRequest({
				URL : 'user/whoami',
				errorMsg : 'Cannot find user ' + data.Username
			}, function (userData) {
				$cookies.put('userName', userData.name);  // sets the userName values to the cookies
				$cookies.put('roles', JSON.stringify(userData.roles));  // sets the roles values to the cookies
				vm.userDetails = userData;

				if (userData.firstLog) { // checks if user is login in for the first time
					vm.stateLogin = false;
					vm.stateResetPassword = true;		
					vm.loggingIn = false;
				} else if (!appSettings.redirectToUrlAfterLogin || appSettings.redirectToUrlAfterLogin == '') {
					$state.go('app.home'); // route to the home page
				} else {
					var redirectUrl = appSettings.redirectToUrlAfterLogin;
					appSettings.redirectToUrlAfterLogin = '';
					$location.path(redirectUrl);
				}
				
			}, function fail(fail){
				vm.formData = {}; // clears the login form data
			});			
		}, function fail(fail){
			vm.loggingIn = false; // turns the flag off for logginIn
			vm.formData = {}; // clears the login form data							
		});
	};
	/**
	 *  function to reset password
	 */
	vm.resetPassword = function (){	
		if (vm.resetPass.password != vm.resetPass.retypePassword){
			Flash.create('warning', 'Passwords does not match.', 'large-text');
			vm.resetPass = {};
			return;
		}
		vm.loggingIn = true;
		apiService.serviceRequest({
			URL: appSettings.requestURL.createUser,
			method: 'POST',
			payLoad: {
				password : vm.resetPass.password,
				id : vm.userDetails.id,
				email : vm.userDetails.email,
				enabled : vm.userDetails.enabled,
				login : vm.userDetails.login,
				name : vm.userDetails.name,
				roles : vm.userDetails.roles,
				firstLog : vm.userDetails.firstLog
			}
		}, function (response) {
			$state.go('app.home');
			vm.loggingIn = false; // to show the user summary div
		}, function (fail){
			vm.resetPass = {};
			vm.loggingIn = false;
		});
	};
	/**
	 *  function to reset password on forgot password scenario
	 */
	vm.forgotPassword = function (){
		vm.loggingIn = true;
		apiService.serviceRequest({
			URL: appSettings.requestURL.forgotPassword,
			method: 'POST',
			payLoad: {
				login : vm.forgotPass.login,
				email : vm.forgotPass.email
			}
		}, function (response) {
			if (response.status == "SUCCESS") {
				Flash.create('success', 'Passwords reset successful. Please check your email.', 'large-text');
				vm.loggingIn = false; // to show the user summary div
				vm.stateLogin = true; 
				vm.stateResetPassword = false; 
				vm.stateForgotPassword = false;
				vm.forgotPass = {};		
			} else {
				Flash.create('danger', 'Sorry, data provided does not match any record.', 'large-text');
				vm.forgotPass = {};			
				vm.loggingIn = false;
			}					
		}, function (fail){
			vm.forgotPass = {};			
			vm.loggingIn = false;
		});
	}

	init();

}]);