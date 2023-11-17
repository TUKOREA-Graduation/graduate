package kr.co.thesis.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.thesis.model.AccountHistory;

public interface AccountHistoryDao extends JpaRepository<AccountHistory, Long>{
	
	List<AccountHistory> findByUserNoAndDelYn(Long userNo, String delYn);
	
	int countByAccountNoAndUserNoAndDealDateAndDealSeqAndDelYn(Long accountNo, Long userNo, String dealDate,
															   String dealSeq, String delYn);
}
