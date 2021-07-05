package com.hteis.webtest.selenium;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

public class HtmlPage {
	
	private WebDriver driver;
	
	public HtmlPage(){	
		this.driver = Driver.webDriver;
	}
	
	public HtmlElement findElementById(String id){
		return new HtmlElement(this.driver.findElement(By.id(id)));
	}
	
	public HtmlElement findElementByName(String name){
		return new HtmlElement(this.driver.findElement(By.name(name)));
	}
	
	public HtmlElement findElementByClass(String className){
		return new HtmlElement(this.driver.findElement(By.className(className)));
	}
	
	public HtmlElement findElementByCss(String cssSelector){
		return new HtmlElement(this.driver.findElement(By.cssSelector(cssSelector)));
	}
	
	public ArrayList<HtmlElement> findElementsByCss(String cssSelector){
		return this.findElements(By.cssSelector(cssSelector));
	}
	
	public ArrayList<HtmlElement> findElementsByTag(String tagName){
		return this.findElements(By.tagName(tagName));
	}
	
	private ArrayList<HtmlElement> findElements(By by){
		ArrayList<HtmlElement> elements = new ArrayList<HtmlElement>();
		for(WebElement element : this.driver.findElements(by)){
			elements.add(new HtmlElement(element));
		}
		
		return elements;
	}
	
	public HtmlElement findElementByPartialLinkText(String linkText){
		return new HtmlElement(this.driver.findElement(By.partialLinkText(linkText)));
	}
	
	public HtmlElement findElementByLinkText(String linkText){
		return new HtmlElement(this.driver.findElement(By.linkText(linkText)));
	}
	
	public HtmlElement findElementByTag(String tagName){
		return new HtmlElement(this.driver.findElement(By.tagName(tagName)));
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
	
	public boolean existByTag(String tagName){
		return this.exist(By.tagName(tagName));
	}
	
	public boolean existByCss(String css){
		return this.exist(By.cssSelector(css));
	}
	
	private boolean exist(By by){
		boolean ret;
		this.driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		
		try{			
			this.driver.findElement(by);
			ret = true;
		}
		catch(NoSuchElementException ex){
			ret = false;
		}
		finally{
			this.driver.manage().timeouts().implicitlyWait(Config.waitTimeout, TimeUnit.MILLISECONDS);
		}
		
		return ret;
	}
}
