login.controller("loginCtrl", ['$rootScope', '$scope', '$state', '$location', '$cookies', 'Flash', 'apiService', 'appSettings', '$http',

    function ($rootScope, $scope, $state, $location, $cookies, Flash, apiService, appSettings, $http) {
		var vm = this;
	
		vm.formData = {Username : 'cancure', Password : 'cancure'};
		vm.loggingIn = false;
	
		//access login
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
					URL : 'user/login/' + data.Username,
					errorMsg : 'Cannot find user ' + data.Username
				}, function (userData) {
					$cookies.put('userName', userData.name);  // sets the userName values to the cookies
					$cookies.put('roles', JSON.stringify(userData.roles));  // sets the roles values to the cookies
					$state.go('app.home'); // route to the home page
				}, function fail(fail){
					vm.formData = {}; // clears the login form data
				});			
			}, function fail(fail){
				Flash.create('danger', 'Authentication Failed. Try Again!', 'large-text');
				vm.loggingIn = false; // turns the flag off for logginIn
				vm.formData = {}; // clears the login form data				
            });
		};

}]);