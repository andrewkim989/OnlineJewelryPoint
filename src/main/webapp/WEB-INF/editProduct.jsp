<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">  
    <meta charset="UTF-8">
    <title>Edit Page</title>
    <style>
		.red{
			color:red;
		}
	</style>
</head>
<body>
	<div class = "container">
        <div class = "row">
            <div class = "col-5"> 
            <h1>Edit product !</h1>     
	    	  
	   		<form method = "POST" action = "/updateProduct/${product.id}">
	   			Product: ${product.productName}
	   			<p class =" form-group">
		            <input type = "text" name = "productName" class = "form-control" value = "${product.productName}"/>
		        </p>
		        <p class = "form-group">
		            <input type = "text" name = "prodPrice" class = "form-control" value = "${product.prodPrice}"/>
		        </p>
		        <p class = "form-group">
		            <input type = "text" name = "prodDesc" class = "form-control" value = "${product.prodDesc}"/>
		        </p>
		        <p class = "form-group">
		            <input type="text" name="prodCategory" class = "form-control" value = "${product.prodCategory}"/>
		        </p>
	             
		        <button type = "submit" class = "btn btn-primary" class = "form-control">Submit !</button>
		        <a href = "/logout">Logout</a>
		    </form>
	    	</div> 		
   		</div>
	</div>  
</body>
</html>