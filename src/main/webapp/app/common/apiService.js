app.service('apiService', ['$http', '$q', '$state', '$cookies', 'appSettings', 'Flash', function ($http, $q,  $state, $cookies, appSettings, Flash) {

    var apiService = {};

    // function to place http request
    var serviceRequest = function (params, success, fail) {

        var request = $http({
            method: params.method || "GET",
            url: appSettings.baseURL + params.URL,
            data: params.payLoad || {},
            headers: params.headers || {
                'Content-Type': 'application/json'
            }
        });
        // success function
        request.success(function (response) {
            success(response);
        });
        // success function
        request.error(function (response) {
            Flash.create('danger', (params.errorMsg) ? params.errorMsg : 'Action Failed. Try Again!', 'large-text');
            (fail) ? fail(response): null;
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
    }

    // function to be called on logout
    var logoutAction = function (){
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
    
    apiService.serviceRequest = serviceRequest; // function to place http request
    apiService.asyncServiceRequest = asyncServiceRequest; // function to place async service request
    apiService.logoutAction = logoutAction; // function to be called on logout

    return apiService;

}]);