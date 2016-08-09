core.controller("CreateUserController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings', 'Loader',
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings, Loader) {
        var vm = this;
        vm.formData = {};
        vm.formData.roles = [];

        var init = function () {
            apiService.serviceRequest({
                URL: appSettings.requestURL.userRoles
            }, function (response) {
                $scope.roles = response; // assigns the roles to roles object in the scope
            });
        };

        // init function, execution starts here
        init();

        //function to handle save button click
        vm.onSend = function () {
        	Loader.create('Please wait while we register you...');

            var serverData = angular.copy(vm.formData);
            delete serverData.retypepassword;
            serverData.enabled = true;

            // making the server call
            apiService.serviceRequest({
                URL: appSettings.requestURL.createUser,
                method: 'POST',
                payLoad: serverData
            }, function (response) {
            	Loader.destroy();
                Flash.create('success', 'User Successfully Registered.', 'large-text');
                vm.formData = {};
            });
        }

        //function to handle save button click
        vm.roleSelection = function (selectedId) {
            // checks if the index is already present in roles array, if yes the remove, else push the index
            var pushItem = true;
            for (var i = 0; i < vm.formData.roles.length; i++) {
                if (vm.formData.roles[i].id == selectedId) {
                    vm.formData.roles.splice(i, 1);
                    pushItem = false;
                    break;
                }
            }
            if (pushItem)
                vm.formData.roles.push({
                    id: selectedId
                });
        }

}]);