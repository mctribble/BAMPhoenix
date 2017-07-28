<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Stub Documentation</title>
</head>
<body>
	<nav class="navbar navbar-inverse bg-inverse">
  		<a class="navbar-brand" href="#">Stub Documentation</a>
	</nav>
	
	<div class="container">
		<div class="well">
			These are the URI's that are available in this Stub REST server. Every URI is prepended with the server domain/ip (at this time we don't have it). 
			Please keep this document up to date with new URI's that get added so that documentation for future batches won't be in the dark.
		</div>
		<table class="table">
			<tr>
				<th>URI
				<th>Method
				<th>Returns
				<th>Description
			<tr>
				<td>/Batch/Trainer?email={TrainerEmail}
				<td>GET
				<td>JSON
				<td>Returns all batches that the trainer is in control of, past, present, and future.
			<tr>
				<td>/Topic/Curriculum?id={CurriculumId}
				<td>GET
				<td>JSON
				<td>Returns all top-level topics for a specific curriculum
		</table>
	</div>
</body>
</html>