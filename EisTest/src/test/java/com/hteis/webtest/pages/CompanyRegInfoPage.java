package com.hteis.webtest.pages;

import com.hteis.webtest.common.*;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;

public class CompanyRegInfoPage extends PageBase {
	
	/***营业执照注册号*/
	public HtmlInput getRegNoInput(){
		return this.findElementById("licenseNo").toHtmlInput();
	}
	
	/***营业执照所在地*/
	public HtmlInput getRegLocationInput(){
		return this.findElementById("licenseAddr").toHtmlInput();
	}
	
	/***营业期限*/
	public HtmlInput getLicExpDateInput(){
		return this.findElementById("licenseTime").toHtmlInput();
	}
	
	/***组织机构代码*/
	public HtmlInput getOrgCodeInput(){
		return this.findElementById("orgCode").toHtmlInput();
	}
	
	/***常用地址*/
	public HtmlInput getAddressInput(){
		return this.findElementById("address").toHtmlInput();
	}
	
	/***固定电话*/
	public HtmlInput getTelInput(){
		return this.findElementById("phone").toHtmlInput();
	}
	
	/***上传扫描件链接*/
	public HtmlLink getUploadLicCopyLink(){
		return this.findElementByLinkText("上传扫描件").toHtmlLink();
	}
	
	/***传真*/
	public HtmlInput getFaxInput(){
		return this.findElementById("fax").toHtmlInput();
	}
	
	/***营业范围*/
	public HtmlInput getBusinessScopeInput(){
		return this.findElementById("business").toHtmlInput();
	}
	
	/***法定代表人真实姓名*/
	public HtmlInput getLegalPersonNameInput(){
		return this.findElementById("legalPerson").toHtmlInput();
	}
	
	/***身份证号*/
	public HtmlInput getIdInput(){
		return this.findElementById("cerNo").toHtmlInput();
	}
	
	/***身份证类型-二代身份证*/
	public HtmlRadioBox getSecondGenIdRb(){
		return this.findElementsByCss("input[name='cerType']").get(0).toHtmlRadioBox();
	}
	
	/***身份证类型-二代身份证*/
	public HtmlRadioBox getTempIdRb(){
		return this.findElementsByCss("input[name='cerType']").get(1).toHtmlRadioBox();
	}
	
	/***手机号码*/
	public HtmlInput getPhoneInput(){
		return this.findElementById("mobile").toHtmlInput();
	}
	
	/***上传身份证照片正面链接*/
	public HtmlLink getIdFrontCopyLink(){
		return this.findElementByLinkText("上传身份证照片正面").toHtmlLink();
	}
	
	/***上传身份证照片反面链接*/
	public HtmlLink getIdBackCopyLink(){
		return this.findElementByLinkText("上传身份证照片反面").toHtmlLink();
	}
	
	public void setFieldValues(CompanyRegInfo regInfo) throws Exception{
		this.getRegNoInput().setText(regInfo.RegisterNo);
		this.getRegLocationInput().setText(regInfo.RegLocation);
		this.getLicExpDateInput().setText(DateUtil.getCNDateStr(regInfo.LicenseExpireDate));
		this.getOrgCodeInput().setText(regInfo.OrganiztionNo);
		this.getAddressInput().setText(regInfo.Address);
		this.getTelInput().setText(regInfo.Telephone);
		this.getFaxInput().setText(regInfo.Fax);
		this.getBusinessScopeInput().setText(regInfo.BusinessScope);
		this.getLegalPersonNameInput().setText(regInfo.LegalPersonName);
		this.getIdInput().setText(regInfo.LegalPersonId);
		this.getSecondGenIdRb().check();
		this.getPhoneInput().setText(regInfo.MobilePhone);
		
		// 上传扫描件
		this.getUploadLicCopyLink().click();
		Driver.wait(1000);
		String path = PathUtil.getResourceFilePath(regInfo.LicenseCopyFileName);
		Keyboard.TypeString(path);	
		Keyboard.TypeEnter();
		
		// 上传身份证正面照
		this.getIdFrontCopyLink().click();
		Driver.wait(1000);
		path = PathUtil.getResourceFilePath(regInfo.IdFrontCopyFileName);
		Keyboard.TypeString(path);	
		Keyboard.TypeEnter();
		
		// 上传身份证反面照
		this.getIdBackCopyLink().click();
		Driver.wait(1000);
		path = PathUtil.getResourceFilePath(regInfo.IdBackCopyFileName);
		Keyboard.TypeString(path);	
		Keyboard.TypeEnter();
		Driver.wait(1000);
	}
}
