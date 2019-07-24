package com.luv2code.springDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springDemo.dao.CustomerDAO;
import com.luv2code.springDemo.entity.Customer;
import com.luv2code.springDemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject the customer service
	@Autowired
	private CustomerService customerService; 
	
	
	
	// ======show all customer data======
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get customer from the dao
		List<Customer> theCustomers = customerService.getCustomers();
		
		// add the customer to the model
		theModel.addAttribute("customers",theCustomers);
		
		return "list-customers";
	}
	// End of show customer data method
	
	
	
	// ====data insert for customer=====
	@GetMapping(value="/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);
		
		return "add-customer";
	}
	
	@PostMapping(value="/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		// save the customer using our service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	// End of insert method
	
	
	
	// ======update customer data========
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel ) {
		
		// get the customer form our service
		Customer theCustomer = customerService.getCustomer(theId);
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		// send over to our form
		return "add-customer";
	}
	
	
	// delete customer method
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		//delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	
	
	// method for search 
	@PostMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
									Model theModel) {

		// search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
				
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);

		return "list-customers";		
	}

	

}
