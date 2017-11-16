
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
<!-- <link type="text/css" rel="stylesheet" href="form.css" /> -->
</head>
<body>
	<script>
		var button = 0;
	</script>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/JavaLevel2/computer?page=1">
				Application - Computer Database </a>
		</div>
	</header>

	<section id="main">

		<div class="container">
			<c:if test="${not empty error }">
				<h3 style="color: red;">${error}</h3>
			</c:if>
			<h1 id="homeTitle">${nbTotal} computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="/JavaLevel2/computer" method="GET"
						class="form-inline">
						<input type="hidden" name="ACTION_TYPE" value="getName" /> <input
							type="search" id="searchbox" name="search" class="form-control"
							placeholder="Search name" /> <input type="submit"
							id="searchsubmit" value="Filter by name" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="/JavaLevel2/computer?ACTION_TYPE=create">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="editMode">
				<div>
					<label for="companyId">Companies</label>
				</div>
				<form id="deleteFormCompany" action="/JavaLevel2/computer"	style="display:inline;"method="POST">
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
		<form id="deleteForm" action="/JavaLevel2/computer" method="POST">
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
						<th>Computer name</th>
						<th>Introduced</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value=${computer.id}></td>
							<td><a
								href="/JavaLevel2/computer?computerId=${computer.id}&ACTION_TYPE=update">${computer.name}</a></td>
							<td>${computer.introducedDate}</td>
							<td>${computer.discontinuedDate}</td>
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
				<a href="/JavaLevel2/computer?length=10"><button type="button"
						class="btn btn-default">10</button></a> <a
					href="/JavaLevel2/computer?length=50"><button type="button"
						class="btn btn-default">50</button></a> <a
					href="/JavaLevel2/computer?length=100"><button type="button"
						class="btn btn-default">100</button></a>
			</form>
		</div>

		<div class="container text-center">
			<ul class="pagination">
				<li><a id="next" href="/JavaLevel2/computer?plus=-1"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach items="${pages}" var="page">
					<li><a id="${page}" href="/JavaLevel2/computer?page=${page}"
						aria-label="${page}"> <span aria-hidden="true">${page}</span>
					</a></li>
				</c:forEach>
				<li><a id="previous" href="/JavaLevel2/computer?plus=1"
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