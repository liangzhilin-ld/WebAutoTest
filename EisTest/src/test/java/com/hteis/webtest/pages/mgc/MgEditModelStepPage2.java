package com.hteis.webtest.pages.mgc;

import com.hteis.webtest.entities.MgLinkData;
import com.hteis.webtest.selenium.*;

public class MgEditModelStepPage2 extends HtmlPage{
	/***下一步*/
	public HtmlButton getNextStepBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	public int getLinkFormCount() {
		return this.findElementsByCss("div[ng-repeat='link in linkList']").size();
	}

	public void setFieldValues(MgLinkData[] dataArr) {
		for (int i = 0; i < dataArr.length; i++) {
			HtmlElement div = this.findElementsByCss("div[ng-repeat='link in linkList']").get(i);
			MgLinkForm form = new MgLinkForm(div);
			form.updateFieldValues(dataArr[i]);
		}
	}
	
	public void verifyData(String mgName, MgLinkData[] dataArr){
		for (int i = 0; i < dataArr.length; i++) {
			HtmlElement div = this.findElementsByCss("div[ng-repeat='link in linkList']").get(i);
			MgLinkForm form = new MgLinkForm(div);
			form.verifyData(mgName, dataArr[i]);
		}
	}
}
