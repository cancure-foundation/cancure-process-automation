core.controller("DoctorCreateController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader',
                                         function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader) {
	var vm = this;
	vm.formData = {};
	vm.hospitalList = [];
	vm.doctorCreated = false;
	
	var init = function() {
		fetchHospitals();
	}
	
	var getDoctorDetails = function(){
		var id = $stateParams.doctor_id;
		if (id) {
			apiService.serviceRequest({
				URL: 'doctor/' + id,
				method: 'GET'
			}, function (response) {
				Loader.destroy();
				vm.formData = response;
			});
		}
	}
	
	var fetchHospitals = function() {
		apiService.serviceRequest({
			URL: 'hospital/list',
			method: 'GET'
		}, function (response) {
			Loader.destroy();
			vm.hospitalList = response;
			getDoctorDetails();
		});
	}
	
	// init function, execution starts here
	init();

	/**
	 * function to handle save button click
	 */
	vm.createDoctor = function () { 	
		var formState = formValidator(); // function to validate form fields
		if(!formState.valid){
			Flash.create('warning', formState.errMsg, 'large-text');
			return;
		}
		
		Loader.create('Please wait ...');

		var serverData = angular.copy(vm.formData);
		serverData.enabled = true;

		// making the server call
		apiService.serviceRequest({
			URL: 'doctor/save',
			method: 'POST',
			payLoad: serverData
		}, function (response) {
			Loader.destroy();
			Flash.create('success', 'Doctor successfully saved', 'large-text');   
			vm.doctorCreated = true; // to show the user summary div
		});
	};
	/**
	 * function to validate form fields
	 */
	var formValidator = function (){
		var formState = {
				valid : true
		};
				
		return formState;
	}
	
	/**
	 * function to show created user
	 */
	vm.createNwDoctorBtn = function (){
		vm.doctorCreated = false; // to hide the user summary div
		$timeout(function (){
			vm.formData = {};      
			vm.formData.roles = [];
		});
		vm.registerForm.$setUntouched();
		vm.registerForm.$setPristine();
	}
}]);