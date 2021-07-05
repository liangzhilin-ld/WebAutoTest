package com.hteis.webtest.pages;

import org.testng.*;
import java.util.Date;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.selenium.*;

public class TaskCompletedPage extends PageBase {

	public static final int ApplicantColNo = 2;
	public static final int FlowTypeColNo = 3;
	public static final int ApplyTimeColNo = 4;
	public static final int OpsColNo = 5;
	
	
	public HtmlTable getTaskTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}
	
	public HtmlRow getAndVerifyTask(String applicant, String flowType, Date createDate) throws Exception{
		HtmlRow row = this.getTaskTable().findFirstRowByTime(ApplyTimeColNo, createDate);
		
		Assert.assertNotNull(row, "未找到以下时间创建的已处理任务: " + DateUtil.getCNDateTimeStr(createDate));
		Assert.assertEquals(row.getCellValue(ApplicantColNo), applicant, "申请人数据错误");
		Assert.assertEquals(row.getCellValue(FlowTypeColNo), flowType, "流程类型数据错误");
		
		return row;		
	}
}
