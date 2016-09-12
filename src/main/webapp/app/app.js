var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'flash', 'ngCookies', 'loader', 'ngAnimate', 'ngMaterial', 'ngSanitize', 'ngIdle',
    //main modules
    'login', 'core' , 'errorPages']);

app.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', '$httpProvider', 'IdleProvider', 
            function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider, IdleProvider) {

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
    
    IdleProvider.idle(5 * 60); // 5 minutes idle
}]);

app.run(function($rootScope, Idle, $state) {
	  Idle.watch();
	  $rootScope.$on('IdleStart', function() { 	
		  var currentState = $state.current.url.replace('/','');
		  if (currentState != 'login' && currentState != 'sessionExpired') {
			  console.log("Session Expiry : " + new Date());
			  $state.go('sessionExpired');
		  }
	  });
});

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