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
<title>BAM Documentation</title>
</head>
<body>
	<nav class="navbar navbar-inverse bg-inverse">
  		<a class="navbar-brand" href="#">BAM Documentation</a>
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
                <th>Input
                <th>Description
            <tr>
                <td>/Batch/Trainer
                <td>GET
                <td>JSON
                <td>email : {TrainerEmail}
                <td>Returns all batches that the trainer is in control of, past, present, and future.
            <tr>
                <td>/Topic/Curriculum
                <td>GET
                <td>JSON
                <td>id : {CurriculumId}
                <td>Returns all top-level topics for a specific curriculum
        </table>
        
        <div class="well">
            These are the REST calls for BAM's spring controllers
        </div>
        <table class="table">
            <tr>
                <th>URI
                <th>Method
                <th>Returns
                <th>Input
                <th>Description
            <tr>
                <td>/Batches/All.do
                <td>GET
                <td>JSON
                <td>N/A
                <td>Returns all batches
            <tr>
                <td>/Batches/Past.do
                <td>GET
                <td>JSON
                <td>email : {TrainerEmail}
                <td>Returns all past batches for a specific trainer
            <tr>
                <td>/Batches/Future.do
                <td>GET
                <td>JSON
                <td>email : {TrainerEmail}
                <td>Returns all future batches for a specific trainer
            <tr>
                <td>/Batches/InProgress.do
                <td>GET
                <td>JSON
                <td>email : {TrainerEmail}
                <td>Returns batch in progress for the specific trainer
            <tr>
                <td>/Batches/Edit.do
                <td>GET
                <td>JSON
                <td>batch : {Batch}
                <td>Returns Batch Details
            <tr>
                <td>/Batches/ById
                <td>GET
                <td>JSON
                <td>batchId : {BatchId}
                <td>Returns Batch Details
            <tr>
                <td>/Batches/AddBatch.do
                <td>POST
                <td>JSON
                <td>batch : {Batch}
                <td>Returns Batch Details
            <tr>
                <td>/Users/InBatch.do
                <td>GET
                <td>JSON
                <td>id : {BatchId}
                <td>Returns all users in a batch
            <tr>
                <td>/Users/Drop.do
                <td>POST
                <td>JSON
                <td>id : {UserId}
                <td>Returns all users in a batch
            <tr>
                <td>/Users/Remove.do
                <td>POST
                <td>JSON
                <td>id : {UserId}
                <td>Returns all users in a batch
            <tr>
                <td>/Users/Update.do
                <td>POST
                <td>JSON
                <td>user : {User}
                <td>Returns User Details
            <tr>
                <td>/Users/Add.do
                <td>POST
                <td>JSON
                <td>userId : {UserId}<br>batchId : {batchId}
                <td>Returns all user with a null batch
            <tr>
                <td>/Users/NotInABatch.do
                <td>GET
                <td>JSON
                <td>N/A
                <td>Returns all associates with a null batch
            <tr>
                <td>/Users/AllAssociates.do
                <td>GET
                <td>JSON
                <td>N/A
                <td>Returns all associates
            <tr>
                <td>/Users/AllTrainers.do
                <td>GET
                <td>JSON
                <td>N/A
                <td>Returns all trainers
            <tr>
                <td>/Calendar/Topics.do
                <td>GET
                <td>JSON
                <td>batchId : {BatchId}
                <td>Returns all Week Topics for a batch
            <tr>
                <td>/Calendar/Subtopics.do
                <td>GET
                <td>JSON
                <td>batchId : {BatchId}
                <td>Returns all subtopics and tasks for a batch
            <tr>
                <td>/Calendar/AddTopics.do
                <td>POST
                <td>JSON
                <td>Topics : {SetOfTopics}
                <td>N/A
        </table>
    </div>
</body>
</html>