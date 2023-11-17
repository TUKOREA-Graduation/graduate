package kr.co.thesis.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.co.thesis.dao.AccountDao;
import kr.co.thesis.dao.AccountHistoryDao;
import kr.co.thesis.dao.MemberDao;
import kr.co.thesis.model.Account;
import kr.co.thesis.model.AccountHistory;
import kr.co.thesis.model.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	
	private final AccountDao accountDao;
	private final AccountHistoryDao accountHistoryDao;
	
	private final MemberDao memberDao;
	
	@Transactional
	public void excelAccount(MultipartFile excelAccount, Authentication auth, Model model) throws Exception {
		
		OPCPackage opcPackage = OPCPackage.open(excelAccount.getInputStream());
        @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        
        String userId = auth.getName();
        Member member = memberDao.findByUserIdAndDelYn(userId, "N").orElse(null);
        
        String agencyName = "";			//기관명
        String accountNumber = "";		//계좌번호
        String productName = "";		//상품명
        String foreignYn = "";			//외화계좌여부
        String minusAgreeYn = "";		//마이너스약정여부
        String accountType = "";		//계좌구분
        String accountStatus = "";		//계좌상태
        String remainPrice = "";		//현재잔액
        String availablePrice = "";		//출금가능액
        
        boolean duplicateYn = false;

        // 첫 번째 시트를 불러온다.
        XSSFSheet sheet = workbook.getSheetAt(0);	
		
        for (int i=0; i<14; i++) {
        	
        	XSSFRow row = sheet.getRow(i);
        	
        	switch (i) {
				case 2:
					agencyName = row.getCell(5).getStringCellValue();
					break;
				case 4:
					accountNumber = row.getCell(5).getRawValue();
					break;
				case 5:
					productName = row.getCell(5).getStringCellValue();
					break;
				case 6:
					foreignYn = row.getCell(5).getStringCellValue();
					minusAgreeYn = row.getCell(15).getStringCellValue();
					break;
				case 7:
					accountType = row.getCell(5).getStringCellValue();
					accountStatus = row.getCell(15).getStringCellValue();
					break;
				case 13:
					remainPrice = row.getCell(5).getRawValue();
					availablePrice = row.getCell(9).getRawValue();
					break;
				default:
					break;
			}
        }
        
        int dupCnt = accountDao.countByAccountNumberAndUserNoAndDelYn(accountNumber, member.getUserNo(), "N");
        
        Account insertAccount = null;
        
        if(dupCnt == 0) {
        	insertAccount = Account.builder()
        			.userNo(member.getUserNo())
					.agencyName(agencyName)
					.accountNumber(accountNumber)
					.productName(productName)
					.foreignYn(foreignYn)
					.minusAgreeYn(minusAgreeYn)
					.accountType(accountType)
					.accountStatus(accountStatus)
					.remainPrice(remainPrice)
					.availablePrice(availablePrice)
					.delYn("N")
					.build();

    		accountDao.save(insertAccount);
        } else {
        	insertAccount = accountDao.findByAccountNumberAndUserNoAndDelYn(accountNumber, member.getUserNo(), "N");
        }
        
        for(int j=20; j<sheet.getLastRowNum() + 1; j++) {
        	
        	String dealDate = "";			//거래일시
        	String dealSeq = "";			//거래번호
        	String dealMethod = "";			//거래유형
        	String dealType = "";			//거래구분
        	String dealCode = "";			//통화코드
        	String dealPrice = "";			//거래금액
        	String afterPrice = "";			//거래후잔액
        	String paymentCount = "";		//납입회차
        	String summary = "";			//적요
        	
        	XSSFRow row = sheet.getRow(j);
        	
        	if(!"null".equals(String.valueOf(row.getCell(1))) &&
        	   !"".equals(String.valueOf(row.getCell(1)))) {
        		if (!row.getCell(1).getStringCellValue().equals("거래 일시")) {
        			dealDate = row.getCell(1).getStringCellValue();
				}
        	}
        	
        	if(!"null".equals(String.valueOf(row.getCell(2))) &&
         	   !"".equals(String.valueOf(row.getCell(2)))) {
        		if (!String.valueOf(row.getCell(2)).equals("거래 번호")) {
        			dealSeq = row.getCell(2).getRawValue();
				}
         	}
        	
        	if(!"null".equals(String.valueOf(row.getCell(4))) &&
          	   !"".equals(String.valueOf(row.getCell(4)))) {
        		if (!row.getCell(4).getStringCellValue().equals("거래 유형")) {
        			dealMethod = row.getCell(4).getStringCellValue();
				}
          	}
        	
        	if(!"null".equals(String.valueOf(row.getCell(6))) &&
           	   !"".equals(String.valueOf(row.getCell(6)))) {
        		if (!row.getCell(6).getStringCellValue().equals("거래 구분")) {
         			dealType = row.getCell(6).getStringCellValue();
 				}
           	}
        	
			if (!"null".equals(String.valueOf(row.getCell(7))) && 
				!"".equals(String.valueOf(row.getCell(7)))) {
				if (!row.getCell(7).getStringCellValue().equals("통화 코드")) {
					dealCode = row.getCell(7).getStringCellValue();
				}
			}
			
			if (!"null".equals(String.valueOf(row.getCell(10))) && 
				!"".equals(String.valueOf(row.getCell(10)))) {
				if (!String.valueOf(row.getCell(10)).equals("거래 금액")) {
					dealPrice = row.getCell(10).getRawValue();
				}
			}
			
			if (!"null".equals(String.valueOf(row.getCell(13))) && 
				!"".equals(String.valueOf(row.getCell(13)))) {
				if (!String.valueOf(row.getCell(13)).equals("거래후 잔액")) {
					afterPrice = row.getCell(13).getRawValue();
				}
			}
			
			if (!"null".equals(String.valueOf(row.getCell(17))) && 
				!"".equals(String.valueOf(row.getCell(17)))) {
				if (!String.valueOf(row.getCell(17)).equals("납입 회차")) {
					paymentCount = row.getCell(17).getRawValue();
				}
			}
			
			if (!"null".equals(String.valueOf(row.getCell(19))) && 
				!"".equals(String.valueOf(row.getCell(19)))) {
				if (!row.getCell(19).getStringCellValue().equals("적요")) {
					summary = row.getCell(19).getStringCellValue();
					
					if(dupCnt == 0) {
						
						AccountHistory insertAccountHistory = AccountHistory.builder()
																.accountNo(insertAccount.getAccountNo())
																.userNo(member.getUserNo())
																.dealDate(dealDate)
																.dealSeq(dealSeq)
																.dealMethod(dealMethod)
																.dealType(dealType)
																.dealCode(dealCode)
																.dealPrice(dealPrice)
																.afterPrice(afterPrice)
																.paymentCount(paymentCount)
																.summary(summary)
																.delYn("N")
																.build();
	
						accountHistoryDao.save(insertAccountHistory);
					} else {
						int dupHistoryCnt = accountHistoryDao.countByAccountNoAndUserNoAndDealDateAndDealSeqAndDelYn(insertAccount.getAccountNo(), member.getUserNo(), dealDate, dealSeq, "N");
						
						if(dupHistoryCnt == 0) {
							
							AccountHistory insertAccountHistory = AccountHistory.builder()
									.accountNo(insertAccount.getAccountNo())
									.userNo(member.getUserNo())
									.dealDate(dealDate)
									.dealSeq(dealSeq)
									.dealMethod(dealMethod)
									.dealType(dealType)
									.dealCode(dealCode)
									.dealPrice(dealPrice)
									.afterPrice(afterPrice)
									.paymentCount(paymentCount)
									.summary(summary)
									.delYn("N")
									.build();

							accountHistoryDao.save(insertAccountHistory);
						}
					}
				}
			}
			
        }
    	
    	model.addAttribute("uploadYn", true);
    	model.addAttribute("duplicateYn", duplicateYn);
	}
	
	@Transactional
	public void deleteAccount(Authentication auth, Model model) {
		
		String userId = auth.getName();
        Member member = memberDao.findByUserIdAndDelYn(userId, "N").orElse(null);
        
        List<Account> accountList = accountDao.findByUserNoAndDelYn(member.getUserNo(), "N");
        List<AccountHistory> accountHistoryList = accountHistoryDao.findByUserNoAndDelYn(member.getUserNo(), "N");
        
        for(Account account : accountList) {
        	account.setDelYn("Y");
        	account.setModDate(LocalDateTime.now());
        }
        
        for(AccountHistory accountHistory : accountHistoryList) {
        	accountHistory.setDelYn("Y");
        	accountHistory.setModDate(LocalDateTime.now());
        }
        
        model.addAttribute("delYn", true);
	}
	
}
