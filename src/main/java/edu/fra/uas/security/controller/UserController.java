package edu.fra.uas.security.controller;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.player.service.PlayerService;
import edu.fra.uas.security.model.UserCreateForm;
import edu.fra.uas.security.service.dto.UserDTO;
import edu.fra.uas.security.service.user.UserService;
import edu.fra.uas.security.service.validator.UserCreateFormValidator;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private UserService userService;

	private PlayerService playerService;

	private UserCreateFormValidator userCreateFormValidator;

	@Autowired
	public UserController(UserService userService, PlayerService playerService,
			UserCreateFormValidator userCreateFormValidator) {
		this.userService = userService;
		this.playerService = playerService;
		this.userCreateFormValidator = userCreateFormValidator;
	}

	@InitBinder("regform")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(userCreateFormValidator);
	}
	//handles GET and POST requests for the "/profile" endpoint
	@RequestMapping(value = "/profile", method = { RequestMethod.POST, RequestMethod.GET })
	private String profile(@RequestParam String name, Model model) {
		log.debug("/profile --> profile for player " + name);

		UserDTO userDTO = userService.getUserByNickname(name);

		model.addAttribute("user", userDTO);
		model.addAttribute("nickname", userDTO.getNickname());
		return "/profile";
	}
	//handling GET and POST requests to "/registration" endpoint. It returns the "registration" view 
	@RequestMapping(value = "/registration", method = { RequestMethod.POST, RequestMethod.GET })
	public String registrationUser(Model model) {
		log.debug("/registration --> getting user create form");
		model.addAttribute("regform", new UserCreateForm());
		return "registration";
	}
	//handles a POST request to create a new user
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String handleUserCreateForm(@Valid @ModelAttribute("regform") UserCreateForm form,
			BindingResult bindingResult, Model model) {
		log.info("/create --> processing user create form= " + form + " bindingResult= " + bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", bindingResult.getGlobalError().getDefaultMessage());
			return "registration";
		}
		userService.create(form);
		playerService.createPlayer(form);
		boolean done = true;
		model.addAttribute("done", done);
		return "login";
	}
	// handles the deletion of a user account
	@RequestMapping(value = "/deleteUser", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteUser(@RequestParam String name, Model model) {
		log.debug("/deleteUser --> delete user");
		
		userService.delete(name);
		playerService.deletePlayer(name);

		return "logout";
	}

}
