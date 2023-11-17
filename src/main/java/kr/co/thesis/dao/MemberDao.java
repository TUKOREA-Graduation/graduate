package kr.co.thesis.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.thesis.model.Member;

public interface MemberDao extends JpaRepository<Member, Long>{
	
	Optional<Member> findByUserIdAndDelYn(String userId, String delYn);
	
	List<Member> findByRoleAndDelYn(String role, String delYn);
	
	int countByUserId(String userId);
	
	int countByUserEmail(String userEmail);
}
