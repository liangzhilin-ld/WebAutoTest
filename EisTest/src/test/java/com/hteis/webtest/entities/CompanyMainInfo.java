package com.hteis.webtest.entities;

import com.hteis.webtest.common.DateUtil;

public class CompanyMainInfo {
	
	/***企业名称*/
	public String CompanyName;
	
	/***用电编号*/
	public String ElectronNo;
	
	/***目录电价*/
	public float CatalogPrice;
	
	/***销售电价*/
	public float SalePrice;
	
	/***所属行业*/
	public String Industry;
	
	/***用户类型*/
	public String UserType;
	
	/***企业性质*/
	public String CompanyType;
	
	/***企业规模*/
	public String CompanyScale;
	
	/***用电性质*/
	public String ElectronUsageType;
	
	/***高耗能行业*/
	public boolean IsHighEnergy;
	
	/***工作班制*/
	public String ShiftType;
	
	/***公休情况*/
	public String VacationType;
	
	/***电压等级*/
	public String VoltageLevel;
	
	/***主营业务/产品*/
	public String BusinessScope;
	
	/***创建大用户企业基础信息*/
	public static CompanyMainInfo createLargeCmpyInfo(){
		CompanyMainInfo mainInfo = new CompanyMainInfo();
		mainInfo.UserType = "大用户";
		mainInfo.CompanyName = DateUtil.getNoFromDateTime("四川" + mainInfo.UserType);
		mainInfo.ElectronNo = DateUtil.getNoFromDateTime().substring(4);
		mainInfo.CatalogPrice = 0.576f;
		mainInfo.SalePrice = 0.603f;
		mainInfo.Industry = "制造业";
		mainInfo.CompanyType = "国企";
		mainInfo.CompanyScale = "10000人以上";
	    mainInfo.ElectronUsageType = "大工业用电"; // 315kva以上用户
		mainInfo.IsHighEnergy = true;
		mainInfo.ShiftType = "三班制";
		mainInfo.VacationType = "正常公休";
		mainInfo.VoltageLevel = "220kV";
		mainInfo.BusinessScope = "战舰、太空母舰";
		
		return mainInfo;
	}
}
