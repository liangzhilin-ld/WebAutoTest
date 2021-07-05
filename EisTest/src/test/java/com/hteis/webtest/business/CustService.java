package com.hteis.webtest.business;

import com.hteis.webtest.entities.CustServiceData;
import com.hteis.webtest.pages.PageBase;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.selenium.*;

public class CustService {
	public static void create(CustServiceData data){
		//打开客户服务管理页面
		CustServicePage custPage = new PageBase().openCustServicePage();
		
		//点击创建服务请求按钮
		custPage.getNewBtn().click();
		
		//输入数据
		CustServiceNewDialog dialog = new CustServiceNewDialog();
		dialog.setFieldValues(data);
		
		//点击保存按钮
		dialog.getSaveBtn().click();
		Driver.wait(1000);
		
		//验证提示消息：保存成功
		custPage.verifyAlert("保存成功");
		custPage.closeAlert();
		Driver.wait(1000);
	}
}
