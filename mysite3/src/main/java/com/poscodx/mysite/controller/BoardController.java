package com.poscodx.mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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
	public String index(@RequestParam(value="p", required=true, defaultValue="1") Integer pageNo, Model model) {
		Map<String, Object> map = boardService.getContentsList(pageNo);
		model.addAllAttributes(map);
		return "board/index";
	}
	
	@Auth
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@AuthUser UserVo authUser, @ModelAttribute BoardVo boardVo, @RequestParam(value="p", required=true, defaultValue="1") Integer pageNo) {
		boardVo.setUserNo(authUser.getNo());
		boardVo.setUserName(authUser.getName());
		boardService.addContents(boardVo);
		
		return "redirect:/board?p=" + pageNo;
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("detail", boardVo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no, @RequestParam(value="p", required=true, defaultValue="1") Integer pageNo) {
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board?p=" + pageNo;
	}
	
	@Auth
	@RequestMapping("/modify/{no}")
	public String modify(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no, authUser.getNo()); 
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, BoardVo boardVo, @RequestParam(value="p", required=true, defaultValue="1") Integer pageNo) {
		boardVo.setUserNo(authUser.getNo());
		boardVo.setUserName(authUser.getName());
		boardService.modifyContents(boardVo);
		return "redirect:/board/view/" + boardVo.getNo() + "?p=" + pageNo;
	}
	
	@Auth
	@RequestMapping(value="/reply/{no}")
	public String reply(@PathVariable("no") Long no, @RequestParam(value="p", required=true, defaultValue="1") Integer pageNo, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply/p=" + pageNo;
	}

}
