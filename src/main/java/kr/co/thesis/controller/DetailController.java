package kr.co.thesis.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.thesis.service.DetailService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DetailController {
	
	private final DetailService detailService;
	
	@GetMapping("/detail/{dates}")
	public String getDetailDates(@PathVariable("dates") String dates,
								 @RequestParam Map<String, Object> params,
								 Authentication auth, Model model) {
		
		detailService.getDetailDates(dates, params, auth, model);
		
		return "detail";
	}
}
