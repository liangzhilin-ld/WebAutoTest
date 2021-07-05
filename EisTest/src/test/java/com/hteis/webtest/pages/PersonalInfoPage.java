package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class PersonalInfoPage extends PageBase{
	
	public HtmlInput getLoginIdInput(){
		return this.findElementById("userName").toHtmlInput();
	}
	
	public HtmlInput getNameInput(){
		return this.findElementById("cnName").toHtmlInput();
	}
	
	public HtmlInput getIdInput(){
		return this.findElementById("cerNo").toHtmlInput();
	}
	
	public HtmlInput getPhoneInput(){
		return this.findElementById("phone").toHtmlInput();
	}

	public HtmlInput getCellPhoneInput(){
		return this.findElementById("mobile").toHtmlInput();
	}
	
	public HtmlInput getEmailInput(){
		return this.findElementById("email").toHtmlInput();
	}
	
	public HtmlInput getCompanyNameInput(){
		return this.findElementById("comp").toHtmlInput();
	}
	
	public HtmlInput getOrgNameInput(){
		return this.findElementById("groupName").toHtmlInput();
	}
	
	public HtmlButton getSaveBtn(){
		return this.findElementByCss(".modal-footer > button").toHtmlButton();
	}
	
	public HtmlElement getCellPhoneReqErrorSpan(){
		return this.findElementByCss("span[ng-show='userUpForm.mobile.$error.required']");
	}
	
	public HtmlElement getEmailReqErrorSpan(){
		return this.findElementByCss("span[ng-show='userUpForm.email.$error.required']");
	}
}
