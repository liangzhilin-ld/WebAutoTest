package com.hteis.webtest.pages.mgc;

import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;

public class MgNewModelStepPage3 extends HtmlPage{

	/***下一步*/
	public HtmlButton getNextStepBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	public HtmlButton getAddBtn(){
		return this.findElementByCss(".modal-body button.btn-new").toHtmlButton();
	}
	
	/***获取第n个间隔*/
	public MgBayForm getForm(int n){
		HtmlElement div = this.findElementByCss(String.format("div[ng-repeat='eq in eqList']:nth-child(%d)", n + 1));
		return new MgBayForm(div);
	}
	
	public void setFieldValues(MgBayData[] bays){
		for(int i = 0;i<bays.length; i++){
			HtmlElement div = this.findElementByCss(String.format("div[ng-repeat='eq in eqList']:nth-child(%d)", i + 1));
			MgBayForm form = new MgBayForm(div);
			form.setFieldValues(bays[i]);
			
			if(i < bays.length -1){
				this.getAddBtn().click();
				Driver.wait(1000);
			}
		}
	}	
}
