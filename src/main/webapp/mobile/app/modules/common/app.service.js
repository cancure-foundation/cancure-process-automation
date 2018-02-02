(function () {
    angular.module('cancure').service('apiService', apiService);

    function apiService($rootScope, $http, $q, $state, appConfig, $mdToast, $document, $rootScope,  $timeout) {
        var $self = this;
        /**
         * function to place http request
         */
        $self.serviceRequest = function (config, success, fail) {
            var requestParams = angular.merge({
                method: config.method || "GET"
                , url: appConfig.baseURL + config.url
                , params: config.params || {}
                , data: config.data || {}
                , headers: config.headers || {
                    'Content-Type': 'application/json'
                }
            }, config.addOns);
            var request = $http(requestParams);
            request.then(function successCallback(response) {
                if (response && response.status == 200) {
                    if (success) success(response.data);
                    else {
                        if (fail) fail(response.data);
                    }
                }
                else {
                    if (fail) fail(response.data);
                }
            }, function errorCallback(response) {
                if (fail) fail(response.data);
            });
        };
        /**
         * function to place async service request
         */
        $self.asyncServiceRequest = function (params) {
            var deferred = $q.defer(); // creating the promise object
            serviceRequest(params, function (response) {
                deferred.resolve(response); // resolving the promise
            }, function (response) {
                deferred.reject(response); // rejecting the promise
            });
            return deferred.promise; // returning the promise object
        };
        /**
         * speech to text conversion
         */
        $self.speechToText = function (success, fail, configs) {
            // checks for speech recognition plugin
            if (!window.plugins || !window.plugins.speechRecognition) {
                if (fail) fail();
                return;
            }
            // start listening
            window.plugins.speechRecognition.startListening(function (result) {
                if (result) {
                    var text = result.sort(function (a, b) { // retrieves the best match
                        return b.length - a.length;
                    })[0];
                    if (success) success(text);
                }
                else {
                    if (fail) fail();
                }
            }, function (error) { // in case of error in recording
                if (fail) fail(error);
            });
        };
        /**
         *
         */
        $self.toast = function (text, param) {
            $mdToast.show($mdToast.simple().textContent(text || 'Take2').hideDelay(1500));
        };
        /**
         * function to log user out and clear session settings
         */
        $self.logout = function (param) {
            var storage = window.localStorage;
            // clear all cookies
            angular.forEach(storage, function (value, key) {
                window.localStorage.removeItem(key);
            });
            $state.go('login'); // navigate to login
        };
    }
})();