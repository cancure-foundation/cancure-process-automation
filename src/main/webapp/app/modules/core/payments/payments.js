core.controller("PaymentsController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	var vm.searched = false;
	
	var init = function() {
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacy/all/',
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			vm.partners = response;
			vm.searched = true;
		}, function (fail){
			vm.searched = true;
			Flash.create('danger', fail.message, 'large-text');
		});
	};
	
	vm.searchInvoices = function() {
		
		Loader.create('Searching.. Please wait ...');
		
		// making the server call
		apiService.serviceRequest({
			URL: '/invoices/' + vm.formData.partnerType + "/" + vm.formData.partnerId,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			vm.invoices = response;
			Flash.create('danger', "Saved successfully", 'large-text');
			Loader.destroy();
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		}); 
		
	};
		
	init();

}]);