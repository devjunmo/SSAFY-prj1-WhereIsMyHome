package com.ssafy.home.board.service;

import com.ssafy.home.board.dto.*;

import java.util.List;

public interface BoardService {
	boolean writeArticle(BoardDto boardDto);
	Integer getArticleCount();
	List<BoardListDto> listArticle(BoardParameterDto boardParameterDto);
//	PageNavigation makePageNavigation(BoardParameterDto boardParameterDto);
//
	BoardViewDto getArticle(int id);
	void updateHit(int id);
	void modifyArticle(BoardDto boardDto);
	void deleteArticle(int id);
	void writeComment(CommentDto commentDto);
	List<CommentDto> getComments(int postId);
	void deleteComment(int commentId);
	void modifyComment(CommentDto commentDto);
//	Integer getArticleCount();
}
