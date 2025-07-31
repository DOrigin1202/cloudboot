package com.smhrd.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.board.config.FileUploadConfig;
import com.smhrd.board.config.WebConfig;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

//controller의 default mapping 주소 설정
@Controller
@RequestMapping("/board")
public class BoardController {

    private final WebConfig webConfig;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	FileUploadConfig fileUploadConfig;

    BoardController(WebConfig webConfig) {
        this.webConfig = webConfig;
    }
	
	//게시글 작성 기능
	@PostMapping("/write")
	public String write(@RequestParam String title, @RequestParam String content, HttpSession session, @RequestParam MultipartFile image) {
		// 1. 필요한거 뭐가 있을까? 
		// title, writer,content,imgPath 그리고 session 필요할거같아.. writeday는 자동으로 들어가게할거라서 필요없음.
		// input 태그에서는 File로 넘어 오는 중( 정확히는 file을 받아주어야 합니다! )
		// image처리 해보자 이미지가 없을땐 null처리하고 있을때를 주로 처리할 것임
		String imgPath = "";
		//이미지를 저장 할 경로 -C:upload/
		String uploadDir = fileUploadConfig.getUploadDir();
		if(!image.isEmpty()) {//이미지가 들어왔다면.
			//1. 파일의 이름 설정해줄 것임
			String fileName = UUID.randomUUID()+"_"+image.getOriginalFilename(); //UUID고유 식별자.( 중복을 막을려고 )
			//2. 파일이 저장될 이름과 경로를 설정해줄것
			String filePath = Paths.get(uploadDir, fileName).toString();
			//3. 서버에 저장 및 경로를 설정할 것
			try {
				image.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//4. 마지막으로 DB에는 경로가 저장된다고 했죠? DB에 저장될 경로 문자열로 설정
			imgPath = "/home/git/uploads/"+fileName;
			
		
		}
		
	
		BoardEntity board = new BoardEntity();
		//원하는 기능 세팅한것 뿐
		board.setTitle(title);
		board.setContent(content);
		
		//writer => downcasting했음 add머시기 눌러서 ...
		UserEntity user = (UserEntity) session.getAttribute("user");
		board.setWriter(user.getId());
		board.setImgPath(imgPath);
		boardService.write(board);
		// 2. DB연결 -> repository, service, 
		return"redirect:/";
		
	
	}
	//게시글 상세 페이지 이동
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {//URL에 담긴 값 가지고 오는 방법 @PathVariable
		System.out.println(id);
		//1. 필요한 것 
		// -> id, 다음페이지로 값을 보내기 위한 Model객체 필요함. 
		//2. 서비스 연결
		Optional<BoardEntity> detail = boardService.detailPage(id);
		// detail의 타입은 Optional타입임 그러므로 우리가 원하는 것은 BoardEntity타입임.
		// .get()을 활용한다면 안에있는 객체 값을가져올 수 있음.
		model.addAttribute("detail",detail.get());
		
		return "detail";
	}
	
	//edit으로 이동할 수 있도록 즉, 게시판 수정 기능이 있는 edit.html로 이동할 수 있도록 해보자.
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id,Model model) {
		//1 필요한거
		
		//2.서비스 연결
		Optional<BoardEntity> board = boardService.detailPage(id);
		model.addAttribute("board",board.get());
		return"edit";
	}
	
	//게시판 수정 기능을 만들어보자.
	
	@PostMapping("/update")
	public String update(@RequestParam String title, @RequestParam String content, @RequestParam MultipartFile image, @RequestParam String oldImgPath, @RequestParam Long id) {
		
		//1. title, content, imgpath, id, oldImgPath
		// 기존 이미지 처리. 이미지가 새롭게 업데이터 되었을 때 수정해주어야 한다.
		//db접근 해서 게시글의 정보를 다시 가지고 오겠습니다.
		BoardEntity entity = boardService.detailPage(id).get();    //.get()을 해서 entity가 담김
		
		
		
		
		
		String uploadDir = fileUploadConfig.getUploadDir();
		
		if(!image.isEmpty()) {// <= 이미지가 새롭게 업로드를 했다.
			//기존에 있던 이미지를 삭제해보자.
			if(oldImgPath != null && !oldImgPath.isEmpty()) {
				Path oldFilePath = Paths.get(uploadDir, oldImgPath.replace("/uploads/",""));
				try {
					Files.deleteIfExists(oldFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//새로운 이미지 저장
			String newFileName = UUID.randomUUID()+"_"+image.getOriginalFilename();
			Path newFilePath = Paths.get(uploadDir, newFileName);
			
			try {//사람으로 치면 긴장중!
				image.transferTo(newFilePath);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
			
			entity.setImgPath("/uploads/"+newFileName); // entity를 detail페이지에서 다~ 가지고 왔어 즉, 데이터베이스의 모든 정보가 entity에 담겨있다는 말이야.
			// 그런데 우린 이미지가 수정되었을 때만 바꾸기 위해서 if문일때 imgpath를 수정하는 .setImgPath를 해주었어
		}
		
		//작성자? 
		// BoardEntity entity = new BoardEntity();
		entity.setTitle(title);
		entity.setContent(content);
		
		//update문 실행 -- service에 연결
		
		/* JPA에서 save했을 때 insert문이 아닌 update문이 실행되는 조건
		 * findById() 해준 후 save 코드를 실행하면 JPA가 save를 update라고 인식함. 신기하쥬? 
		 *  
		 *  대규모로 update해야할때 복잡한 update를 해야한다면 @Query("sql문으로 query문 실행")
		 * 
		 * 
		 * */
		boardService.write(entity);
		
		
		
		
		
		return"redirect:/board/detail/"+id;
	}
	

}
