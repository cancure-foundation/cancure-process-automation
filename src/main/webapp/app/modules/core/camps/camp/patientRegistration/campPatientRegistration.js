core.controller("CampPatientRegisterController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.campSearched = false;
	vm.campSearchForm = {};
	vm.campSearchForm.searchMonthYear = new Date();
	vm.selectedCamp = {};
	vm.formData = {};
	vm.camps = [];
	
	vm.alllabtests = [{"listValueId":"4","listId":1,"value":"Daughter"},{"listValueId":"8","listId":1,"value":"Mother"},{"listValueId":"7","listId":1,"value":"Father"},{"listValueId":"3","listId":1,"value":"Son"},{"listValueId":"1","listId":1,"value":"Wife"},{"listValueId":"9","listId":1,"value":"Friend"},{"listValueId":"5","listId":1,"value":"Sister"},{"listValueId":"2","listId":1,"value":"Husband"},{"listValueId":"10","listId":1,"value":"Well Wisher"},{"listValueId":"6","listId":1,"value":"Brother"}];
	vm.allgenders = [{
		value : 'Male'
	},{
		value : 'Female'
	},{
		value : 'Transgender'
	}];
	
	var init = function() {
		vm.campSearchForm.searchMonthYear = new Date();
		apiService.serviceRequest({
			URL: 'common/lov/CampLabTests',
			method: 'GET'
		}, function (response) {
			Loader.destroy();
			vm.alllabtests = response[0].listValues;
		});
	}

	vm.searchCamp = function() {
		var month = vm.campSearchForm.searchMonthYear.getMonth() + 1;
		var year = vm.campSearchForm.searchMonthYear.getFullYear();
		
		apiService.serviceRequest({
			URL: 'camp/' + month + '/' + year,
			method: 'GET'
		}, function (response) {
			vm.camps = response;
			vm.campSearched = true;
		});
	}

	vm.selectCamp = function(id) {
		for (var i=0; i < vm.camps.length; i++) {
			if (vm.camps[i].campId == id) {
				vm.selectedCamp = vm.camps[i];
				vm.campSelected = true;
				break;
			}
		}
	}
	
	vm.resetCamp = function() {
		vm.camps = [];
		vm.campSearched = false;
		vm.selectedCamp = {};
		vm.campSelected = false;
		vm.campSearchForm = {};
	}
	
	init();
}]);