package _jz;

import java.util.regex.Pattern;

public class is_digit {
	/*
	  * 判断是否为整数 
	  * @param str 传入的字符串 
	  * @return 是整数返回true,否则返回false 
	*/
	 public static boolean isInteger(String str) {
			if (null == str || "".equals(str)) {
				return false;
			}
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
			return pattern.matcher(str).matches();
		}
		 
		public static boolean isDouble(String str) {
			if (null == str || "".equals(str)) {
				return false;
			}
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			return pattern.matcher(str).matches();
		}

}
