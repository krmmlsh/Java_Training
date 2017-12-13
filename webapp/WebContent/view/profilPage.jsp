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
<link href="/webapp/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/webapp/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/webapp/css/main.css" rel="stylesheet" media="screen">
<script src="/webapp/js/validate.js"></script>
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
                    <h1>Page de profil</h1>
                    <form:form action="/webapp/computer/description" id="descriptionForm" name="descriptionForm" modelAttribute="descriptionDTO" method="POST">
                        <fieldset>
                          	<form:input type="hidden" name="id" path="id" id="id" />
                          	<form:input type="hidden" name="user_id" path="user_id" id="user_id" />
                            <div class="form-group">
                                <label for="firstname">firstname</label>
                                <form:input type="text" class="form-control" id="firstname" name="firstname"  path="firstname" placeholder="firstname"/>
                            	<form:errors path="firstname" cssClass="error"></form:errors>
                            </div>
                             <div class="form-group">
                                <label for="lastname">lastname</label>
                                <form:input type="text" class="form-control" id="lastname" name="lastname"  path="lastname" placeholder="lastname"/>
                            	<form:errors path="firstname" cssClass="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="email">email</label>
                                <form:input type="email" class="form-control" id="email" name="email" path="email" placeholder="ex : xxx@yyy.aa"/>
                                <form:errors path="email" cssClass="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="information">Information de l'utilisateur</label>
                                <form:textarea class="form-control" id="information" name="information" path="information" />
                                <form:errors path="information" cssClass="error"></form:errors>
                            </div>
                             <div class="form-group">
                                <label for="company">Company</label>
                                <form:input type="text" class="form-control" id="company" name="company"  path="company" placeholder="company"/>
                            	<form:errors path="company" cssClass="error"></form:errors>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Modify Profile" class="btn btn-primary">
                            or
                            <a href=/webapp/computer" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>