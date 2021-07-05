package com.hteis.webtest.pages.cust;

import org.testng.*;

import com.hteis.webtest.entities.Gender;
import com.hteis.webtest.entities.PowerCompanyData;
import com.hteis.webtest.selenium.*;

public class PowerCompanyDetailsDialog extends HtmlPage{
	
	public HtmlElement getContainer(){
		return this.findElementByClass("modal-body");
	}
	
	public HtmlTable getMainInfoTable(){
		return this.getContainer().findElementByCss("div table:first-child").toHtmlTable();
	}
	
	public HtmlTable getContactInfoTable(){
		return this.getContainer().findElementByCss("div table:nth-child(2)").toHtmlTable();
	}
	
	public HtmlTable getEleInfoTable(){
		return this.getContainer().findElementByCss("div table:nth-child(3)").toHtmlTable();
	}
	
	public void verifyData(PowerCompanyData data){
		HtmlTable mainInfoTable = this.getMainInfoTable();
		Assert.assertEquals(mainInfoTable.getCell(1, 2).getText(), data.MainInfo.Number, "企业编号数据错误");
		Assert.assertEquals(mainInfoTable.getCell(1, 4).getText(), data.MainInfo.Name, "企业名称数据错误");
		Assert.assertEquals(mainInfoTable.getCell(2, 2).getText(), data.MainInfo.CompanyType, "企业性质数据错误");
		Assert.assertEquals(mainInfoTable.getCell(2, 4).getText(), data.MainInfo.PowerType, "发电类型数据错误");
		Assert.assertEquals(mainInfoTable.getCell(3, 2).getText(), data.MainInfo.Scale, "企业规模数据错误");
		Assert.assertEquals(mainInfoTable.getCell(3, 4).getText(), data.MainInfo.District, "所属区域数据错误");
		
		HtmlTable contactTable = this.getContactInfoTable();
		Assert.assertEquals(contactTable.getCell(1, 2).getText(), data.ContactInfo.CompanyAddress, "企业地址数据错误");
		Assert.assertEquals(contactTable.getCell(1, 4).getText(), data.ContactInfo.CompanyTel, "企业电话数据错误");
		Assert.assertEquals(contactTable.getCell(2, 2).getText(), data.ContactInfo.Fax, "企业传真数据错误");
		Assert.assertEquals(contactTable.getCell(2, 4).getText(), data.ContactInfo.Name, "主联系人姓名 数据错误");
		Assert.assertEquals(contactTable.getCell(3, 2).getText(), data.ContactInfo.Sexuality == Gender.Female ? "女" : "男", "性别数据错误");
		Assert.assertEquals(contactTable.getCell(3, 4).getText(), data.ContactInfo.MobilePhone, "移动电话数据错误");
		Assert.assertEquals(contactTable.getCell(4, 2).getText(), data.ContactInfo.Telephone, "固定电话数据错误");
		Assert.assertEquals(contactTable.getCell(4, 4).getText(), data.ContactInfo.Email, "电子信箱数据错误");
		Assert.assertEquals(contactTable.getCell(5, 2).getText(), data.ContactInfo.Department, "部门数据错误");
		Assert.assertEquals(contactTable.getCell(5, 4).getText(), data.ContactInfo.Position, "职务数据错误");
		
		HtmlTable eleInfoTable = this.getEleInfoTable();
		Assert.assertEquals(eleInfoTable.getCell(1, 2).getText(), Integer.toString(data.EleInfo.YearlyPowerGen), "年发电量数据错误");
		Assert.assertEquals(eleInfoTable.getCell(1, 4).getText(), Integer.toString(data.EleInfo.YearlyPowerOutput), "年上网电量数据错误");
		Assert.assertEquals(eleInfoTable.getCell(2, 2).getText(), Integer.toString(data.EleInfo.YearlyPowerConsume), "年用电量数据错误");
		Assert.assertEquals(eleInfoTable.getCell(2, 4).getText(), Integer.toString(data.EleInfo.Capacity), "装机容量 数据错误");	
	}
}
