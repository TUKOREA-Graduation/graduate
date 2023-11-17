package kr.co.thesis.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="account_history")
public class AccountHistory extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyNo;
	
	@Column
	private Long accountNo;

	@Column
	private Long userNo;
	
	@Column
	private String dealDate;
	
	@Column
	private String dealSeq;
	
	@Column
	private String dealMethod;
	
	@Column
	private String dealType;
	
	@Column
	private String dealCode;
	
	@Column
	private String dealPrice;
	
	@Column
	private String afterPrice;
	
	@Column
	private String paymentCount;
	
	@Column
	private String summary;
	
	@Column
	private String delYn;
	
	@Column
	private LocalDateTime modDate;

	@Transient
	private String start;
	
	@Transient
	private String title;
	
	@Transient
	private String color;
	
	@Transient
	private String url;
}
