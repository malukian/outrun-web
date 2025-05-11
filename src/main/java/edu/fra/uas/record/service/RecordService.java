package edu.fra.uas.record.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.fra.uas.player.model.Player;
import edu.fra.uas.record.model.Record;

public interface RecordService {
//declares several methods to manage and retrieve records of player times
	Record newRecord(Player player, String time);

	void deleteRecord(Player player, int num);

	Double convertStringRecordToDoubleSec(String record);

	String convertDoubleRecordToString(double recordSec);

	LinkedHashMap<Player, String> sortPlayerByBestRecords(HashMap<Player, String> targetMap);

	List<Record> getAllRecords();
	
	List<Record> findBestThreeRecords(List<Record> records);

}
