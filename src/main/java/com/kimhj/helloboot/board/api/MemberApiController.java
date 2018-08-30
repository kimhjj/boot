package com.kimhj.helloboot.board.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kimhj.helloboot.board.service.MemberService;
import com.kimhj.helloboot.board.vo.Member;
import com.kimhj.helloboot.exceptions.ApiException;
import com.kimhj.helloboot.response.ApiDataResponse;
import com.kimhj.helloboot.response.ApiResponse;
import com.kimhj.helloboot.response.error.ApiErrors;

@RestController
public class MemberApiController {

	@Autowired
	MemberService memberService;
	
	@PostMapping("/members")
	public ApiResponse insertOneMember(@Valid @RequestBody Member member, Errors errors) {
		
		if(errors.hasErrors()) {
			throw new ApiException(errors.getFieldErrors());
		}
		
		Member newMember = memberService.insertNewMember(member);
		
		ApiDataResponse<Member> result = new ApiDataResponse<>(newMember);
		
		return result;
	}
	
	@GetMapping("/members/{index}")
	public ApiResponse selectOneMember(@PathVariable long index) {
		
		Member member = memberService.selectOneMember(index);
		if(member == null) {
			return new ApiResponse(ApiErrors.NO_DATA);
		}
		ApiDataResponse<Member> result = new ApiDataResponse<>(member);
		
		return result;
	}
	
	@DeleteMapping("/members/{index}")
	public ApiResponse deleteOneMember(@PathVariable long index) {
		
		memberService.deleteOneMember(index);
		ApiResponse result = new ApiResponse();
		
		return result;
	}
	
	@PutMapping("/members/{index}")
	public ApiResponse updateOneMember(@RequestBody Member member, @PathVariable long index) {

		Member originMember = memberService.selectOneMember(index);
		if(originMember == null) {
			return new ApiResponse(ApiErrors.NO_DATA);
		}
		
		member.setIndex(index);
		Member newMember = memberService.updateOneMember(member);
		ApiDataResponse<Member> result = new ApiDataResponse<>(newMember);
		
		return result;
	}
}
