package com.dangdang.logtest;

import java.util.ArrayList;
import java.util.List;

public class SeriesBean {

	private String name;
	private String type = "column";
	private int yAxis = 0;
	private List<Integer> data = new ArrayList<>();
	
	public SeriesBean(){
		
	}
	
	public SeriesBean(String name){
		this.name = name;
	}
	
	public SeriesBean(String name,String type){
		this.name = name;
		this.type = type;
	}
	
	public SeriesBean(String name,int yAxis){
		this.name = name;
		this.yAxis = yAxis;
	}
	
	public SeriesBean(String name,String type,int yAxis){
		this.name = name;
		this.type = type;
		this.yAxis = yAxis;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

	public int getyAxis() {
		return yAxis;
	}

	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}
	
}
