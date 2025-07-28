package com.smhrd.board.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEntity {
	@Id// PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increase 자동으로 숫자가 하나씩 증가하는 기능
	private Long id;
	
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String writer;
	@Column(nullable = false, columnDefinition = "TEXT") // DB컬럼은 텍스트로 바꾼다는 코드
	private String content;
	
	private String imgPath;// 이미지 저장 DB에 이미지 저장이 아니라, 서버에 이미지 저장하고 "서버 경로"를 DB에 저장하는 시스템
	
	//현재 날짜( 글 작성 시간일시 )
	@Column(nullable = false, updatable = false)// update를 불가능하게 할 계획.
	private LocalDate writeDay;
	
	//JPA 글 작성 시 자동으로 오늘 날짜 입력 할 수 있게 하는 코드가 있다.
	@PrePersist // Entity가 처음 저장되기 직전에 실행되는 자동 메서드 표시용 어노테이션이야.
	protected void onWriteDay() {
		this.writeDay = LocalDate.now();
		
	}
}
