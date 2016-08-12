core.controller("CreateUserController", ['$scope', '$timeout', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.formData.roles = [];

	var init = function () {
		apiService.serviceRequest({
			URL: appSettings.requestURL.userRoles
		}, function (response) {
			$scope.roles = response; // assigns the roles to roles object in the scope
		});
	};

	// init function, execution starts here
	init();

	//function to handle save button click
	vm.onSend = function () { 	
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
			Flash.create('success', 'User Successfully Registered.', 'large-text');                
			$timeout(function (){
				vm.formData = {};      
				vm.formData.roles = [];
			});
			vm.registerForm.$setUntouched();
			vm.registerForm.$setPristine();
		});
	};
	// function to validate form fields
	var formValidator = function (){
		var formState = {
				valid : true
		};
		if (vm.formData.roles.length == 0) {
			formState.valid = false;
			formState.errMsg = 'Please select atleast 1 role to proceed.';
			return formState;
		}

		if(vm.formData.password != vm.formData.retypepassword){
			formState.valid = false;
			formState.errMsg = 'Passwords do not match.';
			return formState;
		}
		
		return formState;
	}
	//function to handle save button click
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
	// handles the checkbox selection
	vm.exists = function(id){
		var list = this.formData.roles;
		for(var i = 0; i < list.length; i++){
			if(list[i].id == id){
				return true;
				break;
			}
		}
	}

}]);