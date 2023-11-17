package kr.co.thesis.mapper;

import java.util.List;

import kr.co.thesis.model.HistoryModel;
import kr.co.thesis.model.RankModel;

public interface HistoryMapper {
	
	public List<HistoryModel> selectHistoryTotalPrice(Long userNo);
	
	public List<HistoryModel> selectHistoryList(Long userNo);
	
	public List<RankModel> selectRankPrice(Long userNo);
	
	public List<RankModel> selectRankHistory(Long userNo);
}
