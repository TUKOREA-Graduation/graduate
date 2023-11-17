package kr.co.thesis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.co.thesis.security.BsFailureHandler;
import kr.co.thesis.security.BsPasswordEncoder;
import kr.co.thesis.security.BsSuccessHandler;
import lombok.RequiredArgsConstructor;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean	
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/webjars/jquery/**",
                "/assets/css/**",
                "/assets/vendor/**",
                "/assets/**"
        );
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
				http.csrf().disable()
                    .headers()
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(
                            XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
	                .authorizeRequests()
	                .antMatchers("/loginForm", "/signUpForm", "/signUp", "/send/email").permitAll()
	                .antMatchers("/**").hasAnyRole("ADMIN", "USER");
	                
                http
                    .formLogin()
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .usernameParameter("userId")
                    .passwordParameter("userPwd")
                    .successHandler(loginSuccessHandler())
                    .failureHandler(loginFailureHandler());
				
                http
                    .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
	    		.and()
	    			.exceptionHandling();
                	
        return http.build();
    }
	
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new BsSuccessHandler();
	}
	
	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return new BsFailureHandler();
	}
	
	// Bean 등록하면 알아서 matches 호출
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BsPasswordEncoder();
    }
    
}
