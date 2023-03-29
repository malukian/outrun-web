package edu.fra.uas.player.service;

import java.util.HashMap;
import java.util.List;

import edu.fra.uas.player.model.Player;
import edu.fra.uas.security.model.UserCreateForm;

public interface PlayerService {

//is an interface for the PlayerService that defines the methods that the service should implement. 
	Player getByName(String name);

	List<Player> getAll();

	void newRecordTo(String name, String time);

	void deleteRecordWithNum(String name, int num);
	
	HashMap<Player, String> findBestRecords(List<Player> players);

	void createPlayer(UserCreateForm form);
	
	void deletePlayer(String name);
}
