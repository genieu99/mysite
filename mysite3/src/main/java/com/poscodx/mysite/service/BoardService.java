package com.poscodx.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Service
public class BoardService {
	
	private BoardRepository boardRepository;
	private PageService pageService;
	
	public BoardService(BoardRepository boardRepository, PageService pageService) {
		this.boardRepository = boardRepository;
		this.pageService = pageService;
	}
	
	@Transactional
	public void addContents(BoardVo vo) {
		if (vo.getGroupNo() != null) {
			boardRepository.replyUpdate(vo);
			System.out.println(vo.getOrderNo());
		}
		
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
		BoardVo vo = boardRepository.findByNo(no);
		if (vo != null) {
			boardRepository.updateHitByNo(vo);
		}
		return vo;
	}
	
	public BoardVo getContents(Long boardNo, Long userNo) {
		return boardRepository.findByNoAndUserNo(boardNo, userNo);
	}
	
	public void modifyContents(BoardVo vo) {
		boardRepository.modify(vo);
	}
	
	public void deleteContents(Long boardNo, Long userNo) {
		boardRepository.delete(boardNo, userNo);
	}
	
	public Map<String, Object> getContentsList(int currentPage) {
		List<BoardVo> list = boardRepository.findAll(currentPage);
		PageVo page = pageService.calculatePage(currentPage);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("list", list);
		map.put("page", page);
		
		return map;
	}
}
