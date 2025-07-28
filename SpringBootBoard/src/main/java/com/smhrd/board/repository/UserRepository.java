package com.smhrd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.smhrd.board.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	//1. 순서로는 일단 @을 
	// 가장중요한 커스텀 메서드(메서드를 커스텀해서 가져올 수 있음 id, pw만을 가져온다거나.)
	// 그런데 규칙이 있었음 카멜기법으로 작성해야했음. 그리고 findBy를 붙어야함.
	// ex) findByEmailAndPw(); 요렇게
	
	//2.JpaRepository 인터페이스 extends상속받기
	// T는 entity ID는 entity의 pk값의 자료형
	
	//
	//exists()메서드  데이터의 존재 여부 판단. -> 있으면 True 없으면 False
	//커스텀 이용 카멜작성법기억해야함!! existsBy컬럼명
	
	boolean existsById(String id);
	
	//로그인 기능을 하기 위해
	UserEntity findAllByIdAndPw(String id, String pw);
	
	
	
}
