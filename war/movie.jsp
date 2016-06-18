<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>

<head>
<!--Import Google Icon Font-->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet"
	href="assets/css/materialize.min.css" media="screen,projection" />
<link type="text/css" rel="stylesheet"
	href="assets/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="assets/css/movevote.css" />

<!-- theme color for android chrome -->
<meta name="theme-color" content="#ee6e73" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>MoveVote - Movie recommendation</title>
</head>

<body>
	<c:if test="${isLoggedIn}">
		<ul id="dropdown1" class="dropdown-content">
			<li><a href="${pageContext.request.contextPath}/user_profile">Profile</a></li>
			<li><a
				href="${pageContext.request.contextPath}/gitkit?mode=manageAccount">Manage
					account</a></li>
			<li><a href="${pageContext.request.contextPath}/sign_out">Sign
					out</a></li>
		</ul>
		<ul id="dropdown2" class="dropdown-content">
			<li><a href="${pageContext.request.contextPath}/user_profile">Profile</a></li>
			<li><a
				href="${pageContext.request.contextPath}/gitkit?mode=manageAccount">Manage
					account</a></li>
			<li><a href="${pageContext.request.contextPath}/sign_out">Sign
					out</a></li>
		</ul>
	</c:if>

	<nav>
		<div class="nav-wrapper container">
			<a href="${pageContext.request.contextPath}/home"
				class="brand-logo grey-text text-lighten-5"><span class="bold">Move</span><span
				class="thin">Vote</span></a> <a href="#" data-activates="mobile-navbar"
				class="button-collapse"><i class="material-icons">menu</i></a>
			<ul id="nav-mobile" class="right hide-on-med-and-down">
				<li><a id="toggle-search" href="#"><i
						class="material-icons tooltipped" data-position="bottom"
						data-delay="50" data-tooltip="search">search</i></a></li>
				<li><a href="#"><i class="material-icons tooltipped"
						data-position="bottom" data-delay="50" data-tooltip="discover">movie</i></a></li>
				<li><a href="#"><i class="material-icons tooltipped"
						data-position="bottom" data-delay="50" data-tooltip="group">group_work</i></a></li>
				<c:choose>
					<c:when test="${not isLoggedIn}">
						<li><a
							href="${pageContext.request.contextPath}/gitkit?mode=select">Sign
								in</a></li>
					</c:when>
					<c:otherwise>
						<li><a class="dropdown-button" href="#!"
							data-activates="dropdown1"><img class="circle profile_logo"
								align="middle"
								src="<c:out value="${userInfo.profilePath}"></c:out>"> <c:out
									value="${userInfo.name}"></c:out><i
								class="material-icons right">arrow_drop_down</i></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<ul class="side-nav" id="mobile-navbar">
				<li><a id="toggle-search" href="#">Search</a></li>
				<li><a href="#">Discover</a></li>
				<li><a href="#">Group</a></li>
				<c:choose>
					<c:when test="${not isLoggedIn}">
						<li><a
							href="${pageContext.request.contextPath}/gitkit?mode=select">Sign
								in</a></li>
					</c:when>
					<c:otherwise>
						<li><a class="dropdown-button" href="#!"
							data-activates="dropdown2"><img class="circle profile_logo"
								align="middle"
								src="<c:out value="${userInfo.profilePath}"></c:out>"> <c:out
									value="${userInfo.name}"></c:out><i
								class="material-icons right">arrow_drop_down</i></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</nav>

	<main>
	<div class="container">
		<div class="spacer-normal"></div>
		<div class="row">
			<div class="col s12 m3">
				<div class="col s8 offset-s2 m12 center">
					<img class="responsive-img"
						src="<c:out value="${movie.imageUrl}"></c:out>">
				</div>
			</div>

			<div class="col s12 m9">
				<h4>
					<c:out value="${movie.title}"></c:out>
					<c:if test="${fn:length(movie.releaseDate) gt 0}">
						<c:out value="(${fn:substring(movie.releaseDate, 0, 4)})"></c:out>
					</c:if>
				</h4>
				<h6 class="uppercase">
					<c:out value="${movie.tagLine}"></c:out>
				</h6>
				<p>
					<c:choose>
						<c:when test="${fn:length(movie.releaseDate) eq 0}">
							-
						</c:when>
						<c:otherwise>
							<c:out value="${movie.releaseDate}"></c:out>
						</c:otherwise>
					</c:choose>

					<c:out value=" | ${duration} | "></c:out>
					<c:out value="${movie.rating}/10"></c:out>

				</p>

				<c:forEach items="${ movie.genreList }" var="genre">
					<div class="chip">
						<c:out value="${genre.name}"></c:out>
					</div>
				</c:forEach>

				<div class="section">
					<ul class="tabs">
						<li class="tab col s4"><a class="active" href="#overview">Overview</a></li>
						<li class="tab col s4"><a href="#showing">Now Showing</a></li>
						<li class="tab col s4"><a href="#similar">Similar</a></li>
						<li class="tab col s4"><a href="#review">Reviews</a></li>
					</ul>
				</div>

				<div id="overview" class="col s12">
					<p>
						<c:out value="${movie.overview}"></c:out>
					</p>

					<div class="section">
						<h5>Director</h5>
						<c:forEach items="${ directorList }" var="director">
							<div class="chip">
								<c:out value="${director.name}"></c:out>
							</div>
						</c:forEach>

						<h5>Cast</h5>
						<c:forEach items="${ movie.castList }" var="cast">
							<div class="chip">
								<c:out value="${cast.name}"></c:out>
							</div>
						</c:forEach>
					</div>

					<div class="section">
						<c:choose>
							<c:when test="${ hasRated }">
								<h5 id="rateTitle">You have rated</h5>
								<div id="rate" class="red-text text-lighten-1"
									data-id="<c:out value="${movie.id}"></c:out>"
									data-prevRating="<c:out value="${rating}"></c:out>"
									onclick="rateMovie()"></div>
							</c:when>
							<c:otherwise>
								<h5 id="rateTitle">How much you like this movie?</h5>
								<div id="rate" class="red-text text-lighten-1"
									data-id="<c:out value="${movie.id}"></c:out>"
									data-prevRating="0" onclick="rateMovie()"></div>
							</c:otherwise>
						</c:choose>
						<div class="input-field col s12 m8 l6" style="margin-left: -10px">
							<h5>Have you watched?</h5>
							<select id="rateWatch">
								<option value="" disabled
									<c:if test="${empty rateWatch }"> selected</c:if>>Choose your option</option>
								<option value="want_to_watch"
									<c:if test="${ rateWatch == 'want_to_watch'}"> selected</c:if>>Want to watch</option>
								<option value="watched"
									<c:if test="${ rateWatch == 'watched'}"> selected</c:if>>Watched</option>
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>

					<div class="section">
						<h5>Trailers</h5>
						<c:if test="${fn:length(movie.youTubeVideoList) eq 0}">
							<p class="center-align">No trailer available yet.</p>
						</c:if>
						<c:if test="${fn:length(movie.youTubeVideoList) gt 0}">
							<c:forEach items="${ movie.youTubeVideoList }" var="video">
								<h6>
									<c:out value="${video.name}"></c:out>
								</h6>
								<div class="spacer-thin"></div>
								<div class="video-container">
									<iframe width="853" height="480"
										src="//www.youtube.com/embed/<c:out value="${video.key}"></c:out>
										?rel=0"
										frameborder="0" allowfullscreen></iframe>
								</div>
								<div class="spacer-thin"></div>
							</c:forEach>
						</c:if>
					</div>
				</div>

				<div id="similar" class="col s12">
					<c:if test="${fn:length(movie.similarList) eq 0}">
						<p class="center-align">
							Not enough data to recommend any movies based on
							<c:out value="${movie.title}"></c:out>
							yet.
						</p>
					</c:if>
					<c:if test="${fn:length(movie.similarList) gt 0}">
						<div class="row">
							<c:forEach items="${ movie.similarList }" var="similar">
								<div class="col s6 m4">
									<div class="card medium">
										<div class="card-image">
											<img
												src="<c:out
											value="${similar.imageUrl}"></c:out>">
											<span class="card-title truncate black"
												style="opacity: 0.8; width: 100%;"><c:out
													value="${similar.title}"></c:out></span>
										</div>
										<div class="card-content">
											<p>
												<c:out value="${similar.overview}"></c:out>
											</p>
										</div>
										<div class="card-action">
											<a href="#">more info</a>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>

				<div id="review" class="col s12">
					<c:if test="${fn:length(movie.reviewList) eq 0}">
						<p class="center-align">Not reviewed yet.</p>
					</c:if>
					<c:if test="${fn:length(movie.reviewList) gt 0}">
						<ul class="collection">
							<c:forEach items="${ movie.reviewList }" var="review">
								<li class="collection-item avatar"><i
									class="material-icons circle green">person</i> <span
									class="title"><c:out value="${review.author.name }"></c:out></span>
									<div class="review-text">
										<c:out value="${review.content }" escapeXml="false"></c:out>
									</div></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>

				<div id="showing" class="col s12"
					data-id="<c:out value="${ inSingId }"></c:out>"
					data-title2="<c:out value="${ title2 }"></c:out>">
					<div class="input-field col s12">
						<select id="showingSelector">
							<option value="<c:out value="${ today }"></c:out>" selected>Today
								(
								<c:out value="${ today }"></c:out>)
							</option>
							<option value="<c:out value="${ tomorrow }"></c:out>">Tomorrow
								(
								<c:out value="${ tomorrow }"></c:out>)
							</option>
						</select>
					</div>

					<div id="showingLoading" class="col s12 center">
						<div class="preloader-wrapper small active">
							<div class="spinner-layer spinner-red-only">
								<div class="circle-clipper left">
									<div class="circle"></div>
								</div>
								<div class="gap-patch">
									<div class="circle"></div>
								</div>
								<div class="circle-clipper right">
									<div class="circle"></div>
								</div>
							</div>
						</div>
					</div>

					<div id="showingLoaded" class="col s12 hide">
						<ul id="showingAccordion" class="collapsible popout"
							data-collapsible="accordion"></ul>
						<div class="spacer-normal"></div>
						<p>
							<b>Note:</b> Please remember to still check the right movie
							details before making your final bookings at the respective
							cinemas.
						</p>
					</div>

					<div id="showingError" class="col s12 hide center">
						<p>No showing time.</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="spacer-thick"></div>
	</main>

	<footer class="page-footer">
		<div class="footer-copyright">
			<div class="container white-text">
				<i class="fa fa-copyright" aria-hidden="true"></i> 2016 Copyright
				Civil War <span class="right">Made using Materialize</span>
			</div>
		</div>
	</footer>

	<!--Import jQuery before materialize.js-->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="assets/js/materialize.min.js"></script>
	<script type="text/javascript"
		src="assets/js/jquery.star.rating.min.js"></script>
	<script type="text/javascript" src="assets/js/jquery.shorten.min.js"></script>
	<script>
		$(document).ready(function() {
			$(".button-collapse").sideNav({});

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

			$(".review-text").shorten({
				showChars : 200,
				moreText : 'read more',
				lessText : 'read less'
			});

			// method to set rating
			var i = $("#rate").attr("data-prevRating");
			$('#rate').find('i').each(function() {
				if (i == 0) {
					return false;
				}
				$(this).text('star');
				i--;
			});

			// fetch movie showing time
			fetchShowingTime(null);

			$("#showingSelector").change(function() {
				fetchShowingTime($(this).val());
			})

			$('#rateWatch').change(function() {
				var tmdbId = $('#rate').attr("data-id");
				var action = $('#rateWatch').val();
				watchMovie(tmdbId, action);
			})
		});

		function watchMovie(tmdbId, value) {
			$
					.ajax({
						type : "POST",
						url : "/movie",
						data : "tmdbId=" + tmdbId + "&action=" + value,
						dataType : "json",

						//if received a response from the server
						success : function(data) {
							if (!data.success) {
								Materialize
										.toast(
												"An error has occured, please try again later.",
												3000);
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
				dataParam = "action=showtime&id=" + id + "&title2=" + title2
						+ "&date=" + date;
			}

			$
					.ajax({
						type : "GET",
						url : "/movie",
						data : dataParam,
						dataType : "json",
						beforeSend : function() {
							$("#showingError").attr("class",
									"col s12 center hide");
							$("#showingLoading")
									.attr("class", "col s12 center");
							$("#showingLoaded").attr("class",
									"col s12 center hide");
							$("#showingAccordion").empty();
						},
						//if received a response from the server
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
																			body += '<a href="' + standard.url + '">'
																					+ standard.timing
																					+ '</a> ';
																		});
													}

													if (cinema.Atmos) {
														body += '<h6>Atmos</h6>';
														$
																.each(
																		cinema.Atmos,
																		function(
																				arrayId,
																				atmos) {
																			if (arrayId > 0) {
																				body += ", ";
																			}
																			body += '<a href="' + atmos.url + '">'
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
																			body += '<a href="' + digital.url + '">'
																					+ digital.timing
																					+ '</a> ';
																		});
													}

													body += '</div></li>';
													$("#showingAccordion")
															.append(body);

													$("#showingLoaded").attr(
															"class", "col s12");
													$("#showingLoading")
															.attr("class",
																	"col s12 hide center");
												});
							} else {
								$("#showingError").attr("class",
										"col s12 center");
								$("#showingLoading").attr("class",
										"col s12 hide center");
							}
						}
					});
		}

		function rateMovie() {
			var tmdbId = $("#rate").attr("data-id");
			var rating = $("#rateRating").val();
			$
					.ajax({
						type : "POST",
						url : "/movie",
						data : "tmdbId=" + tmdbId + "&action=rate&rating="
								+ rating,
						dataType : "json",

						//if received a response from the server
						success : function(data) {
							if (data.success) {
								$("#rateTitle").html("You have rated");
							} else {
								Materialize
										.toast(
												"An error has occured, please try again later.",
												3000);
							}
						}
					});
		}
	</script>
</body>

</html>