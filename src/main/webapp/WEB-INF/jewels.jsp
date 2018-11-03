
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<table class = "table table-striped table-dark">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Image</th>
                  <th>Products</th>
                  <th>Desc</th>
                  <th>Price</th>
                  <th>Quantity Sold</th>
                  <th>Quantity in Stock</th>
                  <th>Category</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${products}" var="product">
		        <tr>
		            <td><c:out value="${product.id}"/></td>
		            <td><img src="${product.prodImg}"></td>
		            <td>${product.productName}</td>  
		            <td>${product.prodDesc}</td>
		            <td>${product.prodPrice}</td>  
		            <td>${product.prodQtySold}</td> 
		            <td>${product.prodQtyInStock}</td> 
		            <td>${product.prodCategory}</td>
		            <td><a href = "/editProduct/${product.id}">Edit | </a><a href = "/delete/${product.id}">Delete</a></td>
		        </tr>
		       </c:forEach>
              </tbody>
            </table>