<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Computer Database</title>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/webapp/computer?page=1">
				Application - Computer Database </a>

			
			<div class="pull-right"  style="margin-top: 12px;margin-right:20px;">
				<a href="/webapp/login?locale=fr">
					<img src="/webapp/img/france.png" alt="Smiley face" height="25" width="25">
				</a>
				<a href="/webapp/login?locale=en">
					<img src="/webapp/img/uk.png" alt="Smiley face" height="25" width="25">
				</a>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-6 col-xs-offset-3 box">
					<table>
						<tr>
							<td class="error" colspan="2" style="color: red;"><c:if
									test="${not empty error}"> ${error}</c:if></td>
						</tr>
					</table>
					<h1><spring:message code="label.signin"/></h1>

					<form name="loginForm" action="<c:url value='spring_security'/>"
						method="POST">
						<fieldset>
							<div class="form-group">
								<label for="username"><spring:message code="label.username"/></label> <input type="text"
									class="form-control" id="username" name="username" placeholder="ex : xXxERIC_LE_DESTRUCTEUR666xXx"/>
							</div>
							<div class="form-group">
								<label for="password"><spring:message code="label.password"/></label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="********" />
							</div>
						</fieldset>
						<div class="actions pull-right">
						<input type="submit" value="<spring:message code="label.signin"/>" class="btn btn-primary"> <spring:message code="label.or"/>
							<a href="/webapp/signup" type="button" class="btn btn-default"
								role="button"><spring:message code="label.signup"/></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>