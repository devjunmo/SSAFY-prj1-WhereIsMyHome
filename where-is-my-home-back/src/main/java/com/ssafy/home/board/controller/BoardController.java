package com.ssafy.home.board.controller;

import java.util.List;

import com.ssafy.home.board.dto.*;
import com.ssafy.home.board.service.BoardService;
import com.ssafy.home.etc.annotation.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	@Autowired
	private BoardService boardService;

	// 새로운 게시글 정보를 입력한다.
	@Auth
	@PostMapping("/write")
	public ResponseEntity<Void> writeArticle(@RequestBody BoardDto boardDto)  {
		if (boardService.writeArticle(boardDto)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// 등록된 글의 총 갯수 가져오기
	@GetMapping("/length")
	public ResponseEntity<Integer> listArticleCount() {
		return new ResponseEntity<>(boardService.getArticleCount(), HttpStatus.OK);
	}

	// 지정된 분량 만큼의 게시글의 목록을 반환한다
	@GetMapping
	public ResponseEntity<List<BoardListDto>> listArticle(BoardParameterDto boardParameterDto)  {
		return new ResponseEntity<>(boardService.listArticle(boardParameterDto), HttpStatus.OK);
	}

	//글번호에 해당 하는 게시글의 정보를 반환
	@Auth
	@GetMapping("/{id}")
	public ResponseEntity<BoardViewDto> getArticle(@PathVariable("id") int id)  {
		return new ResponseEntity<>(boardService.getArticle(id), HttpStatus.OK);
	}

	// 게시글 수정
	@Auth
	@PutMapping
	public ResponseEntity<Void> modifyArticle(@RequestBody BoardDto boardDto)  {
		// 유저와 글작성자
		// 데이터 유효서 검사
		boardService.modifyArticle(boardDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 글번호에 해당하는 게시글의 정보를 삭제
	@Auth
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") int id)  {
		boardService.deleteArticle(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 게시판 댓글 작성
	@Auth
	@PostMapping("/comment")
	public ResponseEntity<Void> writeComment(@RequestBody CommentDto commentDto)  {
		boardService.writeComment(commentDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 현재 게시글 id에 해당하는 댓글들 가져오기
	@GetMapping("/{postId}/comment")
	public ResponseEntity<List<CommentDto>> getComments(@PathVariable("postId") int postId) {
		return new ResponseEntity<>(boardService.getComments(postId), HttpStatus.OK);
	}

	// 글번호에 해당하는 게시글의 정보를 삭제
	@Auth
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable("commentId") int commentId)  {
		boardService.deleteComment(commentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 글 번호에 해당하는 게시글의 정보를 수정
	@Auth
	@PutMapping("/comment")
	public ResponseEntity<Void> modifyComment(@RequestBody CommentDto commentDto)  {
		boardService.modifyComment(commentDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}