package kr.co.thesis.service;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String mail;
	
	public int sendEmail(Map<String, Object> params) {
		
		String userEmail = (String) params.get("userEmail");
		int authNo = generateAuthNo();
		
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        //message.setFrom(email); //from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
        message.setSubject("회원가입 인증번호 전송");
        message.setText("인증번호는 : " + authNo + " 입니다");
        
        mailSender.send(message);
        
        return authNo;
	}
	
	public int generateAuthNo() {
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());
        return generator.nextInt(1000000) % 1000000;
    }
}
