package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Access Control
		if (session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String spageNo = request.getParameter("p");
		Long pageNo = Long.parseLong(spageNo);

		String sno = request.getParameter("no");
		Long no = Long.parseLong(sno);
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		boardVo.setTitle(title);
		boardVo.setContents(contents);
		
		new BoardDao().update(boardVo);
		response.sendRedirect(request.getContextPath() + "/board?a=list&p=" + pageNo);
	}

}
