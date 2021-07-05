package com.hteis.webtest.selenium;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

public class HtmlElement {

	public WebElement webElement;

	public HtmlElement(WebElement element) {
		this.webElement = element;
	}

	public HtmlElement findElementById(String id) {
		return new HtmlElement(this.webElement.findElement(By.id(id)));
	}

	public HtmlElement findElementByName(String name) {
		return new HtmlElement(this.webElement.findElement(By.name(name)));
	}
	
	public HtmlElement findElementByCss(String css){
		return new HtmlElement(this.webElement.findElement(By.cssSelector(css)));
	}
	
	public HtmlElement findElementByLinkText(String linkText){
		return new HtmlElement(this.webElement.findElement(By.linkText(linkText)));
	}
	
	public HtmlElement findElementByTag(String tag){
		return new HtmlElement(this.webElement.findElement(By.tagName(tag)));
	}
	
	public ArrayList<HtmlElement> findElementsByTag(String tag){
		return this.findElements(By.tagName(tag));
	}
	
	public ArrayList<HtmlElement> findElementsByCss(String css){
		return this.findElements(By.cssSelector(css));
	}
	
	private ArrayList<HtmlElement> findElements(By by){
		ArrayList<HtmlElement> elements = new ArrayList<HtmlElement>();
		for(WebElement element : this.webElement.findElements(by)){
			elements.add(new HtmlElement(element));
		}
		
		return elements;
	}

	public boolean existByLinkText(String linkText){
		return this.exist(By.linkText(linkText));
	}
	
	public boolean existByPartialLinkText(String linkText){
		return this.exist(By.partialLinkText(linkText));
	}
	
	public boolean existById(String id){
		return this.exist(By.id(id));
	}
	
	public boolean existByCss(String css){
		return this.exist(By.cssSelector(css));
	}
	
	private boolean exist(By by){
		boolean ret;
		Driver.webDriver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		
		try{			
			this.webElement.findElement(by);
			ret = true;
		}
		catch(NoSuchElementException ex){
			ret = false;
		}
		finally{
			Driver.webDriver.manage().timeouts().implicitlyWait(Config.waitTimeout, TimeUnit.MILLISECONDS);
		}
		
		return ret;
	}
	

	public String getAttribute(String name){
		return this.webElement.getAttribute(name);
	}
	
	public HtmlSelect toHtmlSelect() {
		return new HtmlSelect(this.webElement);
	}
	
	public HtmlInput toHtmlInput(){
		return new HtmlInput(this.webElement);
	}
	
	public HtmlButton toHtmlButton(){
		return new HtmlButton(this.webElement);
	}
	
	public HtmlLink toHtmlLink(){
		return new HtmlLink(this.webElement);
	}
	
	public HtmlCheckBox toHtmlCheckBox(){
		return new HtmlCheckBox(this.webElement);
	}
	
	public HtmlWDatePicker toHtmlWDatePicker(){
		return new HtmlWDatePicker(this.webElement);
	}
	
	public HtmlTable toHtmlTable(){
		return new HtmlTable(this.webElement);
	}
	
	public HtmlRow toHtmlRow(){
		return new HtmlRow(this.webElement);
	}
	
	public HtmlNgPager toHtmlNgPager(){
		return new HtmlNgPager(this.webElement);
	}
	
	public HtmlRadioBox toHtmlRadioBox(){
		return new HtmlRadioBox(this.webElement);
	}
	
	public HtmlNgSuggestInput toHtmlNgSuggestInput(){
		return new HtmlNgSuggestInput(this.webElement);
	}
	
	public HtmlNgTagInput toHtmlNgTagInput(){
		return new HtmlNgTagInput(this.webElement);
	}
	
	public HtmlMultiBodyTable toHtmlMultiBodyTable(){
		return new HtmlMultiBodyTable(this.webElement);
	}

	public void click() {
		this.webElement.click();
		this.afterAction();
	}

	public String getText() {
		return this.webElement.getText();
	}
	
	public boolean isDisplayed(){
		return this.webElement.isDisplayed();
	}	

	public void afterAction() {
		Driver.wait(Config.stepInterval);	
	}
}
