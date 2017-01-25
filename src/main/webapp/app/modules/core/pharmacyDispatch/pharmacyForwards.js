core.controller("PharmacyForwardsController", ['Loader', '$scope', '$state', '$stateParams', 'apiService', 'Flash',
                                                function (Loader, $scope, $state, $stateParams, apiService, Flash) {

	var vm = this;
	
	var init = function (){
		vm.pidn = null;
		vm.formData = {};
		vm.formData.amount = null;
		vm.patient = null;
		vm.approvals = null;
		vm.visits = null;
		vm.balance = null;
		vm.noSearchResult = false;
		vm.bill = [{
			id : 0
		}];
		vm.visitDetails = false;
		vm.balErr = false;
	};
	/**
	 * 
	 */
	vm.searchUser = function() {
		Loader.create('Please wait while we Search patient.');
		
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacydispatch/' + vm.pidn,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {	
			if (response.patientBean == null) {
				vm.noSearchResult = true;
				vm.pageMessage ="Sorry, no items match your query.";
			} else {
				vm.patient = response.patientBean;
				vm.approvals = response.patientApprovals;
				vm.visits = response.patientVisitForwardsBean;
				vm.balance = response.balance;
				vm.noSearchResult = false;
				$scope.$watch('vm.formData.amount', function (newValue, oldValue, scope) {
					if (vm.formData.amount && parseInt(vm.formData.amount) > vm.balance){
						vm.balErr = true;	
						vm.billErr = false;			
						document.getElementById('cancureRdAmt').value = null;
					} else if (checkBill()) {
						vm.balErr = true;
						vm.billErr = true;			
						document.getElementById('cancureRdAmt').value = null;
					} else
						vm.balErr = false;
					
					function checkBill(){						
						var billAmt = 0;
						for (var i=0; i<vm.bill.length;i++){
							billAmt = billAmt + parseInt(vm.bill[i].partnerBillAmount);
						}
						return vm.formData.amount > billAmt;
					}
				});
			}
			Loader.destroy();
		});
	};
	/**
	 *  to calculate bill total
	 */
	vm.calcBillTotal = function (){
		vm.billTotal = 0;
		for (var i=0; i<vm.bill.length;i++){
			if (vm.bill[i].partnerBillAmount)
				vm.billTotal = vm.billTotal + parseInt(vm.bill[i].partnerBillAmount);
		}
		
		if(vm.billTotal == 0)
			vm.formData.amount = 0;
	};
	/**
	 *  function to handle file selection
	 */
	vm.FilesSelection = function(input){
		var index = parseInt(input.attributes.fileid.value);
		vm.bill[index].file = input.files[0];		
	};
	/**
	 * 
	 */
	vm.fetchVisit = function(item){		
		Loader.create('Loading Requested Data ... Please wait..');
		apiService.serviceRequest({
			URL: '/pharmacyforwards/' + item.patientVisitId,
			method: 'GET',
			hideErrMsg : true
		}, function (response) {
			vm.formData.patientVisitId = response.patientVisitForwards.patientVisitId;
			vm.visitDetails = true;
			vm.visitDate = item.date;
			vm.visitDocs = response.patientVisitBean.patientHospitalVisitDocumentBeanList;			
			vm.formData.closeBill = "close";
			Loader.destroy();
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
	};
	/**
	 * 
	 */
	vm.submit = function() {		
		Loader.create('Saving Data .. Please wait ...');
		var fd = new FormData();
		
		fd.append("pidn", vm.patient.pidn);
		fd.append("comments", vm.formData.comments);
		fd.append("amount", parseFloat(vm.formData.amount));
		fd.append("billStatus", vm.formData.closeBill);
		fd.append("patientVisitId", vm.formData.patientVisitId);
		debugger
		
		for (var i=0; i<vm.bill.length;i++){
			fd.append("patientBillsBean[" + i + "].partnerBillNo",  vm.bill[i].partnerBillNo);
			fd.append("patientBillsBean[" + i + "].partnerBillAmount",  parseFloat(vm.bill[i].partnerBillAmount));
			fd.append("patientBillsBean[" + i + "].partnerBillFile",  vm.bill[i].file);
		}	
		
		// making the server call
		apiService.serviceRequest({
			URL: '/pharmacydispatch',
			method: 'POST',
			payLoad: fd,
			hideErrMsg : true,
			headers: {
				'Content-Type': undefined
			}
		}, function (response) {			
			Loader.destroy();
			apiService.showAlert("Data Saved Successfully !!", function (){
				init();
			});	
		}, function (fail){
			Flash.create('danger', 'Action Failed. Try Again!!', 'large-text');
		}); 
		
	};
	init();
	
}]);