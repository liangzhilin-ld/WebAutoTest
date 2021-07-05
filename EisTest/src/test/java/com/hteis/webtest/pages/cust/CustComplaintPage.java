package com.hteis.webtest.pages.cust;

import java.util.ArrayList;

import org.testng.*;
import com.hteis.webtest.common.*;
import com.hteis.webtest.entities.ComplaintData;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.selenium.*;

public class CustComplaintPage extends PageBase {
	
	public static int CreateTimeColNo = 2;
	public static int ComplaintTypeColNo = 3;
	public static int ResponsibleColNo =4;
	public static int DescriptionColNo = 5;
	public static int CustNameColNo = 6;
	public static int IsBigCustColNo = 7;
	public static int StatusColNo = 8;
	public static int ProcessDetailsColNo = 9;
	public static int ProcessPersonColNo = 10;
	public static int OpsColNo = 11;
	
	public HtmlTable getComplaintTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}	
	
	/***客户名称*/
	public HtmlInput getCustNameInput(){
		return this.findElementById("custName").toHtmlInput();
	}
	
	/***投诉时间-开始*/
	public HtmlWDatePicker getCreateStartTimePicker(){
		return this.findElementById("complainTime_start").toHtmlWDatePicker();
	}
	
	/***投诉时间-结束*/
	public HtmlWDatePicker getCreateEndTimePicker(){
		return this.findElementById("complainTime_end").toHtmlWDatePicker();
	}
	
	/***投诉类型*/
	public HtmlSelect getComplaintTypeSelect(){
		return this.findElementByCss("label[for='complainType'] + div select").toHtmlSelect();
	}	
	
	/***处理时间-开始*/
	public HtmlWDatePicker getHandleStartTimePicker(){
		return this.findElementById("handleTime_start").toHtmlWDatePicker();
	}
	
	/***处理时间-结束*/
	public HtmlWDatePicker getHandleEndTimePicker(){
		return this.findElementById("handleTime_end").toHtmlWDatePicker();
	}
	
	/***投诉人*/
	public HtmlInput getComplainantInput(){
		return this.findElementById("complainName").toHtmlInput();
	}
	
	/***业务状态*/
	public HtmlSelect getStatusSelect(){
		return this.findElementByCss("label[for='businessStatus'] + div select").toHtmlSelect();
	}
	
	/***是否大客户-是*/
	public HtmlRadioBox getIsBigCustYesRb(){
		return this.findElementsByCss("input[name='bigCust'][type='radio']").get(0).toHtmlRadioBox();
	}
	
	/***是否大客户-否*/
	public HtmlRadioBox getIsBigCustNoRb(){
		return this.findElementsByCss("input[name='bigCust'][type='radio']").get(1).toHtmlRadioBox();
	}	
	
	/***查询按钮*/
	public HtmlButton getQueryBtn(){
		return this.findElementByCss("form button:last-child").toHtmlButton();		
	}
	
	/***重置*/
	public HtmlButton getResetBtn(){
		return this.findElementByCss("form button:first-child").toHtmlButton();		
	}
	
	/***创建投诉按钮*/
	public HtmlButton getNewBtn(){
		return this.findElementByCss("div.top-button button").toHtmlButton();				
	}
	
	/***打开详情对话框*/
	public CustComplaintDetailsDialog openDetailsDialog(ComplaintData data){
		this.getComplaintTable().findRow(DescriptionColNo, data.Description)
			.getCell(OpsColNo).findElementByLinkText("详情").click();
		Driver.wait(1000);
		
		return new CustComplaintDetailsDialog();
	}
	
	/***打开编辑对话框*/
	public CustComplaintEditDialog openEditDialog(ComplaintData data){
		this.getComplaintTable().findRow(DescriptionColNo, data.Description)
			.getCell(OpsColNo).findElementByLinkText("编辑").click();
		Driver.wait(1000);
		
		return new CustComplaintEditDialog();
	} 
	
	/***打开处理对话框*/
	public CustComplaintHandleDialog openHandleDialog(ComplaintData data){
		this.getComplaintTable().findRow(DescriptionColNo, data.Description)
		.getCell(OpsColNo).findElementByLinkText("处理").click();
		Driver.wait(1000);
	
		return new CustComplaintHandleDialog();
	}
	
	/***打开关闭对话框*/
	public CustComplaintCloseDialog openCloseDialog(ComplaintData data){
		this.getComplaintTable().findRow(DescriptionColNo, data.Description)
		.getCell(OpsColNo).findElementByLinkText("关闭").click();
		Driver.wait(1000);
	
		return new CustComplaintCloseDialog();
	}
	
	/***验证可用的操作*/
	public void verifyOperations(ComplaintData data, String[] ops){
		HtmlRow row = this.getComplaintTable().findRow(DescriptionColNo, data.Description);
		ArrayList<String> operations = new ArrayList<String>();
		
		for(HtmlElement link : row.getCell(OpsColNo).findElementsByTag("a")){
			operations.add(link.getText());
		}
		
		Assert.assertEquals((String[])operations.toArray(new String[]{}), ops, "当前用户对客户投诉的可用操作不正确");
	}
	
	public void verifyData(ComplaintData data) throws Exception{
		HtmlTable table = this.getComplaintTable();
		HtmlRow row = table.findRow(CustComplaintPage.DescriptionColNo, data.Description);
		Assert.assertTrue(DateUtil.isCloseTime(DateUtil.getDateTimeFromString(row.getCellValue(CustComplaintPage.CreateTimeColNo)), data.CreateDateTime), "投诉创建时间错误");	
		Assert.assertEquals(row.getCellValue(CustComplaintPage.ComplaintTypeColNo), data.ComplaintType, "投诉类型错误");
		Assert.assertEquals(row.getCellValue(CustComplaintPage.ResponsibleColNo), data.PersonResponsible, "责任人错误");
		Assert.assertEquals(row.getCellValue(CustComplaintPage.DescriptionColNo), data.Description, "投诉描述错误");
		Assert.assertEquals(row.getCellValue(CustComplaintPage.CustNameColNo), data.CustName, "客户名称错误");
		Assert.assertEquals(row.getCellValue(CustComplaintPage.IsBigCustColNo), data.IsBigCustomer ? "是" : "否", "是否大客户显示错误");
		Assert.assertEquals(row.getCellValue(CustComplaintPage.StatusColNo), data.Status, "状态显示错误");
		Assert.assertEquals(row.getCellValue(CustComplaintPage.ProcessPersonColNo), data.ProcessPerson == null ? "" : data.ProcessPerson);
		Assert.assertEquals(row.getCellValue(CustComplaintPage.ProcessDetailsColNo), data.ProcessDetails == null ? "" : data.ProcessDetails);
	}
	
	public String printData(HtmlRow row){
		String rowData = "";
		if(row == null){
			return rowData;
		}
		
		String format = "%s:%s; ";
		rowData += String.format(format, "投诉时间", row.getCellValue(CreateTimeColNo));
		rowData += String.format(format, "投诉类型", row.getCellValue(ComplaintTypeColNo));
		rowData += String.format(format, "责任部门/人", row.getCellValue(ResponsibleColNo));
		rowData += String.format(format, "投诉描述", row.getCellValue(DescriptionColNo));
		rowData += String.format(format, "客户名称", row.getCellValue(CustNameColNo));
		rowData += String.format(format, "大客户", row.getCellValue(IsBigCustColNo));
		rowData += String.format(format, "状态", row.getCellValue(StatusColNo));
		rowData += String.format(format, "处理详情", row.getCellValue(ProcessDetailsColNo));
		rowData += String.format(format, "处理人", row.getCellValue(ProcessPersonColNo));
		
		return rowData;
	}	
}
