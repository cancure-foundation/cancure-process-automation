dashboard.controller("UserListController", ['$rootScope', '$scope', '$state', '$location', 'dashboardService', 'Flash', '$http',
function ($rootScope, $scope, $state, $location, dashboardService, Flash, $http) {
        var vm = this;
        vm.userList = [];

        var init = function () {
            var request = $http({
                method: "GET",
                url: "http://localhost:8080/user/list",
            });
            // success function
            request.success(function (data) {
                $scope.userList = data; // assigns the roles to roles object in the scope
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
                }, 500);


            });
            // success function
            request.error(function (data) {
                Flash.create('danger', 'Failed to fetch user roles. Try Again!', 'large-text');
            });
        }

        init();

}]);