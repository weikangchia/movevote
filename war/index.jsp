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
<link type="text/css" rel="stylesheet"
	href="assets/css/owl.carousel.css" />
<link type="text/css" rel="stylesheet"
	href="assets/css/owl.theme.default.min.css" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>MoveVote - Movie recommendation</title>
</head>

<body>
	<div class="navbar-fixed">
		<nav>
			<div class="nav-wrapper container">
				<a href="#" class="brand-logo grey-text text-lighten-5"><span
					class="bold">Move</span><span class="thin">Vote</span></a> <a href="#"
					data-activates="mobile-navbar" class="button-collapse"><i
					class="material-icons">menu</i></a>
				<ul id="nav-mobile" class="right hide-on-med-and-down">
					<li><a href="#showing">Now Showing</a></li>
					<li><a href="#features">Features</a></li>
					<li><a href="#team">Team</a></li>
					<li><a href="#">Login</a></li>
				</ul>
				<ul class="side-nav" id="mobile-navbar">
					<li><a href="#showing">Now Showing</a></li>
					<li><a href="#features">Features</a></li>
					<li><a href="#team">Team</a></li>
					<li><a href="#">Login</a></li>
				</ul>
			</div>
		</nav>
	</div>

	<div id="hero">
		<div class="container">
			<div class="row">
				<div class="col s12 center">
					<h1 id="hero-title" class="grey-text text-darken-5">
						<span class="bold">Move</span><span class="thin">Vote</span>
					</h1>
					<h3 class="thin">Recommend movie to you and your friends</h3>
				</div>
			</div>
			<div class="spacer"></div>
			<div class="row">
				<div class="col s12 center">
					<a href="#" class="btn btn-large red lighten-1">Get Started</a>
				</div>
			</div>
		</div>
	</div>

	<div class="divider"></div>

	<div class="container">
		<span id="showing" class="scrollspy"></span>
		<div class="spacer"></div>
		<div class="spacer"></div>
		<div class="row center">
			<h3 class="thin">Now Showing</h3>
			<i class="fa fa-map-marker" aria-hidden="true"></i><span
				class="medium"> Singapore</span>
		</div>
		<div class="row">
			<div class="owl-carousel">
				<c:forEach items="${ movieList }" var="movie">
					<div class="item">
						<div class="card-panel teal row">
							<div class="col s12">
								<img src="<c:out
									value="${movie.imageUrl}"></c:out>"
									class="responsive-img">
							</div>

							<div class="col s12">
								<div class="spacer-small"></div>
								<a href="" class="medium white-text"><c:out
										value="${movie.title}"></c:out></a>
								<div class="spacer-small"></div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<div class="spacer"></div>

		<span id="features" class="scrollspy"></span>
		<div class="spacer"></div>
		<div class="spacer"></div>
		<div class="row center">
			<h3 class="thin">Features</h3>
		</div>
		<div class="row">
			<div class="col s12 l4">
				<h5 class="center thin">Individual</h5>
			</div>

			<div class="col s12 l4">
				<h5 class="center thin">Friends</h5>
			</div>

			<div class="col s12 l4">
				<h5 class="center thin">Couple</h5>
			</div>
		</div>

		<div class="spacer"></div>

		<span id="team" class="scrollspy"></span>
		<div class="spacer"></div>
		<div class="spacer"></div>
		<div class="row center">
			<h3 class="thin">Meet the Team</h3>
			<p class="caption">We are a team of students from National
				University of Singapore.</p>
		</div>
		<div class="spacer"></div>
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
				</div>
			</div>
		</div>
		<div class="spacer"></div>
	</div>

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
	<script type="text/javascript" src="assets/js/owl.carousel.min.js"></script>
	<script>
		$(document).ready(function() {
			vph = $(window).height() - 56;
			$('#hero').css('height', vph);
			$(".button-collapse").sideNav();
			$('.owl-carousel').owlCarousel({
				margin : 30,
				loop : true,
				autoWidth : true,
				lazyLoad : true,
				nav : true,
				dots : false,
			})
		});

		$(window).resize(function() {
			vpw = $(window).width();
			vph = $(window).height() - 56;

			$('#hero').css('height', vph);
		});
	</script>
</body>

</html>