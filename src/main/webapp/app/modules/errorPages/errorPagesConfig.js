var errorPage = angular.module('errorPages', ['ui.router', 'ngResource', 'ngAnimate', 'ngIdle']);


errorPage.config(["$stateProvider", function ($stateProvider) {

    //error pages state
    $stateProvider.state('sessionExpired', {
        url: '/sessionExpired',
        templateUrl: 'app/modules/errorPages/sessionExpired/sessionExpired.html',
        controller: 'sessionExpCtrl',
        controllerAs: 'vm',
        data: {
            pageTitle: 'Session Expired'
        }
    });

}]);

