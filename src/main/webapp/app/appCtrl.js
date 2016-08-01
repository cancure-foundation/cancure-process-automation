app.controller("appCtrl", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'appSettings',
function ($rootScope, $scope, $state, $location, Flash, appSettings) {

        $rootScope.theme = appSettings.theme;
        $rootScope.layout = appSettings.layout;

        var vm = this;

        //avalilable themes
        vm.themes = [
            {
                theme: "purple",
                color: "skin-purple",
                title: "Dark - Purple Skin",
                icon: ""
        }, {
                theme: "black",
                color: "skin-black",
                title: "Dark - Black Skin",
                icon: ""
        }, {
                theme: "blue",
                color: "skin-blue",
                title: "Dark - Blue Skin",
                icon: ""
        },
            {
                theme: "green",
                color: "skin-green",
                title: "Dark - Green Skin",
                icon: ""
        }, {
                theme: "yellow",
                color: "skin-yellow",
                title: "Dark - Yellow Skin",
                icon: ""
        }, {
                theme: "red",
                color: "skin-red",
                title: "Dark - Red Skin",
                icon: ""
        }
    ];

        //available layouts
        vm.layouts = [
            {
                name: "Boxed",
                layout: "layout-boxed"
        },
            {
                name: "Fixed",
                layout: "fixed"
        },
            {
                name: "Sidebar Collapse",
                layout: "sidebar-collapse"
        },
    ];


        //Main menu items of the dashboard
        vm.menuItems = [
            {
                title: "Home",
                icon: "dashboard",
                state: "home"
        },
            {
                title: "Create User",
                icon: "gears",
                state: "createUser"
        },
            {
                title: "User List",
                icon: "phone",
                state: "userList"
        },
            {
                title: "Search List",
                icon: "phone",
                state: "searchUser"
        }
    ];

        //set the theme selected
        vm.setTheme = function (value) {
            $rootScope.theme = value;
        };


        //set the Layout in normal view
        vm.setLayout = function (value) {
            $rootScope.layout = value;
        };


        //controll sidebar open & close in mobile and normal view
        vm.sideBar = function (value) {
            if ($(window).width() <= 767) {
                if ($("body").hasClass('sidebar-open'))
                    $("body").removeClass('sidebar-open');
                else
                    $("body").addClass('sidebar-open');
            } else {
                if (value == 1) {
                    if ($("body").hasClass('sidebar-collapse'))
                        $("body").removeClass('sidebar-collapse');
                    else
                        $("body").addClass('sidebar-collapse');
                }
            }
        };

}]);