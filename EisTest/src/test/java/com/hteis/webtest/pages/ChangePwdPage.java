package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class ChangePwdPage extends PageBase {

	public HtmlInput getOldPwdInput(){
		return this.findElementById("oldPwd").toHtmlInput();
	}
	
	public HtmlInput getNewPwdInput(){
		return this.findElementById("newPwd").toHtmlInput();
	}
	
	public HtmlInput getConfirmNewPwdInput(){
		return this.findElementById("conNewPwd").toHtmlInput();
	} 
	
	public HtmlButton getConfirmButton(){
		return this.findElementByCss("div.modal-footer > button").toHtmlButton();
	}	
}
