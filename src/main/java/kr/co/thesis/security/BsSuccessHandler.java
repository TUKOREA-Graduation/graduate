package kr.co.thesis.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class BsSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		
		//String username = request.getParameter("username");
		
		//Session에 저장해서 Controller에서 활용
		
		String url = "/";
		
	    /*
	    if(authentication.getAuthorities().toString().contains(Role.ADMIN.getValue())) {
			url = "";
		}
		*/
		
		response.sendRedirect(url);
		
		//redirectStrategy.sendRedirect(request, response, url);
		//super.onAuthenticationSuccess(request, response, authentication);
	}
}
