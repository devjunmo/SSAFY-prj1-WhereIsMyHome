package com.ssafy.home.board.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.home.board.dto.*;
import com.ssafy.home.board.mapper.BoardMapper;
import com.ssafy.home.etc.annotation.Auth;
import com.ssafy.home.etc.exception.BaseException;
import com.ssafy.home.etc.util.JwtUtil;
import com.ssafy.home.user.dto.UserDto;
import com.ssafy.home.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final UserMapper userMapper;
	private final JwtUtil jwtUtil;

	@Auth
	@Override
	public boolean writeArticle(BoardDto boardDto) {

		// 작성자 id는 서버에서 accessToken을 파싱해서 넣어준다.
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.substring(7);
		DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
		int userId = Integer.parseInt(payload.getAudience().get(0));
		UserDto registeredUser = userMapper.getUserById(userId);

		// 등록된 회원이 아니면 글 쓰기 권한 없음!
		if (registeredUser == null) {
			throw new BaseException("접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
		}
		boardDto.setUserId(userId);

		if(boardDto.getTitle() == null || boardDto.getContent() == null) {
			throw new BaseException("제목 또는 내용을 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		return boardMapper.writeArticle(boardDto) == 1;
	}

	@Override
	public Integer getArticleCount() {
		return boardMapper.getArticleCount();
	}

	@Override
	public List<BoardListDto> listArticle(BoardParameterDto boardParameterDto) {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		List<BoardListDto> boardDtoList = boardMapper.listArticle(boardParameterDto);
		if (boardDtoList == null) {
			throw new BaseException("등록된 게시글이 없습니다.", HttpStatus.BAD_REQUEST);
		}
		return boardMapper.listArticle(boardParameterDto);
	}


	@Override
	public BoardViewDto getArticle(int postId) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.substring(7);
		DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken); // 검증없이 토큰 디코딩
		int userId = Integer.parseInt(payload.getAudience().get(0));

		UserDto registeredUser = userMapper.getUserById(userId);

		// 등록된 회원이 아니면 글 상세보기 권한 없음!
		if (registeredUser == null) {
			throw new BaseException("접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
		}

		if (postId < 0) {
			throw new BaseException("유효하지 않은 게시글 번호 입니다.", HttpStatus.BAD_REQUEST);
		}
		BoardDto dbPost = boardMapper.getArticleById(postId);
		if (dbPost == null) {
			throw new BaseException("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		Map<String, Integer> postHitParams = new HashMap<>();
		postHitParams.put("userId", userId);
		postHitParams.put("postId", postId);
		PostHitDto postHitDto = boardMapper.getPostHit(postHitParams);
		if (postHitDto == null) {
			updateHit(postId); // 조회 기록이 없을때만 조회수 증가
			boardMapper.setPostHit(postHitParams);
		}
		return boardMapper.getArticle(postId);
	}

	@Override
	public void updateHit(int postId) {
		boardMapper.updateHit(postId);
	}

	@Override
	@Transactional
	public void modifyArticle(BoardDto boardDto) {

		// 토큰에서 userId 가져오기
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.substring(7);
		DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
		int userId = Integer.parseInt(payload.getAudience().get(0));

		// 게시글의 id로 조회한 userId 가져오기
		Integer writerId = boardMapper.getWriterIdByBoardId(boardDto.getId());

		if (writerId == null) {
			throw new BaseException("작성자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
		}

		if (userId != writerId) {
			throw new BaseException("타인의 글은 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
			// BAD_REQUEST 한 이유: 글 소유자가 아닐때는 글 수정 버튼이 안보임. 근데 여기까지 왔다?? 이미 해킹시도임.
			// 따라서 UNAUTHORIZED로 해커에게 힌트를 주지 않기 위함
		}

		if (boardMapper.modifyArticle(boardDto) != 1) {
			throw new BaseException("글 수정 오류", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional
	public void deleteArticle(int id) {
		// 작성자 id는 서버에서 accessToken을 파싱
		HttpServletRequest request
				= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.substring(7);
		DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);

		int userId = Integer.parseInt(payload.getAudience().get(0));

		BoardDto dbPostDto = boardMapper.getArticleById(id);

		if (dbPostDto == null) {
			throw new BaseException("삭제 대상이 없습니다.", HttpStatus.BAD_REQUEST);
		}

		if (userId != dbPostDto.getUserId()) {
			// BAD_REQUEST 한 이유: 글 소유자가 아닐때는 글 삭제 버튼이 안보임. 근데 여기까지 왔다?? 이미 해킹시도임.
			// 따라서 UNAUTHORIZED로 해커에게 힌트를 주지 않기 위함
			throw new BaseException("타인의 글은 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
		}

		boardMapper.deleteArticle(id);
	}

	@Override
	public void writeComment(CommentDto commentDto) {
		// 작성자 id는 서버에서 accessToken을 파싱해서 넣어준다.
		HttpServletRequest request
				= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.substring(7);
		DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
		int userId = Integer.parseInt(payload.getAudience().get(0));
		commentDto.setUserId(userId);

		// content가 비어있는 에러처리는 setter에서 진행

		// select * 한 이유는 추후 soft delete등 확장시 로직이 추가될 수 있어서 그렇게 함
		BoardDto dbPost = boardMapper.getArticleById(commentDto.getPostId());

		// 댓글 쓸 post가 실제로 존재 하는가?
		if (dbPost == null) {
			throw new BaseException("댓글을 쓸 글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		// 게시판 soft delete 지정시 soft delete된 게시글에 쓰려는 시도인가?

		boardMapper.writeComment(commentDto);
	}

	@Override
	public List<CommentDto> getComments(int postId) {
		if (postId < 0) {
			throw new BaseException("유효하지 않은 게시글 번호입니다.", HttpStatus.BAD_REQUEST);
		}
		BoardDto dbPost = boardMapper.getArticleById(postId);

		// 게시글이 실제로 존재 하는가?
		if (dbPost == null) {
			throw new BaseException("글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		return boardMapper.getComments(postId);
	}

	@Override
	public void deleteComment(int commentId) {
		// 작성자 id는 서버에서 accessToken을 파싱
		HttpServletRequest request
				= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.substring(7);
		DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
		int userId = Integer.parseInt(payload.getAudience().get(0));

		if (commentId < 0) {
			throw new BaseException("유효하지 않은 댓글 번호입니다.", HttpStatus.BAD_REQUEST);
		}

		CommentDto dbCommentDto = boardMapper.getCommentById(commentId);

		// 이미 삭제되었는지 체크
		if (dbCommentDto.getIsDeleted()) {
			throw new BaseException("이미 삭제된 댓글 입니다.", HttpStatus.BAD_REQUEST);
		}

		// 해당 댓글의 userId와 현재 로그인 한 userId가 같은지 체크
		if (dbCommentDto.getUserId() != userId) {
			throw new BaseException("삭제 권한이 없습니다.", HttpStatus.BAD_REQUEST);
		}

		// soft delete 처리
		boardMapper.softDeleteComment(commentId);
	}

	@Override
	public void modifyComment(CommentDto commentDto) {
		// 토큰에서 userId 가져오기
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = request.getHeader("Authorization");
		accessToken = accessToken.substring(7);
		DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
		int userId = Integer.parseInt(payload.getAudience().get(0));

		CommentDto dbCommentDto = boardMapper.getCommentById(commentDto.getId());

		// 댓글번호는 dto setter에서 체크

		if (dbCommentDto == null) {
			throw new BaseException("수정할 댓글이 없습니다.", HttpStatus.BAD_REQUEST);
		}

		// 등록된 댓글 userId와 수정 요청한 사용자의 userId가 같은지 체크
		if (userId != dbCommentDto.getUserId()) {
			throw new BaseException("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST);
		}

		// 이미 삭제되었는지 체크
		if (dbCommentDto.getIsDeleted()) {
			throw new BaseException("이미 삭제된 댓글 입니다.", HttpStatus.BAD_REQUEST);
		}
		boardMapper.modifyComment(commentDto);
	}
}
