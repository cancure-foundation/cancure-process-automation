core.controller("DoctorCreateController", ['$scope', '$timeout', '$stateParams', 'Flash', 'apiService', 'appSettings', 'Loader', '$q',
                                           function ($scope, $timeout, $stateParams, Flash, apiService, appSettings, Loader, $q) {
	var vm = this;
	vm.formData = {};
	vm.hospitalList = [];
	vm.doctorCreated = false;

	var init = function() {
		Loader.create('Loding data ... Please wait');
		var id = $stateParams.doctor_id;
		var hospitalList = apiService.asyncServiceRequest({URL: 'hospital/list'});
		var reqlist = [hospitalList];
		if (id) { // check if its edit mode
			var doctorDetails = apiService.asyncServiceRequest({URL: 'doctor/' + id});
			reqlist.push(doctorDetails);
		}
		$q.all(reqlist).then(function (response){
			vm.hospitalList = response[0];
			if (response[1]) {
				vm.formData = response[1];
				vm.formData.hospital = {hospitalId:response[1].hospital.hospitalId.toString()};
			}
			Loader.destroy();
		});
	};

	// init function, execution starts here
	init();

	/**
	 * function to handle save button click
	 */
	vm.createDoctor = function () { 
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
			for (var i = 0 ; i < vm.hospitalList.length; i++){
				var id = parseInt(vm.formData.hospital.hospitalId)
				if (id == vm.hospitalList[i].hospitalId){
					vm.hospName = vm.hospitalList[i].name;
					break;
				}
			}
		});
	};
	/**
	 * function to show created user
	 */
	vm.createNwDoctorBtn = function (){
		vm.doctorCreated = false; // to hide the user summary div
		$timeout(function (){
			vm.formData = {};      
		});
	}
}]);