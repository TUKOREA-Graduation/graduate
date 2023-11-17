package kr.co.thesis.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.thesis.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService mainService;
	
	@GetMapping("/")
	public String getMain(@RequestParam Map<String, Object> params,
						  Authentication auth, Model model) {
		
		mainService.getDataList(params, auth, model);
		
		return "main";
	}
	
	@GetMapping("/loginForm")
	public String getLoginForm() {
		
		return "loginForm";
	}
	
	@PostMapping("/loginForm")
	public String postLoginForm() {
		
		return "loginForm";
	}
	
	@GetMapping("/signUpForm")
	public String getSignUpForm() {
		
		return "signUpForm";
	}
	
	@PostMapping("/signUp")
	@ResponseBody
	public String signUp(@RequestParam Map<String, Object> params) {
		
		return mainService.signUp(params);
	}
}
