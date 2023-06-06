package edu.fra.uas.record.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.fra.uas.player.model.Player;
import edu.fra.uas.record.model.Record;
import edu.fra.uas.player.service.PlayerService;
import edu.fra.uas.record.service.RecordService;

@Controller
@RequestMapping("/")
public class RecordController {
	
	private static final Logger log = LoggerFactory.getLogger(RecordController.class);
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private RecordService recordService;

	/* retrieves a list of all players and all records from the corresponding services, then uses the playerService to find 
	 * the best records for each player, sorts them and stores them in a LinkedHashMap.
	 * retrieved data is added as attributes to the model and the "records" view is returned.*/
	@RequestMapping(value = "records", method = RequestMethod.GET)
	public String showRecords(Model model) {
		log.debug("GET /records --> page shows best records");
		
		List<Player> players = playerService.getAll();
		List<Record> records = recordService.getAllRecords();
		
		HashMap<Player, String> bestRecordsMap = playerService.findBestRecords(players);
		LinkedHashMap<Player, String> sortedBestRecordsMap = recordService.sortPlayerByBestRecords(bestRecordsMap);
		
		List<Record> playersRecords = recordService.findBestThreeRecords(records);
				
		model.addAttribute("playersRecords", playersRecords);
		model.addAttribute("bestRecordsList", sortedBestRecordsMap);
		
		return "records";
	}

}
