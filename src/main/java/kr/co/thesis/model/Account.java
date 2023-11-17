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
@Entity(name="account")
public class Account extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountNo;
	
	@Column
	private Long userNo;
	
	@Column
	private String agencyName;
	
	@Column
	private String accountNumber;
	
	@Column
	private String productName;
	
	@Column
	private String foreignYn;
	
	@Column
	private String minusAgreeYn;
	
	@Column
	private String accountType;
	
	@Column
	private String accountStatus;
	
	@Column
	private String remainPrice;
	
	@Column
	private String availablePrice;
	
	@Column
	private String delYn;
	
	@Column
	private LocalDateTime modDate;
}
