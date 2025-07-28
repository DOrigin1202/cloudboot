package com.smhrd.board.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.board.config.FileUploadConfig;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.service.BoardService;

@RestController
public class BoardRestController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	FileUploadConfig fileUploadConfig;
	
	@DeleteMapping("/delete/{id}")
	public void deleteBoard(@PathVariable Long id) {
		//1. 필요한건... ! id
		
		// 이미지 삭제
		// id를 바탕으로 select를 
		// Optional<BoardEntity> board = boardService.detailPage(id);
		// BoardEntity board = boardService.detailPage(id).get();//우리가 원하는 것은 BoardEntity이지 optional타입이 아님.
		String imgPath = boardService.detailPage(id).get().getImgPath();//이렇게 . 을 붙여서 이어서 작성할 수 있음.
		String uploadDir = fileUploadConfig.getUploadDir();
		if(imgPath != null && !imgPath.isEmpty()) {
			//경로 접근 get(경로, 파일이름)
			//imgPath ->> uploads/파일명.....->>실제 파일명은 uploads/를 제외한 나머지문자열들.
			Path filePath = Paths.get(uploadDir, imgPath.replace("/uploads/", ""));
			try {
				Files.deleteIfExists(filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//2. service 연결
		// DB에 삭제는 완료가 될 것임. 그러나 서버의 이미지는 삭제가 되지 않음.
		boardService.deleteBoard(id);
		
		//3. 서버이미지를 삭제하는 중간단계를 구성..
		
	}
	
	//검색기능
	@GetMapping("/search")
	public List<BoardEntity> search(@RequestParam String type, @RequestParam String keyword) {
		//1. 필요한거 type이랑 keyword
		//2. service랑 연결
		return boardService.searchResult(type, keyword);
	}
	
	
	
	
	
	
}
