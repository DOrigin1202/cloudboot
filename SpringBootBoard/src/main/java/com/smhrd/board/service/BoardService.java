package com.smhrd.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.repository.BoardRepository;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;
	//게시글 작성 기능
	public BoardEntity write(BoardEntity entity) {
		return boardRepository.save(entity); // insert는 save()로 작성한다는 것을 기억하자 
	}
	
	 // 게시글 보이는기능전부 가지고 오기. ArrayList<>를 사용함.-> selectAll()로 모두 가져올거라서
	// select * from table 이 findAll()로 해석됨.
	 public ArrayList<BoardEntity> selectAll() {
		 //게시글이 오래된 순서대로 출력해야함. <=> 게시글이 최신순으로 출력되게 만들고 싶어요.
		 //sql문으로 풀이 order by writeday desc
	        return (ArrayList<BoardEntity>) boardRepository.findAllByOrderByWriteDayDesc();  // 전체 글 가져오기
	    }//return boardRepository.findAll() 하고 다운캐스팅 해주어야함..
	
	
	 //게시글 상세보기 기능
	 public Optional<BoardEntity> detailPage(Long id) {
		 // repository 연결 후 코드 실행
		 // 내장 되어 있는 메소드 사용 할때는 Repository 코드를 수정하지 않는다.
		 // Controller -> Service로 연결했으며 이후 -> Repository를 연결하는 과정이라고 생각하면 좋다.
		 
		 // sql생각해보면... select * from table명 where id = ? ;
		 
		 // Optional<객체> = 
		 // findById의 return타입이 Optional<BoardEntity>임 이 말의 뜻은, null을 체크한다. 라는 뜻.
		 // 해당 BoardEntity가 null일 수도 있고 아닐 수도 있고,, 이걸 체크해 주는 건데,, 
		 // 일단 npe에러(null pointer exception)의 뜻을 알아보자면 null이 있으면 에러가 남. 그래서 이걸 막아주기 위해서 Optional을 사용해서
		 // null을 체크한다는 뜻.
		 return boardRepository.findById(id);
		 			
	 }
	
	 public void deleteBoard(Long id) {
		 boardRepository.deleteById(id);
	 }
	 
	//검색 기능 구현하기 위해서 BoardRestController작성하다가 옴
	 public  List<BoardEntity> searchResult(String type, String keyword) {
		
		List<BoardEntity> list = null;
		 //조건 처리
		switch (type) {
		case "title": 
			list = boardRepository.findByTitleContaining(keyword);
			break;
		
		case "content":
			list = boardRepository.searchContent(keyword);
			break;
			
		case "writer":
			break;
			
		default:
			break;
		
		}
		return list;
	
	
	 }
}
