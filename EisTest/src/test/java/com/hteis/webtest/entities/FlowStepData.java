package com.hteis.webtest.entities;

import java.util.Date;

public class FlowStepData {
	
	public FlowStepData(){
		
	}
	
	public FlowStepData(int sequenceNo, String operator, Date createDate, String conclusion, String comment){
		this.SequenceNo = sequenceNo;
		this.Operator = operator;
		this.CreateDate = createDate;
		this.Conclusion = conclusion;
		this.Comment = comment;
	}

	/***序号*/
	public int SequenceNo;
	
	/***处理人*/
	public String Operator;
	
	/***处理时间*/
	public Date CreateDate;
	
	/***审批结论*/
	public String Conclusion;
	
	/***审批意见*/
	public String Comment;
	
}
