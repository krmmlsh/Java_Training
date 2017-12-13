<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<script>
		var button = 0;
	</script>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/webapp/computer?page=1">
				Application - Computer Database </a>
            <div class="pull-right" style="margin-top: 7px">
				<a style="color:white;font-size:25px;margin-right:10px;" href="/webapp/computer/description">${username}</a>
				<a class="btn btn-danger" href="/webapp/spring_logout" role="button" >Log out</a>
			</div>
		</div>
	</header>

	<section id="main">

		<div class="container">
			<c:if test="${not empty error }">
				<h3 style="color: red;">${error}</h3>
			</c:if>
			<h1 id="homeTitle">${nbTotal} <spring:message code="label.found"/></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="/webapp/computer" method="GET"
						class="form-inline">
						<input type="hidden" name="ACTION_TYPE" value="getName" /> <input
							type="search" id="searchbox" name="search" class="form-control"
							placeholder="Search name" /> <input type="submit"
							id="searchsubmit" value=<spring:message code="label.filter"/>class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="/webapp/computer?ACTION_TYPE=create"><spring:message code="label.add"/></a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="editMode">
				<div>
					<label for="companyId"><spring:message code="label.companies"/></label>
				</div>
				<form id="deleteFormCompany" action="/webapp/computer"	style="display:inline;"method="POST">
					<select name="companyIdDeleted">
						<c:forEach var="company" items="${companies}">
							<option value="${company.id}">${company.name}</option>
						</c:forEach>
					</select> 
					<input type="hidden" name="ACTION_TYPE" value="deleteFormCompany" />
				</form>
				<a href="#" id="deleteCompany" onclick="$.fn.deleteSelectedCompany();">
					<i style="color: red;" class="fa fa-trash-o fa-lg"></i>
				</a>
			</div>
		</div>
		<form id="deleteForm" action="/webapp/computer" method="POST">
			<input type="hidden" name="selection" value=""> <input
				type="hidden" name="ACTION_TYPE" value="delete">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="label.name"/></th>
						<th><spring:message code="label.introduced"/></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="label.discontinued"/></th>
						<!-- Table header for Company -->
						<th><spring:message code="label.name"/></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a
								href="/webapp/computer?computerId=${computer.id}&ACTION_TYPE=update">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.company}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="btn-group btn-group-sm pull-right" role="group">
			<form>
				<a href="/webapp/computer?length=10"><button type="button"
						class="btn btn-default">10</button></a> <a
					href="/webapp/computer?length=50"><button type="button"
						class="btn btn-default">50</button></a> <a
					href="/webapp/computer?length=100"><button type="button"
						class="btn btn-default">100</button></a>
			</form>
		</div>

		<div class="container text-center">
			<ul class="pagination">
				<li><a id="next" href="/webapp/computer?plus=-1"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach items="${pages}" var="page">
					<li><a id="${page}" href="/webapp/computer?page=${page}"
						aria-label="${page}"> <span aria-hidden="true">${page}</span>
					</a></li>
				</c:forEach>
				<li><a id="previous" href="/webapp/computer?plus=1"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</div>

	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
	<script type="text/javascript">
		
	</script>

</body>
</html>