package com.bd.Control.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

public class StringUtil {
	private static HashSet<String> bedSet = new HashSet<String>();
	private static HashSet<String> phoneSet = new HashSet<String>();
	private static HashSet<String> alarmType = new HashSet<String>();

	static {
		for (int i = 0; i < 7; ++i) {
			for (int j = 0; j < 10; ++j) {
				bedSet.add("" + i + j);
			}
		}
		for (int i = 1; i < 10; ++i) {
			bedSet.add("" + i);
		}
		bedSet.add("70");
		bedSet.add("71");
		bedSet.add("72");

		for (int i = 1; i < 10; ++i) {
			phoneSet.add("0" + i);
		}
		for (int i = 1; i < 10; ++i) {
			phoneSet.add("01" + i);
		}
		phoneSet.add("10");

		for (int i = 1; i < 8; ++i)
			alarmType.add(i + "");

	}

	// *判断报警号是否为1到7
	public static boolean isAlarmType(String alarmNO) {
		return alarmType.contains(alarmNO);
	}

	// *判断手机的IMEI是否合法，即是否为15位数字
	public static boolean isIMEI(String imei) {
		return imei.matches("^\\d{15}$");

	}

	// *判断是否为住院号
	public static boolean isPatientID(String patientID) {
		return patientID.matches("[0-9]{4,10}");

	}

	// * 判断终端号是否合法，即是否为10位数字
	public static boolean isDeviceID(String deviceID) {
		return deviceID.matches("^\\d{10}$");

	}

	// *判断病床号是否合法，即是否为01到72
	public static boolean isBedID(String bedID) {
		return bedSet.contains(bedID);
	}

	// * 检验填写信息是否完整， 其中包括病床号、姓名、住院号、年龄和疾病
	public static boolean verifyWrite(String bedID, String name, String patientID, String patientAge, String disease) {

		if (StringUtil.isBedID(bedID) && StringUtil.isPatientID(patientID)) {
			if (StringUtil.notEmptyString(name) && StringUtil.isAge(patientAge) && StringUtil.notEmptyString(disease))
				return true;
		}
		return false;
	}

	// *判断String是否为空
	public static boolean notEmptyString(String string) {
		return !string.trim().equals("");
	}

	// *判断年龄是否合法
	public static boolean isAge(String age) {
		if (age != null && isNumeric(age)) {
			if (age.matches("0{1,3}"))
				return true;
			String ag = age.replace("^(0+)", "");
			if (ag.length() > 0 && ag.matches("^\\d{1,3}$"))
				return (getNum(ag) > 0 && getNum(ag) < 150);
		}
		return false;
	}

	// *判断数字是否为0-3位数字
	public static boolean isSpeed(String spd) {
		if (spd != null) {
			return spd.matches("^\\d{1,3}$");
		}
		return false;
	}

	// *将字符串转换回数字
	public static int getNum(String num) {
		String nu = num.trim();
		if (nu != null && isNumeric(nu)) {
			if (nu.matches("0{1,}"))
				return 0;
			nu = nu.replaceAll("^(0+)", "");
			if (nu.length() > 0)
				return Integer.parseInt(nu);
		}
		return -1;

	}

	// *男转换成1,女转换成0
	public static String getTransSextoInt(String sex) {
		if (sex.trim().equals("男"))
			return "1";
		return "0";
	}

	// *转换年龄
	public static String getTransAge(String age) {
		if (StringUtil.isAge(age.trim())) {
			String a = StringUtil.getNum(age) + "";
			if (a.matches("^\\d{3}$"))
				return age;
			else if (a.matches("^\\d{2}$"))
				return " " + age;
			else
				return "  " + age;
		}
		return "   ";
	}

	// 取字符串的后面两位
	public static String getStringTwo(String phonenumber) {
		int length = phonenumber.length();
		return phonenumber.substring(length - 2, length);
	}

	// *转换容量
	public static String getTransVol(String vol) {
		String a = StringUtil.getNum(vol.trim()) + "";
		if (a.matches("^\\d{3}$"))
			return vol;
		else if (a.matches("^\\d{2}$"))
			return "0" + vol;
		else
			return "00" + vol;
	}

	// * 转换住院号
	public static String getTransPatientID(String PatiendId) {
		char[] patientID = new char[10];
		for (int i = 0; i < PatiendId.length() && i < 10; i++)
			patientID[i] = PatiendId.charAt(i);
		return new String(patientID);
	}

	// * 判断输液剩余时间是否小于9分钟
	public static boolean isLessNine(String time) {
		if (time != null) {
			if (isNumeric(time))
				return (getNum(time) < 9);
		}
		return false;
	}

	// *判断字符串能否转化为数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	// *判断是不是手机编号
	public static boolean isPhoneNum(String pn) {

		return phoneSet.contains(pn);
	}

	// *将字符串转化为固定长度
	public static String appendSpace(String str, int length) {
		for (int i = str.length(); i < length; i++) {
			str = str + " ";
		}
		return str;
	}

	// *将字符串转化为固定长度
	public static String appendZero(String str) {
		if (str == null) {
			return "000";
		}
		for (int i = str.length(); i < 3; i++) {
			str = "0" + str;
		}
		return str;
	}

	// *字符串中所占汉字个数
	public static int getChineseCharacterCount(String str) {
		int ccCount = 0;
		String regEx = "[\u4e00-\u9fa5]";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				ccCount = ccCount + 1;
			}
		}
		return ccCount;
	}

	// ArrayList存储转换为一个长字符串
	public static String getArrayListString(ArrayList<String> al) {
		String result = "";
		if (al.isEmpty())
			return result;
		Iterator<String> iter = al.iterator();
		while (iter.hasNext()) {
			result += (iter.next() + ",");
		}
		return result;
	}

	// 一个长字符串转换为ArrayList
	public static ArrayList<String> getArrayList(String str) {
		String[] sb = new String[73];
		split2ArrayDirect(str, ',', sb);
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < sb.length; ++i) {
			if (StringUtil.isBedID(sb[i]))
				al.add(sb[i]);
		}
		return al;
	}

	public static void split2ArrayDirect(final String str, final char c, String[] splits) {
		split2ArrayDirect(str, c, false, 0, splits);
	}

	public static void split2ArrayDirect(final String str, final char c, boolean ignoreMultiple, int skipFirstColumns,
			String[] splits) {

		int size = splits.length;
		int index = 0, strLen = str.length();
		int start = 0, end = 0;
		// skip first
		if (skipFirstColumns > 0) {
			int skip = skipFirstColumns;
			// skip
			for (; start < strLen && skip > 0; start++) {
				if (str.charAt(start) == c) {
					skip--;
				}
			}
		}
		boolean startWithDelimiter;
		while (start < strLen) {
			startWithDelimiter = str.charAt(start) == c;
			if (startWithDelimiter && !ignoreMultiple) {
				start++;
				splits[index++] = "";
				if (index == size) {
					return;
				}
			} else {
				// skip multiple
				if (ignoreMultiple) {
					// search start
					start++;
					for (; start < strLen;) {
						if (str.charAt(start) == c) {
							start++;
						} else {
							break;
						}
					}
				}

				// search end
				for (end = start + 1; end < strLen; end++) {
					if (str.charAt(end) == c) {
						break;
					}
				}
				if (index == size) {
					return;
				}
				splits[index++] = str.substring(start, end);
				start = end + 1;
			}
		}
	}

	public static ArrayList<String> propSort(HashSet<String> hs) {
		ArrayList<String> al = new ArrayList<>();
		for (String s : hs)
			al.add(s);
		Collections.sort(al);
		return al;
	}

	// public static void main(String[] args) {
	//
	// String str =
	// "aaa;bbb;ccc;ddd;1234;124;1;1;2;3;;41;1;3;1;324;;23;;23;4;12;3;12;34;21;3;41;23;1;23;1;234;12;341;3;23;234;";
	// // compare
	// long start = System.currentTimeMillis();
	// for (int i = 0; i < 1000000; i++) {
	// str.split(";");
	// }
	// System.out.println(System.currentTimeMillis() - start);
	//
	// start = System.currentTimeMillis();
	// String[] array = new String[40];
	// for (int i = 0; i < 1000000; i++) {
	// split2ArrayDirect(str, ';', array);
	// }
	// System.out.println("split(): " + (System.currentTimeMillis() - start));
	//
	// }
}
