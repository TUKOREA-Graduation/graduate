package kr.co.thesis.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BsPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		boolean result = false;
		
		try {
			result = BCrypt.checkpw(rawPassword.toString(), encodedPassword);
			//result = rawPassword.toString().equals(encodedPassword) ? true : false;
		} catch (Exception e) {
			
		}
		
		return result;
	}

}
