package com.hteis.webtest.pages;

import java.util.ArrayList;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.FlowStepData;
import com.hteis.webtest.selenium.*;

public class CompanyInfoMgmtPage extends PageBase {

	public HtmlButton getIncompleteBtn() {
		return this.findElementById("a1").toHtmlButton();
	}

	public HtmlButton getIncompleteBtn2() {
		return this.findElementByCss("#a1 + button").toHtmlButton();
	}

	public HtmlButton getCompletingBtn() {
		return this.findElementById("a2").toHtmlButton();
	}

	public HtmlButton getCompletingBtn2() {
		return this.findElementByCss("#a2 + button").toHtmlButton();
	}

	public HtmlButton getReviewBtn() {
		return this.findElementById("a3").toHtmlButton();
	}

	public HtmlButton getReviewBtn2() {
		return this.findElementByCss("#a3 + button").toHtmlButton();
	}

	public HtmlButton getSuccessBtn() {
		return this.findElementById("a4").toHtmlButton();
	}

	public HtmlButton getSuccessBtn2() {
		return this.findElementByCss("#a4 + button").toHtmlButton();
	}
	
	/***基础信息标签页*/
	public HtmlLink getMainInfoTab(){
		return this.findElementByLinkText("基础信息").toHtmlLink();
	}
	
	/***企业认证信息标签页*/
	public HtmlLink getEntInfoTab(){
		return this.findElementByLinkText("企业认证信息").toHtmlLink();
	}
	
	/***账号信息标签页*/
	public HtmlLink getAccInfoTab(){
		return this.findElementByLinkText("账号信息").toHtmlLink();
	}
	
	/***主要联系人信息标签页*/
	public HtmlLink getContactInfoTab(){
		return this.findElementByLinkText("主要联系人信息").toHtmlLink();
	}
	
	/***用电信息标签页*/
	public HtmlLink getEleInfoTab(){
		return this.findElementByLinkText("用电信息").toHtmlLink();
	}
	
	/***添加用电信息链接*/
	public HtmlLink getAddEleInfoLink(){
		return this.findElementByLinkText("添加用电信息").toHtmlLink();
	}	
	
	public ArrayList<HtmlElement> getEleInfoForms(){
		return this.findElementById("elecInfo").findElementsByTag("form");
	}
	
	public HtmlButton getSaveBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	public HtmlButton getCloseBtn(){
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	public HtmlButton getViewDetailBtn(){
		return this.findElementByCss("button[href='#/salecustpre']").toHtmlButton();
	}
	
	public HtmlButton getSubmitBtn(){
		return this.findElementByCss("button[href='#/salecustpre'] + button").toHtmlButton();
	}
	
	public HtmlButton getViewHistoryBtn(){
		return this.findElementByCss("button[href='#/salecustpre'] + button + button").toHtmlButton();
	}
	
	public HtmlButton getDelConfirmBtn(){
		return this.findElementByCss(".modal-footer button.confirm-btn").toHtmlButton();
	}

	public ArrayList<FlowStepData> getFlowStepData() throws Exception {

		ArrayList<FlowStepData> stepData = new ArrayList<FlowStepData>();
		if (this.existByTag("table")) {

			for (HtmlRow row : this.findElementByTag("table").toHtmlTable().getRows()) {
				FlowStepData data = new FlowStepData();
				data.SequenceNo = Integer.parseInt(row.getCellValue(1));
				data.Operator = row.getCellValue(2);
				data.CreateDate = DateUtil.getDateTimeFromString(row.getCellValue(5));
				data.Conclusion = row.getCellValue(3);
				data.Comment = row.getCellValue(4);

				stepData.add(data);
			}
		}

		return stepData;
	}
	
	
	public Boolean[] getBtnStatus() {
		return new Boolean[] { this.getIncompleteBtn().isEnabled(), this.getIncompleteBtn2().isEnabled(),
				this.getCompletingBtn().isEnabled(), this.getCompletingBtn2().isEnabled(),
				this.getReviewBtn().isEnabled(), this.getReviewBtn2().isEnabled(), this.getSuccessBtn().isEnabled(),
				this.getSuccessBtn2().isEnabled() };
	}
}
