package com.hteis.webtest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlNgSuggestInput extends HtmlElement {

	public HtmlNgSuggestInput(WebElement element){
		super(element);
	}
	
	public HtmlInput getInput(){
		return new HtmlInput(this.webElement.findElement(By.tagName("input")));
	}
	
	/***输入搜索字符串，选择符合条件的第一个候选项*/
	public void InputAndSelect(String searchTxt){
		this.getInput().input(searchTxt);
		Driver.wait(1000);
		this.webElement.findElements(By.cssSelector(".angucomplete-row")).get(0).click();
		//Driver.pressDownArrow();
		//Driver.pressEnter();
	}
	
	/***输入搜索字符串，选择符合条件的指定候选项*/
	public void InputAndSelect(String searchTxt, int index){
		this.getInput().input(searchTxt);
		Driver.wait(1000);
		this.webElement.findElements(By.cssSelector(".angucomplete-row")).get(index).click();
		//Driver.pressDownArrow();
		//Driver.pressEnter();
	}
	
	public void input(String text){
		this.getInput().input(text);
	}
	
	public void setText(String text){
		this.clear();
		this.input(text);
	}
	
	public String getText(){
		return this.getInput().getAttribute("value");
	}
	
	public void clear(){
		this.getInput().clear();
	}
}
