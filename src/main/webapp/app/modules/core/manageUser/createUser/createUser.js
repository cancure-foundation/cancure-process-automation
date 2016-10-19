core.controller("CreateUserController", ['$scope', '$stateParams', '$timeout', 'Flash', 'apiService', 'appSettings', 'Loader', '$q',
                                         function ($scope, $stateParams, $timeout, Flash, apiService, appSettings, Loader, $q) {
	var vm = this;	

	var init = function () {
		vm.varInit();
		Loader.create('Fetching Data. Please wait');

		var userDetails = ($stateParams.user) ? JSON.parse($stateParams.user) : undefined,
				reqList = [];

		reqList.push(apiService.asyncServiceRequest({URL: appSettings.requestURL.userRoles}));
		reqList.push(apiService.asyncServiceRequest({URL: appSettings.requestURL.hospitalList}));

		if (userDetails) { // check if its edit mode
			reqList.push(apiService.asyncServiceRequest({URL: 'user/' + userDetails.id}));
			if (userDetails.isDoctor) {
				vm.doctorDetails = true; // shows the doctor details tab
				reqList.push(apiService.asyncServiceRequest({URL: 'doctor/user/' + userDetails.id}));
			}
			if (userDetails.isHpoc) {
				vm.hpocDetails = true; // shows the hpoc details tab
				reqList.push(apiService.asyncServiceRequest({URL: 'list/hospital/hpoc/' + userDetails.id}));
			}
			vm.editMode = true;
		}

		$q.all(reqList).then(function (response){			
			$scope.roles = response[0];
			vm.hospitalList = response[1];
			
			if (response[2]) 
				vm.formData = response[2];				

			if (vm.doctorDetails && vm.hpocDetails) {
				vm.doctor = response[3];
				vm.hpoc = response[4];
				vm.doctor.hospital.hospitalId = response[3].hospital.hospitalId.toString();
				vm.hpoc.hospitalId = response[4].hospitalId.toString();
			} else if (vm.doctorDetails){
				vm.doctor = response[3];
				vm.doctor.hospital.hospitalId = response[3].hospital.hospitalId.toString();
			} else if (vm.hpocDetails) {
				vm.hpoc = response[3];	
				vm.hpoc.hospitalId = response[3].hospitalId.toString();
			}
			
			Loader.destroy();
		});
	};
	/**
	 *  reset entire page to initial state
	 */
	vm.varInit = function (token) {
		vm.formData = {};
		vm.formData.roles = [];
		vm.editMode = false;
		vm.userCreated = false;
		vm.doctor = {};
		vm.doctor.hospital = {};
		vm.doctorDetails = false;
		vm.hpoc = {};
		vm.hpocDetails = false;
		if (token) {
			vm.registerForm.$setUntouched();
			vm.registerForm.$setPristine();
		}
	};
	// init function, execution starts here
	init();

	/**
	 * function to handle save button click
	 */
	vm.createUser = function () { 
		if (vm.formData.roles.length == 0) {
			Flash.create('warning', 'Please select at least 1 role to proceed.', 'large-text');
			return;
		}	

		Loader.create((vm.editMode) ? 'Updating Data .. Please wait ...' : 'Saving Data .. Please wait ...');

		var serverData = angular.copy(vm.formData);
		serverData.enabled = true;		

		if (vm.doctorDetails) {
			serverData.doctor = angular.copy(vm.doctor);
			serverData.doctor.name = serverData.name;
			serverData.doctor.email = serverData.email;
		}

		if (vm.hpocDetails) {
			serverData.hospitalId = angular.copy(vm.hpoc.hospitalId);
		}

		// making the server call
		apiService.serviceRequest({
			URL: appSettings.requestURL.createUser,
			method: 'POST',
			payLoad: serverData,
			hideErrMsg : true
		}, function (response) {
			Loader.destroy();  
			vm.userCreated = true; // to show the user summary div
		}, function (fail){
			Flash.create('danger', fail.message, 'large-text');
		});
	};
	/**
	 * function to handle save button click
	 */
	vm.roleSelection = function (selectedId) {
		// checks if the index is already present in roles array, if yes the remove, else push the index
		var pushItem = true;
		vm.doctorDetails = false;
		vm.hpocDetails = false;
		for (var i = 0; i < vm.formData.roles.length; i++) {
			if (vm.formData.roles[i].id == selectedId) {
				vm.formData.roles.splice(i, 1);
				pushItem = false;
				break;
			}
		}
		if (pushItem)
			vm.formData.roles.push({
				id: selectedId
			});
		for (var i = 0; i < vm.formData.roles.length; i++) {
			if (vm.formData.roles[i].id == 5) {
				vm.doctorDetails = true; // shows the doctor details tab
			}
			if (vm.formData.roles[i].id == 4) {
				vm.hpocDetails = true; // shows the hpoc details tab
			}
		}
	};
	/**
	 * handles the checkbox selection
	 */
	vm.exists = function(id){
		var list = this.formData.roles;
		for(var i = 0; i < list.length; i++){
			if(list[i].id == id){
				return true;
				break;
			}
		}
	};
	/**
	 * function to reset user password
	 */
	vm.resetPassword = function (){
		Loader.create('Please wait ...');

		// making the server call
		apiService.serviceRequest({
			URL: appSettings.requestURL.resetPassword + '/' + vm.formData.id,
			method: 'POST',
			errorMsg : 'Unable to reset. Please try again'
		}, function (response) {
			vm.formData.firstLog = true;
			Loader.destroy();
			Flash.create('success', 'Reset link send for ' + vm.formData.name, 'large-text'); 
		});
	};
}]);