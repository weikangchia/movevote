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
				<h4 class="grey-text text-darken-2 thin">
					<c:out value="${userInfo.name}"></c:out>
				</h4>
			</div>
		</div>

		<div class="row center grey-text text-darken-2">
			<div class="col s10 m6 offset-m3 offset-s1">
				<div class="row">
					<div class="col s6">
						<span class="caption-large thin"><c:out
								value="${fn:length(friendList)}"></c:out></span><br />friends
					</div>
					<div class="col s6">
						<span class="caption-large thin">0</span><br />groups
					</div>
				</div>

			</div>
			<div class="spacer-thin"></div>
		</div>

		<div class="divider"></div>
	</div>

	<div class="container">
		<div class="spacer-normal"></div>

		<c:if test="${hasSurvey}">
			<div id="survey" class="row">
				<div class="col s12">
					<div class="card-panel teal lighten-2">
						<span class="white-text">It seems that you have not
							completed the movie survey yet. Please continue to rate the
							movies below.</span>
					</div>
				</div>
				<div class="col s12">
					<div class="card white">
						<div class="card-content">
							<div id="rateLoadingPanel" class="row center">
								<div class="col s12">
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
							</div>
							<div id="rateLoadedPanel" class="row hide">
								<div class="col s12 m4 l3">
									<img id="rateImg" class="responsive-img hide-on-small-only">
									<img id="rateImgBackdrop"
										class="responsive-img hide-on-med-and-up" src="">
								</div>
								<div class="col s12 m8 l9">
									<h5 id="rateTitle"></h5>
									<p id="rateSubText"></p>
									<div class="spacer-thin"></div>
									<div id="rateGenre"></div>
									<div class="spacer-thin"></div>
									<div id="rateDesc"></div>
									<div class="spacer-thin"></div>
									<div id="rateWidget"></div>
								</div>
							</div>
						</div>
						<div id="rateMsgPanel" class="row hide center">
							<p id="rateErrorMsg" class="col s10 offset-s1">So
								embarrassing, we are unable to generate any more movies for you
								to rate.</p>
						</div>
						<div id="rateCardAction" class="card-action hide">
							<a id="rateSkip" href="javascript:void(0)">Skip</a>
						</div>
					</div>
				</div>
			</div>
		</c:if>

		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="tab col s3"><a class="active" href="#friend">Friend</a></li>
					<li class="tab col s3"><a href="#group">Group</a></li>
				</ul>
			</div>

			<div id="friend" class="col s12">
				<div class="spacer-thin"></div>
				<div class="row">
					<div class="input-field col s12">
						<label id="userSearchLbl" class="active">Search</label> <input
							type="text" id="autocompleteUser"
							class="autocomplete inputFields">
					</div>
					<div class="col s12">
						<a id="addBtn" class="waves-effect waves-light btn hide"
							href="javascript:void(0)">Add</a>
					</div>
				</div>
				<ul id="friendCollection" class="collection">
					<c:forEach items="${ friendList }" var="friend">
						<li class="collection-item avatar"><i
							class="material-icons circle green">person</i> <span
							class="title"><c:out value="${friend.name}"></c:out></span> <a
							href="#!" class="secondary-content"><i class="material-icons"
								data-id="<c:out value="${friend.id}"></c:out>"
								onclick="removeFriend(this)">clear</i></a></li>
					</c:forEach>
				</ul>
			</div>

			<div id="group" class="col s12">
				<div class="spacer-thin"></div>
				<p class="center-align">You have not created any group yet.</p>
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
	<script type="text/javascript" src="assets/js/movevote-user_profile.js"
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