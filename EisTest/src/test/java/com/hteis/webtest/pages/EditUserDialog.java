package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class EditUserDialog extends HtmlPage {

	public HtmlCheckBox getRoleCk(String roleName){
		for(HtmlElement element : this.findElementsByCss("form[name='userUpForm'] div[ng-repeat*='role in roleList']")){
			if(element.findElementByTag("span").getText().equals(roleName)){
				return element.findElementByTag("input").toHtmlCheckBox();
			}
		}
		
		return null;
	}
	
	public HtmlButton getSaveBtn(){
		return this.findElementByCss("div.modal-footer button:first-child").toHtmlButton();
	}
	
	public HtmlRadioBox getEnableRadio(){
		return this.findElementById("optionsRadios1").toHtmlRadioBox();
	}
	
	public HtmlRadioBox getDisableRadio(){
		return this.findElementById("optionsRadios2").toHtmlRadioBox();
	}
}
