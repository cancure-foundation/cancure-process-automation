app.factory('httpInterceptor', ['$q', 'appSettings', function ($q, appSettings) {

    var httpInterceptor = {};

    // function to intercept all http response to the application
    var response = function (param) {
    	return param;
    };
    
    // function to intercept all http request from the application
    var request = function (param) {
    	return param;
    };
    
    httpInterceptor.response = response;
    httpInterceptor.request = request;
    
    return httpInterceptor;

}]);