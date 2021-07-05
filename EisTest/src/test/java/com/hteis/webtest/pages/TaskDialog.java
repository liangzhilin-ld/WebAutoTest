package com.hteis.webtest.pages;

import org.testng.Assert;

import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.cust.PowerCompanyDetailsDialog;
import com.hteis.webtest.pages.trade.TradeFairViewPage;
import com.hteis.webtest.selenium.*;

public class TaskDialog extends HtmlPage {

	/***确定按钮*/
	public HtmlButton getConfirmBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();		
	}
	
	/***取消按钮*/
	public HtmlButton getCancelBtn(){
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();		
	}
	
	/***附件链接*/
	public HtmlLink getAttachmentLink(){
		return this.findElementByLinkText("附件").toHtmlLink();
	}
	
	/***同意多选按钮*/
	public HtmlRadioBox getAgreeRb(){
		return this.findElementByCss("input[name='aaOpinion'][value='同意'").toHtmlRadioBox();
	}
	
	/***驳回多选按钮*/
	public HtmlRadioBox getRejectRb(){
		return this.findElementByCss("input[name='aaOpinion'][value='驳回'").toHtmlRadioBox();
	}
	
	/***查看原始单据*/
	public HtmlLink getOrigDocLink(){
		return this.findElementByLinkText("查看原始单据").toHtmlLink();
	}
	
	/***审批意见*/
	public HtmlInput getCommentInput(){
		return this.findElementByCss(".modal-body textarea").toHtmlInput();
	}
	
	public void verifyCompanyData(CompanyData companyData){
		HtmlElement frame = this.findElementByTag("iframe");
		Driver.switchToFrame(frame);
		CompanyDetailsPage detailPage = new CompanyDetailsPage();
		detailPage.verifyCompanyInfo(companyData);
		Driver.switchBack();
	}
	
	public void verifyContractData(ContractData contractData){
		HtmlElement frame = this.findElementByTag("iframe");
		Driver.switchToFrame(frame);
		ContractPreviewPage previewPage = new ContractPreviewPage();
		Assert.assertEquals(previewPage.getTitle(), contractData.ContractName + "购售电合同");
		
		Driver.switchBack();
	}
	
	public void verifyTradeFairData(TradeFairData tradeFairData){
		HtmlElement frame = this.findElementByTag("iframe");
		Driver.switchToFrame(frame);
		TradeFairViewPage viewPage = new TradeFairViewPage();
		viewPage.verifyData(tradeFairData);
		
		Driver.switchBack();
	}
	
	public void verifyPowerCompanyData(PowerCompanyData data){
		HtmlElement frame = this.findElementByTag("iframe");
		Driver.switchToFrame(frame);
		PowerCompanyDetailsDialog dialog = new PowerCompanyDetailsDialog();
		dialog.verifyData(data);		
		
		Driver.switchBack();
	}
}
