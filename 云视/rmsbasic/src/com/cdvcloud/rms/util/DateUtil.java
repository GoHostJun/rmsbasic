package com.cdvcloud.rms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author: huangaigang
 * @version： v1.0
 * @类说明 日期时间工具类
 * @date: 2015-11-29 13:57:05
 */

public final class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	// 所有大月
	private final static int[] full_day_month = new int[] { 1, 3, 5, 7, 8, 10, 12 };
	// 所有小月
	private final static int[] normal_day_month = new int[] { 4, 6, 9, 11 };
	/**
	 * The year of the gregorianCutover, with 0 representing 1 BC, -1
	 * representing 2 BC, etc.
	 */
	private static transient int gregorianCutoverYear = 1582;

	private static SimpleDateFormat _ISODate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat _shortTime = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat _timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat _ISODateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat _SDFDateTime = new SimpleDateFormat("yyyyMMddHHmm");
	private static SimpleDateFormat _SDFDate = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat _timeFormat1 = new SimpleDateFormat("HHmmss");
	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";
	private static boolean LENIENT_DATE = false;

	/**
	 * 返回当前时间,格式HH:mm:ss new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	 * 
	 * @return String 当前时间
	 */
	public static String getCurrentDateTime() {
		String time = null;
		try {
			time = _ISODateTime.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 当前日期时间。
	 * 
	 * @return
	 */
	public static Date currentDateTime() {
		return new Date();
	}

	/**
	 * 当前日期时间字符串(yyyyMMddHHmm)。
	 * 
	 * @return
	 */
	public static String currentDateTimeStr() {
		return _SDFDateTime.format(new Date());
	}

	/**
	 * 当前时间字符串(MMddss)。
	 * 
	 * @return
	 */
	public static String currentTimeStr() {
		return _timeFormat1.format(new Date());
	}

	/**
	 * 当前日期时间字符串(yyyyMMdd)。
	 * 
	 * @return
	 */
	public static String currentDateStr() {
		return _SDFDate.format(new Date());
	}

	/**
	 * 取得某年月最大日。
	 * 
	 * @param y
	 * @param m
	 * @return
	 */
	public static int getMaxDay(int y, int m) {
		if (isLeapNormalMonth(y, m))
			return 29;
		else if (isNormalMonth(y, m))
			return 28;
		else if (isSmallMonth(m))
			return 30;
		else
			return 31;
	}

	/**
	 * 是否为大月
	 * 
	 * @param m
	 * @return
	 */
	public static boolean isBigMonth(int m) {
		for (int i = 0; i < full_day_month.length; i++) {
			if (m == full_day_month[i])
				return true;
		}
		return false;
	}

	/**
	 * 是否为闰平月
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static boolean isLeapNormalMonth(int y, int m) {
		return isleapyear(y) && m == 2;
	}

	/**
	 * 是否闰年。
	 * 
	 * @param y
	 * @return
	 */
	public static boolean isleapyear(int y) {
		return y >= gregorianCutoverYear ? ((y % 4 == 0) && ((y % 100 != 0) || (y % 400 == 0))) : // Gregorian
				(y % 4 == 0); // Julian
	}

	/**
	 * 是否为普通平月
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static boolean isNormalMonth(int y, int m) {
		return !isleapyear(y) && m == 2;
	}

	/**
	 * 是否为小月
	 * 
	 * @param m
	 * @return
	 */
	public static boolean isSmallMonth(int m) {
		for (int i = 0; i < normal_day_month.length; i++) {
			if (m == normal_day_month[i])
				return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是日期类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.parse(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * <pre>
	 * 时间格式语法： 
	 *                         
	 * 使用一个 time pattern 字符串指定时间格式。 在这种方式下，所有的 ASCII 字母被保留为模式字母，定义如下： 
	 *                         
	 *   符号     含义                    表示                示例
	 *   ------   -------                 ------------        -------
	 *   G        年代标志符              (Text)              AD
	 *   y        年                      (Number)            1996
	 *   M        月                      (Text & Number)     July & 07
	 *   d        日                      (Number)            10
	 *   h        时 在上午或下午 (1?12)  (Number)            12
	 *   H        时 在一天中 (0?23)      (Number)            0
	 *   m        分                      (Number)            30
	 *   s        秒                      (Number)            55
	 *   S        毫秒                    (Number)            978
	 *   E        星期                    (Text)              Tuesday
	 *   D        一年中的第几天          (Number)            189
	 *   F        一月中第几个星期几      (Number)            2  (2nd Wed in July)
	 *   w        一年中第几个星期        (Number)            27
	 *   W        一月中第几个星期        (Number)            2
	 *   a        上午 / 下午 标记符      (Text)              PM
	 *   k        时 在一天中 (1?24)      (Number)            24
	 *   K        时 在上午或下午 (0?11)  (Number)            0
	 *   z        时区                    (Text)      Pacific Standard Time
	 *   '        文本转义符              (Delimiter)
	 *   ''       单引号                  (Literal)           '
	 * </pre>
	 * 
	 * @param date
	 * @param parttern
	 */
	public static String format(Date date, String parttern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(parttern);
		return sdf.format(date);
	}

	/**
	 * 返回短时间类型(HH:mm)
	 */
	public static String toShortTimeString(Date date) {
		return _shortTime.format(date);
	}

	/**
	 * 返回时间串(HH:mm:ss)
	 */
	public static String toTimeString(Date date) {
		return _timeFormat.format(date);
	}

	/**
	 * 返回ISO格式（yyyy-MM-dd）的日期字符
	 */
	public static String toISODateString(Date date) {
		return _ISODate.format(date);
	}

	/**
	 * 返回ISO格式（yyyy-MM-dd HH:mm:ss）的日期字符
	 */
	public static String toISODateTimeString(Date date) {
		return _ISODateTime.format(date);
	}

	/**
	 * 按照几种常见类型，将字符串解析出Date类型
	 */
	public static Date parseDate(String szDate) throws ParseException {
		return parseDate(szDate, new String[] { "yyyy-MM-dd HH:mm", "yyyy-M-d HH:mm", "yyyy-MM-dd hh:mm aaa", "MM/dd/yyyy HH:mm",
				"MM/dd/yyyy hh:mm aaa", "M/d/yyyy HH:mm", "M/d/yyyy hh:mm aaa", "yyyy-M-d hh:mm aaa", "yyyy-MM-dd", "yyyy-M-d", "MM/dd/yyyy",
				"M/d/yyyy" });
	}

	/**
	 * 将字符串解析出Date类型
	 * 
	 * @param szDate
	 *            待解析的字符串
	 * @param patterns
	 *            DateFormat格式集合
	 * @throws ParseException
	 */
	public static Date parseDate(String szDate, String[] patterns) throws ParseException {
		if (szDate == null) {
			return null;
		}
		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < patterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(patterns[0]);
			} else {
				parser.applyPattern(patterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(szDate, pos);
			if (date != null && pos.getIndex() == szDate.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + szDate, -1);
	}

	public static synchronized Date parseDateDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDateFormat(String strDate, String pattern) {
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
			try {
				date = sdf.parse(strDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return date;
		}
	}

	public static String toISODateString1(Date date) {
		return _ISODate.format(date);
	}

	/**
	 * 获得当前的年份
	 * 
	 * @return
	 */
	public static int getYear() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.YEAR);
	}

	/**
	 * 获得当前的月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前的日
	 * 
	 * @return
	 */
	public static int getDay() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 将long类型的时间转为时:分:秒
	 * 
	 * @param millisecond
	 * @return
	 */
	public static String getTimeLenght(long millisecond) {
		long minSecond = (long) Math.floor(millisecond); // 时间不足一秒向下取整

		long vsecond = minSecond % 60000 / 1000;
		long vhour = (long) Math.floor(minSecond / 3600000);
		long vminute = (long) Math.floor(minSecond / 60000 - vhour * 60);

		String hour = String.valueOf(vhour);
		String minute = String.valueOf(vminute);
		String second = String.valueOf(vsecond);

		hour = hour.length() == 0 ? "00" : hour;
		minute = minute.length() == 0 ? "00" : minute;
		second = second.length() == 0 ? "00" : second;

		hour = hour.length() == 1 ? "0" + hour : hour;
		minute = minute.length() == 1 ? "0" + minute : minute;
		second = second.length() == 1 ? "0" + second : second;

		String str = hour + ":" + minute + ":" + second;
		return str;
	}

	public static String getDateStr(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Formatter ft = new Formatter(Locale.CHINA);
		return ft.format("%1$tY-%1$tm-%1$td %1$tT", cal).toString();
	}

	public static String longToDate(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");// 初始化Formatter的转换格式。
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		String hms = formatter.format(time);
		return hms;
	}

	/**
	 * 获取本周一
	 * 
	 * @return 本周一
	 */
	public static Date getNowWeekMonday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 解决周日会出现 并到下一周的情况
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return 本月第一天
	 */
	public static Date getFirstDayMonth() {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return calendar.getTime();
	}

	/**
	 * 获取今年第一天
	 * 
	 * @return 本月第一天
	 */
	public static Date getFirstDayYear() {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR, 1);// 设置为1号,当前日期既为本月第一天
		return calendar.getTime();
	}

	/**
	 * 通过今日/昨日/本周/本月/今年获取开始+结束时间
	 * 
	 * @return 开始时间,结束时间
	 */
	public static String getBeginAndEndTime(String shortcutTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		if (shortcutTime.equals("今日")) {
			result = sdf.format(new Date()) + "," + sdf.format(new Date());
		} else if (shortcutTime.equals("昨日")) {
			result = sdf.format(getYesterday(-1)) + "," + sdf.format(getYesterday(-1));
		} else if (shortcutTime.equals("本周")) {
			result = sdf.format(getNowWeekMonday()) + "," + sdf.format(new Date());
		} else if (shortcutTime.equals("本月")) {
			result = sdf.format(getFirstDayMonth()) + "," + sdf.format(new Date());
		} else if (shortcutTime.equals("今年")) {
			result = sdf.format(getFirstDayYear()) + "," + sdf.format(new Date());
		} else {
			result = "参数不正确";
		}
		return result;
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 返回指定日期的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return calendar.getTime();
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 日期增加-按日增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByDay(Date date, int days) {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * 获取哪一年第几周的周一/周日日期
	 * 
	 * @param string
	 *            yyyy-week 年+周
	 * @return monday+","+sunday
	 */
	@SuppressWarnings("static-access")
	public static String getMondayAndSunday(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		int weekYear = Integer.parseInt(string.split("/")[0]);
		int weekOfYear = Integer.parseInt(string.split("/")[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);// 中国习俗，周一为一周开始
		calendar.set(calendar.YEAR, weekYear);
		calendar.set(calendar.WEEK_OF_YEAR, weekOfYear);
		calendar.set(calendar.DAY_OF_WEEK, 1);
		String sunday = sdf.format(calendar.getTime());
		calendar.set(calendar.DAY_OF_WEEK, 2);
		String monday = sdf.format(calendar.getTime());
		// calendar.setWeekDate(weekYear, weekOfYear, 1);//获取周日的日期jdk1.7才有
		// String sunday =sdf.format(calendar.getTime());
		// calendar.setWeekDate(weekYear, weekOfYear, 2);//获取周一日期
		// String monday = sdf.format(calendar.getTime());
		return monday + "-" + sunday;
	}

	/**
	 * 获取某月的1号和最后一号
	 * 
	 * @param string
	 * @return 如：2014/08/01-2014/08/31
	 */
	public static String getMonthFirstLastDay(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return "参数错误";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = sdf.format(calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		String lastDay = sdf.format(calendar.getTime());
		return firstDay + "-" + lastDay;
	}

	/**
	 * 获取昨日
	 * 
	 * @param i
	 *            =-1
	 * @return 昨日
	 */
	@SuppressWarnings("static-access")
	public static Date getYesterday(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH) + i);
		return calendar.getTime();
	}

	/**
	 * 本方法完成计算两个给定的时间之差
	 * 
	 * @param startdate
	 *            给定开始时间字符串
	 * @param enddate
	 *            给定结束时间字符串
	 * @param fmt
	 *            给定时间的格式
	 * @param refmt
	 *            ms毫秒 s秒 m分 h小时 d天
	 * @return 时间差，单位由refmt决定
	 */
	public static String cntTimeDifference(String startdate, String enddate, String fmt, String refmt) {
		String ret = null;
		try {
			if ((startdate == null) || (enddate == null) || (fmt == null) || (refmt == null)) {
				return ret;
			}
			Date scal = getCal(startdate, fmt).getTime();
			Date ecal = getCal(enddate, fmt).getTime();
			if ((scal == null) || (ecal == null)) {
				return ret;
			}

			if (scal.after(ecal)) {
				return ret;
			}

			long diffMillis = ecal.getTime() - scal.getTime();

			long diffSecs = diffMillis / 1000L;

			long diffMins = diffMillis / 60000L;

			long diffHours = diffMillis / 3600000L;

			long diffDays = diffMillis / 86400000L;

			if (refmt.equals("ms"))
				ret = Long.toString(diffMillis);
			else if (refmt.equals("s"))
				ret = Long.toString(diffSecs);
			else if (refmt.equals("m"))
				ret = Long.toString(diffMins);
			else if (refmt.equals("h"))
				ret = Long.toString(diffHours);
			else if (refmt.equals("d"))
				ret = Long.toString(diffDays);
			else
				ret = Long.toString(diffHours);
		} catch (Exception e) {
		}

		return ret;
	}

	/**
	 * 格式化时间
	 * 
	 * @param strdate
	 * @param fmt
	 * @return
	 */
	private static Calendar getCal(String strdate, String fmt) {
		Calendar cal = null;
		try {
			if ((strdate == null) || (fmt == null)) {
				return cal;
			}
			SimpleDateFormat nowDate = new SimpleDateFormat(fmt);
			Date d = nowDate.parse(strdate, new ParsePosition(0));
			if (d == null) {
				return cal;
			}
			cal = Calendar.getInstance();
			cal.clear();
			cal.setTime(d);
		} catch (Exception e) {
		}

		return cal;
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @param lenient
	 *            日期越界标志
	 * @return
	 */
	public static Date stringToDate(String dateText, String format, boolean lenient) {
		if (dateText == null) {
			return null;
		}
		DateFormat df = null;
		try {
			if (format == null) {
				df = new SimpleDateFormat();
			} else {
				df = new SimpleDateFormat(format);
			}
			df.setLenient(false);
			return df.parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 */
	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {
		return stringToDate(dateString, format, LENIENT_DATE);
	}

	/**
	 * 根据时间变量返回时间字符串
	 * 
	 * @return 返回时间字符串
	 * @param pattern
	 *            时间字符串样式
	 * @param date
	 *            时间变量
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);
			return sfDate.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据时间变量返回时间字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 判断两个日期是否为同年同月
	 * 
	 * @param date1
	 *            date1
	 * @param date2
	 *            date2
	 * @return boolean
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isSameYYYYMM(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
	}

	/**
	 * 得到指定时间的周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		return dateToString(c.getTime());
	}

	/**
	 * 得到指定时间的周日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		return dateToString(c.getTime());

	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 *            日期字符串
	 * @return 前一天日期
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = stringToDate(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return dateToString(c.getTime());
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 *            日期字符串
	 * @return 后一天日期
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = stringToDate(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		return dateToString(c.getTime());
	}

	/**
	 * 查询指定时间段内的时间点集合
	 * 
	 * @param dBegin
	 *            开始时间
	 * @param dEnd
	 *            结束时间
	 * @return
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	/**
	 * 按周分组
	 * 
	 * @param starTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 * @throws ParseException
	 */
	public static List<Map<String, Object>> findWeekDate(String starTime, String endTime) throws ParseException {
		List<Map<String, Object>> maplists = new ArrayList<Map<String, Object>>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate = fmt.parse(starTime);
		Date edate = fmt.parse(endTime);
		String ksTime = getSundayOfThisWeek(sdate);// 开始时间的--周日
		String jsTime = getMondayOfThisWeek(edate);// 结束时间的--周一
		String jsTimeCopy = getSundayOfThisWeek(edate);// 结束时间周日
		// 同一周
		if (ksTime.equals(jsTimeCopy) || ksTime == jsTimeCopy) {
			datemap.put("stime", starTime);
			datemap.put("etime", endTime);
			maplists.add(datemap);
			// 非同一周
		} else {
			// 上半周
			// LogUtil.info("上半周开始   " + starTime + "  周日: " + ksTime);
			// 下半周
			// LogUtil.info("下半周结束  " + endTime + "  周一: " + jsTime);
			// 上半周
			datemap.put("stime", starTime);// 开始时间
			datemap.put("etime", ksTime);// 周日
			maplists.add(datemap);

			// 两个日期中有周
			String sundayTime = getSpecifiedDayBefore(jsTime);// 结束时间-周一的前一天【周日】
			if (!ksTime.equals(sundayTime) && ksTime != sundayTime) {
				String mondayTime = getSpecifiedDayAfter(ksTime);// 开始时间-周日的后一天【周一】
				Date ksdate = null;
				Date jsdate = null;
				try {
					ksdate = fmt.parse(mondayTime);
					jsdate = fmt.parse(sundayTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar c_begin = new GregorianCalendar();
				Calendar c_end = new GregorianCalendar();

				c_begin.setTime(ksdate);
				c_end.setTime(jsdate); // set(2015,5,3)Calendar的月从0-11，所以5月是4.
				c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

				datemap = new HashMap<String, Object>();
				while (c_begin.before(c_end)) {
					// 周一
					if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
						datemap.put("stime", fmt.format(c_begin.getTime()));
					}
					// 周日
					if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						datemap.put("etime", fmt.format(c_begin.getTime()));
					}
					if (datemap.containsKey("etime") && !datemap.isEmpty()) {
						maplists.add(datemap);
						datemap = new HashMap<String, Object>();
					}
					c_begin.add(Calendar.DAY_OF_YEAR, 1);
				}
			}
			// 下半周
			datemap = new HashMap<String, Object>();
			datemap.put("stime", jsTime);// 周一
			datemap.put("etime", endTime);// 结束时间
			maplists.add(datemap);
		}
		return maplists;
	}

	/**
	 * 按月分组
	 * 
	 * @param starTime
	 * @param endTime
	 * @return
	 */
	public static List<Map<String, Object>> findGroupByMonth(String starTime, String endTime) {
		List<Map<String, Object>> maplists = new ArrayList<Map<String, Object>>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		Date startDate = stringToDate(starTime);
		Date endDate = stringToDate(endTime);

		// 若是同一个年月内
		if (isSameYYYYMM(startDate, endDate)) {
			datemap.put("stime", starTime);
			datemap.put("etime", endTime);
			maplists.add(datemap);
		} else {
			// 上半月
			Date sDate = getLastDayOfMonth(startDate);
			String sDateStr = dateToString(sDate);// 上半月月末
			Date eDate = getFirstDayOfMonth(endDate);
			String eDateStr = dateToString(eDate);// 下半月月初

			datemap.put("stime", starTime);// 上半月开始时间
			datemap.put("etime", sDateStr);// 上半月结束时间
			maplists.add(datemap);

			Date ksTime = dateIncreaseByDay(sDate, +1);// 上半月月末+1
			String ksTimeStr = dateToString(ksTime);
			Date jsTime = dateIncreaseByDay(eDate, -1);// 下半月月初-1
			if (!StringUtil.isEmpty(ksTimeStr) && !StringUtil.isEmpty(eDateStr)) {
				// 中间有间隔月份
				if (ksTimeStr != eDateStr && !ksTimeStr.equals(eDateStr)) {
					Calendar c_begin = new GregorianCalendar();
					Calendar c_end = new GregorianCalendar();
					c_begin.setTime(ksTime);
					c_end.setTime(jsTime);
					c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
					datemap = new HashMap<String, Object>();
					// 循环添加每月月初和月末
					while (c_begin.before(c_end)) {
						datemap.put("stime", dateToString(getFirstDayOfMonth(c_begin.getTime())));// 月初
						datemap.put("etime", dateToString(getLastDayOfMonth(c_begin.getTime())));// 月末
						if (!maplists.contains(datemap)) {
							maplists.add(datemap);
							datemap = new HashMap<String, Object>();
						}
						c_begin.add(Calendar.DAY_OF_YEAR, 1);
					}

				}
			}
			// 下半月
			datemap = new HashMap<String, Object>();
			datemap.put("stime", eDateStr);// 下半月第一天
			datemap.put("etime", endTime);// 下半月结束时间
			maplists.add(datemap);

		}
		return maplists;
	}
	
}
