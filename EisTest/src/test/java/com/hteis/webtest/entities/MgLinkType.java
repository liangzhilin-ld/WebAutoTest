package com.hteis.webtest.entities;

/***链路类型*/
public enum MgLinkType {
	
	Link104("link-104", 0),
	LinkModBusTCP("link-modbus-tcp", 1),
	LinkModBusRTU("link-modbus-rtu", 2);
	
	private String name;
	private int index;
	
	private MgLinkType(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	public int getIndex(){
		return this.index;
	}
	
}
