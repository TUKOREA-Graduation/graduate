package kr.co.thesis.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@Entity(name="card")
public class Card extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardNo;
	
	@Column
	private Long userNo;
	
	@Column
	private String agencyName;
	
	@Column
	private String cardNumber;
	
	@Column
	private String cardType;
	
	@Column
	private String cardProductName;
	
	@Column
	private String cardBrand;
	
	@Column
	private String familyType;
	
	@Column
	private String trafficYn;
	
	@Column
	private String cashYn;
	
	@Column
	private String paymentBank;
	
	@Column
	private String annualFee;
	
	@Column
	private String startDate;
	
	@Column
	private String delYn;
	
	@Column
	private LocalDateTime modDate;
}
