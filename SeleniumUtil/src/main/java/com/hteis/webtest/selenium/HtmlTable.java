package com.hteis.webtest.selenium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlTable extends HtmlElement {

	public HtmlTable(WebElement element) {
		super(element);
	}
	
	/***在当前分页中搜索特定行*/
	public HtmlRow findRow(int colNo, String colValue){
		for(WebElement element : this.webElement.findElements(By.cssSelector("tbody tr"))){
			HtmlRow row = new HtmlRow(element);
			if(row.getCellValue(colNo).equals(colValue)){
				return row;
			}
		}
		
		return null;
	}
	
	/***在所有分页中搜索特定行*/
	public HtmlRow findRowInAllPages(HtmlNgPager pager, int colNo, String colValue){
		HtmlRow row = this.findRow(colNo, colValue);
		while(row == null && pager.goNextPage()){
			row = this.findRow(colNo, colValue);
		}
		
		return row;
	}
	
	/***找到指定列中的日期与指定日期相差在60s以内的行，在当前页中从前往后搜索
	 * @throws ParseException */
	public HtmlRow findFirstRowByTime(int timeColNo, Date date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(HtmlRow row : this.getRows()){			
			Date rowDate = format.parse(row.getCellValue(timeColNo));
			if(Math.abs(rowDate.getTime() - date.getTime()) < 60000){
				return row;
			}
		}
		
		return null;
	}
	
	/***找到指定列中的日期与指定日期相差在60s以内的行，在当前页中从后往前搜索
	 * @throws ParseException */
	public HtmlRow findLastRowByTime(int timeColNo, Date date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<HtmlRow> rows = this.getRows();
		for(int i = rows.size() - 1; i >= 0; i-- ){
			HtmlRow row = rows.get(i);
			Date rowDate = format.parse(row.getCellValue(timeColNo));
			if(Math.abs(rowDate.getTime() - date.getTime()) < 60000){
				return row;
			}
		}
		
		return null;
	}
	
	public ArrayList<HtmlRow> getRows(){
		ArrayList<HtmlRow> rows = new ArrayList<HtmlRow>();
		
		for(WebElement element : this.webElement.findElements(By.cssSelector("tbody tr"))){
			HtmlRow row = new HtmlRow(element);
			rows.add(row);
		}
		
		return rows;
	}
	
	public HtmlRow getRow(int rowNo){
		return new HtmlRow(this.webElement.findElement(By.cssSelector(String.format("tbody tr:nth-child(%d)", rowNo))));
	}
	
	public int getRowCount(){
		return this.webElement.findElements(By.cssSelector("tbody tr")).size();
	}
	
	public HtmlRow getFirstRow(){
		return new HtmlRow(this.webElement.findElement(By.cssSelector("tbody tr:first-child")));
	}
	
	public HtmlRow getLastRow(){
		return new HtmlRow(this.webElement.findElement(By.cssSelector("tbody tr:last-child")));
	}
	
	public String getCellValue(int rowNo, int colNo){
		return this.webElement.findElement(By.cssSelector(String.format("tbody tr:nth-child(%d) td:nth-child(%d)", rowNo, colNo))).getText();
	}
	
	public HtmlElement getCell(int rowNo, int colNo){
		return new HtmlElement(this.webElement.findElement(By.cssSelector(String.format("tbody tr:nth-child(%d) td:nth-child(%d)", rowNo, colNo))));
	}
	
	/***验证是否表中所有记录都符合某一条件,返回不符合条件的行,或null(当都符合时)
	 * @throws Exception */
	public HtmlRow VerifyAllRows(HtmlNgPager pager, ICheckRow checkRow) throws Exception{
		pager.goFirstPage();
		Driver.wait(1000);

		for(HtmlRow row : this.getRows()){
			if(!checkRow.CheckRow(row)){				
				return row;
			}
		}
		
		pager.goLastPage();
		Driver.wait(1000);
		
		for(HtmlRow row : this.getRows()){
			if(!checkRow.CheckRow(row)){				
				return row;
			}
		}
		
		return null;
	}

}
