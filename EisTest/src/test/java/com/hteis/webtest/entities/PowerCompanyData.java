package com.hteis.webtest.entities;

import java.util.Date;

public class PowerCompanyData {
	
	/***基础信息*/
	public PowerCompanyMainInfo MainInfo;
	
	/***联系人信息*/
	public CompanyContactInfo ContactInfo;
	
	/***发电信息*/
	public PowerCompanyEleInfo EleInfo;
	
	/***状态*/
	public String Status;
	
	/***提交审核的时间*/
	public Date SubmitTime;
	
	/***通过审核的时间*/
	public Date ApproveTime;
	
	public static PowerCompanyData create(){
		PowerCompanyData data = new PowerCompanyData();
		data.MainInfo = PowerCompanyMainInfo.create();
		data.ContactInfo = CompanyContactInfo.createForPowerCompany();
		data.EleInfo = PowerCompanyEleInfo.create();
		data.Status = "待审核";		
		
		return data;
	}
	
	public void update(){
		this.MainInfo.update();
		this.ContactInfo.updateForPowerCompany();
		this.EleInfo.update();
	}

}
