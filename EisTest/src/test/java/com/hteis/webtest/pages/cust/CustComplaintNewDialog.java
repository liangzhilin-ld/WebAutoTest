package com.hteis.webtest.pages.cust;

import com.hteis.webtest.entities.ComplaintData;
import com.hteis.webtest.selenium.*;

public class CustComplaintNewDialog extends HtmlPage {
	
	public HtmlElement getDialog(){
		return this.findElementByClass("ngdialog");
	}
	
	public HtmlNgSuggestInput getPhoneInput(){
		return this.getDialog().findElementByTag("angucomplete").toHtmlNgSuggestInput();
	}
	
	public HtmlInput getComplainantInput(){
		//return this.getDialog().findElementById("complainName").toHtmlInput();
		return this.getDialog().findElementByCss("label[for='complainName'] + div input").toHtmlInput();
	}
	
	public HtmlInput getCustNameInput(){
		//return this.getDialog().findElementById("custName").toHtmlInput();
		return this.getDialog().findElementByCss("label[for='custName'] + div input").toHtmlInput();
	}
	
	public HtmlSelect getComplaintTypeSelect(){
		return this.getDialog().findElementByName("complainType").toHtmlSelect();
	}
	
	public HtmlInput getDescriptionInput(){
		return this.getDialog().findElementById("description").toHtmlInput();
	}
	
	public HtmlInput getResponsibleInput(){
		return this.getDialog().findElementById("mobile").toHtmlInput();
	}
	
	public HtmlSelect getStatusSelect(){
		return this.getDialog().findElementByName("businessStatus").toHtmlSelect();
	}
	
	public HtmlButton getSaveBtn(){
		return this.getDialog().findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	public HtmlButton getCloseBtn(){
		return this.getDialog().findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	public void setFieldValues(ComplaintData data){
		if(data.IsBigCustomer){
			this.getPhoneInput().InputAndSelect(data.Phone);
		} else {
			this.getPhoneInput().input(data.Phone);
			this.getComplainantInput().input(data.Complainant);
			this.getCustNameInput().input(data.CustName);
		}
		
		this.getComplaintTypeSelect().selectByText(data.ComplaintType);
		this.getDescriptionInput().input(data.Description);
		this.getResponsibleInput().input(data.PersonResponsible);
		this.getStatusSelect().selectByText(data.Status);
	}	
}
