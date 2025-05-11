package edu.fra.uas.record.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import edu.fra.uas.player.model.Player;
import jakarta.persistence.Entity;

@Entity
public class Record {
	
//represents a single record entry in the application.
	@Id 
    @GeneratedValue
    private Integer id;
	
	@ManyToOne
	private Player player;
	
	private static int count = 0;
	
	private int num = count++;
	
	private double recordTimeDouble;
	private String recordTimeString;
	private String timestamp;
	
	public Record() {
		
	}
	
	public Record(Player player) {
		this.player = player;
	}
	//returns the player who achieved the record
	public Player getPlayer() {
		return player;
	}
	//returns the ID of the record
	public Integer getId() {
		return id;
	}
	//returns the numerical identifier of the record
	public Integer getNum() {
		return num;
	}
	//returns the recorded time of a record as a double value
	public double getRecordTimeDouble() {
		return recordTimeDouble;
	}
	//returns a string representing the record time in minutes and seconds
	public String getRecordTimeString() {
		return recordTimeString;
	}
	//setzt das Feld recordTimeDouble auf den angegebenen Wert und errechnet die entsprechende Zeit in Minuten und Sekunden, 
	//die im Feld recordTimeString gespeichert wird
	public void setRecordTime(double recordTime) {
		this.recordTimeDouble = recordTime;
		
		int min = (int) recordTime / 60;
		int sec = (int) recordTime % 60;
		this.recordTimeString = Integer.toString(min) + "min " + Integer.toString(sec) + "sec";
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
    public String toString() {
        return "Record{player=" + player.getName() + " id=" + id + " num:" + num + " Time=" + recordTimeDouble + " at=" + timestamp + "}";
    }
	
}
