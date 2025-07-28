package com.smhrd.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.repository.UserRepository;
import com.smhrd.board.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	//2.순서.
	@Autowired
	private UserService userService;
	
	//3.로그인 기능
	
	@PostMapping("login.do")
	public String login(@RequestParam String id,@RequestParam String pw,HttpSession session) {
		//1. 필요한거 뭐가 있을까 아이디랑 비밀번호?
		//2. 데이터 베이스를 연결해줘야해->service->repository연결->repository적절한 메소드 생성(사용)
		//3. findByIdAndPw해서 메소드 만들어 주어야함.
		//4. 로그인이 성공하면 인덱스로 넘어가기
		//5. 로그인의 정보를 저장도 해야함.
		UserEntity user = userService.login(id, pw);
		
		if(user != null) {//로그인 성공
			session.setAttribute("user", user);
			return "redirect:/";
		}else {//로그인이 안될때 login으로 다시 돌아가도록 설계
			return"redirect:/login";
		}
		
	}
	//로그아웃 기능
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	//1.순서 회원가입 기능
	@PostMapping("/register.do")
	public String register(
			@RequestParam String id,@RequestParam String pw,
			@RequestParam String name,@RequestParam int age) {
		//1. 필요한게 무엇인지 생각해보자 => id, pw, age, name이 필요할 것 같네
		//2. DB연결 --> Repository생성하고 Entity도 생성했음.
		// 회원가입은 Insert겠지? 
		//3. 중간다리인 Service연결해야겠다. 
		
		UserEntity entity = new UserEntity();
		
		entity.setId(id);
		entity.setPw(pw);
		entity.setName(name);
		entity.setAge(age);
		
		//회원가입 실패시에도 고려해야함. 회원가입 실패했을때 다시 돌아갈수 있는 경로 설정.
		String result = userService.register(entity);
		
		if(result.equals("success")) {
		
			return "redirect:/login";
		}else {
			return "redirect:/register";
		}
	}
}