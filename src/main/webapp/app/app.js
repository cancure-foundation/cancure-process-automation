var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'flash', 'ngCookies', 'loader', 'ngAnimate', 'ngMaterial', 'ngSanitize', 'ngIdle',
                                 //main modules
                                 'login', 'core' , 'errorPages']);

app.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', '$httpProvider', 'IdleProvider', 'KeepaliveProvider',
            function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider, IdleProvider, KeepaliveProvider) {

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

	IdleProvider.idle(60 * 60); // idle timeout 1 hour
	IdleProvider.timeout(1); // countdown after 1 hour - 0sec
}]);

app.run(function($rootScope, Idle, $state) {
	Idle.watch();
	$rootScope.$on('IdleStart', function() {
		// defined idle time ends
	});	
	$rootScope.$on('IdleWarn', function(scope, count) { 	
		// countdown beings
	});
	$rootScope.$on('IdleTimeout', function() { 	
		// session expiry		
		var currentState = $state.current.url.replace('/','');
		if (currentState != 'login' && currentState != 'sessionExpired') {
			console.log("Session Expiry : " + new Date());
			$state.go('sessionExpired');
		}
		Idle.watch(); // to restart the idle timer
	});
	$rootScope.$on('IdleEnd', function() {
		// event fired if user intervention during countdown
	});
});

//directive to show pre-loading screen
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
});