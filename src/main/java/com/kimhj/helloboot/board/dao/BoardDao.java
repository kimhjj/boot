package com.kimhj.helloboot.board.dao;

import java.util.List;

import com.kimhj.helloboot.board.vo.Board;

public interface BoardDao {
	public Board insertNewBoard(Board board);

	public Board selectOneBoard(long boardId);

	public List<Board> selectAllBoards();

	public void deleteOneBoard(long boardId);

	public Board updateOneBoard(Board board);

}
