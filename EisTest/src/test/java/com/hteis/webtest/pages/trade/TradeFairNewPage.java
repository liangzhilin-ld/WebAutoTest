package com.hteis.webtest.pages.trade;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.*;

public class TradeFairNewPage extends PageBase {

	/*** 交易中心 */
	public HtmlSelect getTradeCenterSelect() {
		return this.findElementByCss("select[ng-model='num.tracen']").toHtmlSelect();
	}

	/*** 场次名称 */
	public HtmlInput getNameInput() {
		return this.findElementById("tnName").toHtmlInput();
	}

	/*** 交易电量规模 */
	public HtmlInput getTradeAmountInput() {
		return this.findElementById("tnTradeSize").toHtmlInput();
	}

	/*** 交易周期 */
	public HtmlSelect getTradePeriodSelect() {
		return this.findElementById("tnCyc").toHtmlSelect();
	}

	/*** 交易年份 */
	public HtmlWDatePicker getYearPicker() {
		return this.findElementById("tnYear").toHtmlWDatePicker();
	}

	/*** 交易年月 */
	public HtmlWDatePicker getMonthPicker() {
		return this.findElementById("tnYearMon").toHtmlWDatePicker();
	}
	
	/*** 交易截至年月*/
	public HtmlWDatePicker getEndMonthPicker(){
		return this.findElementById("tnEndYearMon").toHtmlWDatePicker();
	}

	/*** 交易品种 */
	public HtmlSelect getTradeTypeSelect() {
		return this.findElementById("tnVari").toHtmlSelect();
	}

	/*** 交易日期 */
	public HtmlWDatePicker getTradeDatePicker() {
		return this.findElementById("tnDate").toHtmlWDatePicker();
	}

	/*** 交易开始时间 */
	public HtmlWDatePicker getTradeStartTimePicker() {
		return this.findElementById("tnStartDate").toHtmlWDatePicker();
	}

	/*** 交易结束时间 */
	public HtmlWDatePicker getTradeEndTimePicker() {
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
		return this.findElementByCss("form[name='numAddForm'] input[type='checkbox']").toHtmlCheckBox();
	}
	
	/*** 申报开始时间 */
	public HtmlWDatePicker getDeclareStartTimePicker() {
		return this.findElementById("tnDecStartDate").toHtmlWDatePicker();
	}

	/*** 申报结束时间 */
	public HtmlWDatePicker getDeclareEndTimePicker() {
		return this.findElementById("tnDecEndDate").toHtmlWDatePicker();
	}

	/*** 确定按钮 */
	public HtmlButton getConfirmBtn() {
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}

	/*** 返回按钮 */
	public HtmlButton getBackBtn() {
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}

	public void setFieldValues(TradeFairData tradeFairData) {
		this.getTradeCenterSelect().selectByText(tradeFairData.TradeCenter);
		this.getNameInput().input(tradeFairData.Name);
		this.getTradeAmountInput().input(Integer.toString(tradeFairData.TradeAmount));
		this.getTradePeriodSelect().selectByText(tradeFairData.TradePeriod);
		this.getTradeTypeSelect().selectByText(tradeFairData.TradeType);
		this.getTradeDatePicker().input(DateUtil.getCNDateStr(tradeFairData.TradeDate));
		this.getTradeStartTimePicker().setText(DateUtil.getCNDateTimeShortStr(tradeFairData.TradeStartDateTime));
		this.getTradeEndTimePicker().setText(DateUtil.getCNDateTimeShortStr(tradeFairData.TradeEndDateTime));

		if (tradeFairData.TradeType.equals("协商")) {
			this.getYearPicker().input(tradeFairData.TradeYear);
		} else {
			this.getMonthPicker().input(tradeFairData.TradeMonth);	
			if(tradeFairData.TradePeriod.equals("多月")) {
				this.getEndMonthPicker().input(tradeFairData.TradeEndMonth);
			}
			
			//申报
			if(tradeFairData.AllowDeclare){
				this.getAllowDeclareCk().check();
				this.getDeclareStartTimePicker().setText(DateUtil.getCNDateStr(tradeFairData.DeclareStartDate));
				this.getDeclareEndTimePicker().setText(DateUtil.getCNDateStr(tradeFairData.DeclareEndDate));
			} else{
				this.getAllowDeclareCk().unCheck();
			}
			
			//供应和需求方数量
			this.getSupplyCountInput().input(Integer.toString(tradeFairData.SupplyCount));
			this.getBuyerCountInput().input(Integer.toString(tradeFairData.BuyerCount));			
		}
	}
}
