package com.hteis.webtest.pages.cust;

import org.testng.*;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.ComplaintData;
import com.hteis.webtest.selenium.*;

public class CustComplaintDetailsDialog extends HtmlPage {
	
	public HtmlElement getDialog(){
		return this.findElementByClass("ngdialog");
	}
	
	/***电话*/
	public HtmlInput getPhoneInput(){
		return this.getDialog().findElementById("mobile").toHtmlInput();
	}
	
	/***投诉人*/
	public HtmlInput getComplainantInput(){
		return this.getDialog().findElementById("complainName").toHtmlInput();
	}
	
	/***客户名称*/
	public HtmlInput getCustNameInput(){
		return this.getDialog().findElementById("custName").toHtmlInput();
	}
	
	/***投诉时间*/
	public HtmlInput getCreateTimeInput(){
		return this.getDialog().findElementById("complainTime").toHtmlInput();
	}
	
	/***投诉类型*/
	public HtmlInput getComplaintTypeInput(){
		return this.getDialog().findElementById("complainType").toHtmlInput();
	}
	
	/***责任部门/人*/
	public HtmlInput getResponsibleInput(){
		return this.getDialog().findElementById("duty").toHtmlInput();
	}
	
	/***投诉描述*/
	public HtmlInput getDescriptionInput(){
		return this.getDialog().findElementById("description").toHtmlInput();
	}
	
	/***是否大客户*/
	public HtmlElement getIsBigCustDiv(){
		return this.getDialog().findElementByCss("label[for='bigCust'] + div");
	}
	
	/***业务状态*/
	public HtmlInput getStatusInput(){
		return this.getDialog().findElementByName("businessStatus").toHtmlInput();
	}
	
	/***创建人*/
	public HtmlInput getCreatorInput(){
		return this.getDialog().findElementById("creator").toHtmlInput();
	}
	
	/***处理人*/
	public HtmlInput getProcessPersonInput(){
		return this.getDialog().findElementById("handler").toHtmlInput();
	}
	
	/***客户反馈*/
	public HtmlInput getCustFeedbackInput(){
		return this.getDialog().findElementById("custFeedback").toHtmlInput();
	}
	
	/***处理详情*/
	public HtmlInput getProcessDetailsInput(){
		return this.getDialog().findElementById("handleDetail").toHtmlInput();
	}
	
	/***处理时间*/
	public HtmlInput getProcessTimeInput(){
		return this.getDialog().findElementById("handleTime").toHtmlInput();
	}
	
	/***投诉关闭人*/
	public HtmlInput getClosePersonInput(){
		return this.getDialog().findElementByName("closer").toHtmlInput();
	}
	
	/***关闭按钮*/
	public HtmlButton getCloseBtn(){
		return this.getDialog().findElementByCss(".modal-footer button").toHtmlButton();
	}
	
	public void verifyData(ComplaintData data) throws Exception{
		Assert.assertEquals(this.getPhoneInput().getText(), data.Phone, "电话数据错误");
		Assert.assertEquals(this.getComplainantInput().getText(), data.Complainant, "投诉人数据错误");
		Assert.assertEquals(this.getCustNameInput().getText(), data.CustName, "客户名称数据错误");
		Assert.assertTrue(DateUtil.isCloseTime(DateUtil.getDateTimeFromString(this.getCreateTimeInput().getText()), data.CreateDateTime), "创建时间数据错误");
		Assert.assertEquals(this.getComplaintTypeInput().getText(), data.ComplaintType, "投诉类型数据错误");
		Assert.assertEquals(this.getResponsibleInput().getText(), data.PersonResponsible, "责任人数据错误");
		Assert.assertEquals(this.getDescriptionInput().getText(), data.Description, "投诉描述数据错误");
		Assert.assertEquals(this.getIsBigCustDiv().getText(), data.IsBigCustomer ? "是" : "否", "是否大客户数据错误");
		Assert.assertEquals(this.getStatusInput().getText(), data.Status, "业务状态显示错误");
		Assert.assertEquals(this.getCreatorInput().getText(), data.Creator, "创建人数据错误");
		
		if(!data.Status.equals("处理中")){
			Assert.assertEquals(this.getProcessPersonInput().getText(), data.ProcessPerson, "处理人数据错误");
			Assert.assertEquals(this.getCustFeedbackInput().getText(), data.CustFeedBack, "客户反馈数据错误");
			Assert.assertEquals(this.getProcessDetailsInput().getText(), data.ProcessDetails, "处理详情数据错误");
			Assert.assertTrue(DateUtil.isCloseTime(DateUtil.getDateTimeFromString(this.getProcessTimeInput().getText()), data.ProcessDateTime), "处理时间数据错误");
		} else if(data.Status.equals("已关闭")){
			Assert.assertEquals(this.getClosePersonInput().getText(), data.ClosePerson, "投诉关闭人数据错误");
		}
	}
}
