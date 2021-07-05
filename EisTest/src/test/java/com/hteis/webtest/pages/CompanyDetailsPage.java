package com.hteis.webtest.pages;

import org.testng.Assert;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;

public class CompanyDetailsPage extends PageBase {
	
	public HtmlTable getMainInfoTable(){
		return this.findElementByCss("table:first-child").toHtmlTable();
	}
	
	public HtmlButton getBackBtn(){
		return this.findElementByCss("button[ng-show='showPre']").toHtmlButton();
	}
	
	public HtmlTable getRegInfoTable(){
		return this.findElementByCss("table:nth-child(2)").toHtmlTable();
	}
	
	public HtmlTable getAccInfoTable(){
		return this.findElementByCss("table:nth-child(3)").toHtmlTable();
	}
	
	public HtmlTable getContactInfoTable(){
		return this.findElementByCss("table:nth-child(4)").toHtmlTable();
	}
	
	public HtmlTable getAttachmentTable(){
		return this.findElementByCss("table:last-child").toHtmlTable();
	}
	
	public HtmlTable getEleInfoTable(int no){
		return this.findElementsByTag("table").get(3+no).toHtmlTable();
	}
	
	public void verifyMainInfo(CompanyMainInfo mainInfo){
		HtmlTable mainInfoTable = this.getMainInfoTable();
	    Assert.assertEquals(mainInfoTable.getCellValue(1, 2), mainInfo.CompanyName);
	    Assert.assertEquals(mainInfoTable.getCellValue(1, 4), mainInfo.ElectronNo);
	    Assert.assertEquals(mainInfoTable.getCellValue(2, 2), mainInfo.UserType);
	    Assert.assertEquals(mainInfoTable.getCellValue(2, 4), Float.toString(mainInfo.SalePrice));
	    Assert.assertEquals(mainInfoTable.getCellValue(3, 2), mainInfo.CompanyType);
	    Assert.assertEquals(mainInfoTable.getCellValue(3, 4), mainInfo.CompanyScale);
	    Assert.assertEquals(mainInfoTable.getCellValue(4, 2), mainInfo.ElectronUsageType);
	    Assert.assertEquals(mainInfoTable.getCellValue(4, 4), mainInfo.IsHighEnergy?"是":"否");
	    Assert.assertEquals(mainInfoTable.getCellValue(5, 2), mainInfo.Industry);
	    Assert.assertEquals(mainInfoTable.getCellValue(5, 4), mainInfo.BusinessScope);
	    Assert.assertEquals(mainInfoTable.getCellValue(6, 2), mainInfo.ShiftType);
	    Assert.assertEquals(mainInfoTable.getCellValue(6, 4), mainInfo.VacationType);
	}
	
	public void verifyRegInfo(CompanyRegInfo regInfo){
		HtmlTable regInfoTable = this.getRegInfoTable();
	    Assert.assertEquals(regInfoTable.getCellValue(1, 2), regInfo.RegisterNo);
	    Assert.assertEquals(regInfoTable.getCellValue(1, 4), regInfo.RegLocation);
	    Assert.assertEquals(regInfoTable.getCellValue(2, 2), DateUtil.getCNDateStr(regInfo.LicenseExpireDate));
	    Assert.assertEquals(regInfoTable.getCellValue(2, 4), regInfo.Address);
	    Assert.assertEquals(regInfoTable.getCellValue(3, 2), regInfo.Telephone);
	    Assert.assertEquals(regInfoTable.getCellValue(3, 4), regInfo.BusinessScope);
	    Assert.assertEquals(regInfoTable.getCellValue(4, 4), regInfo.Fax);
	    Assert.assertEquals(regInfoTable.getCellValue(5, 2), regInfo.LegalPersonName);
	    Assert.assertEquals(regInfoTable.getCellValue(5, 4), regInfo.LegalPersonId);
	    Assert.assertEquals(regInfoTable.getCellValue(6, 2), regInfo.IdType == IdentityType.SecondGenIdentity ? "二代身份证":"临时身份证");
	    Assert.assertEquals(regInfoTable.getCellValue(6, 4), regInfo.MobilePhone);
	}
	
	public void verifyAccInfo(CompanyBankAccInfo accInfo){
		HtmlTable accTable = this.getAccInfoTable();
		Assert.assertEquals(accTable.getCellValue(1, 2), accInfo.CustName);
		Assert.assertEquals(accTable.getCellValue(1, 4), accInfo.BankName);
		//Assert.assertEquals(accTable.getCellValue(2, 2), accInfo.City);
		Assert.assertEquals(accTable.getCellValue(2, 4), accInfo.BankBranchName);
		Assert.assertEquals(accTable.getCellValue(3, 2), accInfo.BankAccNo);
	}
	
	public void verifyContactInfo(CompanyContactInfo contactInfo){
		HtmlTable contactTable = this.getContactInfoTable();
	    Assert.assertEquals(contactTable.getCellValue(1, 2), contactInfo.Name);
	    Assert.assertEquals(contactTable.getCellValue(1, 4), contactInfo.Sexuality == Gender.Male ? "男" : "女");
	    Assert.assertEquals(contactTable.getCellValue(2, 2), contactInfo.IdentityNo);
	    Assert.assertEquals(contactTable.getCellValue(2, 4), contactInfo.Department);
	    Assert.assertEquals(contactTable.getCellValue(3, 2), contactInfo.Position);
	    Assert.assertEquals(contactTable.getCellValue(3, 4), contactInfo.MobilePhone);
	    Assert.assertEquals(contactTable.getCellValue(4, 2), contactInfo.Telephone);
	    Assert.assertEquals(contactTable.getCellValue(4, 4), contactInfo.Email);
	    Assert.assertEquals(contactTable.getCellValue(5, 2), contactInfo.Fax);
	}
	
	public void verifyEleInfo(int eleInfoNo, CompanyEleInfo eleInfo){
		HtmlTable table = this.getEleInfoTable(eleInfoNo);
		Assert.assertEquals(table.getCellValue(1, 2), Integer.toString(eleInfo.MainCapacity));
		Assert.assertEquals(table.getCellValue(1, 4), Integer.toString(eleInfo.ReserveCapacity));
		Assert.assertEquals(table.getCellValue(2, 2), Integer.toString(eleInfo.RunCapacity));
		Assert.assertEquals(table.getCellValue(2, 4), Integer.toString(eleInfo.ControlCapacity));
		Assert.assertEquals(table.getCellValue(3, 2), eleInfo.SubstationName);
		Assert.assertEquals(table.getCellValue(3, 4), eleInfo.LineName);
		Assert.assertEquals(table.getCellValue(4, 2), Integer.toString(eleInfo.LineVoltage));
		Assert.assertEquals(table.getCellValue(4, 4), Integer.toString(eleInfo.YearlyConsume));
		Assert.assertEquals(table.getCellValue(5, 2), Integer.toString(eleInfo.MaxLoad));
		Assert.assertEquals(table.getCellValue(5, 4), Integer.toString(eleInfo.ProtectiveLoad));
		Assert.assertEquals(table.getCellValue(6, 2), eleInfo.Electrician);
		Assert.assertEquals(table.getCellValue(6, 4), eleInfo.DutyPhone);
		Assert.assertEquals(table.getCellValue(7, 2), eleInfo.ElectroFeature);
		Assert.assertEquals(table.getCellValue(7, 4), eleInfo.SettleMethod);
	}
	
	public void verifyCompanyInfo(CompanyData companyData){
		this.verifyMainInfo(companyData.MainInfo);
		this.verifyRegInfo(companyData.RegInfo);
		this.verifyAccInfo(companyData.BankAccInfo);
		this.verifyContactInfo(companyData.ContactInfo);
		this.verifyEleInfo(1, companyData.EleInfo);
	}
	
}
