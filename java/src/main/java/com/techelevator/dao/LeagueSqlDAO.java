package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.League;
import com.techelevator.model.User;

@Component
public class LeagueSqlDAO implements LeagueDAO {
	
	private JdbcTemplate jdbcTemplate;
	private UserSqlDAO dao;

    public LeagueSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.dao = dao;
    }

	@Override
	public List<League> viewLeaguesByUsername(String username) {
		List<League> leagues =new ArrayList<>();
		
		String sqlSelectAllLeagues = "SELECT u.username, l.league_name FROM leagues l JOIN users_leagues USING(league_id) JOIN users u USING(user_id) WHERE u.username = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlSelectAllLeagues, username);
		
		while (result.next()) {
			League theLeague = mapRowToLeague(result);
			
			leagues.add(theLeague);
		}
		return leagues;
	}

	@Override
	public void createLeague(League league) {
	
		String sql = "INSERT INTO leagues (league_id, league_name, course_name, username) VALUES (DEFAULT, ?, ?, ?)";
		
		jdbcTemplate.update(sql, league.getLeagueName(), league.getCourseName(), league.getUsername());
		
		
	}



	@Override
	public void invitePlayers(String username, String leagueName) {
		// TODO Auto-generated method stub //post
		
		String sql = "INSERT INTO invite (invite_id, status_id, league_name, user_name) VALUES (DEFUALT, 1, ?, ?)"; 
		
		jdbcTemplate.update(sql, leagueName, username);
		 
		
	}

	@Override
	public void updateInvite(League invite) {
		
		if(invite.getStatusId() == 2) {
			

			String sql = "UPDATE invite SET status_id = 2 WHERE username = ? AND league_name = ?";

			
			jdbcTemplate.update(sql, invite.getUsername(), invite.getLeagueName());
			
		} else if (invite.getStatusId() == 3) {
			
			String sql = "UPDATE invite SET status_id = 3 WHERE username = ? AND league_name = ?";
			
			jdbcTemplate.update(sql, invite.getUsername(), invite.getLeagueName());
			
		} 
		// TODO Auto-generated method stub //put
		
		
	}

	@Override
	public List<League> getPendingInvitesbyUsername(String username) {
		// TODO Auto-generated method stub //get
		List<League> invites = new ArrayList<>();
		
		String sql = "SELECT league_name FROM invite WHERE username = ? AND status_id = 1";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
		
		while (result.next()) {
			League theInvite = mapRowToLeague(result);
			
			invites.add(theInvite);
		}
		
		return invites;
	}
		
		
	@Override
	public void setTeeTime(League teeTime) {
		// TODO Auto-generated method stub //post
		String sql = "INSERT INTO tee_time (tee_time_id, username, league_name, tee_date, start_time) VALUES (DEFAULT, ?, ?, ?, ?)";
				
		jdbcTemplate.update(sql, teeTime.getUsername(), teeTime.getLeagueName(), teeTime.getDate(), teeTime.getStartTime());
	}

	@Override
	public List<League> viewTeeTimesByUsername(String username) {
	
		List<League> teeTimes = new ArrayList<>();
		
		String sql = "SELECT tee_date, start_time FROM tee_time WHERE username = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
		
		while (result.next()) {
			League theTeeTime = mapRowToLeague(result);
			
			teeTimes.add(theTeeTime);
		}
		return teeTimes;
	}
	@Override
	public List<League> viewTeeTimesByLeagueName(String leagueName) {
	
		List<League> teeTimes = new ArrayList<>();
		
		String sql = "SELECT tee_date, start_time FROM tee_time WHERE league_name = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, leagueName);
		
		while (result.next()) {
			League theTeeTime = mapRowToLeague(result);
			
			teeTimes.add(theTeeTime);
		}
		return teeTimes;
	}
	
	@Override
    public int findLeagueIdByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT league_id from leagues where username = ?", int.class, username);
    }

	private League mapRowToLeague(SqlRowSet rowSet) {
		
		League theLeague = new League();
		
		theLeague.setLeagueId(rowSet.getLong("league_id"));
		theLeague.setLeagueName(rowSet.getString("league_name"));
		theLeague.setCourseName(rowSet.getString("course_name"));
		theLeague.setStatusId(rowSet.getInt("status_id"));
		theLeague.setInviteStatus(rowSet.getString("status_type"));
		theLeague.setTeeTimeId(rowSet.getLong("tee_time_id"));
		theLeague.setDate(rowSet.getString("tee_date"));
		theLeague.setStartTime(rowSet.getString("start_time"));
		
		return theLeague;
		
	}

	


}
