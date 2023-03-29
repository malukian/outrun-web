package edu.fra.uas.player.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import edu.fra.uas.record.model.Record;

@SuppressWarnings("serial")
@Entity
public class Player implements Serializable {

	@Transient
	private static final Logger log = LoggerFactory.getLogger(Player.class);

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	@OneToMany(mappedBy = "player")
	@MapKey(name = "num")
	private Map<Integer, Record> records = new HashMap<>();

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Record> getRecords() {
		return records;
	}
	//addRecord" adds a new record object to records in player class
	public boolean addRecord(Record record) {
		if (this.records == null) {
			this.records = new HashMap<>();
			log.debug("new records map created");
			this.records.put(record.getNum(), record);
			log.debug("new record is added to records map " + record);
			return true;
		} else {
			this.records.put(record.getNum(), record);
			log.debug("new record is added to records map " + record);
			return true;
		}
	}
	//takes a list of records as input. Searches for record with the best time and returns it
	public String getBestRecord(List<Record> targetList) {
		if (targetList == null || targetList.isEmpty()) {
			return null;
		} else {
			String bestRecordString = targetList.get(0).getRecordTimeString();
			double bestRecordDouble = targetList.get(0).getRecordTimeDouble();
			for (Record record : targetList) {
				if (record.getRecordTimeDouble() < bestRecordDouble) {
					bestRecordString = record.getRecordTimeString();
					bestRecordDouble = record.getRecordTimeDouble();
				}
			}
			log.debug("best record is " + bestRecordString);
			return bestRecordString;
		}
	}
	//returns a string representation of the object's state, including the player's name and their records
	@Override
	public String toString() {
		return "Player{name=" + name + ", records=" + records + "}";
	}

}
