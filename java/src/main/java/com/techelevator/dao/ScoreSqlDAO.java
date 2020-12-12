package com.techelevator.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Score;

@Component
public class ScoreSqlDAO implements ScoreDAO {
	private JdbcTemplate jdbcTemplate;
	
	public ScoreSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void recordScore(Score score) {
		String sql = "INSERT INTO scores (round_id, user_id, league_id, score_total) VALUES (DEFAULT, ?, ?, ?)";
		jdbcTemplate.update(sql, score.getUserId(), score.getLeagueName(), score.getScoreTotal());
	}

	@Override
	public List<Score> getAllScoresByLeagueId(Score score) {
		
		return null;
	}

	@Override
	public List<Score> getAllScoresByUserId(Score score) {
		
		return null;
	}
	
	public void mapRowToRecordScore(SqlRowSet rowSet) {
		Score score = new Score();
		score.setUserId(rowSet.getInt("user_id"));
		score.setLeagueId(rowSet.getInt("league_id"));
		score.setRoundScore(rowSet.getInt("score_total"));
	}
	
//	@Override
//	public Score userSendScore(Score score) {
//		
//		return null;
//	}
	
}