package com.kimhj.helloboot.board.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kimhj.helloboot.board.vo.Member;

@Repository
public class MemberDaoInMemory implements MemberDao {
	private Map<Long, Member> memberMap;
	private long index;
	
	public MemberDaoInMemory() {
		this.memberMap = new HashMap<>();
		this.index = 0;
	}
	
	@Override
	public Member insertNewMember(Member member) {
		member.setIndex(++index);
		memberMap.put(index, member);
		return member;
	}

	@Override
	public Member selectOneMember(long index) {
		return memberMap.get(index);
	}

	@Override
	public void deleteOneMember(long index) {
		memberMap.remove(index);
	}

	@Override
	public Member updateOneMember(Member member) {
		Member originMember = this.selectOneMember(member.getIndex());
		long originIndex = originMember.getIndex();
		member.setIndex(originIndex);
		memberMap.put(originIndex, member);
		return member;
	}
}
