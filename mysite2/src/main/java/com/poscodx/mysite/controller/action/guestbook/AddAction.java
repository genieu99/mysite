package com.poscodx.mysite.controller.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String contents = request.getParameter("contents");
		
		GuestbookVo guestbookVo = new GuestbookVo();
		guestbookVo.setName(name);
		guestbookVo.setPassword(password);
		guestbookVo.setContents(contents);
		
		new GuestbookDao().insert(guestbookVo);
		response.sendRedirect(request.getContextPath() + "/guestbook");
	}

}
