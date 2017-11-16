<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
<script src="js/validate.js"></script>
<style>
    .error {
        color: red; font-weight: bold;
    }
</style>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/JavaLevel2/computer?page=1"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">${computer.id}</div>
					<h1>Edit Computer</h1>

					<form:form action="/JavaLevel2/computer?ACTION_TYPE=update" name="computerForm" modelAttribute="computerDTO" method="POST">
					
						<input type="hidden" value="${computer.id}" name="id" id="id" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> 
								<form:input type="text" class="form-control" path="name" name="computerName" id="computerName"
									value="${computer.name}"/>
								<form:errors path="name" cssClass="error"></form:errors>
									
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <form:input
									type="date" class="form-control" name="introduced" path="introduced" id="introduced" placeholder="dd/MM/yyyy"
									value="${computer.introduced}"/>
                            	<form:errors path="introduced"></form:errors>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <form:input
									type="date" class="form-control" name="discontinued" path="discontinued" id="discontinued" placeholder="dd/MM/yyyy"
									value="${computer.discontinued}"/>
                            	<form:errors path="discontinued"></form:errors>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> 
								<form:select class="form-control" name="companyId" path="companyId" id="companyId">
									<c:forEach var="company" items="${companies}">
										<option value="${company.id}" ${company.name == computer.company ? 'selected="selected"' : ''}>${company.name}</option>
									</c:forEach>
								</form:select>
                            	<form:errors path="companyId"></form:errors>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard.html" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>