core.controller("PatientRegistrationController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings', 'Loader',
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings, Loader) {
        var vm = this;
        vm.formData = {};

        var init = function () {
          vm.formData = {};
        };

        // init function, execution starts here
        init();

        //function to handle save button click
        vm.submitForm = function () {
        	Loader.create('Please wait while we register patient.');
        	
        	var fd = new FormData();
       
        	fd.append("document[0].docCategory", 'Age Proof');
        	fd.append("document[0].docType", 'Aadhaar');
        	fd.append("document[0].patientFile",  $scope.ageProof);

        	fd.append("document[1].docCategory", 'Age Proof');
        	fd.append("document[1].docType", 'Income Proof');
        	fd.append("document[1].patientFile", $scope.incomeProof);
        	
        	angular.forEach(vm.formData, function (v, k) {
        		fd.append(k,v);
        	});

            // making the server call
            apiService.serviceRequest({
                URL: appSettings.requestURL.patientRegistration,
                method: 'POST',
                payLoad: fd,
                transformRequest : angular.identity,
                headers: {
                	'Content-Type': undefined
                }
            }, function (response) {
            	Loader.destroy();
                Flash.create('success', 'Patient Successfully Registered.', 'large-text');
                vm.formData = {};
            });
        }

}]);
