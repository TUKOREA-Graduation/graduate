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
public class HistoryModel {
	
	private int sortIdx;
	
	private String start;
	
	private String title;
	
	private String color;
	
	private String url;
	
	private String type;
	
	private String price;
}
