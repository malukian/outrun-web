package edu.fra.uas.player.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.player.model.Player;
import edu.fra.uas.record.model.Record;
import edu.fra.uas.security.model.CurrentUser;
import edu.fra.uas.security.service.user.UserService;
import edu.fra.uas.player.service.PlayerService;

@Controller
@RequestMapping("/")
public class PlayerController {

	private PlayerService playerService;

	private UserService userService;

	private CurrentUser currentUser;

	@Autowired
	public PlayerController(PlayerService playerService, UserService userService, CurrentUser currentUser) {
		this.playerService = playerService;
		this.userService = userService;
		this.currentUser = currentUser;
	}

	private static final Logger log = LoggerFactory.getLogger(PlayerController.class);

	/*checks if the provided email and password exist in the database, logs in the user, and adds the username to the model. 
	 If there is an error, it redirects to the login page with an error message.*/
	@RequestMapping(value = "/first", method = { RequestMethod.POST, RequestMethod.GET })
	private String first(@RequestParam String email, @RequestParam String password, Model model) {
		log.debug("/first --> log in with email " + email + " and password " + password);

		if (!userService.existsByEmail(email)) {
			log.debug("/first --> email was wrong!");
			model.addAttribute("error", "wrong login");
			return "login";
		}
		if (!password.equals(userService.getUserByEmail(email).get().getPassword())) {
			log.debug("/first --> password was wrong!");
			model.addAttribute("error", "wrong login");
			return "login";
		}

		currentUser.setUser(userService.getUserByEmail(email).get());
		String name = currentUser.getNickname();

		model.addAttribute("name", name);
		return "/menu";
	}
	//Displays menu for specified player
	@RequestMapping(value = "/menu", method = { RequestMethod.POST, RequestMethod.GET })
	private String menuPage(@RequestParam String name, Model model) {
		log.debug("/menu --> menu for player " + name);

		name = getCurrentUser(model);
		return "/menu";
	}
	//Returns game page and result is stored in database 
	@RequestMapping(value = "/game", method = { RequestMethod.POST, RequestMethod.GET })
	private String gamePage(@RequestParam String name, Model model) {
		log.debug("POST /game --> game page");

		name = getCurrentUser(model);
		return "/game.final";
	}
	//returns the user page with the specified player's
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	private String userPage(@RequestParam String name, Model model) {
		log.debug("GET /user --> user page " + name);

		Player player = playerService.getByName(name);
		List<Record> targetList = new ArrayList<>(player.getRecords().values());
		String bestRecord = player.getBestRecord(targetList);

		model.addAttribute("player", player);
		model.addAttribute("records", targetList);
		model.addAttribute("bestRecord", bestRecord);

		return "/user";
	}
	//new Records are noted
	@RequestMapping(value = "/newRecord", method = RequestMethod.POST)
	public ResponseEntity<String> addRecord(@RequestParam String record, Model model) {
		log.debug("POST /newRecord --> new record" + ", time = " + record);
		
		String name = getCurrentUser(model);
		playerService.newRecordTo(name, record);
		
		return new ResponseEntity<>("Record Saved!", HttpStatus.OK);
	}
	//Delete Records from User
	@RequestMapping(value = "/deleteRecord", method = RequestMethod.POST)
	public String deleteRecord(@RequestParam String name, @RequestParam int num, Model model) {
		log.debug("POST /deleteRecord --> delete record of " + name + " with number " + num);

		playerService.deleteRecordWithNum(name, num);
		model.addAttribute("name", name);
		return "redirect:user?name=" + name;
	}
	//call home page -> redirect to login page
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String firstPage() {
		log.debug("GET redirect to login");
		return "redirect:login";
	}

	private String getCurrentUser(Model model) {
		String name = currentUser.getUser().getNickname();
		model.addAttribute("name", name);
		return name;
	}

}
