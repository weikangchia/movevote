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
			<li><a
				href="${pageContext.request.contextPath}/user-profile.html">Profile</a></li>
			<li><a href="${pageContext.request.contextPath}/sign_out">Sign
					out</a></li>
		</ul>
		<ul id="dropdown2" class="dropdown-content">
			<li><a
				href="${pageContext.request.contextPath}/user-profile.html">Profile</a></li>
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
						<li class="tab col s3"><a class="active" href="#overview">Overview</a></li>
						<li class="tab col s3"><a href="#showing">Now Showing</a></li>
						<li class="tab col s3"><a href="#trailer">Trailers</a></li>
						<li class="tab col s3"><a href="#similar">Similar</a></li>
						<li class="tab col s3"><a href="#review">Reviews</a></li>
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
						<h5>How much you like this movie?</h5>
						<div class="rating red-text text-lighten-2"></div>
					</div>
				</div>

				<div id="trailer" class="col s12">
					<c:if test="${fn:length(movie.youTubeVideoList) eq 0}">
						<p class="center-align">No trailer available yet.</p>
					</c:if>
					<c:if test="${fn:length(movie.youTubeVideoList) gt 0}">
						<c:forEach items="${ movie.youTubeVideoList }" var="video">
							<h6>
								<c:out value="${video.name}"></c:out>
							</h6>
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
								<c:choose>
									<c:when test="${fn:length(similar.title) lt 25}">
										<c:set var="truncateTitle" value="${similar.title}" />
									</c:when>
									<c:otherwise>
										<c:set var="truncateTitle"
											value="${fn:substring(similar.title, 0, 20)}..." />
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${fn:length(similar.overview) lt 200}">
										<c:set var="truncateOverview" value="${similar.overview}" />
									</c:when>
									<c:otherwise>
										<c:set var="truncateOverview"
											value="${fn:substring(similar.overview, 0, 200)}..." />
									</c:otherwise>
								</c:choose>
								<div class="col s12 m6">
									<div class="card small">
										<div class="card-image waves-effect waves-block waves-light">
											<img class="activator"
												src="<c:out value="${similar.imageUrl}"></c:out>">
										</div>
										<div class="card-content">
											<span class="card-title activator grey-text text-darken-4"><c:out
													value="${truncateTitle}"></c:out><i
												class="material-icons right">more_vert</i> </span>
										</div>
										<div class="card-reveal">
											<span class="card-title grey-text text-darken-4"><c:out
													value="${similar.title}"></c:out><i
												class="material-icons right">close</i> </span>
											<p>
												<c:out value="${truncateOverview}"></c:out>
											</p>
										</div>
										<div class="card-action">
											<a href="#">More info</a>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>

				<div id="review" class="col s12">
					<c:if test="${fn:length(movie.reviewList) eq 0}">
						<p class="center-align">Not review yet.</p>
					</c:if>
					<c:if test="${fn:length(movie.reviewList) gt 0}">
						<ul class="collection">
							<c:forEach items="${ movie.reviewList }" var="review">
								<li class="collection-item avatar"><i
									class="material-icons circle green">person</i> <span
									class="title"><c:out value="${review.author.name }"></c:out></span>
									<div class="more">
										<c:out value="${review.content }" escapeXml="false"></c:out>
									</div></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<div id="showing" class="col s12">
					<div class="input-field col s12">
						<select>
							<option value="" disabled selected>Select a Date</option>
							<option value="1">Today</option>
							<option value="2">Tomorrow</option>
							<option value="3">Tuesday</option>
						</select>
					</div>

					<div class="col s12">
						<ul class="collapsible popout" data-collapsible="accordion">
							<li>
								<div class="collapsible-header active">
									<i class="material-icons">theaters</i>Cathay AMK Hub
								</div>
								<div class="collapsible-body">
									<h6>Standard</h6>
									<a href="">07:20 PM</a>, <a href="">10:30 PM</a>
								</div>
							</li>
							<li>
								<div class="collapsible-header">
									<i class="material-icons">theaters</i>GV Yishun
								</div>
								<div class="collapsible-body">
									<h6>Standard</h6>
									<a href="">08:20 PM</a>, <a href="">10:50 PM</a>
								</div>
							</li>
							<li>
								<div class="collapsible-header">
									<i class="material-icons">theaters</i>Shaw Nex
								</div>
								<div class="collapsible-body">
									<h6>Standard</h6>
									<a href="">08:20 PM</a>, <a href="">10:50 PM</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="spacer-thick"></div>

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

			$('ul.tabs').tabs();

			$('select').material_select();

			$('.rating').addRating();

			$(".dropdown-button").dropdown({
				constrain_width : false,
				alignment : 'right',
				belowOrigin : true,
			});

			// method to set rating
			var i = 1;
			$('.rating').find('i').each(function() {
				if (i == 0) {
					return false;
				}
				$(this).text('favorite');
				i--;
			});
		});

		var showChar = 100; // shows 100 character by default
		var ellipsestext = "...";
		var moretext = "Read more";
		var lesstext = "Collapse";

		$('.more')
				.each(
						function() {
							var content = $(this).html();

							if (content.length > showChar) {

								var c = content.substr(0, showChar);
								var h = content.substr(showChar, content.length
										- showChar);

								var html = c
										+ '<span class="moreellipses">'
										+ ellipsestext
										+ '&nbsp;</span><span class="morecontent"><span>'
										+ h
										+ '</span>&nbsp;&nbsp;<a href="" class="morelink">'
										+ moretext + '</a></span>';

								$(this).html(html);
							}

						});

		$(".morelink").click(function() {
			if ($(this).hasClass("less")) {
				$(this).removeClass("less");
				$(this).html(moretext);
			} else {
				$(this).addClass("less");
				$(this).html(lesstext);
			}
			$(this).parent().prev().toggle();
			$(this).prev().toggle();
			return false;
		});
	</script>
</body>

</html>