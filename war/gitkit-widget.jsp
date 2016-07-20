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

<script type="text/javascript"
	src="//www.gstatic.com/authtoolkit/js/gitkit.js"></script>
<link type="text/css" rel="stylesheet"
	href="//www.gstatic.com/authtoolkit/css/gitkit.css" />
<script type="text/javascript">
	function load() {
		var config = {
			widgetUrl : '/gitkit',
			apiKey : 'AIzaSyDtqW6zARF6LhKFHci_mST7x5yCNgfqRSQ',
			signInSuccessUrl : '/user_profile',
			idps : [ "password", "google", "facebook" ],
			idpButtons : 1,
			oobActionUrl : '/gitkit',
			siteName : 'MoveVote',
			acUiConfig : {
				title : 'MoveVote - Movie recommendation'
			},
		};
		// The HTTP POST body should be escaped by the server to prevent XSS
		window.google.identitytoolkit.start('#gitkitWidgetDiv', // accepts any CSS selector
		config);
	}
</script>
<script type="text/javascript"
	src="//apis.google.com/js/client.js?onload=load"></script>

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
				<li><a href="/discover"><i
						class="material-icons tooltipped" data-position="bottom"
						data-delay="50" data-tooltip="discover">movie</i></a></li>
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
				<li><a href="/discover">Discover</a></li>
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
	<div id="progress" class="progress">
		<div class="indeterminate"></div>
	</div>
	<div class="container">
		<div class="spacer-thick"></div>
		<c:if test="${(mode == 'manageAccount') and (not isVerified)}">
			<div class="row">
				<div class="col s12 center orange lighten-2">
					<p class="white-text">
						<i class="material-icons">announcement</i> An email has been sent
						to the email address below with a link to verify your account.
						Please check your email and follow the link to complete your
						account sign up.<br /> <br /> <a id="resend_verification"
							href="#" class="teal-text">Resend this email.</a>
					</p>
				</div>
			</div>
		</c:if>
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
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script type="text/javascript" src="assets/js/materialize.min.js"></script>

	<script>
		$(document)
				.ready(
						function() {
							$("#progress").hide();
							$(".button-collapse").sideNav({});
							$(".dropdown-button").dropdown({
								constrain_width : false,
								alignment : 'right',
								belowOrigin : true,
							});
							$("#resend_verification")
									.click(
											function() {
												$("#progress").show();
												$
														.ajax({
															type : "POST",
															url : "/gitkit",
															data : "action=resendVerficationLink",
															dataType : "json",

															//if received a response from the server
															success : function(
																	data) {
																if (data.success) {
																	$(
																			"#progress")
																			.hide();
																	Materialize
																			.toast(
																					'A verification email has been sent.',
																					3000);
																	$(
																			"#resend_verification")
																			.hide();
																} else {
																	$(
																			"#progress")
																			.hide();
																	Materialize
																			.toast(
																					"Request failed. Please try again.",
																					3000);
																}
															}
														});
											})
						});
	</script>
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