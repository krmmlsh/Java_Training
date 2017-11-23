<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
            <a class="navbar-brand" href="/webapp/computer?page=1"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="label.add"/></h1>
                    <form:form action="/webapp/computer?ACTION_TYPE=post" id="computerForm" name="computerForm" modelAttribute="computerDTO" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="label.name"/></label>
                                <form:input type="text" class="form-control" id="name" name="name"  path="name" placeholder="Computer name"/>
                            	<form:errors path="name" cssClass="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.introduced"/></label>
                                <form:input type="date" class="form-control" id="introduced" name="introduced" path="introduced" placeholder="dd/MM/yyyy"/>
                                <form:errors path="introduced" cssClass="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.discontinued"/></label>
                                <form:input type="date" class="form-control" id="discontinued" name="discontinued" path="discontinued" placeholder="dd/MM/yyyy"/>
                                <form:errors path="discontinued" cssClass="error"></form:errors>
                            
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="label.company"/></label>
                                <form:select class="form-control" name="companyId" path="companyId" id="companyId">
									<c:forEach var="company" items="${companies}">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard.html" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>