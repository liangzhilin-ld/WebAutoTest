package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class CombPreviewDialog extends HtmlPage {
	

	public CombPreviewDialog(){
		super();
	}
	
	public HtmlElement getDialogDiv(){
		return this.findElementByClass("ngdialog");
	}
	
	public HtmlElement getComboNoDiv(){
		return this.getDialogDiv().findElementByCss("label[for='pacNo'] + div");
	}
	
	public HtmlElement getComboNameDiv(){
		return this.getDialogDiv().findElementByCss("label[for='pacName'] + div");
	}
	
	public HtmlElement getStartDateDiv(){
		return this.getDialogDiv().findElementByCss("label[for='pacEffectDate'] + div");
	}
	
	public HtmlElement getEndDateDiv(){
		return this.getDialogDiv().findElementByCss("label[for='pacAbolishDate'] + div");
	}
	
	public HtmlElement getChargeTermDiv(){
		return this.getDialogDiv().findElementByCss("div[ng-bind-html='pac.elecCitTextTemp']");
	}
	
	public HtmlElement getPriceTermDiv(){
		return this.getDialogDiv().findElementByCss("div[ng-bind-html='pac.priceCitTextTemp']");
	}
	
	public HtmlElement getSettleTermDiv(){
		return this.getDialogDiv().findElementByCss("div[ng-bind-html='pac.settCitTextTemp']");
	}
	
	public HtmlElement getEvalTermDiv(){
		return this.getDialogDiv().findElementByCss("div[ng-bind-html='pac.asseCitTextTemp']");
	}
	
	public void close(){
		this.findElementByCss("div.modal-footer button").click();
		Driver.wait(1000);
	}
}
