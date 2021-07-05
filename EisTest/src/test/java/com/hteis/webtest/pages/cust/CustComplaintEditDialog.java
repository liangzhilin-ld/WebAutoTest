package com.hteis.webtest.pages.cust;

import com.hteis.webtest.entities.ComplaintData;
import com.hteis.webtest.selenium.*;

public class CustComplaintEditDialog extends HtmlPage {

	public HtmlElement getDialog(){
		return this.findElementByClass("ngdialog");
	}
	
	/***电话*/
	public HtmlInput getPhoneInput(){
		return this.getDialog().findElementById("mobile").toHtmlInput();
	}
	
	/***客户名称*/
	public HtmlInput getCustNameInput(){
		return this.getDialog().findElementById("custName").toHtmlInput();
	}
	
	/***投诉人*/
	public HtmlInput getComplainantInput(){
		return this.getDialog().findElementById("complainName").toHtmlInput();
	}
	
	/***投诉类型*/
	public HtmlInput getComplaintTypeInput(){
		return this.getDialog().findElementById("complainType").toHtmlInput();
	}
	
	/***投诉描述*/
	public HtmlInput getDescriptionInput(){
		return this.getDialog().findElementById("description").toHtmlInput();
	}
	
	/***责任部门/人*/
	public HtmlInput getResponsibleInput(){
		return this.getDialog().findElementById("duty").toHtmlInput();
	}
	
	/***业务状态*/
	public HtmlInput getStatusInput(){
		return this.getDialog().findElementById("businessStatus").toHtmlInput();
	}
	
	/***保存按钮*/
	public HtmlButton getSaveBtn(){
		return this.getDialog().findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	/***关闭按钮*/
	public HtmlButton getCloseBtn(){
		return this.getDialog().findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	public void setFieldValue(ComplaintData data){
		this.getResponsibleInput().setText(data.PersonResponsible);
		this.getDescriptionInput().setText(data.Description);
	}
}

