app.service('apiService', ['$http', '$q', 'appSettings', 'Flash', function ($http, $q, appSettings, Flash) {

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

    apiService.serviceRequest = serviceRequest; // function to place http request
    apiService.asyncServiceRequest = asyncServiceRequest; // function to place async service request

    return apiService;

}]);