package kr.co.thesis.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.thesis.model.Account;

public interface AccountDao extends JpaRepository<Account, Long>{
	
	int countByAccountNumberAndUserNoAndDelYn(String accountNumber, Long userNo, String delYn);
	
	Account findByAccountNumberAndUserNoAndDelYn(String accountNumber, Long userNo, String delYn);
	
	List<Account> findByUserNoAndDelYn(Long userNo, String delYn);
	
	Page<Account> findByUserNoAndDelYn(Long userNo, String delYn, Pageable pageable);
}
