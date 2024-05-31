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

public class ReplyAction implements Action {

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
		
//		String sno = request.getParameter("no");
//		Long no = Long.parseLong(sno);
//		BoardVo originVo = new BoardDao().findByNo(no);
//		
//		String title = request.getParameter("title");
//		String contents = request.getParameter("contents");
//		
//		BoardVo boardVo = new BoardVo();
//		boardVo.setTitle(title);
//		boardVo.setContents(contents);
//		boardVo.setUserNo(authUser.getNo());
//		boardVo.setGroupNo(originVo.getGroupNo());
//		boardVo.setOrderNo(originVo.getOrderNo());
//		boardVo.setDepth(originVo.getDepth());
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long groupNo = Long.parseLong(request.getParameter("groupNo"));
		Long orderNo = Long.parseLong(request.getParameter("orderNo"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContents(contents);
		boardVo.setUserNo(authUser.getNo());
		boardVo.setGroupNo(groupNo);
		boardVo.setOrderNo(orderNo);
		boardVo.setDepth(depth);
		
		new BoardDao().reply(boardVo);
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
