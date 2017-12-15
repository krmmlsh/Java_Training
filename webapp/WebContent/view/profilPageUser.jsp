<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/webapp/css/bootstrap.css" rel="stylesheet"
	media="screen">
<link href="/webapp/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="/webapp/css/main.css" rel="stylesheet" media="screen">
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="wrapper">
    
        <!-- Sidebar -->
        <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
        	<table class="nav sidebar-nav table table-striped2 table-bordered">
        		<tr>
        			<th colspan="2" style="text-align: center"><spring:message code="label.userList" /></th>
        		</tr>
				<tr>
					<th><spring:message code="label.name" /></th>
					<th><spring:message code="label.company" /></th>
				</tr>
				<tbody id="results">
					<c:forEach items="${desclist}" var="desc">
						<tr>
							<td><a href="/webapp/computer/description/user?userUsername=${desc.user.username}">${desc.user.username}</a></td>
							<td>${desc.company.name}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</nav>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<c:if test="${ authority == 'ROLE_ADMIN' }">
			<div class="pull-left" style="margin-top: 5px; margin-left: 10px;">
				<div class="hamburger is-closed" data-toggle="offcanvas">
					<div class="hamb-top"></div>
					<div class="hamb-middle"></div>
					<div class="hamb-bottom"></div>
				</div>
			</div>
		</c:if>
		<div class="container">

			<a class="navbar-brand" href="/webapp/computer?page=1">	Application - Computer Database </a>

			<div  class="pull-right"  style="margin-top: 7px">
				<a	class="btn btn-danger" href="/webapp/spring_logout" role="button"><spring:message code="label.logout" /></a>
			</div>
			<div class="pull-right" style="margin-top: 7px;">
				<a style="color: #9d9d9d; font-size: 25px; margin-right: 10px;" href="/webapp/computer/description">${username}</a> 

			</div>
			
			<div class="pull-right"  style="margin-top: 12px;margin-right:20px;">
				<a href="/webapp/computer?page=1&locale=fr">
					<img src="/webapp/img/france.png" alt="France" height="25" width="25">
				</a>
				<a href="/webapp/computer?page=1&locale=en">
					<img src="/webapp/img/uk.png" alt="England" height="25" width="25">
				</a>
			</div>				

		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="label.profilPageUser" /> ${userUsername}</h1>
					<form:form action="/webapp/computer/description"
						id="descriptionForm" name="descriptionForm"
						modelAttribute="descriptionDTO" method="POST">
						<fieldset>
							<form:input type="hidden" name="id" path="id" id="id" />
							<form:input type="hidden" name="user_id" path="user_id"
								id="user_id" />
							<div class="form-group">
								<label for="firstname"><spring:message code="label.firstname" /></label>
								<form:input type="text" class="form-control" id="firstname"
									name="firstname" path="firstname" disabled="true"/>
								<form:errors path="firstname" cssClass="error"></form:errors>
							</div>
							<div class="form-group">
								<label for="lastname"><spring:message code="label.lastname" /></label>
								<form:input type="text" class="form-control" id="lastname"
									name="lastname" path="lastname" disabled="true" />
								<form:errors path="firstname" cssClass="error"></form:errors>
							</div>
							<div class="form-group">
								<label for="email">Email</label>
								<form:input type="email" class="form-control" id="email"
									name="email" path="email" disabled="true"  />
								<form:errors path="email" cssClass="error"></form:errors>
							</div>
							<div class="form-group">
								<label for="information"><spring:message code="label.information" /></label>
								<form:textarea class="form-control" id="information"
									name="information" path="information" disabled="true" />
								<form:errors path="information" cssClass="error"></form:errors>
							</div>
							<div class="form-group">
								<label for="company"><spring:message code="label.company" /></label>
								<form:input type="text" class="form-control" id="company"
									name="company" path="company" disabled="true"/>
								<form:errors path="company" cssClass="error"></form:errors>
							</div>
						</fieldset>
					</form:form>


				</div>
			</div>
		</div>
		<form id="deleteForm" action="/webapp/computer?profil=user" method="POST">
			<input type="hidden" name="selection" value=""> <input
				type="hidden" name="ACTION_TYPE" value="delete">
		</form>
		<div class="container" style="margin-top: 50px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
					<th><a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
						</a></th>
						<th><spring:message code="label.name" /></th>
						<th><spring:message code="label.introduced" /></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="label.discontinued" /></th>
						<!-- Table header for Company -->
						<th><spring:message code="label.company" /></th>
						
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td><input type="checkbox" name="cb" class="cb" value="${computer.id}"></td>
							<td><a href="/webapp/computer?computerId=${computer.id}&ACTION_TYPE=update">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.company}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>
	</div>
	<script src="/webapp/js/jquery.min.js"></script>
	<script src="/webapp/js/bootstrap.min.js"></script>
	<script src="/webapp/js/dashboard.js"></script>
	<script type="text/javascript">
		
	</script>
</body>
</html>