package kr.co.thesis.model;

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
public class DetailModel {
	
	private String productName;
	
	private String detailType;
	
	private String useName;
	
	private String price;
	
	private String useDate;
}
