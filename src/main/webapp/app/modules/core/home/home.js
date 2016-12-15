core.controller("HomeController", ['$rootScope', '$scope', '$state', '$location', 'Flash', 'appSettings',
function ($rootScope, $scope, $state, $location, Flash, appSettings) {
	
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
            vm.showDetails = true;
            vm.home = {};
        };

        // init function, execution starts here
        init();
}]);