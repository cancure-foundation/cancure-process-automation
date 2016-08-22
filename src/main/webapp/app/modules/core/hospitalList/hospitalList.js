core.controller("HospitalListController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings) {
        var vm = this;
        
        var init = function () {
        	apiService.serviceRequest({
                URL: 'hospital/list',
                errorMsg: 'Failed to fetch hospitals. Try Again!'
            }, function (response) {
            	$scope.hospitalList = response;
            })
        };
        
        vm.editHospital = function(id){
        	$state.go('app.hospital.hospitalCreate', { hospitalId: id });
        }     
            
        init();
}]);