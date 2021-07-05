package com.hteis.webtest.entities;

public class CompanyEleInfo {

	/***主变容量*/
	public int MainCapacity;
	
	/***备用容量*/
	public int ReserveCapacity;
	
	/***运行容量*/
	public int RunCapacity;
	
	/***可控容量*/
	public int ControlCapacity;
	
	/***所属变电站*/
	public String SubstationName;
	
	/***所属线路*/
	public String LineName;
	
	/***供电电压*/
	public int LineVoltage;
	
	/***年用电量*/
	public int YearlyConsume;
	
	/***最大负荷*/
	public int MaxLoad;
	
	/***保安负荷*/
	public int ProtectiveLoad;
	
	/***企业电工*/
	public String Electrician;
	
	/***值班电话(电工)*/
	public String DutyPhone;
	
	/***用电特性*/
	public String ElectroFeature;
	
	/***电费结算方式*/
	public String SettleMethod;
	
	/***电费结算单附件名称*/
	public String AttachmentFileName;
	
	public static CompanyEleInfo createLargeElectroData(){
		CompanyEleInfo info = new CompanyEleInfo();
		info.MainCapacity = 1000;
		info.ReserveCapacity = 10;
		info.RunCapacity = 800;
		info.ControlCapacity = 300;
		info.SubstationName = "北极变电站";
		info.LineName = "北极变电站1线";
		info.LineVoltage = 220;
		info.YearlyConsume = 500;
		info.MaxLoad = 400;
		info.ProtectiveLoad = 40;
		info.Electrician = "张三疯";
		info.DutyPhone = "010-29376484";
		info.ElectroFeature = "峰谷性";
		info.SettleMethod = "需量结算";
		info.AttachmentFileName = "SettleSheet1.jpg";
		
		return info;
	}
	
	public static CompanyEleInfo createSmallElectroData(){
		CompanyEleInfo info = new CompanyEleInfo();
		info.MainCapacity = 100;
		info.ReserveCapacity = 1;
		info.RunCapacity = 80;
		info.ControlCapacity = 30;
		info.SubstationName = "南极变电站";
		info.LineName = "南极变电站1线";
		info.LineVoltage = 110;
		info.YearlyConsume = 50;
		info.MaxLoad = 40;
		info.ProtectiveLoad = 4;
		info.Electrician = "王重阳";
		info.DutyPhone = "010-345666484";
		info.ElectroFeature = "峰谷性";
		info.SettleMethod = "容量结算";
		info.AttachmentFileName = "SettleSheet2.jpg";
		
		return info;
	}
}
