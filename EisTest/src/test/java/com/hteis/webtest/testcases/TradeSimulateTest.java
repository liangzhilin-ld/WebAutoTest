package com.hteis.webtest.testcases;

import org.testng.annotations.*;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;

public class TradeSimulateTest extends TCBase {
	
	@Test(suiteName="Sep", groups="交易管理", priority=1, testName="交易模拟测算",
			description="套餐管理页面可查看套餐列表, 表格翻页功能正常")
	public void TradeSimulate(){
		
		//使用交易员登录交易系统
		ShopHomePage homePage = this.LoginShop(Constants.tradeTestUsr, Constants.tradeTestPwd);
		
		//打开交易模拟测算页面
		TradeSimulatePage simulatePage = homePage.openTradeSimulatePage();
		
		//输入如下测算数据:
		
	}
	
	
}
