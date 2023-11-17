package kr.co.thesis.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalendarUtils {
	
	public static boolean validBizNo(String bizNo) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{5}");
        Matcher matcher = pattern.matcher(bizNo);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
	
	public static String dateToString(Date date) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		
		return str;
	}
	
	public static String datesToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		
		return str;
	}
	
	public static boolean validApprovalDate(String approvalDate) {
		
		String end = approvalDate.substring(approvalDate.lastIndexOf(".")+1);
		
		if(end.length() > 3) {
			return true;
		} else {
			return false;
		}
	}
}
