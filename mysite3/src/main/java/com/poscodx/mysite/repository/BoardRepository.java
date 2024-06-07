package com.poscodx.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Repository
public class BoardRepository {
	
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}
	
	public void replyUpdate(BoardVo vo) {		
		Long groupNo = vo.getGroupNo();
		Long orderNo = vo.getOrderNo() + 1;
		
		sqlSession.update("board.replyUpdate", Map.of("groupNo", groupNo, "orderNo", orderNo));
	}
	
	public void replyInsert(BoardVo vo) {
//		Long groupNo = vo.getGroupNo();
		Long orderNo = vo.getOrderNo() + 1;
		int depth = vo.getDepth() + 1;

		vo.setOrderNo(orderNo);
		vo.setDepth(depth);
		
		sqlSession.insert("board.replyInsert", vo);
	}
	
	public int countBoard() {
		return sqlSession.selectOne("board.countBoard");
	}
	
	public List<BoardVo> findAll(int currentPage) {
		int perPage = PageVo.getPerPage();
		int startIndex = (currentPage - 1) * perPage;
		
		return sqlSession.selectList("board.findAll", Map.of("startIndex", startIndex, "perPage", perPage));
	}

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}
	
	public BoardVo findByNoAndUserNo(Long boardNo, Long userNo) {
		return sqlSession.selectOne("board.findByNoAndUserNo", Map.of("boardNo", boardNo, "userNo", userNo));
	}

	public void deleteByNo(Long no) {
		sqlSession.delete("board.deleteByNo", no);
	}

	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}

	public void updateHitByNo(BoardVo vo) {
		int viewCount = vo.getHit() + 1;
		
		sqlSession.update("board.updateHitByNo", Map.of("viewCount", viewCount, "no", vo.getNo()));
	}
}
