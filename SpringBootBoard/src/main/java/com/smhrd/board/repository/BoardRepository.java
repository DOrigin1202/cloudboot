package com.smhrd.board.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	// Controller에서 Repository로 바로 가는게 아니라
	// Service를 경유해서 가는 것이 중요한 포인트!
	
	
	
	//게시글이 역순으로 출력될 수 있는 기능을 하나 만들어 봅시다!
	// select * from board_entity order by write_day desc;
	
	
	//주의점 BoardRepository에선 커스텀 작성시 "언더바_" 는 무조건 "카멜기법"으로 작성해야함.
	ArrayList<BoardEntity> findAllByOrderByWriteDayDesc();//ArrayList를 사용하는 이유..
	
	//like문 사용해서 title을 가지고 오는 메소드
	//containing --> like문을 실행해줄 것임.(매개변수 안의 값을 바탕으로)
	List<BoardEntity> findByTitleContaining(String keyword);
	
	//내가 원하는 쿼리문 바로 실행 -> @Query -> 메소드 명은 자유롭게 지어도 됨.
	//like 문 사용하여 content 가지고 오는 메소드
	// : 은 변수를 사용하겠다는 의미.
	@Query("SELECT b FROM BoardEntity b where b.content like %:keyword%")
	List<BoardEntity> searchContent(@Param("keyword") String keyword);
	
	
	
	
	
	
	
	
}
