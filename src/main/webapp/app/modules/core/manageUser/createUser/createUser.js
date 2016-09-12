core.controller("CreateUserController", ['$scope', '$stateParams', '$timeout', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $stateParams, $timeout, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.formData.roles = [];
	vm.userCreated = false;
	
	var init = function () {
		Loader.create('Fetching Data. Please wait');
		var id = $stateParams.userId;
		
		apiService.serviceRequest({
			URL: appSettings.requestURL.userRoles
		}, function (response) {
			$scope.roles = response; // assigns the roles to roles object in the scope
			getUser(id);
			Loader.destroy();
		});
	};
	
	var getUser = function(id){
		if (id != null && id != ''){
			apiService.serviceRequest({
				URL: 'user/' + id
			}, function (response) {
				vm.formData = response;
				Loader.destroy();
			});
		} else {
			Loader.destroy();
		}
	}

	// init function, execution starts here
	init();

	/**
	 * function to handle save button click
	 */
	vm.createUser = function () { 	
		var formState = formValidator(); // function to validate form fields
		if(!formState.valid){
			Flash.create('warning', formState.errMsg, 'large-text');
			return;
		}
		
		Loader.create('Please wait while we register you...');

		var serverData = angular.copy(vm.formData);
		delete serverData.retypepassword;
		serverData.enabled = true;

		// making the server call
		apiService.serviceRequest({
			URL: appSettings.requestURL.createUser,
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			Flash.create('success', 'User successfully saved.', 'large-text');   
			vm.userCreated = true; // to show the user summary div
		});
	};
	/**
	 * function to validate form fields
	 */
	var formValidator = function (){
		var formState = {
				valid : true
		};
		if (vm.formData.roles.length == 0) {
			formState.valid = false;
			formState.errMsg = 'Please select at least 1 role to proceed.';
			return formState;
		}

		if (vm.formData.password == ''){
			formState.valid = false;
			formState.errMsg = 'Password cannot be blank.';
			return formState;
		}
		
		if(vm.formData.password != vm.formData.retypepassword){
			formState.valid = false;
			formState.errMsg = 'Passwords do not match.';
			return formState;
		}
		
		return formState;
	}
	/**
	 * function to handle save button click
	 */
	vm.roleSelection = function (selectedId) {
		// checks if the index is already present in roles array, if yes the remove, else push the index
		var pushItem = true;
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
	 * function to show created user
	 */
	vm.createNwUsrBtn = function (){
		vm.userCreated = false; // to hide the user summary div
		$timeout(function (){
			vm.formData = {};      
			vm.formData.roles = [];
		});
		vm.registerForm.$setUntouched();
		vm.registerForm.$setPristine();
	}
}]);