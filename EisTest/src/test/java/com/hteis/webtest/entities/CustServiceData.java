package com.hteis.webtest.entities;

import java.util.Date;
import java.util.Random;

import com.hteis.webtest.common.DateUtil;

public class CustServiceData {
	
	/***单位名称*/
	public String Company;
	
	/***用户类型*/
	public String UserType;
	
	/***问题类型*/
	public String IssueType;
	
	/***问题描述*/
	public String Description;
	
	/***客户姓名*/
	public String CustName;
	
	/***手机号码*/
	public String Phone;
	
	/***请求记录时间*/
	public Date CreateDateTime;
	
	/***处理人*/
	public String ProcessPerson;
	
	/***处理结果*/
	public String ProcessResult;
	
	/***处理时间*/
	public Date ProcessDateTime;
	
	/***状态*/
	public String Status;
	
	public static CustServiceData createRegCustData(){
		CustServiceData data = new CustServiceData();
		String[] names = {"张强", "张二强", "罗玉凤", "韦小宝", "爱新觉罗玄烨"};
		String[] companys = {"四川绿叶用电需求公司", "四川大用户20170303091851", "四川大用户20170228144434", "四川大用户20170228155930", "四川大用户20170228160159"};
		String[] issueTypes = {"平台使用", "业务咨询", "合同问题", "账号问题", "其他"};
		String[] issues = {"如何注册？", "每月最多能买多少电？", "合同找谁签字？", "密码忘记了", "找人"};
		String[] phones = {"14523655421", "14523655422", "14523655423", "14523655424", "14523655425"};
		
		int index = new Random().nextInt(companys.length - 1);
		data.Company = companys[index];
		data.CustName = names[index];
		data.IssueType = issueTypes[index];
		data.Description = issues[index] + DateUtil.getCurrentDateTimeStr();
		data.Phone = phones[index];
		//data.ProcessResult = results[index];
		data.UserType = "注册用户";
		data.Status = "处理中";
		data.ProcessPerson = Constants.custTestName;
		data.CreateDateTime = new Date();
		data.ProcessDateTime = new Date();
		
		return data;
	}
	
	public static void updateRegCustData(CustServiceData data){
		CustServiceData newData = CustServiceData.createRegCustData();
		
		//data.Company = newData.Company;
		data.CustName = newData.CustName;
		data.IssueType = newData.IssueType;
		data.Phone = newData.Phone;
		data.Description = newData.Description;
		String[] results = {"已回答", "已回复", "已回复客户问题", "已重置密码", "已交给其他人"};
		data.ProcessResult = results[new Random().nextInt(results.length - 1)];
		
		//data.UserType = "非注册用户";
		data.Status = "处理完成";
	}
	
	public static CustServiceData createNonRegCustData(){
		CustServiceData data = new CustServiceData();
		data.Company = DateUtil.getNoFromDateTime("公司");
		data.CustName = DateUtil.getNoFromDateTime("客户");
		data.IssueType = "业务咨询";
		data.Phone = DateUtil.getUniquePhone();
		data.Description = "如何做月度申报?" + DateUtil.getCurrentDateTimeStr();
		data.UserType = "非注册用户";
		data.Status = "处理中";
		data.ProcessPerson = Constants.custTestName;
		data.CreateDateTime = new Date();
		data.ProcessDateTime = new Date();
		
		return data;
	}
	
	public static void updateNonRegCustData(CustServiceData data){		
		
		CustServiceData newData = CustServiceData.createNonRegCustData();
		//data.Company = newData.Company;
		data.CustName = newData.CustName;
		data.IssueType = newData.IssueType;
		data.Phone = newData.Phone;
		data.Description = newData.Description;
		data.ProcessResult = "处理结果" + DateUtil.getCurrentDateTimeStr();
		//data.UserType = "注册用户";
		data.Status = "处理完成";		
	}
}
