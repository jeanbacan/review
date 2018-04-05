package br.com.vivo.bcm.business.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public long getDifferenceInSeconds(long initialMs, long finalMs) {
		return (finalMs - initialMs) / 1000;
	}

	public long getDifferenceInMinutes(long initialMs, long finalMs) {
		return (finalMs - initialMs) / 1000 / 60;
	}

	public Date getInitialDateHourOfTheDay() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		return cal.getTime();
	}

	public Date addMinutesToDate(int minutes) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}
	
	public static Date addDaysToDate(int days) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	public static String getFormattedDate(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
}