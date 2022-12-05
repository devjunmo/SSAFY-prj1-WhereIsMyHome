package com.ssafy.home.board.mapper;

import com.ssafy.home.board.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
	int writeArticle(BoardDto boardDto);
	List<BoardListDto> listArticle(BoardParameterDto boardParameterDto);
	int getTotalCount(BoardParameterDto boardParameterDto);
	BoardViewDto getArticle(int postId);
	void updateHit(int postId);
	int modifyArticle(BoardDto boardDto);
	void deleteMemo(int postId);
	void deleteArticle(int postId);
	Integer getArticleCount();
	Integer getWriterIdByBoardId(int id);
	int writeComment(CommentDto commentDto);

	BoardDto getArticleById(int postId);

	List<CommentDto> getComments(int postId);

	CommentDto getCommentById(int commentId);

	void softDeleteComment(int commentId);

	void modifyComment(CommentDto commentDto);

	PostHitDto getPostHit(Map<String, Integer> prams);

	void setPostHit(Map<String, Integer> postHitParams);
}