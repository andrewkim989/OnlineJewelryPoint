package com.andrewkim.javaproject.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.andrewkim.javaproject.models.Order;
import com.andrewkim.javaproject.models.Product;
import com.andrewkim.javaproject.models.User;
import com.andrewkim.javaproject.repositories.OrderRepository;
import com.andrewkim.javaproject.repositories.ProductRepository;
import com.andrewkim.javaproject.repositories.UserRepository;

@Service
public class ProjectService {
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	
	public ProjectService (UserRepository userRepository, ProductRepository productRepository,
			OrderRepository orderRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}
	
	public User registerUser(User user){
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
	}
	
	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public User findUserById(Long id){
		Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
	
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        
        if(user == null) {
            return false;
        }
        else {
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            }
            else {
                return false;
            }
        }
    }

	public User update(User user) {
		return userRepository.save(user);		
	}
	
    public List <Product> allProducts() {
        return productRepository.findAll();
    }
    
	public Product createProduct(Product product){
        return productRepository.save(product);
	}
	
	public Product findProductById(Long id){
		Optional<Product> u = productRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }


	public Product update(Product e) {
		return productRepository.save(e);		
	}

	public void deleteById(Long id) {
		productRepository.deleteById(id);	
	}

	public Product update(Long id, String productName) {
		Product q = findProductById(id);
		q.setProductName(productName);
		return productRepository.save(q);	
	}
	
	public Product update(Long id, String productName, double bd, String prodDesc, String prodCategory) {
		Product q = findProductById(id);
		q.setProductName(productName);
		q.setProdPrice(bd);
		q.setProdDesc(prodDesc);
		q.setProdCategory(prodCategory);		
		return productRepository.save(q);
		
	}
	
	public List <Product> findProducts (String product) {
		return productRepository.findByProductNameContaining(product);
	}
	
	public List <Product> findPrice (double price) {
		return productRepository.findByProdPriceLessThanEqual(price);
	}
	
	public List <Product> findDescription (String description) {
		return productRepository.findByProdDescContaining(description);
	}
	
	public List <Product> findJewels (String category) {
		return productRepository.findByProdCategory(category);
	}
	
	public List <Order> allOrders() {
        return orderRepository.findAll();
    }
	
	public Order createOrder(Order order){
        return orderRepository.save(order);
	}
	
	public Order findOrderById(Long id){
		Optional<Order> u = orderRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }

	public Order update(Order e) {
		return orderRepository.save(e);		
	}

	public void deleteOrderById (Long id) {
		orderRepository.deleteById(id);	
	}

	public Order updateOrder (Long id, String orderName) {
		Order q = findOrderById(id);
		q.setOrderName(orderName);
		return orderRepository.save(q);	
	}

	public Order createOrder(String orderName, String string) {
		Order o = new Order(orderName, string);
		return orderRepository.save(o);		
	}
	
	public List <Order> findStatus (String status) {
		return orderRepository.findByOrderStatus(status);
	}
}
