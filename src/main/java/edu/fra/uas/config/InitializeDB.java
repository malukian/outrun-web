package edu.fra.uas.config;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.player.model.Player;
import edu.fra.uas.record.model.Record;
import edu.fra.uas.player.repository.PlayerRepository;
import edu.fra.uas.record.repository.RecordRepository;
import edu.fra.uas.security.model.User;
import edu.fra.uas.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
public class InitializeDB {
	// some data initialization before playing the game

	private static final Logger log = LoggerFactory.getLogger(InitializeDB.class);
	
	//The player objects are stored in the database in the PlayerRepository
	@Autowired
	PlayerRepository playerRepository;

	//Record objects are stored in the RecordRepository.
	@Autowired
	RecordRepository recordRepository;

	//three user objects created in the UserRepository
	@Autowired
	UserRepository userRepository;

	@PostConstruct
	public void init() {
		log.debug(" >>> init database");
		
		//three players are created
		User user = new User();
        user.setNickname("john");
        user.setEmail("john@example.com");
        user.setPassword("john");
        userRepository.save(user);

        user = new User();
        user.setNickname("zoe");
        user.setEmail("zoe@example.com");
        user.setPassword("zoe");
        userRepository.save(user);

        user = new User();
        user.setNickname("alex");
        user.setEmail("alex@example.com");
        user.setPassword("alex");
        userRepository.save(user);

		DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
				.withLocale(Locale.GERMAN);

		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();

		player1.setName("john");
		player2.setName("zoe");
		player3.setName("alex");

		playerRepository.save(player1);
		playerRepository.save(player2);
		playerRepository.save(player3);
		
		
		//records are created for the players in each case
		Record record1 = new Record(player1);
		record1.setRecordTime(125.5);
		String timestamp = LocalTime.now().minusMinutes(14).plusSeconds(12).format(germanFormatter);
		record1.setTimestamp(timestamp);
		player1.addRecord(record1);

		Record record2 = new Record(player1);
		record2.setRecordTime(181.0);
		timestamp = LocalTime.now().minusMinutes(30).plusSeconds(12).format(germanFormatter);
		record2.setTimestamp(timestamp);
		player1.addRecord(record2);

		Record record3 = new Record(player1);
		record3.setRecordTime(121.0);
		timestamp = LocalTime.now().minusMinutes(25).plusSeconds(12).format(germanFormatter);
		record3.setTimestamp(timestamp);
		player1.addRecord(record3);

		recordRepository.save(record1);
		recordRepository.save(record2);
		recordRepository.save(record3);

		Record record4 = new Record(player2);
		record4.setRecordTime(119.5);
		timestamp = LocalTime.now().minusMinutes(10).plusSeconds(44).format(germanFormatter);
		record4.setTimestamp(timestamp);
		player2.addRecord(record4);

		recordRepository.save(record4);

		Record record5 = new Record(player3);
		record5.setRecordTime(133.0);
		timestamp = LocalTime.now().minusMinutes(18).plusSeconds(33).format(germanFormatter);
		record5.setTimestamp(timestamp);
		player3.addRecord(record5);

		recordRepository.save(record5);
	}
}
