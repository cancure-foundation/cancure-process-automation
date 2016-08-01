core.controller("UserListController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'apiService', 'appSettings',
function ($rootScope, $scope, $state, $location, Flash, apiService, appSettings) {
        var vm = this;
        vm.userList = [];

        var init = function () {
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
            });
        }

        init();

}]);