package com.smhrd.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Configuration
public class FileUploadConfig {
// 환경설정을 해주어야함 @Configuration을하면 
	// upload될 경로를 설정해주어야 함
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	
	public String getUploadDir() {
		return uploadDir;
	}
	
	
	
	
	
	
	
	
	
	
	
}
