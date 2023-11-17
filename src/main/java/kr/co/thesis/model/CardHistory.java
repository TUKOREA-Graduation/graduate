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
@Entity(name="card_history")
public class CardHistory extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyNo;
	
	@Column
	private Long cardNo;
	
	@Column
	private Long userNo;
	
	@Column
	private String approvalNo;
	
	@Column
	private String updateDate;
	
	@Column
	private String paymentStatus;
	
	@Column
	private String useType;
	
	@Column
	private String approvalDate;
	
	@Column
	private String franchiseeName;
	
	@Column
	private String franchiseeNo;
	
	@Column
	private String usePrice;
	
	@Column
	private String updatePrice;
	
	@Column
	private String installmentCount;
	
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
