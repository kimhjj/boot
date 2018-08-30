package com.kimhj.helloboot.board.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Board {
	
	private long postId;
	
	@NotEmpty(message="Subject is mandatory field.")	// not empty가 위배 되었을 때, 나타날 메시지 (blank and null)
	@Length(min=10, max=100, message="10~100")
	private String subject;
	
	@NotEmpty(message="Content is mandatory field.")
	private String content;

	
	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
