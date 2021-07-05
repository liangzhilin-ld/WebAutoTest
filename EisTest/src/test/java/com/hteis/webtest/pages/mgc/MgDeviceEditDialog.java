package com.hteis.webtest.pages.mgc;

import com.hteis.webtest.selenium.*;

public class MgDeviceEditDialog extends HtmlPage {
	
	public HtmlElement getContainer(){
		return this.findElementByClass("ngdialog-content");
	}
	
	public HtmlButton getDelBtn(){
		return this.getContainer().findElementByCss(".modal-footer button:nth-child(2)").toHtmlButton();
	}
	
	public HtmlButton getSaveBtn(){
		return this.getContainer().findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	public HtmlButton getCancelBtn(){
		return this.getContainer().findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
}
