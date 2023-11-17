package kr.co.thesis.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.thesis.consts.Role;
import kr.co.thesis.dao.AccountDao;
import kr.co.thesis.dao.MemberDao;
import kr.co.thesis.mapper.HistoryMapper;
import kr.co.thesis.model.Account;
import kr.co.thesis.model.HistoryModel;
import kr.co.thesis.model.Member;
import kr.co.thesis.model.RankModel;
import kr.co.thesis.util.PaginationUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {
	
	private final MemberDao memberDao;
	private final AccountDao accountDao;
	
	private final HistoryMapper historyMapper;
	
	@Transactional
	public String signUp(Map<String, Object> params) {
		
		String userId = (String) params.get("userId");
		String userPwd = (String) params.get("userPwd");
		String userName = (String) params.get("userName");
		String userEmail = (String) params.get("userEmail");
		
		int isExistMember = memberDao.countByUserId(userId);
		int isExistEmail = memberDao.countByUserEmail(userEmail);
		
		if(isExistMember > 0) {
			return "이미 가입된 아이디입니다.";
		}
		
		if(isExistEmail > 0) {
			return "이미 가입된 이메일입니다.";
		}
		
		String encUserPwd = BCrypt.hashpw(userPwd, BCrypt.gensalt(4));
		
		Member insertMember = Member.builder()
								.userId(userId)
								.userName(userName)
								.userEmail(userEmail)
								.pwd(encUserPwd)
								.role(Role.USER.getValue())
								.delYn("N")
								.build();
		
		memberDao.save(insertMember);
		
		return "success";
	}
	
	public void getDataList(Map<String, Object> params, Authentication auth, Model model) {
		
		int page = PaginationUtil.nulltoZero((String) params.get("page"));
		int rowCnt = 1;
		
		String dates = (String) params.get("dates");
		
		int pageNum = 0;
		
		if(page > 0) {
			pageNum = page - 1;
		}
		
		String userId = auth.getName();
		Member member = memberDao.findByUserIdAndDelYn(userId, "N").orElse(null);
		
		Pageable pageable = PageRequest.of(pageNum, rowCnt, Sort.by(Sort.Direction.ASC, "accountNo"));
		
		Page<Account> account = accountDao.findByUserNoAndDelYn(member.getUserNo(), "N", pageable);
		
		int totalCnt = (int) account.getTotalElements();
		PaginationUtil paging = new PaginationUtil(totalCnt, page, rowCnt);
		
		int totalPrice = 0;
		
		List<Account> list = accountDao.findByUserNoAndDelYn(member.getUserNo(), "N");
		
		for(Account a : list) {
			totalPrice = totalPrice + Integer.parseInt(a.getRemainPrice());
		}
		
		List<HistoryModel> historyTotalPrice = historyMapper.selectHistoryTotalPrice(member.getUserNo());
		List<HistoryModel> historyList = historyMapper.selectHistoryList(member.getUserNo());
		
		int idx = 0;
		
		for(HistoryModel h : historyList) {
			h.setSortIdx(idx);
			idx++;
		}
		
		String totalPlus = "0";
		String totalMinus = "0";
		
		if(historyTotalPrice.size() > 0) {
			totalPlus = historyTotalPrice.get(0).getPrice();
			totalMinus = historyTotalPrice.get(1).getPrice();
		}
		
		List<RankModel> rankPrice = historyMapper.selectRankPrice(member.getUserNo());
		List<RankModel> rankHistory = historyMapper.selectRankHistory(member.getUserNo());
		
		model.addAttribute("account", account.getContent());
		model.addAttribute("paging", paging);
		model.addAttribute("historyList", historyList);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("dates", dates);
		model.addAttribute("totalPlus", totalPlus);
		model.addAttribute("totalMinus", totalMinus);
		
		System.out.println("rankPrice: " + rankPrice);
		
		model.addAttribute("rankPrice", rankPrice);
		model.addAttribute("rankHistory", rankHistory);
		
		model.addAttribute("member", member);
	}
}
