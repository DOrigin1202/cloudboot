package com.smhrd.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.board.service.UserService;

@RestController
public class UserRestController {
	// RestController란?
	// 비동기 통신 때무네 UserRestController을 생성했음
	// Controller역할을 하지만 HTML이 아닌 데이터Json을 보내주는 컨트롤러
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user/check-id")
	public HashMap<String, Boolean> checkId(@RequestParam String id) {
		//1. 필요한것 id
		//2. DB연결이 필요. -> Service가 함.
		boolean exist = userService.check(id);
		
		// Map 구조로 만들어서 Json화 시켜서 데이터를 전송하자.
		// True False값으로 보넬수 없기 때무님
		// Map == python의 dictionary임. =>key, value를 의미함.
		// <key의 자료형, value의 자료형> 을 적어주어야함.
		HashMap<String, Boolean> res = new HashMap<>();
		// map에 데이터를 넣는법,,
		// arraylist --> .add()
		// map -> put
		res.put("exist", exist);
		return res;
		
	}

}
