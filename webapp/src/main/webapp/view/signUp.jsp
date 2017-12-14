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
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
<script src="js/validate.js"></script>
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
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-6 col-xs-offset-3 box">
					<h1>Inscription</h1>
					<form:form action="/webapp/signup" id="signUpForm"
						name="signUpForm" modelAttribute="userDTO" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="username">Nom d'utilisateur</label>
								<form:input type="text" class="form-control" id="username"
									name="username" path="username" placeholder="username" />
								<form:errors path="username" cssClass="error"></form:errors>
							</div>
							<div class="form-group">
								<label for="password">Mot de passe</label>
								<form:input type="password" class="form-control" id="password"
									name="password" path="password" placeholder="********" />
								<form:errors path="password" cssClass="error"></form:errors>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Sign up" class="btn btn-primary">
							or <a href="/webapp/login" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>