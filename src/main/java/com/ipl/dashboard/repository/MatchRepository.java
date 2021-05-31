package com.ipl.dashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.ipl.dashboard.model.Match;

public interface MatchRepository extends CrudRepository<Match, Long>{
	List<Match> findByTeam1OrTeam2OrderByDateDesc (String teamName1, String teamName2, Pageable pageable);
	
	List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
			String teamName1, LocalDate date1, LocalDate date2,
			String teamName2, LocalDate date3, LocalDate date4
			);
}
