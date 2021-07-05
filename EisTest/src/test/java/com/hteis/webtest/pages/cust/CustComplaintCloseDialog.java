package com.hteis.webtest.pages.cust;

import com.hteis.webtest.entities.ComplaintData;
import com.hteis.webtest.selenium.*;

public class CustComplaintCloseDialog extends HtmlPage {
	
	public HtmlElement getDialog(){
		return this.findElementByClass("ngdialog");
	}
	
	/***电话*/
	public HtmlInput getPhoneInput(){
		return this.getDialog().findElementById("mobile").toHtmlInput();
	}
	
	/***投诉人*/
	public HtmlInput getComplainantInput(){
		return this.getDialog().findElementById("complainName").toHtmlInput();
	}
	
	/***客户名称*/
	public HtmlInput getCustNameInput(){
		return this.getDialog().findElementById("custName").toHtmlInput();
	}
	
	/***投诉时间*/
	public HtmlInput getCreateTimeInput(){
		return this.getDialog().findElementById("complainTime").toHtmlInput();
	}
	
	/***投诉类型*/
	public HtmlInput getComplaintTypeInput(){
		return this.getDialog().findElementById("complainType").toHtmlInput();
	}
	
	/***责任部门/人*/
	public HtmlInput getResponsibleInput(){
		return this.getDialog().findElementById("duty").toHtmlInput();
	}
	
	/***投诉描述*/
	public HtmlInput getDescriptionInput(){
		return this.getDialog().findElementById("description").toHtmlInput();
	}
	
	/***是否大客户*/
	public HtmlElement getIsBigCustDiv(){
		return this.getDialog().findElementByCss("label[for='bigCust'] + div");
	}
	
	/***业务状态*/
	public HtmlSelect getStatusSelect(){
		return this.getDialog().findElementByCss("Select[name='businessStatus'").toHtmlSelect();
	}
	
	/***创建人*/
	public HtmlInput getCreatorInput(){
		return this.getDialog().findElementById("creator").toHtmlInput();
	}
	
	/***处理人*/
	public HtmlInput getProcessPersonInput(){
		return this.getDialog().findElementById("handler").toHtmlInput();
	}
	
	/***客户反馈*/
	public HtmlInput getCustFeedbackInput(){
		return this.getDialog().findElementByName("custFeedback").toHtmlInput();
	}
	
	/***处理详情*/
	public HtmlInput getProcessDetailsInput(){
		return this.getDialog().findElementById("handleDetail").toHtmlInput();
	}
	
	/***处理时间*/
	public HtmlInput getProcessTimeInput(){
		return this.getDialog().findElementById("handleTime").toHtmlInput();
	}	
	
	/***确认关闭投诉*/
	public HtmlCheckBox getConfirmCloseCk(){
		return this.getDialog().findElementByCss("input[type='checkbox']").toHtmlCheckBox();
	}
	
	/***投诉关闭人*/
	public HtmlInput getClosePersonInput(){
		return this.getDialog().findElementByName("colser").toHtmlInput();
	}

	/***关闭按钮*/
	public HtmlButton getCloseBtn(){
		return this.getDialog().findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	/***保存按钮*/
	public HtmlButton getSaveBtn(){
		return this.getDialog().findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	public void setFieldValues(ComplaintData data){
		this.getConfirmCloseCk().check();
		this.getClosePersonInput().input(data.ClosePerson);
	}	
}
