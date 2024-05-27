package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.controller.action.main.MainAction;
import com.poscodx.mysite.controller.action.user.JoinAction;
import com.poscodx.mysite.controller.action.user.JoinFormAction;
import com.poscodx.mysite.controller.action.user.JoinSuccess;
import com.poscodx.mysite.controller.action.user.LoginAction;
import com.poscodx.mysite.controller.action.user.LoginFormAction;

public class UserServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"joinform", new JoinFormAction(),
			"join", new JoinAction(),
			"joinsuccess", new JoinSuccess(),
			"loginform", new LoginFormAction(),
			"login", new LoginAction()
	); 

	@Override
	protected Action getAction(String actionName) {
		Action action = mapAction.get(actionName);
		if (action == null) {
			action = new MainAction();
		}
		
		return action;
	}
}
