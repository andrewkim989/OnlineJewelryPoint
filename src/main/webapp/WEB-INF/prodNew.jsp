<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">  
    <meta charset="UTF-8">
    <title>Create a new product</title>
    <style>
    	.red{
    		color:red;
    	}
    </style>
</head>
<body>
	<div class="container">
        <div class="row">
            <div class="col-5"> 
             <h1>Create a new product!</h1>    
	    	 <%-- <p><form:errors path="product.*"/></p> --%>   
	   		<form:form method="POST" action="/createProducts" modelAttribute="product">
	   			<p class="form-group">
		            <form:label path="productName">Product Name: </form:label>
		            <form:errors path="productName" class="red"/>
		            <form:input type="text" path="productName" class="form-control"/>
		        </p>
	   			<p class="form-group">
		            <form:label path="prodPrice">Product Price: </form:label>
		            <form:errors path="prodPrice" class="red"/>
		            <form:input type="text" path="prodPrice" class="form-control"/>
		        </p>
		        <p class="form-group">
		            <form:label path="prodDesc">Product description: </form:label>
		            <form:errors path="prodDesc" class="red"/>
		            <form:input type="text" path="prodDesc" class="form-control"/>
		        </p>
		        <p class="form-group">
		            <form:label path="prodCategory">Product category: </form:label>
		            <form:errors path="prodCategory" class="red"/>
		            <form:input type="text" path="prodCategory" class="form-control"/>
		         </p>   
		          <p class="form-group">
		            <form:label path="prodImg">Product Image: </form:label>
		            <form:errors path="prodImg" class="red"/>
		            <form:input type="text" path="prodImg" class="form-control" value="/img/img-4.jpg"/>
		         </p>  
		         <p class="form-group">
		            <form:label path="prodQtyInStock">Product in stock: </form:label>
		            <form:errors path="prodQtyInStock" class="red"/>
		            <form:input type="text" path="prodQtyInStock" class="form-control"/>
		         </p> 
		         	<p class="form-group">
		            <form:label path="prodQtySold">Product Sold: </form:label>
		            <form:errors path="prodQtySold" class="red"/>
		            <form:input type="text" path="prodQtySold" class="form-control"/>
		         </p> 
		         
		        <button type="submit" class="btn btn-primary" class="form-control">Create a new product !</button>
		    </form:form>
	    </div> 		
   </div>
  </div>  
</body>
</html>