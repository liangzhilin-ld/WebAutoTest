package com.hteis.webtest.entities;

import java.util.Date;
import com.hteis.webtest.common.DateUtil;

public class TradeFairData {
	
	/***交易中心*/
	public String TradeCenter;
	
	/***状态*/
	public String Status;
	
	/***交易场次名称*/
	public String Name;
	
	/***交易规模*/
	public int TradeAmount;
	
	/***交易周期*/
	public String TradePeriod;
	
	/***交易品种*/
	public String TradeType;
	
	/***交易日期*/
	public Date TradeDate;
	
	/***交易年份*/
	public String TradeYear;
	
	/***交易年月*/
	public String TradeMonth;
	
	/***交易截至年月*/
	public String TradeEndMonth;
	
	/***一共包括多少个月*/
	public int MonthCount;
	
	/***交易开始时间*/
	public Date TradeStartDateTime;
	
	/***交易结束时间*/
	public Date TradeEndDateTime;
	
	/***供应方数量*/
	public int SupplyCount;
	
	/***需求方数量*/
	public int BuyerCount;
	
	/***允许客户申报*/
	public boolean AllowDeclare;
	
	/***申报开始时间*/
	public Date DeclareStartDate;
	
	/***申报结束时间*/
	public Date DeclareEndDate;
	
	/***录入人*/
	public String Creator;
	
	/***录入时间*/
	public Date CreateDateTime;
	
	/***创建月度竞价交易数据*/
	public static TradeFairData createMonthBidData(String tradeCenter) throws Exception{
		TradeFairData data = new TradeFairData();
		String nextMonth = DateUtil.getCNYearMonthStr(DateUtil.addMonth(new Date(), 1));
		data.TradeCenter = tradeCenter;
		data.Name = String.format("(%s)月度竞价%s", nextMonth, DateUtil.getNoFromDateTime());
		data.TradeAmount = 500;
		data.TradePeriod = "月度";
		data.TradeType = "竞价";
		data.TradeMonth = nextMonth;
		data.MonthCount = 1;
		data.TradeDate = DateUtil.getThisMonthLastDate();
		data.TradeStartDateTime = DateUtil.getDateTimeFromString(DateUtil.getCNDateStr(data.TradeDate) + " 09:00:00");
		data.TradeEndDateTime = DateUtil.getDateTimeFromString(DateUtil.getCNDateStr(data.TradeDate) + " 15:00:00");
		data.DeclareStartDate = new Date();
		data.DeclareEndDate = DateUtil.getThisMonthLastDate();
		data.AllowDeclare = true;
		data.SupplyCount = 20;
		data.BuyerCount = 200;
		data.Creator = Constants.tradeTestName;
		data.CreateDateTime = new Date();
		data.Status = "编辑";
		
		return data;
	}
	
	public static void updateMonthBidData(TradeFairData data){
		data.Name = data.Name + "new";
		data.TradeAmount = data.TradeAmount * 2;
		data.TradeMonth = DateUtil.getCNYearMonthStr(DateUtil.addMonth(new Date(), 2));
		data.TradeDate = DateUtil.addMonth(data.TradeDate, 1);
		data.TradeStartDateTime = DateUtil.addMonth(data.TradeStartDateTime, 1);
		data.TradeEndDateTime = DateUtil.addMonth(data.TradeEndDateTime, 1);
		data.SupplyCount = data.SupplyCount*2;
		data.BuyerCount = data.BuyerCount*2;
		data.DeclareStartDate = DateUtil.addMonth(data.DeclareStartDate, 1);
		data.DeclareEndDate = DateUtil.addMonth(data.DeclareEndDate, 1);
	}
	
	/***创建年度协商交易数据*/
	public static TradeFairData createYearNegotiateData(String tradeCenter) throws Exception{
		TradeFairData data = new TradeFairData();
		int nextYear = DateUtil.getThisYear() + 1;
		data.TradeCenter = tradeCenter;
		data.Name = String.format("(%d)年度协商%s", nextYear, DateUtil.getNoFromDateTime());
		data.TradeAmount = 5000;
		data.TradePeriod = "年度";
		data.TradeYear = Integer.toString(nextYear);
		data.TradeType = "协商";		
		data.TradeDate = DateUtil.getDateFromString(DateUtil.getThisYearStr() + "-12-30");
		data.TradeStartDateTime = DateUtil.getDateTimeFromString(DateUtil.getThisYearStr() + "-12-30 09:00:00");
		data.TradeEndDateTime = DateUtil.getDateTimeFromString(DateUtil.getThisYearStr() + "-12-31 15:00:00");
		data.Creator = Constants.tradeTestName;
		data.CreateDateTime = new Date();
		data.Status = "编辑";
		
		return data;
	}
	
	public static void updateYearNegotiateData(TradeFairData data){
		data.Name = data.Name + "new";
		data.TradeAmount = data.TradeAmount * 2;
		data.TradeYear = Integer.toString(DateUtil.getThisYear() + 2);
		data.TradeDate = DateUtil.addYear(data.TradeDate, 1);
		data.TradeStartDateTime = DateUtil.addYear(data.TradeStartDateTime, 1);
		data.TradeEndDateTime = DateUtil.addYear(data.TradeEndDateTime, 1);
	}
	
	/***创建多月竞价交易数据*/
	public static TradeFairData createMultiMonthBidData(String tradeCenter, int monthCount) throws Exception{
		TradeFairData data = new TradeFairData();
		String nextMonth = DateUtil.getCNYearMonthStr(DateUtil.addMonth(new Date(), 1));
		data.TradeCenter = tradeCenter;
		data.Name = String.format("(%s)多月竞价%s", nextMonth, DateUtil.getNoFromDateTime());
		data.TradeAmount = 500;
		data.TradePeriod = "多月";
		data.TradeType = "竞价";
		data.TradeMonth = nextMonth;
		data.MonthCount = monthCount;
		data.TradeEndMonth = DateUtil.getCNYearMonthStr(DateUtil.addMonth(new Date(), data.MonthCount));
		data.TradeDate = DateUtil.getThisMonthLastDate();
		data.TradeStartDateTime = DateUtil.getDateTimeFromString(DateUtil.getCNDateStr(data.TradeDate) + " 09:00:00");
		data.TradeEndDateTime = DateUtil.getDateTimeFromString(DateUtil.getCNDateStr(data.TradeDate) + " 15:00:00");
		data.DeclareStartDate = new Date();		
		data.DeclareEndDate = DateUtil.getThisMonthLastDate();
		data.AllowDeclare = true;
		data.SupplyCount = 30;
		data.BuyerCount = 300;
		data.Creator = Constants.tradeTestName;
		data.CreateDateTime = new Date();
		data.Status = "编辑";
		
		return data;
	}
	
	/***创建一个3个月的多月交易场次*/
	public static TradeFairData createMultiMonthBidData(String tradeCenter) throws Exception{
		return createMultiMonthBidData(tradeCenter, 3);
	}
	
	public static void updateMultiMonthBidData(TradeFairData data){
		data.Name = data.Name + "new";
		data.TradeAmount = data.TradeAmount * 2;
		data.TradeMonth = DateUtil.getCNYearMonthStr(DateUtil.addMonth(new Date(), 2));
		data.TradeEndMonth = DateUtil.getCNYearMonthStr(DateUtil.addMonth(new Date(), 4));
		data.TradeDate = DateUtil.addMonth(data.TradeDate, 1);
		data.TradeStartDateTime = DateUtil.addMonth(data.TradeStartDateTime, 1);
		data.TradeEndDateTime = DateUtil.addMonth(data.TradeEndDateTime, 1);
		data.SupplyCount = data.SupplyCount*2;
		data.BuyerCount = data.BuyerCount*2;
		data.DeclareStartDate = DateUtil.addMonth(data.DeclareStartDate, 1);
		data.DeclareEndDate = DateUtil.addMonth(data.DeclareEndDate, 1);
	}
}
