package com.hteis.webtest.entities;

import com.hteis.webtest.common.DateUtil;

public class CompanyBankAccInfo {

	public String CustName;
	
	public String BankName;
	
	public String City;
	
	public String BankBranchName;
	
	public String BankAccNo;
	
	public static CompanyBankAccInfo createBankData(){
		CompanyBankAccInfo bankInfo = new CompanyBankAccInfo();
		bankInfo.BankName = "招商银行";
		bankInfo.City = "北京";
		bankInfo.BankBranchName = "上地支行";
		bankInfo.BankAccNo = "622" + DateUtil.getNoFromDateTime();
		
		return bankInfo;
	}
	
}
