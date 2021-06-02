package com.ipl.dashboard.controller;

import java.time.LocalDate;
import java.util.List;
//import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipl.dashboard.model.Match;
import com.ipl.dashboard.model.Team;
import com.ipl.dashboard.repository.MatchRepository;
import com.ipl.dashboard.repository.TeamRepository;

@RestController
@CrossOrigin
public class TeamController {
	
	private TeamRepository tRepo;
	private MatchRepository mRepo;
	
	public TeamController(TeamRepository tRepo, MatchRepository mRepo) {
		this.tRepo = tRepo;
		this.mRepo = mRepo;
	}
	
//	@GetMapping("/team/{teamName}/{count}")
//	public Team getTeam(@PathVariable("teamName") String teamName, @PathVariable("count") int count) {
//		Team team = this.tRepo.findByTeamName(teamName);
//		List<Match> matches = this.mRepo.findByTeam1OrTeam2(teamName, teamName)
//				.stream().limit(count).collect(Collectors.toList()); 
//		team.setMatches(matches);
//		return team;
//	}
	
	@GetMapping("/allteam")
	public Iterable<Team> getAllTeam() {
		return tRepo.findAll();
	}
	
	
	
	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable("teamName") String teamName) {
		Team team = this.tRepo.findByTeamName(teamName);
		Pageable pageable = PageRequest.of(0, 4);
		team.setMatches(this.mRepo.findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable));
		return team;
	}

	@GetMapping("/team/{teamName}/matches")
	public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year){
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year+1, 1, 1);
		return mRepo.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
	} 
	
	@GetMapping("/team/{teamName}/activeYears")
	public List<Integer> getTeamActiveYears(@PathVariable String teamName){
		return mRepo.getTeamActiveYears(teamName);
		
//		return null;
	}
	
		
		// Below method will give same output as above.
		// but the only difference is below method is jpa way of writing method as well as query.
//		return mRepo.getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
//				teamName, startDate, endDate,
//				teamName, startDate, endDate 
//				);	
	
}
