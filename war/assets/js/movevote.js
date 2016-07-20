$(document).ready(function() {
	$(".button-collapse").sideNav({});
	$(".dropdown-button").dropdown({
		constrain_width : false,
		alignment : 'right',
		belowOrigin : true,
	});
	$('.owl-carousel').owlCarousel({
		margin : 30,
		loop : true,
		autoWidth : true,
		lazyLoad : true,
		nav : true,
		dots : false,
		items: 5
	})
});