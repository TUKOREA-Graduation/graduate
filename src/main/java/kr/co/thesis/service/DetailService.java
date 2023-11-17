package kr.co.thesis.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.thesis.dao.MemberDao;
import kr.co.thesis.mapper.DetailMapper;
import kr.co.thesis.model.DetailModel;
import kr.co.thesis.model.Member;
import kr.co.thesis.util.PaginationUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailService {
	
	private final MemberDao memberDao;
	
	private final DetailMapper detailMapper;
	
	public void getDetailDates(String dates, Map<String, Object> params, Authentication auth, Model model) {
		
		int page = PaginationUtil.nulltoZero((String) params.get("page"));
		int rowCnt = (String) params.get("rowCnt") == null ? 10 : Integer.valueOf((String) params.get("rowCnt"));

		int pageNum = 0;
		
		if(page > 0) {
			pageNum = page - 1;
		}
		
		int start = pageNum * rowCnt;
		int end = rowCnt;
		
		String userId = auth.getName();
		Member member = memberDao.findByUserIdAndDelYn(userId, "N").orElse(null);
		
		int totalCnt = detailMapper.selectDetailCount(member.getUserNo(), dates);
		
		List<DetailModel> detailList = detailMapper.selectDetailList(member.getUserNo(), dates, start, end);
		
		PaginationUtil paging = new PaginationUtil(totalCnt, page, rowCnt);
		
		model.addAttribute("detailList", detailList);
		model.addAttribute("paging", paging);
		model.addAttribute("page", page);
		model.addAttribute("dates", dates);
	}
	
	public boolean validBizNo(String bizNo) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{5}");
        Matcher matcher = pattern.matcher(bizNo);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
	
	public String dateToString(Date date) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		
		return str;
	}
	
	public String datesToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		
		return str;
	}
	
	public boolean validApprovalDate(String approvalDate) {
		
		String end = approvalDate.substring(approvalDate.lastIndexOf(".")+1);
		
		if(end.length() > 3) {
			return true;
		} else {
			return false;
		}
	}
	
}
