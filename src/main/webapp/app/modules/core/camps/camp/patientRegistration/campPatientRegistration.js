core.controller("CampPatientRegisterController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.alllabtests = [{"listValueId":"4","listId":1,"value":"Daughter"},{"listValueId":"8","listId":1,"value":"Mother"},{"listValueId":"7","listId":1,"value":"Father"},{"listValueId":"3","listId":1,"value":"Son"},{"listValueId":"1","listId":1,"value":"Wife"},{"listValueId":"9","listId":1,"value":"Friend"},{"listValueId":"5","listId":1,"value":"Sister"},{"listValueId":"2","listId":1,"value":"Husband"},{"listValueId":"10","listId":1,"value":"Well Wisher"},{"listValueId":"6","listId":1,"value":"Brother"}];
	vm.allgenders = [{
		value : 'Male'
	},{
		value : 'Female'
	},{
		value : 'Transgender'
	}];
	
	var init = function() {
		var id = $stateParams.labId;
		if (id) {
			Loader.create('Please wait .. Loading Data ...');
			apiService.serviceRequest({
				URL: 'common/lov/CampLabTests',
				method: 'GET'
			}, function (response) {
				Loader.destroy();
				vm.alllabtests = response[0].listValues;
			});
		}
	}

	
	
}]);