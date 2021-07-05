package com.hteis.webtest.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlRadioBox extends HtmlElement {

	public HtmlRadioBox(WebElement element) {
		super(element);
	}

	public void check() {
		if(!this.isChecked()){
			this.webElement.click();
			this.afterAction();
		}
	}
	
	public void unCheck() {
		if(this.isChecked()){
			this.webElement.click();
			this.afterAction();
		}
	}
	
	public boolean isChecked(){
		return this.webElement.isSelected();
	}
	
	public static int getSelectedIndex(String name){
		int i = 0;
		for(WebElement element : Driver.webDriver.findElements(By.cssSelector(String.format("input[name='%s'][type='radio']", name)))){			
			if(element.isSelected()){
				return i;
			}
			
			i++;
		}
		
		return -1;
	}
	
	public static void selectByIndex(String name, int index){
		if(index < 0){
			index = 0;
		}
		
		int i = 0;
		for(WebElement element : Driver.webDriver.findElements(By.cssSelector(String.format("input[name='%s'][type='radio']", name)))){		
			if(i == index){
				new HtmlRadioBox(element).check();
				break;
			}
			
			i++;
		}
	}
}