package com.poscodx.mysite.service;

import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.PageVo;

@Service
public class PageService {
	
	private BoardRepository boardRepository;
	
	public PageService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public PageVo calculatePage(int currentPage) {
		int pageBlock = PageVo.getPageBlock();
		int perPage = PageVo.getPerPage();
		int totalBoard = boardRepository.countBoard();
		
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
		int maxPage = (totalBoard % perPage == 0) ? totalBoard / perPage : (totalBoard / perPage) + 1;
		
		int index = totalBoard - (currentPage - 1) * perPage;
		
		boolean previousTab = currentPage > 5;
		boolean nextTab = endPage < maxPage;
		
		PageVo pageVo = new PageVo();
		pageVo.setCurrentPage(currentPage);
		pageVo.setStartPage(startPage);
		pageVo.setStartPage(startPage);
		pageVo.setEndPage(endPage);
		pageVo.setMaxPage(maxPage);
		pageVo.setIndex(index);
		pageVo.setPreviousTab(previousTab);
		pageVo.setNextTab(nextTab);
		
		return pageVo;
	}
	
	
}
