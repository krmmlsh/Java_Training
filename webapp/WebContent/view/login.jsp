<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		</div>
	</header>
	<table>
		<tr>
			<td colspan="2" style="color: red;"><c:if
					test="${not empty error}"> ${error}</c:if></td>
		</tr>
	</table>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-6 col-xs-offset-3 box">
					<h1>Connexion</h1>

					<form name="loginForm" action="<c:url value='spring_security'/>"
						method="POST">
						<fieldset>
							<div class="form-group">
								<label for="username">Username</label> <input type="text"
									class="form-control" id="username" name="username" placeholder="ex : xXxERIC_LE_DESTRUCTEUR666xXx"/>
							</div>
							<div class="form-group">
								<label for="password">Password</label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="********" />
							</div>
						</fieldset>
						<div class="actions pull-right">
						<input type="submit" value="Connexion" class="btn btn-primary"> or 
							<a href="/webapp/signup" type="button" class="btn btn-default"
								role="button">Sign up</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>