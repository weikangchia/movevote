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
			widgetUrl : '/gitkit',
			apiKey : 'AIzaSyDtqW6zARF6LhKFHci_mST7x5yCNgfqRSQ',
			signInSuccessUrl : '/',
			idps : [ "password", "google", "facebook" ],
			idpButtons : 1,
			oobActionUrl : '/gitkit',
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

<title>MoveVote - Sign in</title>
</head>

<body>
	<div class="navbar-fixed">
		<nav>
			<div class="nav-wrapper container">
				<a href="${pageContext.request.contextPath}/home"
					class="brand-logo grey-text text-lighten-5"><span class="bold">Move</span><span
					class="thin">Vote</span></a> <a href="" data-activates="mobile-navbar"
					class="button-collapse"><i class="material-icons">menu</i></a>
			</div>
		</nav>
	</div>
	<main>
	<div class="container">
		<div class="spacer-thick"></div>
		<div class="row">
			<!-- Placeholder for the GAT widget panels -->
			<div id="gitkitWidgetDiv"></div>
		</div>
		<div class="spacer-thick"></div>
	</div>
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
	<script type="text/javascript" src="assets/js/owl.carousel.min.js"></script>
</body>

</html>