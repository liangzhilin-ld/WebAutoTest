package com.hteis.webtest.pages.cust;

import org.testng.*;
import com.hteis.webtest.entities.CustServiceData;
import com.hteis.webtest.selenium.*;

public class CustServiceDetailsDialog extends HtmlPage{

	public HtmlElement getDialog(){
		return this.findElementByClass("ngdialog");
	}
	
	/***客户姓名*/
	public HtmlInput getCustNameInput(){
		return this.getDialog().findElementById("custName").toHtmlInput();
	}
	
	/***用户类型*/
	public HtmlInput getUserTypeInput(){
		return this.getDialog().findElementById("userType").toHtmlInput();
	}
	
	/***单位名称*/
	public HtmlInput getCompanyInput(){
		return this.getDialog().findElementById("enterName").toHtmlInput();
	}
	
	/***问题类型*/
	public HtmlInput getIssueTypeInput(){
		return this.getDialog().findElementById("classification").toHtmlInput();
	}
	
	/***问题描述*/
	public HtmlInput getDescriptionInput(){
		return this.getDialog().findElementById("description").toHtmlInput();
	}
	
	/***手机号码*/
	public HtmlInput getPhoneInput(){
		return this.getDialog().findElementById("mobile").toHtmlInput();
	}
	
	/***处理状态*/
	public HtmlInput getStatusInput(){
		return this.getDialog().findElementById("handleStatus").toHtmlInput();
	}
	
	/***处理结果*/
	public HtmlInput getProcessResultInput(){
		return this.getDialog().findElementById("handleResult").toHtmlInput();
	}
	
	/***关闭按钮*/
	public HtmlButton getCloseBtn(){
		return this.getDialog().findElementByCss(".modal-footer button").toHtmlButton();
	}
	
	public void verifyData(CustServiceData data){
		Assert.assertEquals(this.getCustNameInput().getText(), data.CustName);
		Assert.assertEquals(this.getUserTypeInput().getText(), data.UserType);
		Assert.assertEquals(this.getCompanyInput().getText(), data.Company);
		Assert.assertEquals(this.getIssueTypeInput().getText(), data.IssueType);
		Assert.assertEquals(this.getDescriptionInput().getText(), data.Description);
		Assert.assertEquals(this.getPhoneInput().getText(), data.Phone);
		Assert.assertEquals(this.getStatusInput().getText(), data.Status);
		Assert.assertEquals(this.getProcessResultInput().getText(), data.ProcessResult == null ? "" : data.ProcessResult);
	}
	
}
