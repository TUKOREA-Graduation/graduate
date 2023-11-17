package kr.co.thesis.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.co.thesis.service.AccountService;
import kr.co.thesis.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {
	
	private final AccountService accountService;
	private final MainService mainService;
	
	@PostMapping("/excel/account")
	public String excelAccount(@RequestPart MultipartFile excelAccount,
							   @RequestParam Map<String, Object> params,
							   Authentication auth, Model model) throws Exception {
		
		accountService.excelAccount(excelAccount, auth, model);
		
		mainService.getDataList(params, auth, model);
		
		return "main";
	}
	
	@PostMapping("/delete/account")
	public String deleteAccount(@RequestParam Map<String, Object> params,
							    Authentication auth, Model model) throws Exception {
		
		accountService.deleteAccount(auth, model);
		
		mainService.getDataList(params, auth, model);
		
		return "main";
	}
}
