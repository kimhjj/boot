package com.kimhj.helloboot.board.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kimhj.helloboot.board.vo.Board;

@Repository
public class BoardDaoJdbc implements BoardDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Board insertNewBoard(Board board) {
		StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO POST (subject, content) ");
		query.append(" VALUES (?,?)                        ");
		
		int insertCount = jdbcTemplate.update(
											query.toString()
											, board.getSubject()	// ? 물음표에 bind 1
											, board.getContent()	// ? 물음표에 bind 2
										);
		return board;
	}

	@Override
	public Board selectOneBoard(long boardId) {
		StringBuffer query = new StringBuffer();
		query.append(" SELECT *           ");
		query.append(" FROM POST          ");
		query.append(" WHERE POST_ID = ?  ");
		
		return jdbcTemplate.queryForObject(
								query.toString()
								, new RowMapper<Board>() {

									@Override
									public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
										Board board = new Board();
										board.setPostId(rs.getLong("POST_ID"));
										board.setSubject(rs.getString("SUBJECT"));
										board.setContent(rs.getString("CONTENT"));
										return board;
									}
								}
								, boardId	// ? 물음표에 bind 1
							);
	}

	@Override
	public List<Board> selectAllBoards() {
		StringBuffer query = new StringBuffer();
		query.append(" SELECT *               ");
		query.append(" FROM POST              ");
		query.append(" ORDER BY POST_ID DESC  ");
		
		return jdbcTemplate.query(
								query.toString()
								, new RowMapper<Board>() {

									@Override
									public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
										Board board = new Board();
										board.setPostId(rs.getLong("POST_ID"));
										board.setSubject(rs.getString("SUBJECT"));
										board.setContent(rs.getString("CONTENT"));
										return board;
									}
								}
							);
	}

	@Override
	public void deleteOneBoard(long boardId) {
		StringBuffer query = new StringBuffer();
		query.append(" DELETE FROM POST   ");
		query.append(" WHERE POST_ID = ?  ");
		
		int deleteCount = jdbcTemplate.update(
											query.toString()
											, boardId	// ? 물음표에 bind 1
										);
	}

	@Override
	public Board updateOneBoard(Board board) {
		StringBuffer query = new StringBuffer();
		query.append(" UPDATE POST        ");
		query.append(" SET SUBJECT = ?    ");
		query.append("     , CONTENT = ?  ");
		query.append(" WHERE POST_ID = ?  ");
		
		int updateCount = jdbcTemplate.update(
											query.toString()
											, board.getSubject()	// ? 물음표에 bind 1
											, board.getContent()	// ? 물음표에 bind 2
											, board.getPostId()		// ? 물음표에 bind 3
										);
		return this.selectOneBoard(board.getPostId());
	}
}
