package kr.co.thesis.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.co.thesis.service.CardService;
import kr.co.thesis.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CardController {
	
	private final CardService cardService;
	private final MainService mainService;
	
	@PostMapping("/excel/card")
	public String excelCard(@RequestPart MultipartFile excelCard, 
							@RequestParam Map<String, Object> params,
							Authentication auth, Model model) throws Exception {
		
		cardService.excelCard(excelCard, auth, model);
		
		mainService.getDataList(params, auth, model);
		
		return "main";
	}
	
	@PostMapping("/delete/card")
	public String deleteCard(@RequestParam Map<String, Object> params,
							 Authentication auth, Model model) throws Exception {
		
		cardService.deleteCard(auth, model);
		
		mainService.getDataList(params, auth, model);
		
		return "main";
	}
}
