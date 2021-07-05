package com.hteis.webtest.pages.trade;

import org.testng.*;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.TradeFairData;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.HtmlButton;
import com.hteis.webtest.selenium.HtmlCheckBox;
import com.hteis.webtest.selenium.HtmlInput;
import com.hteis.webtest.selenium.HtmlSelect;
import com.hteis.webtest.selenium.HtmlWDatePicker;


public class TradeFairEditPage extends PageBase {

	/***交易中心*/
	public HtmlInput getTradeCenterInput(){
		return this.findElementById("tcName").toHtmlInput();
	}
	
	/***状态*/
	public HtmlInput getStatusInput(){
		return this.findElementById("tnStatus").toHtmlInput();
	}
	
	/***场次名称*/
	public HtmlInput getNameInput(){
		return this.findElementById("tnName").toHtmlInput();
	}
	
	/***交易电量规模*/
	public HtmlInput getTradeAmountInput(){
		return this.findElementById("tnTradeSize").toHtmlInput();
	}
	
	/***交易周期*/
	public HtmlSelect getTradePeriodSelect(){
		return this.findElementById("tnCyc").toHtmlSelect();
	}
	
	/***交易年份*/
	public HtmlWDatePicker getYearPicker(){
		return this.findElementById("tnYear").toHtmlWDatePicker();
	}
	
	/*** 交易年月 */
	public HtmlWDatePicker getMonthPicker() {
		return this.findElementById("tnYearMon").toHtmlWDatePicker();
	}
	
	/*** 交易截至年月 */
	public HtmlWDatePicker getEndMonthPicker() {
		return this.findElementById("tnEndYearMon").toHtmlWDatePicker();
	}
	
	/***交易品种*/
	public HtmlSelect getTradeTypeSelect(){
		return this.findElementById("tnVari").toHtmlSelect();
	}
	
	/***交易日期*/
	public HtmlWDatePicker getTradeDatePicker(){
		return this.findElementById("tnDate").toHtmlWDatePicker();
	}
	
	/***交易开始时间*/
	public HtmlWDatePicker getTradeStartTimePicker(){
		return this.findElementById("tnStartDate").toHtmlWDatePicker();
	}
	
	/***交易结束时间*/
	public HtmlWDatePicker getTradeEndTimePicker(){
		return this.findElementById("tnEndDate").toHtmlWDatePicker();
	}
	
	/***供应商数量*/
	public HtmlInput getSupplyCountInput(){
		return this.findElementByName("tnSuppQua").toHtmlInput();
	}
	
	/***需求方数量*/
	public HtmlInput getBuyerCountInput(){
		return this.findElementByName("tnRequQua").toHtmlInput();
	}
	
	/***允许客户申报单选框*/
	public HtmlCheckBox getAllowDeclareCk(){
		return this.findElementByCss("form[name='numEditForm'] input[type='checkbox']").toHtmlCheckBox();
	}
	
	/*** 申报开始时间 */
	public HtmlWDatePicker getDeclareStartTimePicker() {
		return this.findElementById("tnDecStartDate").toHtmlWDatePicker();
	}

	/*** 申报结束时间 */
	public HtmlWDatePicker getDeclareEndTimePicker() {
		return this.findElementById("tnDecEndDate").toHtmlWDatePicker();
	}
	
	/***录入人*/
	public HtmlInput getCreatorInput(){
		return this.findElementById("modifier").toHtmlInput();
	}
	
	/***录入时间*/
	public HtmlInput getCreatedDateTimeInput(){
		return this.findElementById("modifyTime").toHtmlInput();
	}
	
	/***保存按钮*/
	public HtmlButton getSaveBtn(){
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}

	/***返回按钮*/
	public HtmlButton getBackBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	/***删除按钮*/
	public HtmlButton getDelBtn(){
		return this.findElementByCss(".modal-footer button:nth-child(3)").toHtmlButton();
	}
	
	/***提交按钮*/
	public HtmlButton getSubmitBtn(){
		return this.findElementByCss(".modal-footer button:nth-child(2)").toHtmlButton();
	}
	
	public void setFieldValues(TradeFairData data){
		this.getNameInput().setText(data.Name);
		this.getTradeAmountInput().setText(Integer.toString(data.TradeAmount));
		this.getTradePeriodSelect().selectByText(data.TradePeriod);
		this.getTradeTypeSelect().selectByText(data.TradeType);
		this.getTradeDatePicker().setText(DateUtil.getCNDateStr(data.TradeDate));
		this.getTradeStartTimePicker().setText(DateUtil.getCNDateTimeShortStr(data.TradeStartDateTime));
		this.getTradeEndTimePicker().setText(DateUtil.getCNDateTimeShortStr(data.TradeEndDateTime));

		if (data.TradeType.equals("协商")) {
			this.getYearPicker().setText(data.TradeYear);
		} else {
			this.getMonthPicker().setText(data.TradeMonth);
			if(data.TradePeriod.equals("多月")){
				this.getEndMonthPicker().setText(data.TradeEndMonth);
			}
			
			if(data.AllowDeclare){
				this.getAllowDeclareCk().check();
				this.getDeclareStartTimePicker().setText(DateUtil.getCNDateStr(data.DeclareStartDate));
				this.getDeclareEndTimePicker().setText(DateUtil.getCNDateStr(data.DeclareEndDate));
			} else{
				this.getAllowDeclareCk().unCheck();
			}
			
			this.getSupplyCountInput().setText(Integer.toString(data.SupplyCount));
			this.getBuyerCountInput().setText(Integer.toString(data.BuyerCount));
		}
	}
	
	public void verifyData(TradeFairData data){
		Assert.assertEquals(this.getTradeCenterInput().getText(), data.TradeCenter);
		Assert.assertEquals(this.getStatusInput().getText(), data.Status);
		Assert.assertEquals(this.getNameInput().getText(), data.Name);
		Assert.assertEquals(this.getTradeAmountInput().getText(), Integer.toString(data.TradeAmount));
		Assert.assertEquals(this.getTradePeriodSelect().getSelectedText(), data.TradePeriod);		
		Assert.assertEquals(this.getTradeTypeSelect().getSelectedText(), data.TradeType);
		Assert.assertEquals(this.getTradeDatePicker().getText(), DateUtil.getCNDateStr(data.TradeDate));
		Assert.assertEquals(this.getTradeStartTimePicker().getText(), DateUtil.getCNDateTimeShortStr(data.TradeStartDateTime));
		Assert.assertEquals(this.getTradeEndTimePicker().getText(), DateUtil.getCNDateTimeShortStr(data.TradeEndDateTime));
		Assert.assertEquals(this.getCreatorInput().getText(), data.Creator);
		Assert.assertEquals(this.getCreatedDateTimeInput().getText().substring(0, 10), DateUtil.getCNDateStr(data.CreateDateTime), "录入时间不对");
		
		if(data.TradeType.equals("协商")){
			Assert.assertEquals(this.getYearPicker().getText(), data.TradeYear);
		} else {
			Assert.assertEquals(this.getMonthPicker().getText(), data.TradeMonth);
			if(data.TradePeriod.equals("多月")){
				Assert.assertEquals(this.getEndMonthPicker().getText(), data.TradeEndMonth);
			}
			
			Assert.assertEquals(this.getAllowDeclareCk().isChecked(), data.AllowDeclare);
			Assert.assertEquals(this.getSupplyCountInput().getText(), Integer.toString(data.SupplyCount));
			Assert.assertEquals(this.getBuyerCountInput().getText(), Integer.toString(data.BuyerCount));
			Assert.assertEquals(this.getDeclareStartTimePicker().getText(), DateUtil.getCNDateStr(data.DeclareStartDate));
			Assert.assertEquals(this.getDeclareEndTimePicker().getText(), DateUtil.getCNDateStr(data.DeclareEndDate));
		}
	}
}
