app.factory('httpInterceptor', [ 'appSettings', 'Flash','$injector',  function ( appSettings, Flash, $injector) {

    var httpInterceptor = {};

    // function to intercept all http response to the application
    var response = function (param) {
    	return param;
    };
    
    // function to intercept all http response error from the application
    var responseError  = function (param) {
    	if (param.status == 401){
    		Flash.create('danger', 'Invalid Session. Please Log in.' , 'large-text');
    		setTimeout(function (){
    			$injector.get('apiService').logoutAction();
    		}, 1000);
    	} 
    	return param;
    };
    
    // function to intercept all http request from the application
    var request = function (param) {
    	return param;
    };
    
    // function to intercept all http request from the application
    var requestError  = function (param) {
    	return param;
    };
    
    httpInterceptor.response = response;
    httpInterceptor.responseError = responseError;
    httpInterceptor.request = request;
    httpInterceptor.requestError = requestError;

    
    return httpInterceptor;

}]);

