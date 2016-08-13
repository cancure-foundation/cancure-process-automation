core.controller("doctorController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings) {
        var vm = this;
        
        var init = function () {
        	apiService.serviceRequest({
                URL: 'doctor/list',
                errorMsg: 'Failed to fetch doctors. Try Again!'
            }, function (response) {
            	$scope.doctorList = response;
            })
            };
        
        init();

}]);