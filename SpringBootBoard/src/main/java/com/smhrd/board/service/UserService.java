package com.smhrd.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.repository.UserRepository;

@Service
public class UserService {
/*
 * 1. Spring Controller - Service - Repository(Mapper) 이렇게 3계층 구조로 구성되어 있음.
 * =================================================================================
 * Controller : 클라이언트의 요청을 처리하고 사용자 인터페이스 (UI)와 직접적으로 상호 작용하는 공간.
 * Service : 비지니스 로직, 여러개의 Repository와 조합할 수 있음.
 * Repository : 데이터 베이스와 직접 통신. 
 * =================================================================================
 * Service객체를 두는 이유 
 * 1. Controller가 Repository와 직접 연결하게 되면, 가독성이 떨어짐.
 * 2. Controller역할 자체가 비슷한 코드가 중복될 가능성이 높음.
 * 3. 직접연결했을때 변경이 일어난다면 다수의 수정이 요구됨...
 * -> 유지보수 향상을 위해 생성.
 * 4. 
 * 
 * */
	
	// 서비스객체 사용법
	// 1. 서비스 어노테이션 사용.
	// 2. DB연결 - repository interface 객체 생성이 안되게 때문에 autowired해줘야함
	@Autowired
	UserRepository userRepository;
	
	// 3. 기능 구현을 위한 메서드 생성해보자.
	// 회원가입 기능.
	public String register(UserEntity entity) {
		//entity
		UserEntity e = userRepository.save(entity);
		if(e != null) {
			//회원가입 성공
			return "success";
		}else {
			//실패
			return "fail";
		}
	}
	
	//아이디 중복 체크 기능
	public boolean check(String id) {
		return userRepository.existsById(id);
	}
	
	
	//로그인 기능 이 부분을 Service부분을 모르겠어.. 특히 UserEntity를 작성하는 이유를
	public UserEntity login(String id, String pw) {
		
		return userRepository.findAllByIdAndPw(id,pw);
		
	}
	
	//게시글? ? ?
	
	
	
	
	
}
