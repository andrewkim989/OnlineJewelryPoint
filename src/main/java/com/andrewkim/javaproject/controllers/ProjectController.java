package com.andrewkim.javaproject.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andrewkim.javaproject.models.Order;
import com.andrewkim.javaproject.models.Product;
import com.andrewkim.javaproject.models.User;
import com.andrewkim.javaproject.services.ProjectService;
import com.andrewkim.javaproject.validators.ProjectValidator;


@Controller
public class ProjectController {
	private final ProjectService projectService;
	private final ProjectValidator projectValidator;
	
	public ProjectController (ProjectService projectService, ProjectValidator projectValidator) {
		this.projectService = projectService;
		this.projectValidator = projectValidator;
	}
	
	@RequestMapping("/")
    public String index() {
        return "index.jsp";
    }
	
	@RequestMapping("/hindi")
    public String hindi() {
        return "hindi.jsp";
    }
    @RequestMapping("/korean")
    public String korean() {
        return "korean.jsp";
    }
	
	@RequestMapping("/register")
    public String registerForm(@ModelAttribute("user") User user) {
		return "regLog.jsp";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String register (@Valid @ModelAttribute("user") User user,
    		BindingResult result, HttpSession session) {
		projectValidator.validate(user, result);
		if (result.hasErrors()) {
 	        return "index.jsp";
 	    }
		else {
			projectService.registerUser(user);
 	    	session.setAttribute("id", user.getId());
 	    	return "redirect:/products";
 	    }
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login (@RequestParam("email") String email,
    		@RequestParam("password") String password, Model model,
    		HttpSession session, RedirectAttributes r) {
		boolean success = projectService.authenticateUser(email, password);
    	
    	if (email.length() < 1) {
    		r.addFlashAttribute("error", "Email field cannot be blank.");
    		return "redirect:/";
    	}
    	else if (password.length() < 1) {
    		r.addFlashAttribute("error", "Please enter your password.");
    		return "redirect:/";
    	}
    	else if (!success) {
    		r.addFlashAttribute("error", "Email and password do not match.");
    		return "redirect:/";
    	}
    	else {
    		User user = projectService.findByEmail(email);
    		Long id = user.getId();
    		session.setAttribute("id", id);
    		return "redirect:/products";
    	}
    }
	
	@RequestMapping ("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping ("/products")
    public String products(HttpSession session, Model model,
    		@Valid @ModelAttribute("product") Product product, BindingResult result) {

    	List <Product> productAll = projectService.allProducts();				
		model.addAttribute("productAll", productAll);
		
		Long id = (Long) session.getAttribute("id");
    	User user = projectService.findUserById(id);
    	model.addAttribute("user", user);
    	return "products.jsp";
    }
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
    public String createProducts(@Valid @ModelAttribute("product") Product product,
    		BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "products.jsp";
		}
		else {
 	    	projectService.createProduct(product);    	
 	    	return "redirect:/products";
 	    }
    	 
    }
	
	@RequestMapping("/showProduct/{id}")
    public String showProduct(@PathVariable("id") Long id,Model model) {   	
	    Product product = projectService.findProductById(id);
	    model.addAttribute("product",product);	    
	    return "prodDetail.jsp";
    }
	
	@RequestMapping("/addToCart/{id}")
	public String addToCart(@PathVariable("id") Long id, HttpSession session) {
		if (session.getAttribute("id") == null) {
			 return "redirect:/register";
		}
		Long user_id = (Long) session.getAttribute("id");
		User user = projectService.findUserById(user_id);
		 
		Product product = projectService.findProductById(id);
		product.getSelectors().add(user);
		projectService.update(product);		 
		return "redirect:/cart/" + id;
	}
	 
	@RequestMapping("/removeFromCart/{id}")
	public String removeFromCart(@PathVariable("id") Long id, HttpSession session) {
		Long user_id = (Long) session.getAttribute("id");
		User user = projectService.findUserById(user_id);
		 
		Product product = projectService.findProductById(id);
		product.getSelectors().remove(user);
		projectService.update(product);
			    
		return "redirect:/cart/" + id;
	}
	
	//https://jqueryui.com/autocomplete/
	
	@RequestMapping (value = "/products/search", method = RequestMethod.POST)
	public String findProduct (HttpSession session, @RequestParam("input") String input,
			 @RequestParam("selection") String selection) {
		if (selection.equals("productName")) {
			List <Product> products = projectService.findProducts(input);
			session.setAttribute("products", products);
			return "redirect:/products/" + input;
		}
		else if (selection.equals("prodDesc")){
			List <Product> products = projectService.findDescription(input);
			session.setAttribute("products", products);
			return "redirect:/products/" + input;
		}
		else {
			double price = Double.parseDouble(input);
			List <Product> products = projectService.findPrice(price);
			session.setAttribute("products", products);
			return "redirect:/products/" + input;
		}
	}
	
	@RequestMapping("/products/{input}")
	public String listProducts (@PathVariable("input") String input) {
		return "search.jsp";
	}
	
	@RequestMapping("/cart/{id}")
    public String productWaitlisted(@PathVariable("id") Long id, HttpSession session,
    	Model model, @Valid @ModelAttribute("product") Product product, BindingResult result) {
	
		Long user_id = (Long) session.getAttribute("id");
		User user = projectService.findUserById(user_id);
		model.addAttribute("user", user);
		List<Product> selectList = user.getShopProducts();
		model.addAttribute("selectList", selectList);
		
		double sum = 0 ;
		for (int i = 0; i < selectList.size();i++) {
			 double price = selectList.get(i).getProdPrice();
			 sum = price + sum;
		}
		
		model.addAttribute("sumTotal", sum);
		session.setAttribute("sumTotal", sum);
			 
		return "shoppingCart.jsp";
   }
	
	@RequestMapping("/checkout")
	public String checkout(HttpSession session) {
		Long user_id = (Long) session.getAttribute("id");
		User user = projectService.findUserById(user_id);
		
		List<Product> selectList = user.getShopProducts();
		for (int i = 0; i < selectList.size(); i++) {
			Product product = projectService.findProductById(selectList.get(i).getId());
			product.getBuyers().add(user);
			product.getSelectors().remove(user);
			projectService.update(product);
			
			Date date = new Date();
			String orderName = product.getProductName();
			Order newOrder = new Order();
			newOrder.setOrderName(orderName);
			newOrder.setOrderer(user);
			newOrder.setCreatedAt(date);
			newOrder.setOrderStatus("Ordered");
			projectService.createOrder(newOrder);
		}		 
		return "redirect:/productBought";
	}
	 
	@RequestMapping("/productBought")
	public String productBought(HttpSession session, Model model) {
		Long user_id = (Long) session.getAttribute("id");
		User user = projectService.findUserById(user_id);
		model.addAttribute("user", user);
		List<Product> boughtList = user.getBuyProducts();
		model.addAttribute("boughtList",boughtList);
		 
		double sumTotal = (double) session.getAttribute("sumTotal");
		model.addAttribute("sumTotal", sumTotal);
		
		/*String selectQuery = "select * from users where id = 1";
		
		Map<?, ?> map = jdbcTemplate.queryForMap(selectQuery);
		model.addAttribute("data", map.toString());
		
		String query = "select * from users";
		model.addAttribute("query", query);*/
		
		return "confirm.jsp";
	}
	
	@RequestMapping("/admin")
    public String adminPage(Model model, HttpSession session) {
		Long user_id = (Long) session.getAttribute("id");
		User user = projectService.findUserById(user_id);
		model.addAttribute("user", user);
		List<Product> productAll = projectService.allProducts();				
		model.addAttribute("productAll",productAll);
		return "admin.jsp";
	}
	
	@RequestMapping(value = "/admin/jewels", method = RequestMethod.POST)
    public String chooseJewels (Model model, @RequestParam("selectJewels") String selectJewels,
    		HttpSession session) {
		if (selectJewels.equals("all")) {
			return "redirect:/admin";
		}
		else {
			List<Product> selectedProducts = projectService.findJewels(selectJewels);	
			session.setAttribute("selectedProducts", selectedProducts);
			return "redirect:/admin/" + selectJewels;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "admin/{jewels}")
	public String displayJewels (@PathVariable("jewels") String jewels, HttpSession session, Model model) {
		List <Product> selectedProducts = (List<Product>) session.getAttribute("selectedProducts");
		model.addAttribute("selectedProducts", selectedProducts);
		return "jeweltable.jsp";
	}
	
	@RequestMapping("/prodNew")
    public String prodNew(@ModelAttribute("product") Product product) {
		return "prodNew.jsp";
	}
 
	@RequestMapping(value = "/createProducts", method=RequestMethod.POST)
    public String createProducts(@Valid @ModelAttribute("product") Product product, BindingResult result) {
    	 if (result.hasErrors()) {
 	        return "prodNew.jsp";
 	    }
    	 else {
 	    	projectService.createProduct(product);
 	    	return "redirect:/admin";
 	    }	    	 
    }
   @RequestMapping("/editProduct/{id}")
   public String editProduct(@ModelAttribute("product") Product product,@PathVariable("id") Long id,Model model) {
	   Product productA = projectService.findProductById(id);
	   model.addAttribute("product",productA);
	   return "editProduct.jsp";
   }
   
   @RequestMapping(value = "/updateProduct/{id}", method = RequestMethod.POST)
   public String updateProduct(@PathVariable("id") Long id, 
		   @RequestParam(value="productName") String productName,
		   @RequestParam(value="prodPrice") String prodPrice,
		   @RequestParam(value="prodDesc") String prodDesc,
		   @RequestParam(value="prodCategory") String prodCategory
		   ) {
	   double price = Double.parseDouble(prodPrice);
	   projectService.update(id, productName, price, prodDesc, prodCategory);
	   return "redirect:/admin";
   }
   
   @RequestMapping("/delete/{id}")
   public String destroy(@PathVariable("id") Long id) {
       projectService.deleteById(id);
       return "redirect:/admin";
   }
   
   @RequestMapping("/orderMgmt")
   public String orderMgmt(Model model) {
	   List<Order> allOrders = projectService.allOrders();
	   model.addAttribute("allOrders", allOrders);
	   return "orders.jsp";
   }
   
   @RequestMapping(value = "/orderMgmt/process", method = RequestMethod.POST) 
   public String orderMgmtProcess (Model model, @RequestParam("status") String status,
    		HttpSession session) {
	   if (status.equals("all")) {
		   return "redirect:/orderMgmt";
	   }
	   else {
		   List <Order> selectedOrders = projectService.findStatus(status);
		   session.setAttribute("selectedOrders", selectedOrders);
		   return "redirect:/orderMgmt/" + status;
	   }
   }
   
   @RequestMapping ("/orderMgmt/{process}")
   public String ordersTable (@PathVariable("process") String process, Model model) {
	   return "orderstable.jsp";
   }
   
}

/*
 * purplesmart@eq.net; Twily123
 * fabulous@eq.net; Spike5
*/
//https://www.tutorialspoint.com/jfreechart/jfreechart_database_interface.htm
//http://onlinejewelrypoint.com/
