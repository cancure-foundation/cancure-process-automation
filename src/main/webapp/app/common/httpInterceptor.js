app.factory('httpInterceptor', ['$q', 'appSettings', function ($q, appSettings) {

    var httpInterceptor = {};

    var response = function (param) {
    	return param;
    };
    
    var request = function (param) {
    	return param;
    };
    
    httpInterceptor.response = response;
    httpInterceptor.request = request;
    
    return httpInterceptor;

}]);