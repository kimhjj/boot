package com.kimhj.helloboot.board.dao;

import com.kimhj.helloboot.board.vo.Member;

public interface MemberDao {
	public Member insertNewMember(Member member);
	public Member selectOneMember(long index);
	public void deleteOneMember(long index);
	public Member updateOneMember(Member member);
}
