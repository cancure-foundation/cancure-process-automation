(function () {
	'use strict';
	var app = angular.module('loader', []);

	app.run(['$rootScope', function ($rootScope) {
		// initialize variables
		$rootScope.showLoader = false;
	}]);


	// Create app-loader directive
	app.directive('appLoader', ['$compile', '$rootScope', function ($compile, $rootScope) {
		return {
			restrict: 'E',
			template: '<div  class="loaderClass"> <i class="fa fa-cog fa-spin" aria-hidden="true"></i> <br> {{loaderText}} </div>',
			link: function (scope, ele, attrs) {     

			}
		};
	}]);

	app.factory('Loader', ['$rootScope', '$timeout',
	                       function ($rootScope, $timeout) {

		var dataFactory = {};
		// Create loader 
		dataFactory.create = function (text) {
			var $this = this;
			$rootScope.loaderText = text || 'Loading, Please wait...';
			$rootScope.showLoader = true;
		};
		// Dismiss loader 
		dataFactory.destroy = function () {                
			$rootScope.showLoader = false;
		};
		return dataFactory;
	}]);
	
}());