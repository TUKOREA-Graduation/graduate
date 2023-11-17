package kr.co.thesis.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.thesis.dao.MemberDao;
import kr.co.thesis.model.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BsUserDetailsService implements UserDetailsService {
	
	private final MemberDao memberDao;
	
	private final HttpSession httpSession;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws AuthenticationServiceException {
		
		Member member = memberDao.findByUserIdAndDelYn(username, "N")
				.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정"));
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority(member.getRole()));
		
		httpSession.setAttribute("role", member.getRole());
		
		return BsUser.builder()
				.member(member)
				.username(member.getUserId())
				.password(member.getPwd())
				.authorities(authorities)
				.build();
	}
}
