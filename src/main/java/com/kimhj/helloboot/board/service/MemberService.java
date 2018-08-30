package com.kimhj.helloboot.board.service;

import com.kimhj.helloboot.board.vo.Member;

public interface MemberService {
	public Member insertNewMember(Member member);
	public Member selectOneMember(long index);
	public void deleteOneMember(long index);
	public Member updateOneMember(Member member);
}
