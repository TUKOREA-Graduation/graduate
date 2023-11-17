package kr.co.thesis.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.thesis.model.Card;

public interface CardDao extends JpaRepository<Card, Long>{
	
	int countByCardNumberAndUserNoAndDelYn(String cardNumber, Long userNo, String delYn);
	
	Card findByCardNumberAndUserNoAndDelYn(String cardNumber, Long userNo, String delYn);
	
	List<Card> findByUserNoAndDelYn(Long userNo, String delYn);
	
}
