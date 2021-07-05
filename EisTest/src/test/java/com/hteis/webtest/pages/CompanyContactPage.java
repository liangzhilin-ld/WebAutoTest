package com.hteis.webtest.pages;

import com.hteis.webtest.common.PathUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;


public class CompanyContactPage extends PageBase {

	/***姓名*/
	public HtmlInput getNameInput(){
		return this.findElementById("contactName").toHtmlInput();
	}
	
	/***性别-男*/
	public HtmlRadioBox getGenderMaleRb(){
		return this.findElementsByCss("input[name='sex']").get(0).toHtmlRadioBox();
	}
	
	/***性别-女*/
	public HtmlRadioBox getGenderFemalRb(){
		return this.findElementsByCss("input[name='sex']").get(1).toHtmlRadioBox(); 
	}
	
	/***身份证号*/
	public HtmlInput getIdInput(){
		return this.findElementById("cerNo").toHtmlInput();
	}
	
	/***上传身份证照片正面链接*/
	public HtmlLink getIdFrontUploadLink(){
		return this.findElementByLinkText("上传身份证照片正面").toHtmlLink();
	}
	
	/***上传身份证照片反面链接*/
	public HtmlLink getIdBackUploadLink(){
		return this.findElementByLinkText("上传身份证照片反面").toHtmlLink();
	}
	
	/***部门*/
	public HtmlInput getDepartInput(){
		return this.findElementById("department").toHtmlInput();
	}
	
	/***职务/职称*/
	public HtmlInput getPositionInput(){
		return this.findElementById("duty").toHtmlInput();
	}
	
	/***手机号码*/
	public HtmlInput getPhoneInput(){
		return this.findElementById("mobile").toHtmlInput();
	}
	
	/***固定电话*/
	public HtmlInput getTelInput(){
		return this.findElementById("phone").toHtmlInput();
	}
	
	/***邮箱*/
	public HtmlInput getEmailInput(){
		return this.findElementById("email").toHtmlInput();
	}
	
	/***传真号码*/
	public HtmlInput getFaxInput(){
		return this.findElementById("fax").toHtmlInput();
	}
	
	/***上传授权委托书链接*/
	public HtmlLink getDelClaimUploadLink(){
		return this.findElementByLinkText("上传授权委托书").toHtmlLink();
	}
	
	public void setFieldValues(CompanyContactInfo contactInfo) throws Exception{
		this.getNameInput().setText(contactInfo.Name);
		if(contactInfo.Sexuality == Gender.Male){
			this.getGenderMaleRb().check();
		} else {
			this.getGenderFemalRb().check();
		}
		
		this.getIdInput().setText(contactInfo.IdentityNo);
		this.getDepartInput().setText(contactInfo.Department);
		this.getPositionInput().setText(contactInfo.Position);
		this.getPhoneInput().setText(contactInfo.MobilePhone);
		this.getTelInput().setText(contactInfo.Telephone);
		this.getEmailInput().setText(contactInfo.Email);
		this.getFaxInput().setText(contactInfo.Fax);
		
		// 上传授权委托书
		this.getDelClaimUploadLink().click();
		Driver.wait(1000);
		String path = PathUtil.getResourceFilePath(contactInfo.DelegateClaimFileName);
		Keyboard.TypeString(path);	
		Keyboard.TypeEnter();
		
		// 上传身份证正面照
		this.getIdFrontUploadLink().click();
		Driver.wait(1000);
		path = PathUtil.getResourceFilePath(contactInfo.IdFrontCopyFileName);
		Keyboard.TypeString(path);	
		Keyboard.TypeEnter();
		
		// 上传身份证反面照
		this.getIdBackUploadLink().click();
		Driver.wait(1000);
		path = PathUtil.getResourceFilePath(contactInfo.IdBackCopyFileName);
		Keyboard.TypeString(path);	
		Keyboard.TypeEnter();
		Driver.wait(1000);
	}
}
