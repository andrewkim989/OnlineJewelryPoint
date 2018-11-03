<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Products List</title>
	<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
	<style>
		#top {
			padding: 10px;
			padding-left: 150px;
			color: white;
		}
		#top h2 {
			display: inline-block;
		}
		#top a {
			display: inline-block;
			font-size: 20px;
			margin-left: 500px;
		}
		#search {
			padding-left: 150px;
			color: white;
		}
		input[type = "text"] {
			width: 500px;
		}
		#main {
			padding-left: 100px;
			padding-top: 40px;
			width: 1200px;
			color: white;
		}
		#item {
			width: 250px;
			display: inline-block;
			vertical-align: top;
		}
		#item a {
			white-space: nowrap;
		}
		.ui-autocomplete {
		    max-height: 200px;
		    overflow-y: auto;
		    overflow-x: hidden;
		}
	</style>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  	<link rel="stylesheet" href="/resources/demos/style.css">
  	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  	<script>
	  $(function() {
	    var tags = [
	    	"ring", "ruby", "diamond", "price", "wedding", "ruby", "sapphire", "engagement",
	    	"emerald", "cheap", "expensive", "jewel", "jewelry", "necklace", "gold", "pearl",
	    	"platinum", "carat", "new", "cross", "star", "earring", "beautiful"
	    ];
	    $("#suggestion").autocomplete({
			source: tags,
		})
	  } );
  	</script>
</head>
<body>
	<div id = "wrapper">
		<div id = "top">
			<h2>Welcome, ${user.firstName} ${user.lastName}!</h2>
			<a href = "/logout">Logout</a>
		</div>
		
		<hr>
		<div class = "row">
		
			<div id = "search">
				<form action = "/products/search" method = "post">
					Find Products by category: 
					<input type = "text" name = "input" id = "suggestion">
					<input type = "submit" value = "Search">
					<select name = "selection">
						<option value = "productName">Product Name</option>
						<option value = "prodPrice">Product Price (less than the search value)</option>
						<option value = "prodDesc">Product description</option>
					</select>
				</form>
			</div>
		
 		</div>
 		<div id = "main">
 			<c:forEach items = "${productAll}" var = "product">
 				<div id = "item">
					<div class = "col-2">
						<img src = "${product.prodImg}">
						<a href = "/showProduct/${product.id}">${product.productName}</a>
						<div>$${product.prodPrice}</div>			
		 			</div>
		 		</div>
		 	</c:forEach>
		</div>
 	</div>
</body>
</html>