var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'flash', 'ngCookies', 'loader', 
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
        	}, 3000)
        }
    }
);
//directive for file upload
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);