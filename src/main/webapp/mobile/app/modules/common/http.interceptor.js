(function () {
    angular
        .module('cancure')
        .factory('httpInterceptor', httpInterceptor);

    function httpInterceptor($state) {
        return {
            request: function (config) {
                return config;
            },

            requestError: function (rejection) {
                return rejection;
            },

            response: function (response) {
                if (response.status == 401) { // redirects to login page on unauthorized access
                    $state.go('login');
                }
                return response;
            },

            responseError: function (rejection) {
                if (rejection.status == 401) { // redirects to login page on unauthorized access

                }
                return rejection;
            }
        }
    }
})();