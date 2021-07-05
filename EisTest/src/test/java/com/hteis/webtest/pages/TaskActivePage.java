package com.hteis.webtest.pages;

import java.util.Date;

import org.testng.Assert;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.selenium.*;

/***未处理任务页面*/
public class TaskActivePage extends PageBase{
	
	public static final int ApplicantColNo = 2;
	public static final int TaskNameColNo = 3;
	public static final int ApplyTimeColNo = 4;
	public static final int OperationColNo = 5;
	
	public HtmlTable getTaskTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}
	
	public HtmlRow getAndVerifyTask(String applicant, String taskName, Date createDate) throws Exception{
		HtmlRow row = this.getTaskTable().findLastRowByTime(ApplyTimeColNo, createDate);
		
		Assert.assertNotNull(row, "未找到以下时间创建的已处理任务: " + DateUtil.getCNDateTimeStr(createDate));
		Assert.assertEquals(row.getCellValue(ApplicantColNo), applicant, "申请人数据错误");
		Assert.assertEquals(row.getCellValue(TaskNameColNo), taskName, "任务名称数据错误");
		
		return row;
	}
	
	public HtmlRow getTask(Date createDate) throws Exception{
		HtmlRow row = this.getTaskTable().findLastRowByTime(ApplyTimeColNo, createDate);		
		Assert.assertNotNull(row, "未找到以下时间创建的已处理任务: " + DateUtil.getCNDateTimeStr(createDate));		
		return row;
	}
}
