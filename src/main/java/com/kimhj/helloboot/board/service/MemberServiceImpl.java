package com.kimhj.helloboot.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kimhj.helloboot.board.dao.MemberDao;
import com.kimhj.helloboot.board.vo.Member;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberDao memberDao;
	
	@Override
	public Member insertNewMember(Member member) {
		return memberDao.insertNewMember(member);
	}

	@Override
	public Member selectOneMember(long index) {
		return memberDao.selectOneMember(index);
	}

	@Override
	public void deleteOneMember(long index) {
		memberDao.deleteOneMember(index);
	}

	@Override
	public Member updateOneMember(Member member) {
		return memberDao.updateOneMember(member);
	}

}
