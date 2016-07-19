$(document).ready(function() {
	$(".button-collapse").sideNav({});
	$(".dropdown-button").dropdown({
		constrain_width : false,
		alignment : 'right',
		belowOrigin : true,
	});
	
	getUserData();

	if ($("#survey").length) {
		startSurveyMovie();
	}
});

function getUserData() {
	$.ajax({
		url : "/user",
		dataType : "json",
		success : function(data) {
			$('#autocompleteUser').data('array', data.users);
			initUserAutoComplete();
		}
	});
}

function initUserAutoComplete() {
	var input_selector = 'input[type=text], input[type=password], input[type=email], input[type=url], input[type=tel], input[type=number], input[type=search], textarea';
	$(input_selector)
			.each(
					function() {
						var $input = $(this);
						if ($input.hasClass('autocomplete')) {
							var $array = $input.data('array'), $inputDiv = $input
									.closest('.input-field'); // Div to append
							// on
							// Check if "data-array" isn't empty
							if ($array !== '') {
								// Create html element
								var $html = '<ul class="autocomplete-content hide" style="z-index: 100;">';
								for (var i = 0; i < $array.length; i++) {
									// If path and class aren't empty add image
									// to auto complete else create normal
									// element
									if ($array[i]['path'] !== ''
											&& $array[i]['path'] !== undefined
											&& $array[i]['path'] !== null
											&& $array[i]['class'] !== undefined
											&& $array[i]['class'] !== '') {
										$html += '<li class="autocomplete-option"><img src="'
												+ $array[i]['path']
												+ '" class="'
												+ $array[i]['class']
												+ '"><span>'
												+ $array[i]['name']
												+ '</span></li>';
									} else {
										$html += '<li class="autocomplete-option" data-id="'
												+ $array[i]['id']
												+ '" data-name="'
												+ $array[i]['name']
												+ '"><span>'
												+ $array[i]['name']
												+ '</span></li>';
									}
								}
								$html += '</ul>';
								$inputDiv.append($html); // Set ul in body
								// End create html element
								function highlight(string) {
									$('.autocomplete-content li')
											.each(
													function() {
														var matchStart = $(this)
																.text()
																.toLowerCase()
																.indexOf(
																		""
																				+ string
																						.toLowerCase()
																				+ ""), matchEnd = matchStart
																+ string.length
																- 1, beforeMatch = $(
																this)
																.text()
																.slice(0,
																		matchStart), matchText = $(
																this)
																.text()
																.slice(
																		matchStart,
																		matchEnd + 1), afterMatch = $(
																this)
																.text()
																.slice(
																		matchEnd + 1);
														$(this)
																.html(
																		"<span>"
																				+ beforeMatch
																				+ "<span class='highlight'>"
																				+ matchText
																				+ "</span>"
																				+ afterMatch
																				+ "</span>");
													});
								}

								// Perform search
								$(document)
										.on(
												'keyup',
												$input,
												function() {
													var $val = $input.val()
															.trim(), $select = $('.autocomplete-content');
													// Check if the input isn't
													// empty
													$select.css('width', $input
															.width());
													if ($val != '') {
														$select.children('li')
																.addClass(
																		'hide');
														$select
																.children('li')
																.filter(
																		function() {
																			$select
																					.removeClass('hide'); // Show
																			// results
																			// If
																			// text
																			// needs
																			// to
																			// highlighted
																			if ($input
																					.hasClass('highlight-matching')) {
																				highlight($val);
																			}
																			var check = true;
																			for ( var i in $val) {
																				if ($val[i]
																						.toLowerCase() !== $(
																						this)
																						.text()
																						.toLowerCase()[i])
																					check = false;
																			}
																			;
																			return check ? $(
																					this)
																					.text()
																					.toLowerCase()
																					.indexOf(
																							$val
																									.toLowerCase()) !== -1
																					: false;
																		})
																.removeClass(
																		'hide');
													} else {
														$select.children('li')
																.addClass(
																		'hide');
													}
												});

								// Set input value
								$('.autocomplete-option').click(
										function() {
											$input.val($(this).text().trim());
											$('.autocomplete-option').addClass(
													'hide');
											$('#addBtn').removeClass('hide');
											$('#addBtn').attr('data-id',
													$(this).data("id"));
											$('#addBtn').attr('data-name',
													$(this).data("name"));
											$('#addBtn').attr("onclick",
													"addFriend()");
										});
							} else {
								return false;
							}
						}
					});
}

function addFriend() {
	var friendId = $("#addBtn").attr("data-id");
	var friendName = $("#addBtn").attr("data-name");
	$
			.ajax({
				type : "POST",
				url : "/user",
				data : "action=add&friendId=" + friendId + "&friendName="
						+ friendName,
				dataType : "json",
				beforeSend : function() {
					$("#userSearchLbl").attr("class", "");
					$("#autocompleteUser").val('');
				},
				success : function(data) {
					if (data.success) {
						$("#friendCollection")
								.append(
										'<li class="collection-item avatar"><i class="material-icons circle green">person</i> <span class="title">'
												+ friendName
												+ '</span> <a href="#!" class="secondary-content"><i class="material-icons" data-id="'
												+ friendId
												+ '" onclick="removeFriend(this)">clear</i></a></li>');
						$('#addBtn').addClass('hide');
						$( ".autocomplete-content" ).remove();
						getUserData();
					} else {
						Materialize
								.toast(
										"An error has occured, please try again later.",
										3000);
					}
				}
			});
}
function removeFriend(e) {
	var friendId = $(e).attr("data-id");
	console.log(friendId);
	$.ajax({
		type : "POST",
		url : "/user",
		data : "action=remove&friendId=" + friendId,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$(e).parent().parent().remove();
				getUserData();
			} else {
				Materialize.toast(
						"An error has occured, please try again later.", 3000);
			}
		}
	});
}

function startSurveyMovie() {
	$
			.ajax({
				url : "/survey",
				dataType : "json",
				beforeSend : function() {
					$("#rateLoadingPanel").attr("class", "row center");
					$("#rateLoadedPanel").attr("class", "row hide");
					$("#rateMsgPanel").attr("class", "row hide");
					$("#rateCardAction").attr("class", "row hide");
					initializeRate();
				},
				success : function(data) {
					if (data.success) {
						if (data.hasSurvey) {
							$("#rateSkip").attr(
									"onclick",
									'updateRating(' + data.tmdbId + ', -1, '
											+ data.genreBit + ');');
							$("#rate").attr("data-id", data.tmdbId);
							$("#rate").attr("data-genreBit", data.genreBit);
							$("#rateImg").attr("src", data.imageUrl);
							$("#rateImgBackdrop").attr("src",
									data.imageBackdropUrl);
							$("#rateTitle").html(data.title);
							$("#rateDesc").html(data.overview);
							$("#rateSubText").html(
									data.releaseDate + " | " + data.rating
											+ "/10");

							$.each(data.genreList, function(index, obj) {
								$('#rateGenre').append(
										'<div class="chip">' + obj.name
												+ '</div>');
							});

							$("#rateCardAction").attr("class", "card-action");
							$("#rateLoadingPanel").attr("class", "row hide");
							$("#rateLoadedPanel").attr("class", "row");
						} else {
							$("#survey").attr("class", "row hide");
							Materialize
									.toast(
											"Wonderful, I think we know what's your movie preference now.",
											3000);
						}
					} else {
						$("#rateLoadingPanel").attr("class", "row hide");
						$("#rateLoadedPanel").attr("class", "row hide");
						$("#rateMsgPanel").attr("class", "row center");
						$("#rateCardAction").attr("class", "row hide");
					}
				}
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
				startSurveyMovie();
			} else {
				Materialize.toast(
						"An error has occured, please try again later.", 3000);
			}
		}
	});
}

function initializeRate() {
	// empty all children first
	$('#rateGenre').empty();
	$("#rateWidget").empty();

	$("#rateWidget")
			.append(
					'<div id="rate" class="red-text text-lighten-1" onclick="rateMovie()"></div>');
	$('#rate').addRating({
		fieldName : 'rateRating',
		fieldId : 'rateRating',
	});
}