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
@Entity(name="member")
public class Member extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo;
	
	@Column
	private String userId;
	
	@Column
	private String userName;
	
	@Column
	private String userEmail;
	
	@Column
	private String pwd;
	
	@Column
	private String role;
	
	@Column
	private String delYn;
	
	@Column
	private LocalDateTime modDate;
}
