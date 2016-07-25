<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet"
	href="assets/css/materialize.min.css" media="screen,projection" />
<link type="text/css" rel="stylesheet"
	href="assets/css/owl.carousel.css" />
<link type="text/css" rel="stylesheet"
	href="assets/css/owl.theme.default.min.css" />
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
				<li><a href="/home">Home</a></li>
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
				<li><a href="/home">Home</a></li>
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
	<div id="hero">
		<div class="container">
			<div class="spacer-thick"></div>
			<div class="row">
				<div class="col s12 center">
					<img class="responsive-img circle profile_logo"
						src="assets/img/logo.png">
					<h2 id="hero-title" class="grey-text text-darken-5 thin">
						<span class="bold">M</span>ove<span class="bold">V</span>ote
					</h2>
					<h5 class="thin">Recommend movie to you and your friends</h5>
				</div>
			</div>

			<c:if test="${not isLoggedIn}">
				<div class="spacer-thin"></div>
				<div class="row">
					<div class="col s12 center">
						<a href="${pageContext.request.contextPath}/gitkit?mode=select"
							class="btn btn-large red lighten-1">Get Started</a>
					</div>
				</div>
			</c:if>
			<div class="spacer-thick"></div>
			<div class="spacer-thick"></div>
		</div>
	</div>

	<div class="divider"></div>

	<div class="container">
		<div class="spacer-thick"></div>
		<div class="row center">
			<h3 class="thin">Now Showing</h3>
			<span class="caption-normal thin">Singapore</span>
		</div>
		<div class="row">
			<div class="owl-carousel">
				<c:forEach items="${movieList}" var="movie">
					<div class="item">
						<div class="card-panel teal row">
							<div class="col s12">
								<a
									href="/movie?is_id=<c:out
									value="${movie.id}"></c:out>&title2=<c:out
									value="${movie.title2}"></c:out>&tmdb_id=<c:out
									value="${movie.tmdbId}"></c:out>&provider=is"><img
									src="<c:out
									value="${movie.imageUrl}"></c:out>"
									class="responsive-img"></a>
							</div>

							<div class="col s12">
								<div class="spacer-thin"></div>
								<a
									href="/movie?is_id=<c:out
									value="${movie.id}"></c:out>&title2=<c:out
									value="${movie.title2}"></c:out>&tmdb_id=<c:out
									value="${movie.tmdbId}"></c:out>&provider=is"
									class="medium white-text truncate"><c:out
										value="${movie.title}"></c:out></a>
								<div class="spacer-thin"></div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<div class="spacer-thick"></div>
		<div class="divider"></div>

		<div class="spacer-thick"></div>
		<div class="row center">
			<h3 class="thin">Features</h3>
		</div>
		<div class="row">
			<div class="icon-block">
				<div class="col s10 m4 offset-s1">
					<h1 class="center red-text text-lighten-2">
						<i class="material-icons">local_movies</i>
					</h1>
					<h5 class="center thin">Personalized Movie Recommendations</h5>
					<p class="center">Sign up for MoveVote and do a quick movie
						survey, after which we can recommend movies that you and/or your
						group of friends will like.</p>
				</div>
			</div>

			<div class="icon-block">
				<div class="col s10 m4 offset-s1">
					<h1 class="center red-text text-lighten-2">
						<i class="material-icons">access_time</i>
					</h1>
					<h5 class="center thin">Latest Movie Showing Time</h5>
					<p class="center">Not sure when and which cinema your latest
						movie will be showing today and tomorrow? MoveVote can help you
						get all these information for you.</p>
				</div>
			</div>

			<div class="icon-block">
				<div class="col s10 m4 offset-s1">
					<h1 class="center red-text text-lighten-2">
						<i class="material-icons">phonelink</i>
					</h1>
					<h5 class="center thin">Modern Responsive Design</h5>
					<p class="center">To provide our user an optimal viewing and
						interaction experience, we have adopt a modern responsive
						framework based on Material Design by Google.</p>
				</div>
			</div>
		</div>

		<div class="spacer-thick"></div>
		<div class="divider"></div>

		<div class="spacer-thick"></div>
		<div class="row center">
			<h3 class="thin">Meet the Team</h3>
			<p class="caption-normal thin">
				We are a team of students from National University of Singapore.<br />This
				is a project for Orbital 2016.
			</p>
		</div>
		<div class="row">
			<div class="col s12 m6 center">
				<div class="col s6 offset-s3">
					<img class="responsive-img circle"
						src="assets/img/profile/david_ten.jpg">
					<h5 class="center bold red-text text-lighten-1">David Ten</h5>
					<a href="https://www.linkedin.com/in/davidten" class="black-text">
						<i class="fa fa-linkedin-square fa-2x" aria-hidden="true"></i>
					</a> <a href="https://www.facebook.com/dten10" class="black-text">
						<i class="fa fa-facebook-square fa-2x" aria-hidden="true"></i>
					</a>
					<div class="spacer-normal"></div>
				</div>
			</div>

			<div class="col s12 m6 center">
				<div class="col s6 offset-s3">
					<img class="responsive-img circle"
						src="assets/img/profile/wei_kang.jpg">
					<h5 class="center bold red-text text-lighten-1">Wei Kang</h5>
					<a href="https://www.linkedin.com/in/weikangchia"
						class="black-text"> <i class="fa fa-linkedin-square fa-2x"
						aria-hidden="true"></i>
					</a> <a href="https://www.facebook.com/chiaweikang.sg"
						class="black-text"> <i class="fa fa-facebook-square fa-2x"
						aria-hidden="true"></i>
					</a>
					<div class="spacer-normal"></div>
				</div>
			</div>
		</div>

		<c:if test="${not isLoggedIn}">
			<div class="spacer-thick"></div>
			<div class="divider"></div>

			<div class="spacer-thick"></div>
			<div class="row center">
				<h3 class="thin">Interested?</h3>
				<p class="caption-normal thin">Sign up for free now.</p>
				<div class="spacer-normal"></div>
				<a href="${pageContext.request.contextPath}/gitkit?mode=select"
					class="btn btn-large red lighten-1">Sign up</a>
				<p>
					Already have an account? <a
						href="${pageContext.request.contextPath}/gitkit?mode=select">Sign
						In</a>
				</p>
				<div class="spacer-normal"></div>
			</div>
		</c:if>

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
	<script type="text/javascript" src="assets/js/owl.carousel.min.js"
		defer></script>
	<script type="text/javascript" src="assets/js/movevote.js" defer></script>
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