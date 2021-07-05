package com.hteis.webtest.entities;

import java.util.Random;

import com.hteis.webtest.common.DateUtil;

public class CompanyContactInfo {

	/***姓名*/
	public String Name;
	
	/***性别*/
	public Gender Sexuality;
	
	/***身份证号*/
	public String IdentityNo;
	
	/***身份证照片正面文件名*/
	public String IdFrontCopyFileName;
	
	/***身份证照片反面文件名*/
	public String IdBackCopyFileName;
	
	/***部门*/
	public String Department;
	
	/***职务/职称*/
	public String Position;
	
	/***手机号码*/
	public String MobilePhone;
	
	/***固定电话*/
	public String Telephone;
	
	/***邮箱*/
	public String Email;
	
	/***传真号码*/
	public String Fax;
	
	/***委托书文件名称*/
	public String DelegateClaimFileName;
	
	/***企业地址*/
	public String CompanyAddress;
	
	/***企业电话*/
	public String CompanyTel;	
	
	/***创建购电企业联系人信息*/
	public static CompanyContactInfo create(){
		CompanyContactInfo contactInfo = new CompanyContactInfo();
		contactInfo.Name = "罗玉凤";
		contactInfo.Sexuality = Gender.Male;
		contactInfo.IdentityNo = DateUtil.getUniqueId();
		contactInfo.IdFrontCopyFileName = "ContactIdFront.jpg";
		contactInfo.IdBackCopyFileName = "ContactIdBack.jpg";
		contactInfo.Department = "战略规划部";
		contactInfo.Position = "总经理";
		contactInfo.MobilePhone = DateUtil.getUniquePhone();
		contactInfo.Telephone = "010-12345888";
		contactInfo.Email = contactInfo.MobilePhone + "@qq.com";
		contactInfo.Fax = "010-12345777";
		contactInfo.DelegateClaimFileName = "DelegateClaim.jpg";
		
		return contactInfo;
	}
	
	/***创建发电企业联系人信息*/
	public static CompanyContactInfo createForPowerCompany(){
		CompanyContactInfo contactInfo = new CompanyContactInfo();		
		contactInfo.Name = Constants.names[new Random().nextInt(Constants.names.length)];
		contactInfo.Sexuality = Gender.Female;
		contactInfo.Department = "公关部";
		contactInfo.Position = "公关经理";
		contactInfo.MobilePhone = DateUtil.getUniquePhone();
		contactInfo.Telephone = "050-12345999";
		contactInfo.Email = contactInfo.MobilePhone + "@qq.com";
		contactInfo.Fax = "050-12345999";
		
		contactInfo.CompanyAddress = "四川省成都市" + DateUtil.getNoFromDateTime() + "大街";
		contactInfo.CompanyTel = "050-83737636";
		
		return contactInfo;
	}
	
	public void updateForPowerCompany(){
		this.Name = this.Name + "二";
		this.Sexuality = Gender.Male;
		this.Department = "公关部二";
		this.Position = "公关经理二";
		this.MobilePhone = DateUtil.getUniquePhone();
		this.Telephone = "050-12345000";
		this.Email = this.MobilePhone + "@qq.com";
		this.Fax = "050-12345111";
		
		this.CompanyAddress = "四川省成都市" + DateUtil.getNoFromDateTime() + "大街";
		this.CompanyTel = "050-83738777";
	}
	
}
