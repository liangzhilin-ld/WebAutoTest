package com.hteis.webtest.entities;

public class CompanyData {
	
	public UserInfo Admin;
	
	public CompanyMainInfo MainInfo;
	
	public CompanyRegInfo RegInfo;
	
	public CompanyBankAccInfo BankAccInfo;
	
	public CompanyContactInfo ContactInfo;
	
	public CompanyEleInfo EleInfo;
	
	public static CompanyData createCompanyData() throws Exception{
		
		CompanyData companyData = new CompanyData();
		companyData.MainInfo = CompanyMainInfo.createLargeCmpyInfo();
		companyData.RegInfo = CompanyRegInfo.createCompanyRegInfo();
		companyData.BankAccInfo = CompanyBankAccInfo.createBankData();
		companyData.BankAccInfo.CustName = companyData.MainInfo.CompanyName;
		companyData.EleInfo = CompanyEleInfo.createLargeElectroData();
		companyData.ContactInfo = CompanyContactInfo.create();
		
		return companyData;
	}
}
