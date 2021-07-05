package com.hteis.webtest.pages;


import com.hteis.webtest.selenium.*;

public class ContractCombDialog extends HtmlPage {	
	
	private static final int chargeMax = 2, priceMax = 5, settleMax = 5, evalMax = 5;
	private static final String chargeName = "elecCitId";
	private static final String priceName = "priceCitId";
	private static final String settleName = "settCitId";
	private static final String evalName = "asseCitId";
	private static final int textColumnNo = 4;
	
	/***套餐编码*/
	public HtmlInput getComboNoInput(){
		return this.findElementById("pacNo").toHtmlInput();
	}
	
	/***套餐名称*/
	public HtmlInput getComboNameInput(){
		return this.findElementById("pacName").toHtmlInput();
	}
	
	/***开始时间*/
	public HtmlWDatePicker getComboStartTimeInput(){
		return this.findElementById("pacEffectDate").toHtmlWDatePicker();
	}
	
	/***结束时间*/
	public HtmlWDatePicker getComboEndTimeInput(){
		return this.findElementById("pacAbolishDate").toHtmlWDatePicker();
	}
	
	/***所选模板*/
	public HtmlElement getSelectedTempDiv(){
		return this.findElementByCss("label[for='pacTemp'] + div");
	}
	
	/***确定按钮*/
	public HtmlButton getConfirmBtn(){
		return this.findElementByCss(".modal-footer > button").toHtmlButton();
	}
	
	/***套餐编号最小长度错误提示*/
	public HtmlElement GetNoMinErrorSmall(){
		return this.findElementByCss("small[ng-show='pacAddForm.pacNo.$error.minlength']");		
	}
	
	/***套餐编号最大长度错误提示*/
	public HtmlElement GetNoMaxErrorSmall(){
		return this.findElementByCss("small[ng-show='pacAddForm.pacNo.$error.maxlength']");	
	}
	
	/***套餐编号必填错误提示*/
	public HtmlElement GetNoReqErrorSmall(){
		return this.findElementByCss("small[ng-show='pacAddForm.pacNo.$error.required']");	
	}
	
	/***套餐名称最小长度错误提示*/
	public HtmlElement GetNameMinErrorSmall(){
		return this.findElementByCss("small[ng-show='pacAddForm.pacName.$error.minlength']");		
	}
	
	/***套餐名称最大长度错误提示*/
	public HtmlElement GetNameMaxErrorSmall(){
		return this.findElementByCss("small[ng-show='pacAddForm.pacName.$error.maxlength']");	
	}
	
	/***套餐名称必填错误提示*/
	public HtmlElement GetNameReqErrorSmall(){
		return this.findElementByCss("small[ng-show='pacAddForm.pacName.$error.required']");	
	}
	
	public void setValus(String id, String name, int chargeNo, int priceNo, int settleNo, int evaluateNo) throws Exception{
		//String comboNo = DateUtil.getNoFromDate("C");
		this.getComboNoInput().setText(id);
		//String comboName = DateUtil.getNoFromDate("套餐");
		this.getComboNameInput().setText(name);
		this.getComboStartTimeInput().selectToday();
		this.getComboEndTimeInput().selectNextYearToday();
		this.setConfig(chargeNo, priceNo, settleNo, evaluateNo);
	}
	
	public void selectCharge(int chargeNo) throws Exception{
		if(chargeNo > chargeMax ){
			throw new Exception("电量选项超出最大值" + chargeMax);
		}
		
		HtmlRadioBox.selectByIndex(chargeName, chargeNo - 1);
	}	
	
	public int getChargeSelection(){
		return HtmlRadioBox.getSelectedIndex(chargeName) + 1;
	}
	
	public int getPriceSelection(){
		return HtmlRadioBox.getSelectedIndex(priceName) + 1;
	}
	
	public int getSettleSelection(){
		return HtmlRadioBox.getSelectedIndex(settleName) + 1;
	}
	
	public int getEvalSelection(){
		return HtmlRadioBox.getSelectedIndex(evalName) + 1;
	}
	
	public void selectPrice(int priceNo) throws Exception{
		if(priceNo > priceMax ){
			throw new Exception("电价选项超出最大值" + priceMax);
		}
		
		HtmlRadioBox.selectByIndex(priceName, priceNo - 1);
	}	

	public void selectSettle(int settleNo) throws Exception{
		if(settleNo > settleMax ){
			throw new Exception("结算选项超出最大值" + settleMax);
		}
		
		HtmlRadioBox.selectByIndex(settleName, settleNo - 1);		
	}
	
	public void selectEvaluate(int evaluateNo) throws Exception{
		if(evaluateNo > evalMax ){
			throw new Exception("审核选项超出最大值" + evalMax);
		}
		
		HtmlRadioBox.selectByIndex(evalName, evaluateNo - 1);	
	}
	
	public void setConfig(int chargeNo, int priceNo, int settleNo, int evaluateNo) throws Exception{
		this.selectCharge(chargeNo);
		this.selectPrice(priceNo);
		this.selectSettle(settleNo);
		this.selectEvaluate(evaluateNo);
	}
	
	public HtmlTable getSelectionTable(){
		return this.findElementByCss("form[name='pacAddForm'] table").toHtmlTable();
	}
	
	public String getChargeTerm(int chargeNo){
		return this.getSelectionTable().getRow(chargeNo).getCellValue(textColumnNo);
	}
	
	public String getPriceTerm(int priceNo){
		return this.getSelectionTable().getRow(chargeMax + priceNo).getCellValue(textColumnNo);
	}
	
	public String getSettleTerm(int settleNo){
		return this.getSelectionTable().getRow(chargeMax + priceMax + settleNo).getCellValue(textColumnNo);
	}
	
	public String getEvalTerm(int evalNo){
		return this.getSelectionTable().getRow(chargeMax + priceMax + settleMax + evalNo).getCellValue(textColumnNo);
	}
}
