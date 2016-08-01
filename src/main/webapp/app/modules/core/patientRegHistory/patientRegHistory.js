/*==========================================================
    Author      : Ranjithprabhu K
    Date Created: 13 Jan 2016
    Description : Controller to handle Experience page
    Change Log
    s.no      date    author     description     


 ===========================================================*/

core.controller("PatientRegHistoryController", ['$rootScope', '$scope', '$state', '$location', '$http', 'dashboardService', 'Flash',
function ($rootScope, $scope, $state, $location, $http, dashboardService, Flash) {
    var vm = this;
    
    apiService.serviceRequest({
        URL: '/tasks/history/1239'
    }, function (response) {
    	$scope.taskHistory = data;
    });
    
}]);

