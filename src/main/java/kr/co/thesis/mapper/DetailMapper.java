package kr.co.thesis.mapper;

import java.util.List;

import kr.co.thesis.model.DetailModel;

public interface DetailMapper {
	
	public int selectDetailCount(Long userNo, String dates);
	
	public List<DetailModel> selectDetailList(Long userNo, String dates, int start, int end);
}
