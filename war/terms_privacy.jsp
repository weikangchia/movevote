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

	<nav>
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
		<div class="spacer-thick"></div>
		<div class="row">
			<div class="col s10 offset-s1 center">
				<h4 class="grey-text text-darken-2 thin">Terms of Service and
					Privacy Policy</h4>
			</div>
		</div>
		<div class="divider"></div>
	</div>

	<div class="container">
		<div class="spacer-normal"></div>
		<div class="row">
			<div class="col s12">
				<h5>Terms of Service</h5>
				<ol>
					<li>Terms
						<p>
							By accessing the web site at <a
								href="https://movevote.appspot.com">https://movevote.appspot.com</a>,
							you are agreeing to be bound by these web site Terms and
							Conditions of Use, all applicable laws and regulations, and agree
							that you are responsible for compliance with any applicable local
							laws. If you do not agree with any of these terms, you are
							prohibited from using or accessing this site. The materials
							contained in this web site are protected by applicable copyright
							and trademark law.
						</p>
					</li>
					<li>Disclaimer
						<p>The materials on movevote's web site are provided "as is".
							movevote makes no warranties, expressed or implied, and hereby
							disclaims and negates all other warranties, including without
							limitation, implied warranties or conditions of merchantability,
							fitness for a particular purpose, or non-infringement of
							intellectual property or other violation of rights. Further,
							movevote does not warrant or make any representations concerning
							the accuracy, likely results, or reliability of the use of the
							materials on its Internet web site or otherwise relating to such
							materials or on any sites linked to this site.</p>
					</li>
					<li>Limitations
						<p>In no event shall movevote or its suppliers be liable for
							any damages (including, without limitation, damages for loss of
							data or profit, or due to business interruption,) arising out of
							the use or inability to use the materials on movevote's Internet
							site, even if MoveVote or a movevote authorized representative
							has been notified orally or in writing of the possibility of such
							damage. Because some jurisdictions do not allow limitations on
							implied warranties, or limitations of liability for consequential
							or incidental damages, these limitations may not apply to you.</p>
					</li>
					<li>Revisions and Errata
						<p>The materials appearing on movevote's web site could
							include technical, typographical, or photographic errors.
							MoveVote does not warrant that any of the materials on its web
							site are accurate, complete, or current. MoveVote may make
							changes to the materials contained on its web site at any time
							without notice. MoveVote does not, however, make any commitment
							to update the materials.</p>
					</li>
					<li>Links
						<p>MoveVote has not reviewed all of the sites linked to its
							Internet web site and is not responsible for the contents of any
							such linked site. The inclusion of any link does not imply
							endorsement by MoveVote of the site. Use of any such linked web
							site is at the user's own risk.</p>
					</li>
					<li>Site Terms of Use Modifications
						<p>MoveVote may revise these terms of use for its web site at
							any time without notice. By using this web site you are agreeing
							to be bound by the then current version of these Terms and
							Conditions of Use.</p>
					</li>
				</ol>
				<p>General Terms and Conditions applicable to Use of a Web Site.</p>

				<div class="spacer-normal"></div>

				<h5>Privacy Policy</h5>
				<p>Your privacy is important to us, and it is movevote's policy
					to respect your privacy regarding any information we may collect
					while operating our websites. Accordingly, we have developed this
					Policy in order for you to understand how we collect, use,
					communicate and disclose and make use of personal information.</p>
				<p>When you register to use MoveVote, we ask you to supply the
					following information: Your e-mail address (used as your login
					name) and a password. Using your e-mail address allows you to
					easily remember your MoveVote username and recover your password.
					We won't send you mail without your permission.</p>
				<p>Once you are a registered member of MoveVote, we ask you to
					provide ratings data for movies that you have seen. This ratings
					data is used by the MoveVote recommendation system to infer your
					preference in movies and make recommendations.</p>
				<p>MoveVote does not release e-mail addresses or names to any
					third parties. All your information is held as confidential as is
					practical within our secured database.</p>
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