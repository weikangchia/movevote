<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>

<head>
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet"
	href="assets/css/materialize.min.css" media="screen,projection" />
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

	<nav id="main">
		<div class="nav-wrapper container">
			<a href="${pageContext.request.contextPath}/home"
				class="brand-logo grey-text text-lighten-5"><span class="bold">Move</span><span
				class="thin">Vote</span></a> <a href="#" data-activates="mobile-navbar"
				class="button-collapse"><i class="material-icons">menu</i></a>
			<ul id="nav-mobile" class="right hide-on-med-and-down">
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
						<c:if test="${ !empty inSingId }">
							<li class="tab col s4"><a href="#showing">Now Showing</a></li>
						</c:if>
						<li class="tab col s4"><a href="#similar">Similar</a></li>
						<li class="tab col s4"><a href="#trailer">Trailers</a></li>
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
									data-genreBit="<c:out value="${movie.genreBit}"></c:out>"
									data-prevRating="<c:out value="${rating}"></c:out>"
									onclick="rateMovie()"></div>
							</c:when>
							<c:otherwise>
								<h5 id="rateTitle">How much you like this movie?</h5>
								<div id="rate" class="red-text text-lighten-1"
									data-id="<c:out value="${movie.id}"></c:out>"
									data-genreBit="<c:out value="${movie.genreBit}"></c:out>"
									data-prevRating="0" onclick="rateMovie()"></div>
							</c:otherwise>
						</c:choose>

						<div class="spacer-thin"></div>
						<a class="red btn tooltipped" data-position="bottom"
							data-delay="50" data-tooltip="discard" href="javascript:void(0)"
							onclick="updateRating(<c:out value="${movie.id}"></c:out>,-1, <c:out value="${movie.genreBit}"></c:out>)"><i
							class="material-icons">delete</i></a>
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
								<div class="col s6 m6 l4">
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
											<a
												href="${pageContext.request.contextPath}/movie?provider=tmdb&tmdb_id=<c:out
											value="${similar.id}"></c:out>">more
												info</a>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>

				<div id="trailer" class="col s12">
					<c:if test="${fn:length(movie.youTubeVideoList) eq 0}">
						<p class="center-align">No trailer available yet.</p>
					</c:if>
					<c:if test="${fn:length(movie.youTubeVideoList) gt 0}">
						<c:forEach items="${ movie.youTubeVideoList }" var="video">
							<h6 class="bold">
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

				<c:if test="${ !empty inSingId }">
					<div id="showing" class="col s12"
						data-id="<c:out value="${ inSingId }"></c:out>"
						data-title2="<c:out value="${ title2 }"></c:out>">
						<div class="input-field col s12">
							<select id="showingSelector">
								<option value="<c:out value="${ today }"></c:out>" selected><c:out
										value="Today (${ today })"></c:out></option>
								<option value="<c:out value="${ tomorrow }"></c:out>"><c:out
										value="Tomorrow (${ tomorrow })"></c:out>
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
				</c:if>
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
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"
		defer></script>
	<script type="text/javascript" src="assets/js/materialize.min.js" defer></script>
	<script type="text/javascript"
		src="assets/js/jquery.star.rating.min.js" defer></script>
	<script type="text/javascript" src="assets/js/movevote-movie.js" defer></script>
	<script defer>
		var cb = function() {
			var l = document.createElement('link');
			l.rel = 'stylesheet';
			l.href = 'https://fonts.googleapis.com/icon?family=Material+Icons';
			var h = document.getElementsByTagName('head')[0];
			h.parentNode.insertBefore(l, h);
		};
		var raf = requestAnimationFrame || mozRequestAnimationFrame
				|| webkitRequestAnimationFrame || msRequestAnimationFrame;
		if (raf)
			raf(cb);
		else
			window.addEventListener('load', cb);
	</script>
</body>
</html>