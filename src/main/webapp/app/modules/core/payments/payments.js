core.controller("PaymentsController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	vm.searched = false;
	
	var init = function() {
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacy/all/',
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			Flash.dismiss();
			vm.partners = response;
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
	};
	
	vm.clear = function() {
		vm.searched = false;
		vm.invoices = {}
	}	
	
	vm.searchInvoices = function() {
		vm.invoices = [];
		Loader.create('Searching.. Please wait ...');
		
		// making the server call
		apiService.serviceRequest({
			URL: '/invoices/' + vm.formData.partnerType + "/" + vm.formData.partnerId,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			vm.searched = true;
			vm.invoices = response;
			Loader.destroy();
		}, function (fail){
			vm.searched = true;
			Flash.create('danger', fail.message, 'large-text');
		}); 
		
	};
	
	vm.computeTotal = function() {
		var total = 0;
		for (var i=0; i < vm.invoices.length; i++) {
			if (vm.paymentForm.selectedInvoices[vm.invoices[i].id]) {
				total += (vm.invoices[i].amount);
			}
		}
		
		vm.totalAmount = total;
	};
	
	vm.submitPayment = function(){
		
		vm.paymentForm.selectedInvoiceIds = [];
		for (var i=0; i < vm.invoices.length; i++) {
			if (vm.paymentForm.selectedInvoices[vm.invoices[i].id]) {
				vm.paymentForm.selectedInvoiceIds.push(vm.invoices[i].id);
			}
		}
		
		if (vm.paymentForm.selectedInvoiceIds.length == 0) {
			Flash.create('danger', 'Please select at least one invoice', 'large-text');
		}
		
		//alert(vm.paymentForm.selectedInvoiceIds);
		Loader.create('Please wait ...');

		var serverData = angular.copy(vm.paymentForm);
		serverData.selectedInvoiceIds = vm.paymentForm.selectedInvoiceIds;
		serverData.toAccountHolderId = vm.formData.partnerId;
		serverData.toAccountTypeId = vm.formData.partnerType;
		serverData.amount = vm.totalAmount;
		
		// making the server call
		apiService.serviceRequest({
			URL: '/payments',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
		
	};
		
	init();

}]);