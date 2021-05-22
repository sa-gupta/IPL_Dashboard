package com.ipl.dashboard.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.ipl.dashboard.model.Match;

public interface MatchRepository extends CrudRepository<Match, Long>{
	List<Match> findByTeam1OrTeam2OrderByDateDesc (String teamName1, String teamName2, Pageable pageable);
}
