package com.cshuig.tool.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具类。
 * 
 */
public class DateUtil {
	
	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (pattern == null) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return (sdf.format(date));
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 以前yyyy-MM-dd HH:mm:ss刘柱更改yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String formatDateTime() {
		return (formatDate(now(), "yyyyMMddHHmmssSSS"));
	}
	
	/**
	 * @return
	 */
	public static String formatDateTime2() {
		return (formatDate(now(), "yyyyMMddHHmmss"));
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return (formatDate(date, "yyyy-MM-dd"));
	}
	/**
	 * @param date
	 * @return
	 */
	public static String formatDate2(Date date) {
		return (formatDate(date, "yyyyMMdd"));
	}
	
	/**
	 * @return
	 */
	public static String formatDate() {
		return (formatDate(now(), "yyyy-MM-dd"));
	}
	
	/**
	 * @return
	 */
	public static String formatDate2() {
		return (formatDate(now(), "yyyyMMdd"));
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return (formatDate(date, "HH:mm:ss"));
	}
	
	/**
	 * @return
	 */
	public static String formatTime() {
		return (formatDate(now(), "HH:mm:ss"));
	}
	
	/**
	 * @return
	 */
	public static String formatTime2() {
		return (formatDate(now(), "HHmmss"));
	}
	
	/**
	 * @return
	 */
	public static Date now() {
		return (new Date());
	}
	
	/**
	 * @param datetime
	 * @return
	 */
	public static Date parseDateTime(String datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ((datetime == null) || (datetime.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(datetime);
			} catch (ParseException e) {
				return parseDate(datetime);
			}
		}
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static Date parseDate2(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	/**
	 * @param datetime
	 * @return
	 */
	public static Date parseDate(Date datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (datetime == null) {
			return null;
		} else {
			try {
				return formatter.parse(formatter.format(datetime));
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	/**
	 * @param o
	 * @return
	 */
	public static String formatDate(Object o) {
		if (o == null) {
			return "";
		}
		if (o.getClass() == String.class) {
			return formatDate((String) o);
		} else if (o.getClass() == Date.class) {
			return formatDate((Date) o);
		} else if (o.getClass() == Timestamp.class) {
			return formatDate(new Date(((Timestamp) o).getTime()));
		} else {
			return o.toString();
		}
	}
	
	/**
	 * @param o
	 * @return
	 */
	public static String formatDateTime(Object o) {
		if (o.getClass() == String.class) {
			return formatDateTime((String) o);
		} else if (o.getClass() == Date.class) {
			return formatDateTime((Date) o);
		} else if (o.getClass() == Timestamp.class) {
			return formatDateTime(new Date(((Timestamp) o).getTime()));
		} else {
			return o.toString();
		}
	}
	
	/**
	 * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param field
	 *            时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br> *
	 *            Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减。
	 * @return 变动后的时间
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	
	/**
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}
	
	/**
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}
	
	/**
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMiunte(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}
	
	/**
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}
	
	/**
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}
	
	/**
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}
	
	/**
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}
	
	/**
	 * @return
	 */
	public static Date getDate() {
		return parseDate(formatDate2());
	}
	
	/**
	 * @return
	 */
	public static Date getDateTime() {
		return parseDateTime(formatDateTime());
	}
	
	/**
	 * 日期格式转换,从YYYYMMdd格式转换成YYYY-MM-dd
	 * 
	 * @param dateString
	 * @return
	 */
	public static String parseDateString(String dateString) {
		return formatDate(parseDate2(dateString));
	}
	/**
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
    public static String formatTimeStamp(String dat, String tim,int len) { 
        SimpleDateFormat df = new SimpleDateFormat(dat); 
        if (tim.length()>len) {
        	return df.format(new Long(tim));
        }
        return tim;
    } 
}
