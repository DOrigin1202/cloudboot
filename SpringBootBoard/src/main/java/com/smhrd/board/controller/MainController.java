package com.smhrd.board.controller;

import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.repository.BoardRepository;
import com.smhrd.board.service.BoardService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final BoardRepository boardRepository;

    MainController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
	//게시판에 작성했던 글 보여주는 기능 구현.(아래 2줄) 그리고 ArrayList의 2줄
    //Autowired해서 BoardService boardService;해주고 
    //service연결해주세요. board service말입니다!
    @Autowired
    BoardService boardService;
    
    
    
	//순서1. 매핑
	@GetMapping("/")
	public String index(Model model) {
		
		//BoardService.java에서 코드 작성
		//board service -> repository 연결해서 데이터 가지고 와야겠지오?
		//board repository-> findAll() --> 새롭게 메소드 만들 필요가 없음.
		//ArrayList에 담은 후
		
		//model객체에 담아주세요->boardList로 이름지어서 가져오세요.
		
		ArrayList<BoardEntity> list = boardService.selectAll();
		model.addAttribute("boardList",list);
		return"index";
	}
	
	
	
	
	@GetMapping("/login")
	public String login() {
		return"login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/write")
	public String write() {
		return "write";
	}
	
	
	
}
