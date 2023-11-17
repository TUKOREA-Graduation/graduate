package kr.co.thesis.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import kr.co.thesis.dao.CardDao;
import kr.co.thesis.dao.CardHistoryDao;
import kr.co.thesis.dao.MemberDao;
import kr.co.thesis.model.Card;
import kr.co.thesis.model.CardHistory;
import kr.co.thesis.model.Member;
import kr.co.thesis.util.CalendarUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
	
	private final CardDao cardDao;
	private final CardHistoryDao cardHistoryDao;
	
	private final MemberDao memberDao;
	
	@Transactional
	public void excelCard(MultipartFile excelCard, Authentication auth, Model model) throws Exception {
		
		OPCPackage opcPackage = OPCPackage.open(excelCard.getInputStream());
        @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        
        String userId = auth.getName();
        Member member = memberDao.findByUserIdAndDelYn(userId, "N").orElse(null);
        
        String agencyName = "";			//기관명
        String cardNumber = "";			//카드번호
        String cardType = "";			//카드구분
        String cardProductName = "";	//카드상품명
        String cardBrand = "";			//카드브랜드
        String familyType = "";			//본인/가족구분
        String trafficYn = "";			//교통카드기능여부
        String cashYn = "";				//현금카드기능여부
        String paymentBank = "";		//결제은행
        String annualFee = "";			//상품연회비
        String startDate = "";			//발급일자
        
        boolean duplicateYn = false;

        // 첫 번째 시트를 불러온다.
        XSSFSheet sheet = workbook.getSheetAt(0);	
		
        for (int i=0; i<10; i++) {
        	
        	XSSFRow row = sheet.getRow(i);
        	
        	switch (i) {
				case 2:
					agencyName = row.getCell(3).getStringCellValue();
					break;
				case 4:
					cardNumber = row.getCell(3).getRawValue();
					cardType = row.getCell(9).getStringCellValue();
					break;
				case 5:
					cardProductName = row.getCell(3).getStringCellValue();
					break;
				case 6:
					cardBrand = row.getCell(3).getStringCellValue();
					familyType = row.getCell(9).getStringCellValue();
					break;
				case 7:
					trafficYn = row.getCell(3).getStringCellValue();
					cashYn = row.getCell(9).getStringCellValue();
					break;
				case 8:
					paymentBank = row.getCell(3).getStringCellValue();
					annualFee = row.getCell(9).getRawValue();
					break;
				case 9:
					startDate = String.valueOf(CalendarUtils.dateToString(row.getCell(3).getDateCellValue()));
					break;
				default:
					break;
			}
        }
        
        int dupCnt = cardDao.countByCardNumberAndUserNoAndDelYn(cardNumber, member.getUserNo(), "N");
        
        Card insertCard = null;
        
        if(dupCnt == 0) {
        	insertCard = Card.builder()
        					.userNo(member.getUserNo())
        					.agencyName(agencyName)
							.cardNumber(cardNumber)
							.cardType(cardType)
							.cardProductName(cardProductName)
							.cardBrand(cardBrand)
							.familyType(familyType)
							.trafficYn(trafficYn)
							.cashYn(cashYn)
							.paymentBank(paymentBank)
							.annualFee(annualFee)
							.startDate(startDate)
							.delYn("N")
							.build();

    		cardDao.save(insertCard);
        } else {
        	insertCard = cardDao.findByCardNumberAndUserNoAndDelYn(cardNumber, member.getUserNo(), "N");
        }
        
        List<String> approvalNoList = new ArrayList<String>();
        List<String> franchiseeNameList = new ArrayList<String>();
        List<String> updateDateList = new ArrayList<String>();
        List<String> franchiseeNoList = new ArrayList<String>();
        List<String> paymentStatusList = new ArrayList<String>();
        List<String> usePriceList = new ArrayList<String>();
        List<String> useTypeList = new ArrayList<String>();
        List<String> updatePriceList = new ArrayList<String>();
        List<String> approvalDateList = new ArrayList<String>();
        List<String> installmentCountList = new ArrayList<String>();
        
        for(int j=13; j<sheet.getLastRowNum() + 1; j++) {
        	
    	   	String approvalNo = "";			//승인번호
		   	String updateDate = "";			//정정 또는 승인취소 일시
		   	String paymentStatus = "";		//결제상태
		   	String useType = "";			//사용구분
		   	String approvalDate = "";		//승인일시
		   	String franchiseeName = "";		//가맹점명
		   	String franchiseeNo = "";		//가맹점사업자 등록번호
		   	String usePrice = "";			//이용금액
		   	String updatePrice = "";		//정정후금액
		   	String installmentCount = "";	//전체할부회차
    	   
        	XSSFRow row = sheet.getRow(j);
        	
        	if (!"null".equals(String.valueOf(row.getCell(1))) && 
    			!"".equals(String.valueOf(row.getCell(1))) &&
				String.valueOf(row.getCell(1).getCellType()) == "NUMERIC") {
        		
    			approvalNo = row.getCell(1).getRawValue();
    			
    			approvalNoList.add(approvalNo);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(1))) && 
    			!"".equals(String.valueOf(row.getCell(1))) &&
    			String.valueOf(row.getCell(1).getCellType()) == "STRING" &&
    			!String.valueOf(row.getCell(1)).equals("승인번호") &&
    			!String.valueOf(row.getCell(1)).equals("가맹점명")) {
            		
    			franchiseeName = row.getCell(1).getStringCellValue();
    			
    			franchiseeNameList.add(franchiseeName);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(3))) &&
				!"".equals(String.valueOf(row.getCell(1))) &&
    			!String.valueOf(row.getCell(3)).equals("정정 또는 승인취소 일시") &&
    			!String.valueOf(row.getCell(3)).equals("가맹점사업자 등록번호") &&
    			(String.valueOf(row.getCell(3).getCellType()) == "BLANK" || 
    			 String.valueOf(row.getCell(3).getCellType()) == "NUMERIC")) {
            		
    			if(String.valueOf(row.getCell(3).getCellType()) == "NUMERIC") {
    				updateDate = CalendarUtils.datesToString(row.getCell(3).getDateCellValue());
    			}
    			
    			updateDateList.add(updateDate);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(3))) && 
    			!"".equals(String.valueOf(row.getCell(3))) &&
    			String.valueOf(row.getCell(3).getCellType()) == "STRING" &&
    			!String.valueOf(row.getCell(3)).equals("정정 또는 승인취소 일시") &&
    			!String.valueOf(row.getCell(3)).equals("가맹점사업자 등록번호")) {
            		
    			franchiseeNo = row.getCell(3).getStringCellValue();
    			
    			franchiseeNoList.add(franchiseeNo);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(5))) && 
    			!"".equals(String.valueOf(row.getCell(5))) &&
    			String.valueOf(row.getCell(5).getCellType()) == "STRING" &&
    			!String.valueOf(row.getCell(5)).equals("결제상태") &&
    			!String.valueOf(row.getCell(5)).equals("이용금액")) {
            		
    			paymentStatus = row.getCell(5).getStringCellValue();
    			
    			paymentStatusList.add(paymentStatus);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(5))) && 
    			!"".equals(String.valueOf(row.getCell(5))) &&
    			String.valueOf(row.getCell(5).getCellType()) == "NUMERIC" &&
    			!String.valueOf(row.getCell(5)).equals("결제상태") &&
    			!String.valueOf(row.getCell(5)).equals("이용금액")) {
            		
    			usePrice = row.getCell(5).getRawValue();
    			
    			usePriceList.add(usePrice);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(7))) && 
    			!"".equals(String.valueOf(row.getCell(7))) &&
    			String.valueOf(row.getCell(7).getCellType()) == "STRING" &&
    			!String.valueOf(row.getCell(7)).equals("사용구분") &&
    			!String.valueOf(row.getCell(7)).equals("정정후금액")) {
            		
    			useType = row.getCell(7).getStringCellValue();
    			
    			useTypeList.add(useType);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(7))) &&
				!"".equals(String.valueOf(row.getCell(1))) &&
    			!String.valueOf(row.getCell(7)).equals("사용구분") &&
    			!String.valueOf(row.getCell(7)).equals("정정후금액") &&
    			(String.valueOf(row.getCell(7).getCellType()) == "BLANK" || 
    			 String.valueOf(row.getCell(7).getCellType()) == "NUMERIC")) {
            		
    			if(String.valueOf(row.getCell(7).getCellType()) == "NUMERIC") {
    				updatePrice = row.getCell(7).getRawValue();
    			}
    			
    			updatePriceList.add(updatePrice);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(10))) && 
    			!"".equals(String.valueOf(row.getCell(1))) &&
    			String.valueOf(row.getCell(10).getCellType()) == "NUMERIC" &&
    			!String.valueOf(row.getCell(10)).equals("승인일시") &&
    			!String.valueOf(row.getCell(10)).equals("전체할부회차") &&
    			CalendarUtils.validApprovalDate(String.valueOf(row.getCell(10)))) {
    			
    			approvalDate = CalendarUtils.datesToString(row.getCell(10).getDateCellValue());
    			
    			approvalDateList.add(approvalDate);
        	}
    		
    		if (!"null".equals(String.valueOf(row.getCell(10))) && 
    			!"".equals(String.valueOf(row.getCell(10))) &&
    			String.valueOf(row.getCell(10).getCellType()) == "NUMERIC" &&
    			!String.valueOf(row.getCell(10)).equals("승인일시") &&
    			!String.valueOf(row.getCell(10)).equals("전체할부회차") &&
    			!CalendarUtils.validApprovalDate(String.valueOf(row.getCell(10)))) {
    			
    			installmentCount = row.getCell(10).getRawValue();
    			
    			installmentCountList.add(installmentCount);
        	}
        }
        
        for(int k=0; k<approvalNoList.size(); k++) {
    		
    		if(dupCnt == 0) {
            	CardHistory insertCardHistory = CardHistory.builder()
            								.cardNo(insertCard.getCardNo())
            								.userNo(member.getUserNo())
            								.approvalNo(approvalNoList.get(k))
            								.updateDate(updateDateList.get(k))
            								.paymentStatus(paymentStatusList.get(k))
            								.useType(useTypeList.get(k))
            								.approvalDate(approvalDateList.get(k))
            								.franchiseeName(franchiseeNameList.get(k))
            								.franchiseeNo(franchiseeNoList.get(k))
            								.usePrice(usePriceList.get(k))
            								.updatePrice(updatePriceList.get(k))
            								.installmentCount(installmentCountList.get(k))
            								.delYn("N")
            								.build();
            	
            	cardHistoryDao.save(insertCardHistory);
    		} else {
    			int dupHistoryCnt = cardHistoryDao.countByCardNoAndUserNoAndApprovalNoAndDelYn(insertCard.getCardNo(), member.getUserNo(), approvalNoList.get(k), "N");
    			
    			if(dupHistoryCnt == 0) {
    				CardHistory insertCardHistory = CardHistory.builder()
							.cardNo(insertCard.getCardNo())
							.userNo(member.getUserNo())
							.approvalNo(approvalNoList.get(k))
							.updateDate(updateDateList.get(k))
							.paymentStatus(paymentStatusList.get(k))
							.useType(useTypeList.get(k))
							.approvalDate(approvalDateList.get(k))
							.franchiseeName(franchiseeNameList.get(k))
							.franchiseeNo(franchiseeNoList.get(k))
							.usePrice(usePriceList.get(k))
							.updatePrice(updatePriceList.get(k))
							.installmentCount(installmentCountList.get(k))
							.delYn("N")
							.build();

					cardHistoryDao.save(insertCardHistory);
    			}
    		}
    	}
        
    	model.addAttribute("uploadYn", true);
    	model.addAttribute("duplicateYn", duplicateYn);
	}
	
	@Transactional
	public void deleteCard(Authentication auth, Model model) {
		
		String userId = auth.getName();
        Member member = memberDao.findByUserIdAndDelYn(userId, "N").orElse(null);
        
        List<Card> cardList = cardDao.findByUserNoAndDelYn(member.getUserNo(), "N");
        List<CardHistory> cardtHistoryList = cardHistoryDao.findByUserNoAndDelYn(member.getUserNo(), "N");
        
        for(Card card : cardList) {
        	card.setDelYn("Y");
        	card.setModDate(LocalDateTime.now());
        }
        
        for(CardHistory cardHistory : cardtHistoryList) {
        	cardHistory.setDelYn("Y");
        	cardHistory.setModDate(LocalDateTime.now());
        }
        
        model.addAttribute("delYn", true);
	}
	
}
