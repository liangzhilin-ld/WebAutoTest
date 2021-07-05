package com.hteis.webtest.pages;

import java.util.ArrayList;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.ContractData;
import com.hteis.webtest.selenium.*;

public class ContractNewPage extends PageBase {
	
	/*** 合同套餐*/
	public HtmlSelect getCombSelect(){
		return this.findElementByCss("select[ng-model='con.pac'").toHtmlSelect();
	}
	
	/*** 客户名称 */
	public HtmlNgSuggestInput getCustNameInput(){
		return this.findElementByCss("angucomplete[placeholder='检索客户名称']").toHtmlNgSuggestInput();
	}
	
	/*** 电压等级 */
	public HtmlInput getVoltLevelInput(){
		return this.findElementByCss("input[ng-model$='voltName']").toHtmlInput();
	}
	
	/*** 客户企业所在地 */
	public HtmlInput getCustProvinceInput(){
		return this.findElementByCss("input[ng-model$='licenseAddr']").toHtmlInput();
	}
	
	/*** 合同类型 */
	public HtmlInput getCustAddressInput(){
		return this.findElementByCss("input[ng-model$='address']").toHtmlInput();
	}
	
	/*** 用电编号 */
	public HtmlInput getElectricNoInput(){
		return this.findElementByCss("input[ng-model$='eleCode']").toHtmlInput();
	}
	
	/*** 购电人 */
	public HtmlInput getContactNameInput(){
		return this.findElementByCss("input[ng-model$='contactName']").toHtmlInput();
	}
	
	/*** 合同名称 */
	public HtmlInput getContractNameInput(){
		return this.findElementByCss("input[ng-model$='conName']").toHtmlInput();
	}
	
	/*** 合同编号 */
	public HtmlInput getContractNoInput(){
		return this.findElementByCss("input[ng-model$='conNo']").toHtmlInput();
	}	
	
	/*** 合同类型 */
	public HtmlInput getContractTypeInput(){
		return this.findElementById("cxConType").toHtmlInput();
	}
	
	/*** 合同状态 */
	public HtmlInput getContractStatusInput(){
		return this.findElementById("conStatusName").toHtmlInput();
	}
	
	/*** 签订地点*/
	public HtmlInput getSignPlaceInput(){
		return this.findElementById("conSaleAddress").toHtmlInput();
	}
	
	/*** 签订地点*/
	public HtmlWDatePicker getSignDatePicker(){
		return this.findElementById("conSignDate").toHtmlWDatePicker();
	}
	
	/*** 供用电合同号*/
	public HtmlInput getPowerSupplyNoInput(){
		return this.findElementById("conPowerSupplyNo").toHtmlInput();
	}
	
	/*** 供电服务合同号*/
	public HtmlInput getPowSupServiceNoInput(){
		return this.findElementById("conPowSupServiceNo").toHtmlInput();
	}
	
	/*** 合同份数*/
	public HtmlInput getContractCopyNumInput(){
		return this.findElementById("conCopies").toHtmlInput();
	}
	
	/*** 仲裁委员会*/
	public HtmlInput getArbitCommitteeInput(){
		return this.findElementById("conArbitCommission").toHtmlInput();
	}
	
	/*** 甲方份数*/
	public HtmlInput getPartACopyNumInput(){
		return this.findElementById("conPartACopies").toHtmlInput();
	}
	
	/*** 乙方份数*/
	public HtmlInput getPartBCopyNumInput(){
		return this.findElementById("conPartBCopies").toHtmlInput();
	}
	
	/***合同开始日期*/
	public HtmlWDatePicker getStartDatePicker(){
		return this.findElementById("conStartDate").toHtmlWDatePicker();
	}
	
	/***合同结束日期*/
	public HtmlWDatePicker getEndDatePicker(){
		return this.findElementById("conEndDate").toHtmlWDatePicker();
	}
	
	/***月度分解电量*/
	public HtmlInput[] getMonElectronInputs(){
		HtmlInput[] monEle = new HtmlInput[12];
		int i = 0;
		for(HtmlElement element : this.findElementsByCss("input[ng-model='ele.cdDecElec']")){
			monEle[i] = element.toHtmlInput();
			i++;
		}
		
		return monEle;
	}
	
	/***购电总量*/
	public HtmlInput getTotalElectronInput(){
		return this.findElementByCss("input[ng-model='con.conElec']").toHtmlInput();
	}	
	
	/***合同套餐信息*/
	public ArrayList<HtmlInput> getCombInfoInputs(){
		ArrayList<HtmlInput> inputs = new ArrayList<HtmlInput>();

		for(HtmlElement element : this.findElementsByCss("div[ng-repeat='pcr in con.pacContentRel'] input")){
			inputs.add(element.toHtmlInput());
		}
		
		return inputs;
	}
	
	public String[] getCombInfoLabels(){
		ArrayList<String> labels = new ArrayList<String>();
		
		for(HtmlElement element : this.findElementsByCss("div[ng-repeat='pcr in con.pacContentRel'] > div label:nth-child(2)")){
			labels.add(element.getText());
		}
		
		return (String[])labels.toArray(new String[]{});
	}
	
	/***确定按钮*/
	public HtmlButton getConfirmBtn(){
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	/***返回按钮*/
	public HtmlButton getBackBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	/***提交按钮*/
	public HtmlButton getSubmitBtn(){
		return this.findElementByCss(".modal-footer button:nth-child(2)").toHtmlButton();
	}
	
	/***保存按钮*/
	public HtmlButton getSaveBtn(){
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}
	
	
	/***合同条款表格*/
	public HtmlTable getContractTermTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public String getChargeTerm(){
		return this.getContractTermTable().getRow(1).getCellValue(2);
	}
	
	public String getPriceTerm(){
		return this.getContractTermTable().getRow(2).getCellValue(2);
	}
	
	public String getSettleTerm(){
		return this.getContractTermTable().getRow(3).getCellValue(2);
	}
	
	public String getEvaluateTerm(){
		return this.getContractTermTable().getRow(4).getCellValue(2);
	}
	
	public void setFieldValues(ContractData data){
		this.getCustNameInput().InputAndSelect(data.CustName);
		this.getContractNameInput().setText(data.ContractName);
		this.getSignPlaceInput().setText(data.SignLocation);
		this.getSignDatePicker().input(DateUtil.getCNDateStr(data.SignDate));
		this.getPowerSupplyNoInput().setText(data.PowerSupplyContractNo);
		this.getPowSupServiceNoInput().setText(data.PowerServiceContrctNo);
		this.getContractCopyNumInput().input(Integer.toString(data.CopyCount));
		this.getArbitCommitteeInput().setText("企业仲裁委员会");
		this.getPartACopyNumInput().setText(Integer.toString(data.PartACopyCount));
		this.getPartBCopyNumInput().setText(Integer.toString(data.PartBCopyCount));
		this.getStartDatePicker().setText(DateUtil.getCNDateStr(data.ContractStartDate));
		this.getEndDatePicker().setText(DateUtil.getCNDateStr(data.ContractEndDate));
		
		//输入月度分解电量
		HtmlInput[] eleAmtInputs = this.getMonElectronInputs();
		int i = 0;
		for(float amt : data.MonthlyAmount){
			eleAmtInputs[i].setText(Float.toString(amt));
			i++;
		}		
		
		//选择合同模板
		this.getCombSelect().selectByText(data.ContractTemplate);
		
		//输入模板因子的值
		ArrayList<HtmlInput> inputs = this.getCombInfoInputs();
		for(i = 0; i < data.TermFactor.length; i++){
			inputs.get(i).setText(data.TermFactor[i]);
		}
	}
}
