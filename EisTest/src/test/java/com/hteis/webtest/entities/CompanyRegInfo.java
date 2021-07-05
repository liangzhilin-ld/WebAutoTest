package com.hteis.webtest.entities;

import java.util.Date;
import com.hteis.webtest.common.*;

public class CompanyRegInfo {
	
	/***营业执照注册号*/
	public String RegisterNo;
	
	/***营业执照所在地*/
	public String RegLocation;
	
	/***营业期限*/
	public Date LicenseExpireDate;
	
	/***组织机构代码*/
	public String OrganiztionNo;
	
	/***常用地址*/
	public String Address;
	
	/***固定电话*/
	public String Telephone;
	
	/***营业执照副本扫描件文件名*/
	public String LicenseCopyFileName;
	
	/***传真*/
	public String Fax;
	
	/***营业范围*/
	public String BusinessScope;
	
	/***法人代表真实姓名*/
	public String LegalPersonName;
	
	/***身份证号*/
	public String LegalPersonId;
	
	/***身份证类型*/
	public IdentityType IdType;

	/***手机号码*/
	public String MobilePhone;
	
	/***身份证照片正面文件名*/
	public String IdFrontCopyFileName;
	
	/***身份证照片反面文件名*/
	public String IdBackCopyFileName;
	
	public static CompanyRegInfo createCompanyRegInfo() throws Exception{
		CompanyRegInfo regInfo = new CompanyRegInfo();
		regInfo.RegisterNo = DateUtil.getNoFromDateTime();
		regInfo.RegLocation = "四川成都";
		regInfo.LicenseExpireDate = DateUtil.getDateFromString("2099-12-31");
		regInfo.OrganiztionNo = DateUtil.getNoFromDateTime("ZZJG");
		regInfo.Address = "四川省成都市西城区xx大街xxx号";
		regInfo.Telephone = "010-12345678";
		regInfo.LicenseCopyFileName = "License.jpg";
		regInfo.Fax = "010-12345679";
		regInfo.BusinessScope = "武器";
		regInfo.LegalPersonName = "秦飞羽";
		regInfo.LegalPersonId = DateUtil.getUniqueId();
		regInfo.IdType = IdentityType.SecondGenIdentity;
		regInfo.MobilePhone = DateUtil.getUniquePhone();
		regInfo.IdFrontCopyFileName = "IdFront.jpg";
		regInfo.IdBackCopyFileName = "IdBack.jpg";
		
		return regInfo;
	}	
}
