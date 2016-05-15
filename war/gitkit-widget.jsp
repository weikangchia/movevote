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

<script type="text/javascript"
	src="//www.gstatic.com/authtoolkit/js/gitkit.js"></script>
<link type="text/css" rel="stylesheet"
	href="//www.gstatic.com/authtoolkit/css/gitkit.css" />
<script type="text/javascript">
	function load() {
		var config = {
			widgetUrl : 'https://localhost:8888/gitkit',
			apiKey : 'AIzaSyDtqW6zARF6LhKFHci_mST7x5yCNgfqRSQ',
			signInSuccessUrl : '/',
			idps : [ "password", "google", "facebook" ],
			idpButtons : 1,
			oobActionUrl : '/',
			siteName : 'MoveVote',
		};
		// The HTTP POST body should be escaped by the server to prevent XSS
		window.google.identitytoolkit.start('#gitkitWidgetDiv', // accepts any CSS selector
		config);
	}
</script>
<script type="text/javascript"
	src="//apis.google.com/js/client.js?onload=load"></script>

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

	<div class="container">
		<div class="row">
			<!-- Placeholder for the GAT widget panels -->
			<div id="gitkitWidgetDiv"></div>
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