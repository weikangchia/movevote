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
				<li><a href="#"><i class="material-icons tooltipped"
						data-position="bottom" data-delay="50"
						data-tooltip="recommend movie">movie</i></a></li>
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
				<li><a href="#">Recommend Movie</a></li>
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
	<div class="red lighten-2">
		<div class="container">
			<div class="spacer-thick"></div>
			<div class="row">
				<div class="col s10 offset-s1 center">
					<h4 class="white-text">
						<c:out value="${userInfo.name}"></c:out>
					</h4>
				</div>
			</div>

			<div class="row center white-text">
				<div class="col s10 m6 offset-m3 offset-s1">
					<div class="row">
						<div class="col s6">
							<span class="caption-large thin">2</span><br />friends
						</div>
						<div class="col s6">
							<span class="caption-large thin">0</span><br />groups
						</div>
					</div>

				</div>
				<div class="spacer-thin"></div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="spacer-normal"></div>

		<c:if test="${hasSurvey}">
			<div id="survey" class="row">
				<div class="col s12">
					<div class="card-panel teal lighten-2">
						<span class="white-text">It seems that you have not
							completed the movie survey yet. Please continue to rate the
							movies below.</span>
					</div>
				</div>
				<div class="col s12">
					<div class="card white">
						<div class="card-content">
							<div id="rateLoadingPanel" class="row center">
								<div class="col s12">
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
							</div>
							<div id="rateLoadedPanel" class="row hide">
								<div class="col s12 m4 l3">
									<img id="rateImg" class="responsive-img hide-on-small-only">
									<img id="rateImgBackdrop"
										class="responsive-img hide-on-med-and-up" src="">
								</div>
								<div class="col s12 m8 l9">
									<h5 id="rateTitle"></h5>
									<p id="rateSubText"></p>
									<div class="spacer-thin"></div>
									<div id="rateGenre"></div>
									<div class="spacer-thin"></div>
									<div id="rateDesc"></div>
									<div class="spacer-thin"></div>
									<div id="rateWidget"></div>
								</div>
							</div>
						</div>
						<div id="rateMsgPanel" class="row hide center">
							<p id="rateErrorMsg" class="col s10 offset-s1">So
								embarrassing, we are unable to generate any more movies for you
								to rate.</p>
						</div>
						<div id="rateCardAction" class="card-action hide">
							<a id="rateSkip" href="javascript:void(0)">Skip</a>
						</div>
					</div>
				</div>
			</div>
		</c:if>

		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="tab col s3"><a class="active" href="#friend">Friend</a></li>
					<li class="tab col s3"><a href="#group">Group</a></li>
				</ul>
			</div>

			<div id="friend" class="col s12">
				<div class="spacer-thin"></div>
				<ul class="collection">
					<li class="collection-item avatar"><img
						src="assets/img/profile/david_ten.jpg" alt="" class="circle">
						<span class="title">David</span>
						<p>1 movie watched</p> <a href="#!" class="secondary-content"><i
							class="material-icons">clear</i></a></li>
					<li class="collection-item avatar"><i
						class="material-icons circle green">person</i> <span class="title">Wei
							Han</span>
						<p>6 movie watched</p> <a href="#!" class="secondary-content"><i
							class="material-icons">clear</i></a></li>
				</ul>
			</div>

			<div id="group" class="col s12">
				<div class="spacer-thin"></div>
				<p class="center-align">You have not created any group yet.</p>
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
	<script>
		$(document).ready(function() {
			$(".button-collapse").sideNav({});
			$(".dropdown-button").dropdown({
				constrain_width : false,
				alignment : 'right',
				belowOrigin : true,
			});
		});
	</script>

	<c:if test="${hasSurvey}">
		<script>
			$(document).ready(function() {
				startSurveyMovie();
			});

			function startSurveyMovie() {
				$
						.ajax({
							url : "/survey",
							dataType : "json",
							beforeSend : function() {
								$("#rateLoadingPanel").attr("class",
										"row center");
								$("#rateLoadedPanel").attr("class", "row hide");
								$("#rateMsgPanel").attr("class", "row hide");
								$("#rateCardAction").attr("class", "row hide");
								initializeRate();
							},
							success : function(data) {
								if (data.success) {
									if (data.hasSurvey) {
										$("#rateSkip")
												.attr(
														"onclick",
														'skipMovie('
																+ data.skip
																+ ','
																+ data.surveyGenresCategory
																+ ');');
										$("#rate").attr("data-id", data.tmdbId);
										$("#rate").attr("data-genreBit",
												data.genreBit);
										$("#rateImg")
												.attr("src", data.imageUrl);
										$("#rateImgBackdrop").attr("src",
												data.imageBackdropUrl);
										$("#rateTitle").html(data.title);
										$("#rateDesc").html(data.overview);
										$("#rateSubText").html(
												data.releaseDate + " | "
														+ data.rating + "/10");

										$.each(data.genreList, function(index,
												obj) {
											$('#rateGenre').append(
													'<div class="chip">'
															+ obj.name
															+ '</div>');
										});

										$("#rateCardAction").attr("class",
												"card-action");
										$("#rateLoadingPanel").attr("class",
												"row hide");
										$("#rateLoadedPanel").attr("class",
												"row");
									} else {
										$("#survey").attr("class", "row hide");
										Materialize
												.toast(
														"Wonderful, I think we know what's your movie preference now.",
														3000);
									}
								} else {
									$("#rateLoadingPanel").attr("class",
											"row hide");
									$("#rateLoadedPanel").attr("class",
											"row hide");
									$("#rateMsgPanel").attr("class",
											"row center");
									$("#rateCardAction").attr("class",
											"row hide");
								}
							}
						});
			}

			function rateMovie() {
				var tmdbId = $("#rate").attr("data-id");
				var rating = $("#rateRating").val();
				var genreBit = $("#rate").attr("data-genreBit");
				$
						.ajax({
							type : "POST",
							url : "/rate",
							data : "tmdbId=" + tmdbId + "&rating=" + rating
									+ "&genreBit=" + genreBit,
							dataType : "json",

							//if received a response from the server
							success : function(data) {
								if (data.success) {
									startSurveyMovie();
								} else {
									Materialize
											.toast(
													"An error has occured, please try again later.",
													3000);
								}
							}
						});
			}

			function skipMovie(skip, surveyGenresCategory) {
				$
						.ajax({
							url : "/survey",
							data : "action=skip&skip=" + skip
									+ "&surveyGenresCategory="
									+ surveyGenresCategory,
							dataType : "json",
							beforeSend : function() {
								$("#rateLoadingPanel").attr("class",
										"row center");
								$("#rateLoadedPanel").attr("class", "row hide");
								$("#rateMsgPanel").attr("class", "row hide");
								$("#rateCardAction").attr("class", "row hide");
								initializeRate();
							},
							success : function(data) {
								if (data.success) {
									if (data.hasSurvey) {
										$("#rateSkip")
												.attr(
														"onclick",
														'skipMovie('
																+ data.skip
																+ ','
																+ data.surveyGenresCategory
																+ ');');
										$("#rate").attr("data-id", data.tmdbId);
										$("#rate").attr("data-genreBit",
												data.genreBit);
										$("#rateImg")
												.attr("src", data.imageUrl);
										$("#rateImgBackdrop").attr("src",
												data.imageBackdropUrl);
										$("#rateTitle").html(data.title);
										$("#rateDesc").html(data.overview);
										$("#rateSubText").html(
												data.releaseDate + " | "
														+ data.rating + "/10");

										$.each(data.genreList, function(index,
												obj) {
											$('#rateGenre').append(
													'<div class="chip">'
															+ obj.name
															+ '</div>');
										});

										$("#rateCardAction").attr("class",
												"card-action");
										$("#rateLoadingPanel").attr("class",
												"row hide");
										$("#rateLoadedPanel").attr("class",
												"row");
									} else {
										$("#survey").attr("class", "row hide");
										Materialize
												.toast(
														"Wonderful, I think we know what's your movie preference now.",
														3000);
									}
								} else {
									$("#rateLoadingPanel").attr("class",
											"row hide");
									$("#rateLoadedPanel").attr("class",
											"row hide");
									$("#rateMsgPanel").attr("class",
											"row center");
									$("#rateCardAction").attr("class",
											"row hide");
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
		</script>
	</c:if>
</body>
</html>