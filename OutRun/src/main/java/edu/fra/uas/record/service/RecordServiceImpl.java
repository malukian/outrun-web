package edu.fra.uas.record.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.player.model.Player;
import edu.fra.uas.record.model.Record;
import edu.fra.uas.record.repository.RecordRepository;

@Service
public class RecordServiceImpl implements RecordService {

	private static final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);

	private RecordRepository recordRepository;

	@Autowired
	public RecordServiceImpl(RecordRepository recordRepository) {
		log.debug("RecordService instantiated");
		this.recordRepository = recordRepository;
	}
	
	//creates new Record object with the given player and time, sets timestamp and record time, saves it to repository
	@Override
	public Record newRecord(Player player, String time) {
		log.debug("new record");
		Record record = new Record(player);
		
		record.setRecordTime(Double.parseDouble(time));
		String timestamp = getTimestamp();
		record.setTimestamp(timestamp);
		
		recordRepository.save(record);

		return record;
	}
	//deletes the Record object at the specified index for a given player, 
	//removes it from the player's records list, and deletes it from repository
	@Override
	public void deleteRecord(Player player, int num) {
		log.debug("delete record for player " + player.getName() + " with number " + num);

		Record record = player.getRecords().get(num);

		player.getRecords().remove(num);

		recordRepository.delete(record);

	}
	//converts a time string of the format "Xm Ys" to a Double value representing the total time in seconds
	@Override
	public Double convertStringRecordToDoubleSec(String record) {
		double integer = Double.parseDouble(record.substring(0, record.indexOf("m")));
		double partial = Double.parseDouble(record.substring(5, record.indexOf("s")));
		return integer * 60 + partial;
	}
	//converts a time value in seconds to a String
	@Override
	public String convertDoubleRecordToString(double recordSec) {
		int min = (int) recordSec / 60;
		int sec = (int) recordSec % 60;
		return Integer.toString(min) + "min " + Integer.toString(sec) + "sec";
	}
	//sorts HashMap of Player objects and their record times by the record times 
	@Override
	public LinkedHashMap<Player, String> sortPlayerByBestRecords(HashMap<Player, String> map) {

		LinkedHashMap<Player, String> sortedMap = new LinkedHashMap<Player, String>();

		ArrayList<Double> list = new ArrayList<>();

		for (Map.Entry<Player, String> entry : map.entrySet()) {
			list.add(convertStringRecordToDoubleSec(entry.getValue()));
		}
		Collections.sort(list);

		for (double num : list) {
			for (Entry<Player, String> entry : map.entrySet()) {
				if (convertStringRecordToDoubleSec(entry.getValue()).equals(num)) {
					sortedMap.put(entry.getKey(), convertDoubleRecordToString(num));
				}
			}
		}

		return sortedMap;
	}
	//retrieves all Record objects from the recordRepository
	@Override
	public List<Record> getAllRecords() {
		log.debug("get all records");

		List<Record> records = recordRepository.findAll();

		return records;
	}
	//finds three best records from given list of Record objects
	public List<Record> findBestThreeRecords(List<Record> records) {
		log.debug("find best three records");

		//checks if the input list has at least three records
		if (records.size() < 3) {
			return null;
		}
		
		records.sort(Comparator.comparing(Record::getRecordTimeDouble));
		return records.subList(0, 3);
		

	}
	//defines method getTimestamp() that returns the current time as string formatted with the German locale 
	public String getTimestamp() {
		DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
				.withLocale(Locale.GERMAN);
		return LocalTime.now().format(germanFormatter);
	}
}
