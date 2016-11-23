core.controller("LabManageController", ['$scope', '$state', 'Flash', 'apiService', 'appSettings', 'Loader', 
function ($scope, $state, Flash, apiService, appSettings, Loader) {
        var vm = this;       
       
        var init = function () {
        	vm.currentNavItem = $state.current.name; // sets the current selected item
        	//debugger;
        };

        // init function, execution starts here
        init();
        
}]);