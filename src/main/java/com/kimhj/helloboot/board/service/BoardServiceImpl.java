package com.kimhj.helloboot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kimhj.helloboot.board.dao.BoardDao;
import com.kimhj.helloboot.board.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	@Qualifier("boardDaoJdbc")
	private BoardDao boardDao;
	
	@Override
	public Board createNewBoard(Board board) {
		return this.boardDao.insertNewBoard(board);
	}

	@Override
	public Board findOneBoard(long boardId) {
		try {
			return this.boardDao.selectOneBoard(boardId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Board> findAllBoards() {
		return this.boardDao.selectAllBoards();
	}

	@Override
	public void deleteOneBoard(long boardId) {
		this.boardDao.deleteOneBoard(boardId);
	}

	@Override
	public Board updateOneBoard(Board board) {
		return this.boardDao.updateOneBoard(board);
	}

}
