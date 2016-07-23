$(document).ready(function() {
	$('#all').hide();
	$(".button-collapse").sideNav({});
	$(".dropdown-button").dropdown({
		constrain_width : false,
		alignment : 'right',
		belowOrigin : true,
	});
	$('select').material_select();
	$('select').on('change', function() {
		var selectedVal = this.value;
		if (selectedVal == 1) {
			$('#recommended').show();
			$('#all').hide();
		} else if (selectedVal == 2) {
			$('#all').show();
			$('#recommended').hide();
		}
	});
});