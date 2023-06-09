package edu.fra.uas.security.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	//handles GET requests for the "/login" endpoint 
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam Optional<String> error, Model model) {
		log.debug("GET /login --> Welcome to OutRun Game Application");
		return "login";
	}
	//handles POST requests for the "/logout"
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String getLogout(Model model) {
		return "logout";
	}
	
}
