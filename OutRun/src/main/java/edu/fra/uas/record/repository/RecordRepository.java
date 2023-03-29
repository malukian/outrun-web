package edu.fra.uas.record.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fra.uas.record.model.Record;
//provides methods to find a record by player name or record number.
public interface RecordRepository extends JpaRepository<Record, Integer> {

	Optional<Record> findByPlayer(String player);
	
	Optional<Record> findByNum(int num);

}
