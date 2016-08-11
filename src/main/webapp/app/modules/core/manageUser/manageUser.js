core.controller("ManageUserController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings', 'Loader', 
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings, Loader) {
        var vm = this;       
       
        var init = function () {
        	vm.currentNavItem = 'createUser'; // sets the current selected item
        };

        // init function, execution starts here
        init();
        
}]);