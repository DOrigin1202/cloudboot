package com.smhrd.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")//기존의 테이블을 사용하고 싶을때 그리고 user는 DB의 이름이야ㅏ.
@Data
public class UserEntity {
	//pk값이 필수
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idx;
	
	@Column(nullable = false, unique = true)
	private String id;
	private String pw;
	private String name;
	private int age;
}
