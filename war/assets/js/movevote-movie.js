$(document).ready(function() {
	$(".button-collapse").sideNav();
	$('ul.tabs').tabs();
	$('select').material_select();

	$('#rate').addRating({
		fieldName : 'rateRating',
		fieldId : 'rateRating',
	});

	$(".dropdown-button").dropdown({
		constrain_width : false,
		alignment : 'right',
		belowOrigin : true,
	});

	var rating = $("#rate").attr("data-prevRating");
	if (rating != -1) {
		setStarRating(rating);
	}

	if ($("#showing").length) {
		// fetch movie showing time
		fetchShowingTime(null);

		$("#showingSelector").change(function() {
			fetchShowingTime($(this).val());
		})
	}

});

function setStarRating(rating) {
	resetStarRating();

	// method to set rating
	$('#rate').find('i').each(function() {
		if (rating == 0) {
			return false;
		}
		$(this).text('star');
		rating--;
	});
}

function resetStarRating() {
	var i = 5;
	$('#rate').find('i').each(function() {
		$(this).text('star_border');
		i--;
	});
}

function rateMovie() {
	var tmdbId = $("#rate").attr("data-id");
	var rating = $("#rateRating").val();
	var genreBit = $("#rate").attr("data-genreBit");

	updateRating(tmdbId, rating, genreBit);
}

function updateRating(tmdbId, rating, genreBit) {
	$.ajax({
		type : "POST",
		url : "/rate",
		data : "tmdbId=" + tmdbId + "&rating=" + rating + "&genreBit="
				+ genreBit,
		dataType : "json",

		// if received a response from the server
		success : function(data) {
			if (data.success) {
				if (rating == -1) {
					resetStarRating();
				} else {
					$("#rateTitle").html("You have rated");
				}
			} else {
				Materialize.toast(
						"An error has occured, please try again later.", 3000);
			}
		}
	});
}

function fetchShowingTime(date) {
	var title2 = $("#showing").attr("data-title2");
	var id = $("#showing").attr("data-id");

	var dataParam;
	if (date == null) {
		dataParam = "action=showtime&id=" + id + "&title2=" + title2;
	} else {
		dataParam = "action=showtime&id=" + id + "&title2=" + title2 + "&date="
				+ date;
	}

	$
			.ajax({
				type : "GET",
				url : "/movie",
				data : dataParam,
				dataType : "json",
				beforeSend : function() {
					$("#showingError").attr("class", "col s12 center hide");
					$("#showingLoading").attr("class", "col s12 center");
					$("#showingLoaded").attr("class", "col s12 center hide");
					$("#showingAccordion").empty();
				},
				// if received a response from the server
				success : function(data) {
					if (data.success) {
						$
								.each(
										data.cinema,
										function(arrayID, cinema) {
											var cinemaName = cinema.name;
											var cinemaAddress = cinema.address;

											var body = '<li><div class="collapsible-header"><i class="material-icons">theaters</i>'
													+ cinemaName
													+ '</div><div class="collapsible-body">';

											if (cinema.Standard) {
												body += '<h6>Standard</h6>';
												$
														.each(
																cinema.Standard,
																function(
																		arrayId,
																		standard) {
																	if (arrayId > 0) {
																		body += ", ";
																	}
																	body += '<a href="'
																			+ standard.url
																			+ '">'
																			+ standard.timing
																			+ '</a> ';
																});
											}

											if (cinema.Atmos) {
												body += '<h6>Atmos</h6>';
												$.each(cinema.Atmos, function(
														arrayId, atmos) {
													if (arrayId > 0) {
														body += ", ";
													}
													body += '<a href="'
															+ atmos.url + '">'
															+ atmos.timing
															+ '</a> ';
												});
											}

											if (cinema.Digital) {
												body += '<h6>Digital</h6>';
												$
														.each(
																cinema.Digital,
																function(
																		arrayId,
																		digital) {
																	if (arrayId > 0) {
																		body += ", ";
																	}
																	body += '<a href="'
																			+ digital.url
																			+ '">'
																			+ digital.timing
																			+ '</a> ';
																});
											}

											body += '</div></li>';
											$("#showingAccordion").append(body);

											$("#showingLoaded").attr("class",
													"col s12");
											$("#showingLoading").attr("class",
													"col s12 hide center");
										});
					} else {
						$("#showingError").attr("class", "col s12 center");
						$("#showingLoading").attr("class",
								"col s12 hide center");
					}
				}
			});
}