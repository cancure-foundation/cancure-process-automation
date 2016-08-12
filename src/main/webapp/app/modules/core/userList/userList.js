core.controller("UserListController", ['$scope', 'Flash', 'apiService', 'appSettings', 'Loader',
function ($scope, Flash, apiService, appSettings, Loader) {
        var vm = this;
        vm.userList = [];

        var init = function () {
        	Loader.create('Fetching Data. Please wait');
            apiService.serviceRequest({
                URL: appSettings.requestURL.userList,
                errorMsg: 'Failed to fetch user roles. Try Again!'
            }, function (response) {
                $scope.userList = response; // assigns the roles to roles object in the scope
                setTimeout(function () {
                    $(document).ready(
                        function () {
                            $('td div').slideUp();
                            $('td role').click(
                                function () {
                                    $(this).siblings('div').slideToggle();
                                }
                            );
                        }
                    );
                }, 100);
                Loader.destroy();
            });
        }
        // init function, execution starts here
        init();

}]);