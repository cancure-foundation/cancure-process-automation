app.service('apiService', ['$http', '$q', '$state', '$cookies', 'appSettings', 'Flash', 'Loader', function ($http, $q,  $state, $cookies, appSettings, Flash, Loader) {

	var apiService = {};

	// function to place http request
	var serviceRequest = function (params, success, fail) {

		var requestParams = angular.merge({
			method: params.method || "GET",
			url: appSettings.baseURL + params.URL,
			data: params.payLoad || {},
			headers: params.headers || {
				'Content-Type': 'application/json'
			}
		}, params.addOns);

		var request = $http(requestParams);
		// success function
		request.success(function (response) {
			success(response);
		});
		// success function
		request.error(function (response) {
			(fail) ? fail(response): null;
			Loader.destroy();
			if (!params.hideErrMsg)
				Flash.create('danger', (params.errorMsg) ? params.errorMsg : 'Action Failed. Try Again!', 'large-text');
		});

	};

	// function to place async service request
	var asyncServiceRequest = function (params) {
		var deferred = $q.defer(); // creating the promise object

		serviceRequest(params, function (response) {
			deferred.resolve(response); // resolving the promise
		}, function (response) {
			deferred.reject(response); // rejecting the promise
		});

		return deferred.promise; // returning the promise object
	};

	// function to be called on logout
	var logoutAction = function (){
		serviceRequest({
			URL: appSettings.requestURL.logout,
			hideErrMsg : true
		}, function (response) {
			cleanupLoginSettings();
		}, function (response) {
			cleanupLoginSettings();
		});    	
		function cleanupLoginSettings(){
			// removes all cookies
			var cookies = $cookies.getAll();
			angular.forEach(cookies, function (value, key) {
				$cookies.remove(key);
			});
			// removes all appSettings
			appSettings.access_token = undefined; // clears access_token
			appSettings.loginUserName = undefined;  // clears loginUserName
			appSettings.roles = undefined;  // clears roles
			delete $http.defaults.headers.common.Authorization;  // clears Authorization header
			$state.go('login'); // route to the login page    		
		}
	};

	// function to set the center wrapper height so that UI does not fall when content is less
	var adjustScreenHeight = function (){
		var windowHeight = Math.max(document.documentElement.clientHeight, window.innerHeight|| 0),
		windowWidth = Math.max(document.documentElement.clientWidth, window.innerWidth|| 0),
		headerHeight = windowWidth < 800 ? 100 : 50,
				centerContent = document.getElementById('center-content-wrapper'),
				uiViewHolder = document.getElementById('center-ui-view');    	
		if (centerContent) {
			centerContent.style.height = (windowHeight - headerHeight) + 'px'; // reduces the header height
			uiViewHolder.style.minHeight = (windowHeight - headerHeight - 50) + 'px'; // reduces the header and footer height along with 1px as a work around
		}
	};

	apiService.serviceRequest = serviceRequest; // function to place http request
	apiService.asyncServiceRequest = asyncServiceRequest; // function to place async service request
	apiService.logoutAction = logoutAction; // function to be called on logout
	apiService.adjustScreenHeight = adjustScreenHeight; // function to set the center wrapper height so that UI does not fall when content is less

	return apiService;

}]);