package com.poscodx.mysite.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	public void addContents(BoardVo vo) {
		if (vo.getGroupNo() != null) {
			boardRepository.updateOrderNo();
		}
		
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
		BoardVo vo = boardRepository.findByNo(no);
		if (vo != null) {
			boardRepository.updateHit();
		}
		return vo;
	}
	
	// 로그인 사용자들을 위해
	public BoardVo getContents(Long boardNo, Long userNo) {
		
	}
	
	public void updateContents(BoardVo vo) {
		
	}
	
	public void deleteContents(Long boardNo, Long userNo) {
		
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		List<BoardVo> list = null;
		Map<String, Object> map = null;
		
		map.put("list", list);
		map.put("page", page);
		
		return map;
	}
}
