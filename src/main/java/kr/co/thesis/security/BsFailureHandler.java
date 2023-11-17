package kr.co.thesis.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class BsFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		if(exception instanceof BadCredentialsException || exception.getCause() instanceof BadCredentialsException) {
			
			request.setAttribute("errorMsg", "일치하지 않는 비밀번호");
			
		} else if(exception instanceof UsernameNotFoundException || exception.getCause() instanceof UsernameNotFoundException) {
			
			request.setAttribute("errorMsg", "존재하지 않는 계정");
			
		} else if(exception instanceof DisabledException || exception.getCause() instanceof DisabledException) {
			
			request.setAttribute("errorMsg", "승인 대기중인 계정");
			
		} 
		
		//else if(exception instanceof LockedException){ // LoginSuccessHandler에서 LockedException발생시 넘어 온 경우
            
		//	failureCode = "사용자 계정이 잠겨 있습니다.";
		//	request.setAttribute("isLocked", true);
        //}
		
		request.getRequestDispatcher("/loginForm").forward(request, response);
		
		//super.onAuthenticationFailure(request, response, exception);
		//super.setDefaultFailureUrl("/loginForm");
	}
}
