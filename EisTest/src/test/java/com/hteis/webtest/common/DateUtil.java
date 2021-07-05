package com.hteis.webtest.common;

import java.util.*;
import java.text.*;

public class DateUtil {
	public static final String CNDateformat = "yyyy-MM-dd";
	public static final String CNDateTimeformat = "yyyy-MM-dd HH:mm:ss";
	public static final String CNDateTimeShortformat = "yyyy-MM-dd HH:mm";
	public static final String CNYearMonthFormat = "yyyy-MM";
	
	public static String getNoFromDateTime(String prefix) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return prefix + format.format(new Date());
	}
	
	public static String getNoFromDateTime() {
		return getNoFromDateTime("");
	}
	
	public static String getShortNoFromTime(String prefix) {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		return prefix + format.format(new Date());
	}
	
	/***用时间获取唯一手机号*/
	public static String getUniquePhone(){
		return getNoFromDateTime().substring(3);
	}
	
	/***用时间获取唯一身份证号*/
	public static String getUniqueId(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		return "325435"  + format.format(new Date());
	}

	public static String getCNTodayStr() {
		SimpleDateFormat format = new SimpleDateFormat(CNDateformat);
		return format.format(new Date());
	}
	
	public static String getThisYearStr(){
		return getCNTodayStr().substring(0, 4);
	}

	public static String getCNNextYearTodayStr() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 1);		

		SimpleDateFormat format = new SimpleDateFormat(CNDateformat);
		return format.format(calendar.getTime());
	}
	
//	public static String getCNNextMonthTodayStr(){
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		calendar.add(Calendar.DAY_OF_MONTH, 1);		
//
//		SimpleDateFormat format = new SimpleDateFormat(CNDateformat);
//		return format.format(calendar.getTime());
//	}
	

	public static Date addMonth(Date date, int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);	
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	
	public static Date addYear(Date date, int years){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);	
		calendar.add(Calendar.YEAR, years);
		return calendar.getTime();
	}	

	public static Date getThisMonthLastDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());		
		calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
		return calendar.getTime();
	}
	
	public static Date getThisMonthSecondLastDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());		
		calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE) - 1);
		return calendar.getTime();
	}
	
	public static Date getTodayZeroDateTime() throws ParseException{
		return getDateTimeFromString(getCNTodayStr() + " 00:00:00");
	}
	
	public static String getCNNextYearNextDayStr(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 1);	
		calendar.add(Calendar.DAY_OF_MONTH, 1);		

		SimpleDateFormat format = new SimpleDateFormat(CNDateformat);
		return format.format(calendar.getTime());
	}
	
	public static Date getDateFromString(String dateStr) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(CNDateformat);
		return format.parse(dateStr);
	}
	
	public static Date getDateTimeFromString(String dateTimeStr) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(CNDateTimeformat);
		return format.parse(dateTimeStr);
	}	
	
	public static String getCNDateStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat(CNDateformat);
		return format.format(date);
	}
	
	public static String getCNYearMonthStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat(CNYearMonthFormat);
		return format.format(date);
	}
	
	public static String getCNTimeStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}
	
	public static String getCNDateTimeStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat(CNDateTimeformat);
		return format.format(date);
	}
	
	public static String getCNDateTimeShortStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat(CNDateTimeShortformat);
		return format.format(date);
	}
	
	public static String getCurrentDateTimeStr(){
		SimpleDateFormat format = new SimpleDateFormat(CNDateTimeformat);
		return format.format(new Date());
	}
	
	public static int getThisYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}	

	public static int getMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/***判断两个时间是否相近--相差在2分钟之内*/
	public static boolean isCloseTime(Date time1, Date time2){
		return Math.abs(time1.getTime() - time2.getTime()) < 1000 * 60 * 2;
	}
	
	/***判断两个时间是否相近--相差在2分钟之内*/
	public static boolean isCloseTime(String time1Str, Date time2){
		Date time1;
		try{
			time1 = DateUtil.getDateTimeFromString(time1Str);
		} catch(ParseException e){
			return false;
		}
		
		return Math.abs(time1.getTime() - time2.getTime()) < 1000 * 60 * 2;
	}
	
	/***获得下月所在年份*/
	public static int getNextMonthYear(){
		int thisYear = getThisYear();
		return getMonth(new Date()) == 12 ? thisYear + 1 : thisYear;
	}
	
}
