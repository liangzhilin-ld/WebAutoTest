package com.hteis.webtest.pages.cust;

import org.testng.*;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;

public class PowerCompanyNewDialog extends HtmlPage{
	
	public HtmlElement getDialog(){
		return this.findElementByClass("ngdialog");		
	}	
	
	/***企业编号*/
	public HtmlInput getNumberInput(){
		return this.getDialog().findElementById("powerGenEntCode").toHtmlInput();
	}
	
	/***企业名称*/
	public HtmlInput getNameInput(){
		return this.getDialog().findElementById("powerGenEntName").toHtmlInput();
	}
	
	/***企业性质*/
	public HtmlSelect getCompanyTypeSelect(){
		return this.getDialog().findElementByCss("label[for='enterCharacter'] + div select").toHtmlSelect();
	}
	
	/***发电类型*/
	public HtmlSelect getPowerTypeSelect(){
		return this.getDialog().findElementByCss("label[for='powerType'] + div select").toHtmlSelect();
	}
	
	/***企业规模*/
	public HtmlSelect getScaleSelect(){
		return this.getDialog().findElementByCss("label[for='enterScale'] + div select").toHtmlSelect();
	}
	
	/***所属区域*/
	public HtmlInput getDistrictInput(){
		return this.getDialog().findElementById("province").toHtmlInput();
	}
	
	/***基础信息保存按钮*/
	public HtmlButton getMainInfoSaveBtn(){
		return this.getDialog().findElementByCss("#basic .modal-footer button:first-child").toHtmlButton();
	}
	
	/***基础信息关闭按钮*/
	public HtmlButton getMainInfoCloseBtn(){
		return this.getDialog().findElementByCss("#basic .modal-footer button:last-child").toHtmlButton();
	}
	
	/***企业地址*/
	public HtmlInput getAddressInput(){
		return this.getDialog().findElementById("address").toHtmlInput();
	}
	
	/***企业电话*/
	public HtmlInput getCompanyTelInput(){
		return this.getDialog().findElementById("enterPhone").toHtmlInput();
	}
	
	/***企业传真*/
	public HtmlInput getCompanyFaxInput(){
		return this.getDialog().findElementById("fax").toHtmlInput();
	}
	
	/***主联系人姓名*/
	public HtmlInput getContactNameInput(){
		return this.getDialog().findElementById("primaryContact").toHtmlInput();
	}
	
	/***性别-男*/
	public HtmlRadioBox getContactGenderMaleRb(){
		return this.getDialog().findElementsByCss("input[type='radio'][name='sex'").get(0).toHtmlRadioBox();
	}
	
	/***性别-女*/
	public HtmlRadioBox getContactGenderFemaleRb(){
		return this.getDialog().findElementsByCss("input[type='radio'][name='sex'").get(1).toHtmlRadioBox();
	} 
	
	/***移动电话*/
	public HtmlInput getContactPhoneInput(){
		return this.getDialog().findElementById("mobile").toHtmlInput();
	}
	
	/***固定电话*/
	public HtmlInput getContactTelInput(){
		return this.getDialog().findElementById("phone").toHtmlInput();
	}
	
	/***电子邮箱*/
	public HtmlInput getContactEmailInput(){
		return this.getDialog().findElementById("email").toHtmlInput();
	}
	
	/***部门*/
	public HtmlInput getContactDepartInput(){
		return this.getDialog().findElementById("department").toHtmlInput();
	}
	
	/***职务*/
	public HtmlInput getContactPositionInput(){
		return this.getDialog().findElementById("position").toHtmlInput();
	}
	
	/***联系人信息保存按钮*/
	public HtmlButton getContactSaveBtn(){
		return this.getDialog().findElementByCss("#contact .modal-footer button:first-child").toHtmlButton();
	}
	
	/***联系人信息关闭按钮*/
	public HtmlButton getContactCloseBtn(){
		return this.getDialog().findElementByCss("#contact .modal-footer button:last-child").toHtmlButton();
	}
	
	/***年发电量*/
	public HtmlInput getYearlyPowerGenInput(){
		return this.getDialog().findElementById("annualEnergy").toHtmlInput();
	}
	
	/***年上网电量*/
	public HtmlInput getYearlyOutputInput(){
		return this.getDialog().findElementById("annualOnGridEnergy").toHtmlInput();
	}
	
	/***年用电量*/
	public HtmlInput getYearlyConsumeInput(){
		return this.getDialog().findElementById("annualUsedEnergy").toHtmlInput();
	}
	
	/***装机容量*/
	public HtmlInput getCapacityInput(){
		return this.getDialog().findElementById("installedCapacity").toHtmlInput();
	}	
	
	/***发电信息保存按钮*/
	public HtmlButton getEleSaveBtn(){
		return this.getDialog().findElementByCss("#power .modal-footer button:first-child").toHtmlButton();
	}
	
	/***发电信息关闭按钮*/
	public HtmlButton getEleCloseBtn(){
		return this.getDialog().findElementByCss("#power .modal-footer button:last-child").toHtmlButton();
	}
	
	/***基础信息标签页*/
	public HtmlElement getMainInfoTab(){
		return this.getDialog().findElementByCss("ul.nav-tabs li:first-child");
	}
	
	/***联系人信息标签页*/
	public HtmlElement getContactTab(){
		return this.getDialog().findElementByCss("ul.nav-tabs li:nth-child(2)");
	}
	
	/***发电人信息标签页*/
	public HtmlElement getEleInfoTab(){
		return this.getDialog().findElementByCss("ul.nav-tabs li:last-child");
	}
	
	public void verifyMainInfo(PowerCompanyMainInfo data){
		Assert.assertEquals(this.getNumberInput().getText(), data.Number);
		Assert.assertEquals(this.getNameInput().getText(), data.Name);
		Assert.assertEquals(this.getCompanyTypeSelect().getSelectedText(), data.CompanyType);
		Assert.assertEquals(this.getPowerTypeSelect().getSelectedText(), data.PowerType);
		Assert.assertEquals(this.getScaleSelect().getSelectedText(), data.Scale);
		Assert.assertEquals(this.getDistrictInput().getText(), data.District);
	}
	
	public void setMainInfo(PowerCompanyMainInfo data){
		this.getNumberInput().setText(data.Number);
		this.getNameInput().setText(data.Name);
		this.getCompanyTypeSelect().selectByText(data.CompanyType);
		this.getPowerTypeSelect().selectByText(data.PowerType);
		this.getScaleSelect().selectByText(data.Scale);
		this.getDistrictInput().setText(data.District);
	}
	
	public void verifyContactInfo(CompanyContactInfo data){
		Assert.assertEquals(this.getAddressInput().getText(), data.CompanyAddress);
		Assert.assertEquals(this.getCompanyTelInput().getText(), data.CompanyTel);
		Assert.assertEquals(this.getCompanyFaxInput().getText(), data.Fax);
		Assert.assertEquals(this.getContactNameInput().getText(), data.Name);
		Assert.assertEquals(this.getContactGenderFemaleRb().isChecked(), data.Sexuality == Gender.Female);
		
		Assert.assertEquals(this.getContactPhoneInput().getText(), data.MobilePhone);
		Assert.assertEquals(this.getContactTelInput().getText(), data.Telephone);
		Assert.assertEquals(this.getContactEmailInput().getText(), data.Email);
		Assert.assertEquals(this.getContactDepartInput().getText(), data.Department);
		Assert.assertEquals(this.getContactPositionInput().getText(), data.Position);
	}
	
	public void setContactInfo(CompanyContactInfo data){
		this.getAddressInput().setText(data.CompanyAddress);
		this.getCompanyTelInput().setText(data.CompanyTel);
		this.getCompanyFaxInput().setText(data.Fax);
		this.getContactNameInput().setText(data.Name);
		if(data.Sexuality == Gender.Male){
			this.getContactGenderMaleRb().check();
		} else {
			this.getContactGenderFemaleRb().check();
		}
		
		this.getContactPhoneInput().setText(data.MobilePhone);
		this.getContactTelInput().setText(data.Telephone);
		this.getContactEmailInput().setText(data.Email);
		this.getContactDepartInput().setText(data.Department);
		this.getContactPositionInput().setText(data.Position);
	}
	
	public void verifyEleInfo(PowerCompanyEleInfo data){
		Assert.assertEquals(this.getYearlyPowerGenInput().getText(), Integer.toString(data.YearlyPowerGen));
		Assert.assertEquals(this.getYearlyOutputInput().getText(),Integer.toString(data.YearlyPowerOutput));
		Assert.assertEquals(this.getYearlyConsumeInput().getText(),Integer.toString(data.YearlyPowerConsume));
		Assert.assertEquals(this.getCapacityInput().getText(),Integer.toString(data.Capacity));
	}
	
	public void setEleInfo(PowerCompanyEleInfo data){
		this.getYearlyPowerGenInput().setText(Integer.toString(data.YearlyPowerGen));
		this.getYearlyOutputInput().setText(Integer.toString(data.YearlyPowerOutput));
		this.getYearlyConsumeInput().setText(Integer.toString(data.YearlyPowerConsume));
		this.getCapacityInput().setText(Integer.toString(data.Capacity));
	}
	
	public void verifyData(PowerCompanyData data){
		this.getMainInfoTab().click();
		this.verifyMainInfo(data.MainInfo);
		this.getContactTab().click();
		this.verifyContactInfo(data.ContactInfo);
		this.getEleInfoTab().click();
		this.verifyEleInfo(data.EleInfo);
	}
	
}
