package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping("")
	public String index(@RequestParam(value="p", required=true, defaultValue="1") int pageNo, Model model) {
		Map<String, Object> map = boardService.getContentsList(pageNo);
		model.addAllAttributes(map);
		return "board/index";
	}
	
	@Auth
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add() {
		return "board/write";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(HttpSession session, @ModelAttribute BoardVo boardVo, @RequestParam(value="p", required=true, defaultValue="1") Integer page) {
		// access control
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		
		boardVo.setUserNo(authUser.getNo());
		boardVo.setUserName(authUser.getName());
		boardService.addContents(boardVo);
		
		return "redirect:/board?p=" + page;
	}
	

	@RequestMapping("/veiw/{no}")
	public String view(@RequestParam(value="p", required=true, defaultValue="1") int pageNo, @PathVariable("no") Long no) {
		boardService.getContents(no);
		return "board/view";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long no) {
		// access control
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		
		boardService.deleteContents(no, authUser.getNo());
		return "board/index";
	}
	
	@RequestMapping("/update/{no}")
	public String update(HttpSession session, @PathVariable("no") Long no) {
		// access control
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		
		boardService.getContents(no, authUser.getNo());
		return "board/index";
	}
	
	@RequestMapping(value="/reply/{no}")
	public String reply(HttpSession session,@PathVariable("no") Long no, @RequestParam(value="p", required=true, defaultValue="1") Integer page, Model model) {
		// access control
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		
		BoardVo boardVo = boardService.getContents(no);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply/p=" + page;
	}

}
