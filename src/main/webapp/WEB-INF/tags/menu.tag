
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>
	

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">
			
				<spring:message code="homepage" var="homepage"/>
				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="${homepage}">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>${homepage}</span>
				</petclinic:menuItem>
				
				<spring:message code="findowners" var="findowners"/>
				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="${findowners}">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>${findowners}</span>
				</petclinic:menuItem>
				
				<spring:message code="veterinarians" var="veterinarians"/>
				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="${veterinarians}">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>${veterinarians}</span>
				</petclinic:menuItem>
				
				<spring:message code="adoptionsmenu" var="adoptionsmenu"/>
				<petclinic:menuItem active="${name eq 'adoptionsmenu'}" url="/adoptions/all"
					title="${adoptionsmenu}">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>${adoptionsmenu}</span>

				<spring:message code="causes" var="causes"/>
				<petclinic:menuItem active="${name eq 'causes'}" url="/causes"
					title="${causes}">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>${causes}</span>
				</petclinic:menuItem>
				
			
				</petclinic:menuItem>

			</ul>



			<spring:message code="login" var="login"/>
			<spring:message code="register" var="register"/>
			
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">${login}</a></li>
					<li><a href="<c:url value="/users/new" />">${register}</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm"><spring:message code="logout"/>
													</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->						
							<sec:authorize access="hasAuthority('owner')">
											   <li> 
															<div class="navbar-login navbar-login-session">
																<div class="row">
																	<div class="col-lg-12">
																		<p>
																			<a href="/petitions/mypetitions" class="btn btn-primary btn-block">My petitions</a>
																			
																		</p>
																	</div>
																</div>
															</div>
														</li>
											</sec:authorize>
						</ul></li>
				</sec:authorize>
				
				
			</ul>
		</div>



	</div>
</nav>


