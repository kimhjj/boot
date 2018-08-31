package com.kimhj.helloboot.member.dao;

import com.kimhj.helloboot.member.vo.Member;

public interface MemberDao {
	public Member insertNewMember(Member member);
	public Member selectOneMember(long index);
	public void deleteOneMember(long index);
	public Member updateOneMember(Member member);
}
