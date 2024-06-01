package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.PageVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageBlock = PageVo.getPageBlock();
		int perPage = PageVo.getPerPage();
		int totalBoard = new BoardDao().countBoard();
		
		int currentPage = Integer.parseInt(request.getParameter("p"));
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
		int maxPage = (totalBoard % perPage == 0) ? totalBoard / perPage : (totalBoard / perPage) + 1;
		
		int index = totalBoard - (currentPage - 1) * perPage;
		
		boolean previousTab = currentPage > 5;
		boolean nextTab = endPage < maxPage;
		
		PageVo pageVo = new PageVo();
		pageVo.setStartPage(startPage);
		pageVo.setStartPage(startPage);
		pageVo.setEndPage(endPage);
		pageVo.setMaxPage(maxPage);
		pageVo.setIndex(index);
		pageVo.setPreviousTab(previousTab);
		pageVo.setNextTab(nextTab);
		
		request.setAttribute("page", pageVo);
		
		request.setAttribute("list", new BoardDao().findAll(currentPage));
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
	}

}
