package com.hteis.webtest.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class HtmlSelect extends HtmlElement {
	
	Select select;
	
	public HtmlSelect(WebElement element){
		super(element);
		this.select = new Select(element);
	}
	
	public void selectByText(String text){
		this.select.selectByVisibleText(text);
		this.afterAction();
	}
	
	public void selectByValue(String value){
		this.select.selectByValue(value);
		this.afterAction();
	}
	
	public void selectByIndex(int index){ 
		this.select.selectByIndex(index);
		this.afterAction();
	}
	
	public String getSelectedText(){
		return this.select.getFirstSelectedOption().getText();
	}
	
	public String getText(){
		return this.getSelectedText();
	}
	
	public int getSelectedIndex(){
		String selectedText = this.getSelectedText();
		int i = 0;
		for(WebElement element : this.webElement.findElements(By.tagName("option"))){
			if(element.getText().equals(selectedText)){
				return i;
			}
			
			i++;
		}
		
		return i;
	}
	
	public boolean isOptionExist(String option){
		for(WebElement element : this.webElement.findElements(By.tagName("option"))){
			if(element.getText().equals(option)){
				return true;
			}
		}
		
		return false;
	}
}
