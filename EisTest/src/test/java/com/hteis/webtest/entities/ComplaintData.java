package com.hteis.webtest.entities;

import java.util.Date;
import java.util.Random;

import com.hteis.webtest.common.DateUtil;

public class ComplaintData {
	
	/***电话*/
	public String Phone;
	
	/***投诉人*/
	public String Complainant;
	
	/***客户名称*/
	public String CustName;
	
	/***投诉类型*/
	public String ComplaintType;
	
	/***投诉描述*/
	public String Description;
	
	/***责任部门/人*/
	public String PersonResponsible;
	
	/***业务状态*/
	public String Status;
	
	/***投诉时间*/
	public Date CreateDateTime;
	
	/***创建人*/
	public String Creator;
	
	/***处理人*/
	public String ProcessPerson;
	
	/***处理详情*/
	public String ProcessDetails;
	
	/***处理时间*/
	public Date ProcessDateTime;
	
	/***客户反馈*/
	public String CustFeedBack;
	
	/***是否大客户*/
	public boolean IsBigCustomer;
	
	/***投诉关闭人*/
	public String ClosePerson;
	
	/***关闭时间*/
	public Date CloseDateTime;
	
	public static ComplaintData createBigCustData(){
		String[] phones = {"18812345678", "70301160506", "70301155740", "70228160159", "70302135329"};
		String[] custNames = {"四川绿叶用电需求公司", "四川大用户20170301160506", "四川大用户20170301155740", "四川大用户20170228160159", "四川大用户20170302135329"};
		String[] complainants = {"张强", "罗玉凤", "罗玉凤", "罗玉凤", "罗玉凤"};
		String[] types = {"服务", "产品", "产品", "服务", "服务"};
		String[] descriptions = {"服务态度差", "用户数据保密不好", "产品不好用", "咨询电话被挂断", "咨询电话打不通"};
		String[] responsiblePersons = {"业务咨询部", "产品研发部", "司马刚", "月月", "曹仁"};
		
		int index = new Random().nextInt(phones.length);
		ComplaintData data = new ComplaintData();
		data.Phone = phones[index];
		data.CustName = custNames[index];
		data.Complainant = complainants[index];
		data.ComplaintType = types[index];
		data.Description = descriptions[index] + DateUtil.getCurrentDateTimeStr();
		data.PersonResponsible = responsiblePersons[index];
		data.Status = "处理中";
		data.Creator = Constants.custTestName;
		data.IsBigCustomer = true;
		data.CreateDateTime = new Date();
		
		return data;
	}
	
	public static ComplaintData createSmallCustData(){
		ComplaintData data = new ComplaintData();
		data.Phone = DateUtil.getUniquePhone();
		data.CustName = DateUtil.getNoFromDateTime("客户");
		data.Complainant = DateUtil.getShortNoFromTime("李");
		data.ComplaintType = "产品";
		data.Description = DateUtil.getNoFromDateTime("投诉描述");
		data.PersonResponsible = "业务咨询部";
		data.Status = "处理中";
		data.Creator = Constants.custTestName;
		data.IsBigCustomer = false;
		data.CreateDateTime = new Date();
		
		return data;
	}
	
	public void update(){
		this.Description = this.Description + "_modified";
		this.PersonResponsible = this.PersonResponsible + "二";
	}
	
	public void addHandleData(){
		this.Status = "已处理";
		this.ProcessDateTime = new Date();
		
		String[] responsiblePersons = {"业务咨询部", "产品研发部", "司马刚", "月月", "曹仁"};
		this.ProcessPerson = responsiblePersons[new Random().nextInt(responsiblePersons.length)];
		
		String[] results = {"已退货", "已退款", "已罚款", "已解决客户问题", "已修复问题"};
		this.ProcessDetails = results[new Random().nextInt(results.length)];
		
		String[] feedbacks = {"非常满意", "基本满意", "不满意继续投诉"};
		this.CustFeedBack = feedbacks[new Random().nextInt(feedbacks.length)];
	}
	
	public void addCloseData(){
		this.Status = "关闭";
		String[] closePersons = {"夏月", "夏雨", "司马刚", "月月", "曹仁"};
		this.ClosePerson = closePersons[new Random().nextInt(closePersons.length)];
		this.CloseDateTime = new Date();
	}
}
