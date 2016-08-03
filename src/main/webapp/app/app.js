var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'flash',
    //main modules
    'login', 'core']);

app.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', '$httpProvider',
            function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider) {

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
    
    // sets the bad route/default route for the application
    $urlRouterProvider.otherwise('login');
    
    // intercept all http request from the application
    $httpProvider.interceptors.push('httpInterceptor');
}]);

// directive to show pre-loading screen
app.directive("mAppLoading",function( $animate ) {
        return({
            link: link,
            restrict: "C"
        });
        function link( scope, element, attributes ) { 
        	setTimeout(function (){
        		element.fadeOut(); // fades out the pre-loader UI
        		document.getElementsByTagName("body")[0].style.overflow = "auto"; // makes the body tag overflow auto
        	}, 3000)
        }
    }
);

// directive to show page loader
app.directive('loadMask', ['$compile', '$rootScope', function ($compile, $rootScope) {
    return {
        restrict: 'A',
        template: '<div class="m-app-loading"><div class="messaging">Cancure</div></div>',
        link: function (scope, ele, attrs) {
            // get timeout value from directive attribute and set to flash timeout
            
        }
    };
}]);