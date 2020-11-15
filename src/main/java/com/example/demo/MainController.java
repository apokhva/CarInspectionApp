package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller // This means that this class is a Controller
public class MainController {
	@Autowired // This means to get the bean called DriverRepository which is auto-generated by Spring, we will use it to handle the data
	private DriverRepository driverRepository;
	
	// For adding users 
	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewDriver (@RequestParam String firstName, @RequestParam String lastName, 
			@RequestParam String birthday, @RequestParam String phoneNumber, @RequestParam String gender, 
			@RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam boolean isSigned) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Driver n = new Driver(firstName, lastName, birthday, phoneNumber, gender, email, username, password, isSigned);
		driverRepository.save(n);
		return "Driver Saved";
	}
	
	// For getting ArrayList of drivers
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Driver> getAllDrivers() {
		// This returns a JSON or XML with the users
		return driverRepository.findAll();
	}
	
	// Sign up method START
	@GetMapping("/signup")
	public String signupForm(Model model) {
		model.addAttribute("signupDriver", new Driver());
		return "signup";
	}
	
	// Sign up method END
	@PostMapping("/signup")
	public String signupSubmit(@ModelAttribute Driver signupDriver, Model model) {
		model.addAttribute("signupDriver", signupDriver);
		driverRepository.save(signupDriver);
		System.out.println("\nNew Driver Added to carclubdb!\n"+signupDriver.toString());
		return "result";
	}
	
}

