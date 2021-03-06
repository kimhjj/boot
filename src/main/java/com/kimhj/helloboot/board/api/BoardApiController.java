package com.kimhj.helloboot.board.api;

import java.util.List;

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

import com.google.gson.Gson;
import com.kimhj.helloboot.board.service.BoardService;
import com.kimhj.helloboot.board.vo.Board;
import com.kimhj.helloboot.exceptions.ApiException;
import com.kimhj.helloboot.response.ApiDataResponse;
import com.kimhj.helloboot.response.ApiResponse;
import com.kimhj.helloboot.response.error.ApiErrors;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/posts")
	public ApiResponse getAllBoardApi(){
		List<Board> boardList = this.boardService.findAllBoards();
		
		// return 할 타입을 명시
		ApiDataResponse<List<Board>> result = new ApiDataResponse<>(boardList);
		return result;
	}
		
	@GetMapping("/posts/{postId}")
	public ApiResponse getOneBoardApi(@PathVariable long postId){
		Board board = this.boardService.findOneBoard(postId);
		
		if(board == null) {
			return new ApiResponse(ApiErrors.NO_DATA);
		}
		
		ApiDataResponse<Board> result = new ApiDataResponse<>(board);
		return result;
	}
	
	@PostMapping("/posts")
	public ApiResponse createOneBoardApi(@Valid @RequestBody Board board, Errors errors){
		// @Valid object에 선언된 @NotEmpty 를 확인
		// @RequestBody : json => Object
		// Errors ==> validation 체크 후 결과를 나타내는데 @Valid 뒤에 꼭 써야한다. 중간에 다른 클래스 X
		// ★ 브라우저에서는 @Valid @ModelAttribute 모델어트리뷰트가 꼭 있어야 한다. restapi에서는 빼야함.
		
		if(errors.hasErrors()) {
			throw new ApiException(errors.getFieldErrors());
		}
		
		/*
		 * list :: 메모리 존재
		 * .stream() :: 별도의 메모리 생성 (콜렉션의 일종)
		 * .map(input, output)  :: 별도의 메모리 생성 (우리가 아는 map과는 다르다. stream 처리 방식)
		 * .Collectors.toList() :: 별도의 메모리 생성
		 * 
		 * 총 4개의 메모리가 생성되었다
		 */
		
		Board createdBoard = this.boardService.createNewBoard(board);
		
		ApiDataResponse<Board> result = new ApiDataResponse<>(createdBoard);
		
		// json으로 변경하여 history appender에 추가
		Gson gson = new Gson();
		HistoryAppender appender = new HistoryAppender();
		appender.append(
					"POST /posts"
					, gson.toJson(board)
					, gson.toJson(result));
		// @@ ★ 요청을 보내고, DB insert 확인
		// http://localhost:9090/console
		// test2 DB
		// SELECT * FROM HISTORY;
		
		return result;
	}
	
	@PutMapping("/posts/{postId}")
	public ApiResponse updateOneBoardApi(@PathVariable long postId, @RequestBody Board board){
		Board originBoard = this.boardService.findOneBoard(postId);
		
		if(originBoard == null) {
			return new ApiResponse(ApiErrors.NO_DATA);
		}
		
		board.setPostId(postId);
		Board updatedBoard = this.boardService.updateOneBoard(board);
		
		ApiDataResponse<Board> result = new ApiDataResponse<>(updatedBoard);
		return result;
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deleteOneBoardApi(@PathVariable long postId){
		this.boardService.deleteOneBoard(postId);
		
		ApiResponse result = new ApiResponse();
		return result;
	}
}
