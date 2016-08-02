var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'flash',
    //main modules
    'login', 'core']);

app.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', function ($stateProvider, $locationProvider, $urlRouterProvider, $modalInstance) {

    //IdleScreenList
    $stateProvider
        .state('app', {
            url: '/app',
            templateUrl: 'app/baseLayout.html',
            controller: 'appCtrl',
            controllerAs: 'vm',
            data: {
                pageTitle: 'BaseLayout'
            }
        });

    $urlRouterProvider.otherwise('login');
}]);

// set global configuration of application and it can be accessed by injecting appSettings in any modules
var appConfig = {
    title: "Cancure", // app name
    lang: "en", // app default locale format
    dateFormat: "mm/dd/yy", // app date format
    baseURL: 'http://localhost:8080/', // app service URL
    requestURL: {
        authRequest: 'oauth/token', // oauth request
        userRoles : 'roles', // fetching user roles
        createUser: 'user/save', // creating user
        userList: 'user/list' // listing out the user
    },
    theme: 'skin-yellow', // app default theme
    layout: "" // app default layout
};

app.constant('appSettings', appConfig);

// directive to show pre-loading screen
app.directive("mAppLoading",function( $animate ) {
        return({
            link: link,
            restrict: "C"
        });
        function link( scope, element, attributes ) { 
        	setTimeout(function (){
        		element.fadeOut();
        		document.getElementsByTagName("body")[0].style.overflow = "auto";
        	}, 3000)
        }
    }
);