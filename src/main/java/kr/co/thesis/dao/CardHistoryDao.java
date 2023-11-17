package kr.co.thesis.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.thesis.model.CardHistory;

public interface CardHistoryDao extends JpaRepository<CardHistory, Long>{
	
	int countByCardNoAndUserNoAndApprovalNoAndDelYn(Long cardNo, Long userNo, String approvalNo, String delYn);
	
	List<CardHistory> findByUserNoAndDelYn(Long userNo, String delYn);
}
