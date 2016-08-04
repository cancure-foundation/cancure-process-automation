﻿core.controller("PatientRegHistoryController", ['$rootScope', '$scope', '$state', '$stateParams', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $stateParams, apiService, appSettings) {
    
	var vm = this;
    
    apiService.serviceRequest({
        URL: 'tasks/history/' + $stateParams.prn
    }, function (response) {
    	$scope.taskHistory = response;
    });
    
}]);

