package com.poscodx.mysite.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscodx.mysite.dto.JsonResult;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/checkemail")
	public JsonResult checkEmail(@RequestParam(value="email", required=true, defaultValue="") String email) {
		UserVo vo = userService.getUser(email);
		JsonResult jsonResult = new JsonResult();
		
		jsonResult.setResult("");
		
		return jsonResult;
	}
}
