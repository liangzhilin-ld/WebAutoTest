package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;;

public class LoginPage extends HtmlPage {
	
	public HtmlInput getUserInput(){
		return this.findElementById("username").toHtmlInput();
	}
	
	public HtmlInput getPasswordInput(){
		return this.findElementById("password").toHtmlInput();
	}	
	
	public HtmlButton getLoginBtn(){
		return this.findElementByName("submit").toHtmlButton();
	}
	
	public HtmlButton getResetBtn(){
		return this.findElementByName("reset").toHtmlButton();
	}
	
	public HtmlElement getErrorMsgDiv(){
		return this.findElementById("msg");
	}
	
	public HtmlCheckBox getRememberMeCk(){
		return this.findElementById("rememberMe").toHtmlCheckBox();
	}
	
	public void login(String userName, String password){
		this.getUserInput().setText(userName);
		this.getPasswordInput().setText(password);
		this.getLoginBtn().click();
		Driver.wait(1000);
		//Driver.waitPageLoad();
	}
}
