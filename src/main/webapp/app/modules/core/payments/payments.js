core.controller("PaymentsController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'appSettings', '$timeout', 'Flash',
                                       function (Loader, $scope, $state, $stateParams, apiService, appSettings, $timeout, Flash) {

	var vm = this;
	vm.searched = false;

	var init = function() {
		// making the server call
		vm.partnerType = [{
			name : 'Pharmacy',
			id : 3
		},{
			name : 'Laboratory',
			id : 4
		},{
			name : 'Hospital',
			id : 5
		}];

		vm.partnerList = []; // partner name drop-dpown

		vm.partnerFetchURL = {
				3 : "/pharmacy/all",
				4 : "/lab/list",
				5 : "/hospital/list"
		};

		vm.fetchedPartner = {}; // stores the fetched partner name
	};
	/**
	 *  on change event 
	 */
	vm.populatePartnerList = function() {

		if (!vm.fetchedPartner[vm.formData.partnerType]){
			apiService.serviceRequest({
				URL: vm.partnerFetchURL[vm.formData.partnerType],
				method: 'GET',
				hideErrMsg : true
			}, function (response) {	
				vm.partnerList = [];
				for (var i=0;i<response.length;i++){
					vm.partnerList.push({
						name : response[i].name,
						id : response[i].hospitalId || response[i].pharmacyId
					});
				}
				vm.fetchedPartner[vm.formData.partnerType] = vm.partnerList;
				vm.formData.partnerId = null;
			}, function (fail){
				Flash.create('danger', fail.message, 'large-text');
			});
		} else {
			vm.partnerList = vm.fetchedPartner[vm.formData.partnerType];
			vm.formData.partnerId = null;
		}

		vm.searched = false;
		vm.invoices = {}
	}	
	/**
	 * 
	 */
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
	/**
	 * 
	 */
	vm.computeTotal = function() {
		var total = 0;
		for (var i=0; i < vm.invoices.length; i++) {
			if (vm.paymentForm.selectedInvoices[vm.invoices[i].id]) {
				total += (vm.invoices[i].amount);
			}
		}

		vm.totalAmount = total;
	};
	/**
	 * 
	 */
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