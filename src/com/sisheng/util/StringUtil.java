package com.sisheng.util;

public class StringUtil {
	/**
	 * �ж��Ƿ�Ϊ��
	 * @author sisheng
	 * @return boolean
	 */
	public static boolean isEmpty(String str){
		if("".equals(str)||str==null){
			return true;
		}
		else{
			return false;
		}
	}
}
