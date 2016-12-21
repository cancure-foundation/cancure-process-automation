core.controller("HomeController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'appSettings', 'apiService', 'Loader',
function ($rootScope, $scope, $state, $location, Flash, appSettings, apiService, Loader) {
	
		var vm = this;
        
        //Tools I use Carousel
        $("#owl-demo").owlCarousel({
            items: 8, //10 items above 1000px browser width
            itemsDesktop: [1000, 5], //5 items between 1000px and 901px
            itemsDesktopSmall: [900, 3], // betweem 900px and 601px
            itemsTablet: [600, 2], //2 items between 600 and 0
            itemsMobile: false, // itemsMobile disabled - inherit from itemsTablet option
        });
        $("#owl-demo").trigger('owl.play', 2000);

        // Custom Navigation Events
        $(".next").click(function () {
            $("#owl-demo").trigger('owl.next');
        })
        $(".prev").click(function () {
            $("#owl-demo").trigger('owl.prev');
        })
        $(".play").click(function () {
            $("#owl-demo").trigger('owl.play', 1000); //owl.play event accept autoPlay speed as second parameter
        })
        $(".stop").click(function () {
            $("#owl-demo").trigger('owl.stop');
        })

        //cartoon photo slider carosusel
        $("#owl-single").owlCarousel({
            navigation: true, // Show next and prev buttons
            slideSpeed: 300,
            paginationSpeed: 400,
            singleItem: true,
            autoPlay: 5000, //Set AutoPlay to 3 seconds
        });

        var init = function () {
        	vm.fetchData();
        	vm.q_1_loadText = null;
        	vm.q_1_load = false;
        	vm.q_2_load = false;
        };
        
        vm.fetchData = function (refresh){
        	vm.q_1_load = true;
        	vm.q_1_loadText = refresh ? "Refreshing Patient Registration Queue" : 'Loading Patient Registration Queue';
        	
        	vm.q_2_load = true;
        	vm.q_2_loadText = refresh ? "Refreshing Patient Visit Queue" : 'Loading Patient Visit Queue';
        	
        	
    		apiService.serviceRequest({
    			URL: appSettings.requestURL.myQueue
    		}, function (response) {
    			vm.tasks = response;    			
    			if(vm.tasks.PATIENT_REG_PROCESS_DEF_KEY.length == 0){
    				vm.q_1_load = false;
    				vm.q_1_noResult = true;
    				vm.q_1_loadText = "Patient Registration queue is Empty."
    			} else {
    				vm.q_1_load = false;
    				vm.q_1_loadText = null;
    			}
    			if(vm.tasks.PATIENT_HOSPITAL_VISIT_DEF_KEY.length == 0){
    				vm.q_2_load = false;
    				vm.q_2_noResult = true;
    				vm.q_2_loadText = "Patient Hospital Visit queue is Empty."
    			} else {
    				vm.q_2_load = false;
    				vm.q_2_loadText = null;
    			}
    			Loader.destroy();
    		});
        }

        // init function, execution starts here
        init();
}]);