login.controller("loginCtrl", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings',

function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings) {
        var vm = this;

        vm.formData = {};
        vm.loggingIn = false;

        //access login
        vm.login = function (data) {
            vm.loggingIn = true;
//            return;
            if (data.Username == "admin" && data.Password == "admin") {
                appSettings.userName = data.Username;
                $state.go('app.home');
            } else {
                vm.loggingIn = false;
                vm.formData = {};
                Flash.create('danger', 'Invalid Username. Try Again!', 'large-text');
            }
        };

    }])