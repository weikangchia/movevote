<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<nav>
		<div class="nav-wrapper container">
			<a href="${pageContext.request.contextPath}/home"
				class="brand-logo grey-text text-lighten-5 thin"><span
				class="bold">M</span>ove<span class="bold">V</span>ote</a> <a href="#"
				data-activates="mobile-navbar" class="button-collapse"><i
				class="material-icons">menu</i></a>
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
		<div class="spacer-thick"></div>
		<div class="row">
			<div class="col s10 offset-s1 center">
				<h4 class="grey-text text-darken-2 thin">Now Showing</h4>
				<span class="caption-normal thin">Singapore</span>
			</div>
		</div>
		<div class="divider"></div>
	</div>

	<div class="container">
		<div class="spacer-thin"></div>
		<div class="row">
			<div class="input-field col s12 m4 l3 right">
				<select>
					<option value="1" selected>Show recommended only</option>
					<option value="2">Show all</option>
				</select>
			</div>
		</div>

		<div class="row hide-on-small-only">
			<div class="col s12 recommended">
				<c:choose>
					<c:when test="${empty recommendMovieList}">
						<p>It seems that you have not completed the movie survey yet.
							Please go back to your profile and finish the movie survey.</p>
						<a class="waves-effect waves-light btn" href="/user_profile">My
							Profile</a>
					</c:when>
					<c:otherwise>
						<c:forEach items="${recommendMovieList}" var="rMovie">
							<div class="col s12 m6 l4 offset-s1">
								<div class="card medium">
									<div class="card-image">
										<img
											src="<c:out
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
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col s12 all">
				<c:forEach items="${nowMovieList}" var="movie">
					<div class="col s12 m6 l4 offset-s1">
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

		<div class="row hide-on-med-and-up">
			<div class="col s12 recommended">
				<c:choose>
					<c:when test="${empty recommendMovieList}">
						<p>It seems that you have not completed the movie survey yet.
							Please go back to your profile and finish the movie survey.</p>
						<a class="waves-effect waves-light btn" href="/user_profile">My
							Profile</a>
					</c:when>
					<c:otherwise>
						<ul class="collection">
							<c:forEach items="${recommendMovieList}" var="rMovie">
								<li class="collection-item avatar"><img class="circle"
									src="<c:out
											value="${rMovie.imageUrl}"></c:out>"
									alt=""><span class="title"><c:out
											value="${rMovie.title}"></c:out></span>
									<p class="truncate">
										<c:out value="${rMovie.overview}"></c:out>
									</p> <a
									href="${pageContext.request.contextPath}/movie?is_id=<c:out
									value="${rMovie.id}"></c:out>&title2=<c:out
									value="${rMovie.title2}"></c:out>&tmdb_id=<c:out
									value="${rMovie.tmdbId}"></c:out>&provider=is">more
										info</a></li>
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col s12 all">
				<ul class="collection">
					<c:forEach items="${nowMovieList}" var="rMovie">
						<li class="collection-item avatar"><img class="circle"
							src="<c:out
											value="${rMovie.imageUrl}"></c:out>"
							alt=""><span class="title"><c:out
									value="${rMovie.title}"></c:out></span>
							<p class="truncate">
								<c:out value="${rMovie.overview}"></c:out>
							</p> <a
							href="${pageContext.request.contextPath}/movie?is_id=<c:out
									value="${rMovie.id}"></c:out>&title2=<c:out
									value="${rMovie.title2}"></c:out>&tmdb_id=<c:out
									value="${rMovie.tmdbId}"></c:out>&provider=is">more
								info</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

	<div class="spacer-thick"></div>
	</main>

	<footer class="page-footer">
		<div class="container white-text">
			<div class="row">
				<div class="col s12 m3">
					<h5>Top 5 Movies</h5>
					<ol>
						<c:forEach items="${top5MovieList}" var="movie">
							<a
								href="/movie?is_id=<c:out
									value="${movie.id}"></c:out>&title2=<c:out
									value="${movie.title2}"></c:out>&tmdb_id=<c:out
									value="${movie.tmdbId}"></c:out>&provider=is"
								class="medium white-text"><li><c:out
										value="${movie.title}"></c:out></li></a>
						</c:forEach>
					</ol>
				</div>
				<div class="col s12 m3">
					<h5>Info</h5>
					<a class="white-text" href="/privacy">Terms of Service and
						Privacy Policy</a>
				</div>
				<div class="col s12 m6">
					<h5>About</h5>
					<p>MoveVote is a web app designed for optimal viewing and
						interaction experience that recommends you and your friends movies
						that are currently showing in the cinema. You will do a quick
						movie survey so that we can know your individual movie
						preferences. If you do not know the movie, you can skip it.</p>
				</div>
			</div>
		</div>
		<div class="footer-copyright">
			<div class="container white-text">
				<i class="fa fa-copyright" aria-hidden="true"></i> 2016 Copyright
				Civil War <span class="right">Made using Materialize</span>
			</div>
		</div>
	</footer>

	<!--Import jQuery before materialize.js-->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"
		defer></script>
	<script type="text/javascript" src="assets/js/materialize.min.js" defer></script>
	<script type="text/javascript" src="assets/js/movevote-discover.js"
		defer></script>
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