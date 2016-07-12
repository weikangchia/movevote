<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				class="brand-logo grey-text text-lighten-5 thin"><span
				class="bold">M</span>ove<span class="bold">V</span>ote</a> <a href="#" data-activates="mobile-navbar"
				class="button-collapse"><i class="material-icons">menu</i></a>
			<ul id="nav-mobile" class="right hide-on-med-and-down">
				<li><a href="#"><i class="material-icons tooltipped"
						data-position="bottom" data-delay="50" data-tooltip="discover">movie</i></a></li>
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
				<li><a href="#">Discover</a></li>
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
		<div class="spacer-thick"></div>
		<div class="row">
			<div class="col s10 offset-s1 center">
				<h4 class="grey-text text-darken-2 thin">Discover</h4>
			</div>
		</div>

		<div class="divider"></div>
	</div>

	<div class="container">
		<div class="spacer-normal"></div>
		<div class="row">
			<div class="col s12">
				<h5>Movies to Watch</h5>
				<c:forEach items="${recommendMovieList}" var="rMovie">
					<div class="col s10 m6 l4 offset-s1">
						<div class="card medium">
							<div class="card-image">
								<img src="<c:out
											value="${rMovie.imageUrl}"></c:out>">
								<span class="card-title truncate black"
									style="opacity: 0.8; width: 100%;"><c:out
										value="${rMovie.title}"></c:out></span>
							</div>
							<div class="card-content">
								<p>
									<c:out value="${rMovie.overview}"></c:out>
								</p>
							</div>
							<div class="card-action">
								<a
									href="${pageContext.request.contextPath}/movie?is_id=<c:out
									value="${rMovie.id}"></c:out>&title2=<c:out
									value="${rMovie.title2}"></c:out>&tmdb_id=<c:out
									value="${rMovie.tmdbId}"></c:out>&provider=is">more
									info</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			
			<div class="col s12">
				<div class="spacer-normal"></div>
				<h5>Now Showing test</h5>
				<c:forEach items="${nowMovieList}" var="movie">
					<div class="col s10 m6 l4 offset-s1">
						<div class="card medium">
							<div class="card-image">
								<img src="<c:out
											value="${movie.imageUrl}"></c:out>">
								<span class="card-title truncate black"
									style="opacity: 0.8; width: 100%;"><c:out
										value="${movie.title}"></c:out></span>
							</div>
							<div class="card-content">
								<p>
									<c:out value="${movie.overview}"></c:out>
								</p>
							</div>
							<div class="card-action">
								<a
									href="${pageContext.request.contextPath}/movie?is_id=<c:out
									value="${movie.id}"></c:out>&title2=<c:out
									value="${movie.title2}"></c:out>&tmdb_id=<c:out
									value="${movie.tmdbId}"></c:out>&provider=is">more
									info</a>
							</div>
						</div>
					</div>
				</c:forEach>
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
			$(".dropdown-button").dropdown({
				constrain_width : false,
				alignment : 'right',
				belowOrigin : true,
			});
		});
	</script>
</body>

</html>