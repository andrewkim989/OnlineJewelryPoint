<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">  
	<style>
		img {
			width: 100px;
		}
		#chartContainer {
			height: 300px;
			width: 600px;
			margin: 0px auto;
			margin-bottom: 70px;
		}
	</style>
	<script src = "https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
    <script src = "https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
    <script src = "https://canvasjs.com/assets/script/canvasjs.min.js"></script>
    
	<script type = "text/javascript">
	window.onload = function () {
		var chart = new CanvasJS.Chart("chartContainer", {
			title:{
				text: "Popular purchases in the past week"             
			},
			data: [              
			{
				type: "pie",
				dataPoints: [
					{ label: "Diamond",  y: 10  },
					{ label: "Gold", y: 5  },
					{ label: "Sapphire", y: 8  },
					{ label: "Ruby",  y: 4  },
				]
			}
			]
		});
		chart.render();
	}
	</script>
</head>
<body>
	<div class = "container">
		<p>Thanks so much for purchasing from our store. An amount of $${sumTotal} has been deducted from your credit card. <br>Looking forward to see you again soon ! </p>
        <a href = "logout">Logout</a> | <a href = "/products">SHOP MORE</a>
 		<hr>
            <h3>Recently purchased products:</h3>        
            <table class="table table-striped table-dark">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Image</th>
                  <th>Products</th>
                  <th>Price</th>
                  <th>Category</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${boughtList}" var="product">
		        <tr>
		            <td><c:out value="${product.id}"/></td>
		            <td><img src="${product.prodImg}"></td>
		            <td>${product.productName}</td>  
		            <td>${product.prodPrice}</td>  
		            <td>${product.prodCategory}</td> 
		        </tr>
		        </c:forEach>

              </tbody>
            </table>
    
    <div id = "chartContainer"></div>
	</div>  
</body>
</html>