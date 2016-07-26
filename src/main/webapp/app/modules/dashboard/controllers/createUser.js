dashboard.controller("CreateUserController", ['$rootScope', '$scope', '$state', '$location', 'dashboardService', 'Flash', '$http',
function ($rootScope, $scope, $state, $location, dashboardService, Flash, $http) {
        var vm = this;
        vm.formData = {};
        vm.formData.roles = [];

        var init = function () {
            // service request to fetch the roles
            var request = $http({
                method: "GET",
                url: "http://localhost:8080/roles",
            });
            // success function
            request.success(function (data) {
                $scope.roles = data; // assigns the roles to roles object in the scope
            });
            // success function
            request.error(function (data) {
                Flash.create('danger', 'Failed to fetch user roles. Try Again!', 'large-text');
            });
        }

        // init function, execution starts here
        init();

        //function to handle save button click
        vm.onSend = function () {
            Flash.create('info', 'Please wait while we register you.', 'large-text');
            var serverData = angular.copy(vm.formData);
            delete serverData.retypepassword;
            serverData.enabled = true;

            // making the server call
            var request = $http({
                method: "POST",
                url: "http://localhost:8080/user/save",
                data: serverData,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            // success function
            request.success(function (data) {
                Flash.create('success', 'User Successfully Registered.', 'large-text');
                vm.formData = {};
                vm.registerForm.$setPristine();
            });
            // success function
            request.error(function (data) {
                Flash.create('danger', 'Failed to register. Try Again!', 'large-text');
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