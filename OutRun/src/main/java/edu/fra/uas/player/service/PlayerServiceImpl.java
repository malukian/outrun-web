package edu.fra.uas.player.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.player.model.Player;
import edu.fra.uas.player.repository.PlayerRepository;
import edu.fra.uas.record.model.Record;
import edu.fra.uas.record.service.RecordService;
import edu.fra.uas.security.model.UserCreateForm;

// is the implementation of the PlayerService interface. 

@Service
public class PlayerServiceImpl implements PlayerService {

	private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

	private PlayerRepository playerRepository;

	private RecordService recordService;

	@Autowired
	public PlayerServiceImpl(PlayerRepository playerRepository, RecordService recordService) {
		log.debug("PlayerService instantiated");
		this.playerRepository = playerRepository;
		this.recordService = recordService;
	}
	//retrieves a Player object from the database using the provided name
	@Override
	public Player getByName(String name) {
		log.debug("get player by name: " + name);
		return playerRepository.findByName(name).orElse(null);
	}

	//adds a new record to the specified player with the given time
	@Override
	public void newRecordTo(String name, String time) {
		log.debug("add new record for player: " + name);
		Player player = playerRepository.findByName(name).orElse(null);

		Record record = recordService.newRecord(player, time);

		player.addRecord(record);
	}
	
	//deletes a record with a specific number num for the player with the given name
	@Override
	public void deleteRecordWithNum(String name, int num) {
		log.debug("delete record for: " + name + " with num: " + num);

		Player player = playerRepository.findByName(name).orElse(null);
		log.debug(player.toString());

		recordService.deleteRecord(player, num);
	}
	
	//returns a list of all the players in the system 
	@Override
	public List<Player> getAll() {
		log.debug("get all player");

		List<Player> players = playerRepository.findAll();

		return players;
	}
	
	// takes a list of players and returns a HashMap containing each player and their best record
	@Override
	public HashMap<Player, String> findBestRecords(List<Player> players) {
		log.debug("find all best records from playersList");

		HashMap<Player, String> targetMap = new HashMap<>();

		for (Player player : players) {
			if (player.getRecords().values().isEmpty() || player.getRecords().values() == null) {
				continue;
			}
			List<Record> targetList = new ArrayList<>(player.getRecords().values());
			String bestRecord = player.getBestRecord(targetList);

			targetMap.put(player, bestRecord);
		}

		return targetMap;
	}
	
	//creates a new player in the game
	@Override
	public void createPlayer(UserCreateForm form) {
		log.debug("createPlayer " + form.getNickname());
		Player player = new Player();
		player.setName(form.getNickname());
		playerRepository.save(player);
	}
	//deletes a player from the game
	@Override
	public void deletePlayer(String name) {
		log.debug("deletePlayer " + name);

		Player player = playerRepository.findByName(name).orElse(null);
		List<Integer> targetList = new ArrayList<>(player.getRecords().keySet());

		for (int num : targetList) {
			recordService.deleteRecord(player, num);
		}

		playerRepository.delete(player);
	}

}
