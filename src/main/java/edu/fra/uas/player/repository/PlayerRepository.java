package edu.fra.uas.player.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fra.uas.player.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

	//search for a player by name in the database.
	Optional<Player> findByName(String name);

}
