package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;
import com.hteis.webtest.entities.*;

public class RegisterPage extends PageBase {
	
	public HtmlLink getAgreementLink(){
		return this.findElementByPartialLinkText("免责声明").toHtmlLink();
	}
	
	public HtmlLink getRegLink(){
		return this.findElementByLinkText("免费注册").toHtmlLink();
	}
	
	public HtmlInput getUserNameInput(){
		return this.findElementById("userLogName").toHtmlInput();
	}
	
	public HtmlInput getPhoneInput(){
		return this.findElementById("mobilePhone").toHtmlInput();
	}
	
	public HtmlInput getEmailInput(){
		return this.findElementById("email").toHtmlInput();
	}
	
	public HtmlSelect getProvinceSelect(){
		return this.findElementByName("province").toHtmlSelect();
	}
	
	public HtmlInput getPasswordInput(){
		return this.findElementById("logPwd").toHtmlInput();
	}
	
	public HtmlInput getConfirmPasswordInput(){
		return this.findElementById("comLogPwd").toHtmlInput();
	}
	
	public HtmlInput getValCodeInput(){
		return this.findElementById("phoneVerifCode").toHtmlInput();
	}
	
	public HtmlCheckBox getAgreementCheckbox(){
		return this.findElementByCss("input[type='checkbox']").toHtmlCheckBox();
	}
	
	public HtmlButton getSubmitButton(){
		return this.findElementByCss("div.modal-footer > button").toHtmlButton();
	}
	
	public HtmlElement getUserNameReqErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.userLogName.$error.required']+span");		
	}
	
	public HtmlElement getPhoneReqErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.mobilePhone.$error.required']");
	}
	
	public HtmlElement getPhoneMinLenErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.mobilePhone.$error.minlength']");
	}
	
	public HtmlElement getPhoneMaxLenErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.mobilePhone.$error.maxlength']");
	}
	
	public HtmlElement getPhoneValidMark(){
		return this.findElementByCss("span[ng-show^='userLogInForm.mobilePhone.$valid']");
	}
	
	public HtmlElement getEmailReqErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.email.$error.required']"); 
	}
	
	public HtmlElement getEmailErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.email.$error.email']"); 
	}
	
	public HtmlElement getProvinceReqErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.province.$error.required']"); 
	}
	
	public HtmlElement getPwdErrorMark(){
		return this.findElementByCss("span[ng-show='userLogInForm.logPwd.$error.required'] + span");
	}	
	
	public HtmlElement getConfirmPwdReqErrorSpan(){
		return this.findElementByCss("span[ng-show='userLogInForm.comLogPwd.$error.required']"); 
	}
	
	public void inputValidData(){
		this.getUserNameInput().setText("usr837619475");
		this.getPhoneInput().setText("98274971638");
		this.getEmailInput().setText("98274971638@qq.com");
		this.getProvinceSelect().selectByIndex(1);
		this.getPasswordInput().setText("i7yj@9djd&");
		this.getConfirmPasswordInput().setText("i7yj@9djd&");
		this.getValCodeInput().setText("1234");
		this.getAgreementCheckbox().check();
	}
	
	public void registerBuyer(UserInfo data){
		this.getUserNameInput().setText(data.loginName);
		this.getPhoneInput().setText(data.phone);
		this.getEmailInput().setText(data.email);
		this.getProvinceSelect().selectByText(data.province);
		this.getPasswordInput().setText(data.password);
		this.getConfirmPasswordInput().setText(data.password);
		this.getValCodeInput().setText("1234");
		this.getSubmitButton().click();
	}
	
}
