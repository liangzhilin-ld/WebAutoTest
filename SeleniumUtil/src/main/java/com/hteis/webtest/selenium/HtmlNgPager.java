package com.hteis.webtest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlNgPager extends HtmlElement {

	public HtmlNgPager(WebElement element) {
		super(element);
	}

	public void setNumPerPage(int numPerPage){
		HtmlSelect select = new HtmlSelect(this.webElement.findElement(By.tagName("select")));
		select.selectByText(Integer.toString(numPerPage));		
		this.afterAction();
		Driver.wait(1000);
	}
	
	public void selectPage(int pageNum){
		this.webElement.findElement(By.cssSelector(String.format("ul.pagination li:nth-child(%d) span", pageNum + 1))).click();
		this.afterAction();
		Driver.wait(1000);
	}
	
	public void setPageNum(int pageNum){
		this.webElement.findElement(By.tagName("input")).sendKeys(Integer.toString(pageNum));
		this.afterAction();
		Driver.wait(1000);
	}
	
	/***获取当前第几分页的数字*/
	public int getPageNum(){
		String pageNum = new HtmlInput(this.webElement.findElement(By.tagName("input"))).getText();
		return Integer.valueOf(pageNum);
	}
	
	/***翻至下页，如果已翻到最后一页，返回false, 否则返回true*/
	public boolean goNextPage(){
		if(this.getPageNum() < this.getTotalPages()){
			this.webElement.findElement(By.cssSelector(String.format("ul.pagination li:nth-last-child(%d) span", 1))).click();
			this.afterAction();
			Driver.wait(1000);
		}
		
		return this.getPageNum() < this.getTotalPages();
	}
	
	/***翻至上一页，如果已翻至第一页，返回false，否则返回true*/
	public boolean goPrevPage(){
		if(this.getPageNum() > 1){
			this.webElement.findElement(By.cssSelector(String.format("ul.pagination li:nth-child(%d) span", 1))).click();
			this.afterAction();
			Driver.wait(1000);
		}
		
		return this.getPageNum() > 1;
	}
	
	public void goFirstPage(){
		this.webElement.findElement(By.cssSelector(String.format("ul.pagination li:nth-child(%d) span", 2))).click();
		this.afterAction();
		Driver.wait(1000);
	}
	
	public void goLastPage(){
		this.webElement.findElement(By.cssSelector(String.format("ul.pagination li:nth-last-child(%d) span", 2))).click();
		this.afterAction();
		Driver.wait(1000);
	}
	
	public int getTotalCount(){
		return Integer.parseInt(this.webElement.findElement(By.cssSelector("div.page-total > strong")).getText());
	}
	
	public int getTotalPages(){
		return Integer.parseInt(this.webElement.findElement(By.cssSelector(String.format("ul.pagination li:nth-last-child(%d) span", 2))).getText());
	}
}
