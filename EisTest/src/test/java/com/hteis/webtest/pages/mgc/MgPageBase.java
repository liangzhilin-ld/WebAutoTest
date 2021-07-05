package com.hteis.webtest.pages.mgc;

import java.util.ArrayList;

import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.*;

public class MgPageBase extends PageBase {
	
	public HtmlElement getMgNameSpan(){
		return this.findElementByCss(".mg-ctl span + span");
	}
	
	public ArrayList<String> getMgList(){
		ArrayList<String> mgList = new ArrayList<String>();
		this.getMgNameSpan().click();
		for(HtmlElement element : this.findElementsByCss(".mg-ctl ul li a")){
			mgList.add(element.getText());
		}
		
		return mgList;
	}
	
	public void selectMg(String mgName){
		this.getMgNameSpan().click();
		for(HtmlElement element : this.findElementsByCss(".mg-ctl ul li a")){
			if(mgName.equals(element.getText())){
				element.click();
				Driver.wait(1000);
			}
		}
	}
}
