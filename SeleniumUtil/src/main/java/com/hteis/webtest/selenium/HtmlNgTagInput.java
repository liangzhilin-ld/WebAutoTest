package com.hteis.webtest.selenium;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlNgTagInput extends HtmlElement{
	
	public HtmlNgTagInput(WebElement element){
		super(element);
	}
	
	public HtmlInput getInput(){
		return new HtmlInput(this.webElement.findElement(By.tagName("input")));
	}
	
	/***输入搜索字符串，选择符合条件的第一个候选项*/
	public void InputAndSelect(String searchTxt){
		this.getInput().input(searchTxt);
		Driver.wait(1000);
		this.webElement.findElements(By.cssSelector("ul.suggestion-list li")).get(0).click();
		//Driver.pressDownArrow();
		//Driver.pressEnter();
	}
	
	/***输入搜索字符串，选择符合条件的指定候选项*/
	public void InputAndSelect(String searchTxt, int index){
		this.getInput().input(searchTxt);
		Driver.wait(1000);
		this.webElement.findElements(By.cssSelector("ul.suggestion-list li")).get(index).click();
		//Driver.pressDownArrow();
		//Driver.pressEnter();
	}
	
	public String[] getTags(){
		ArrayList<String> members = new ArrayList<String>();
		for(WebElement element : this.webElement.findElements(By.cssSelector("ul.tag-list li span"))){
			members.add(element.getText());
		}
		
		return (String[])members.toArray(new String[]{});
	}
	
	public void clear(){
		for(WebElement element : this.webElement.findElements(By.cssSelector("ul.tag-list li a"))){
			element.click();
		}
	}
}
