core.controller("PatientRegistrationController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings) {
        var vm = this;
        vm.formData = {};

        var init = function () {
          vm.formData = {};
        };

        // init function, execution starts here
        init();

        //function to handle save button click
        vm.onSend = function () {
            Flash.create('info', 'Please wait while we register patient.', 'large-text');

            var serverData = angular.copy(vm.formData);
             serverData.enabled = true;

            // making the server call
            apiService.serviceRequest({
                URL: appSettings.requestURL.patientRegistration,
                method: 'POST',
                payLoad: serverData
            }, function (response) {
                Flash.create('success', 'Patient Successfully Registered.', 'large-text');
                vm.formData = {};
            });
        }

}]);
