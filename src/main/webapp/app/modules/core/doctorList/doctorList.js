core.controller("DoctorListController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings) {
        var vm = this;
        
        var init = function () {
        	apiService.serviceRequest({
                URL: 'doctor/list',
                errorMsg: 'Failed to fetch Doctors. Try Again!'
            }, function (response) {
            	$scope.doctorList = response;
            })
        };
        
        vm.editDoctor = function(id){
        	$state.go('app.doctor.doctorCreate', { doctor_id: id });
        }     
            
        init();
}]);