package com.ipl.dashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ipl.dashboard.model.Team;

@Component
@Transactional
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final EntityManager em;

	@Autowired
	public JobCompletionNotificationListener(EntityManager em) {
		this.em = em;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			Map<String, Team> teamData = new HashMap<>();

			em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class).getResultList()
					.stream().map(o -> new Team((String) o[0], (long) o[1]))
					.forEach(team -> teamData.put(team.getTeamName(), team));

//      teamData.values().forEach(System.out::println);

			em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class).getResultList()
					.stream().forEach(o -> {
						Team team = teamData.get((String) o[0]);
						team.setTotalMatches((long) o[1] + team.getTotalMatches());
					});

//      teamData.values().forEach(System.out::println);

			em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
					.getResultList().stream().forEach(o -> {
						Team team = teamData.get((String) o[0]);
						if (team != null) {
							team.setTotalWins((long) o[1]);
						}
					});

			teamData.values().forEach(team -> em.persist(team));
//      teamData.values().forEach(System.out::println);

		}
	}
}