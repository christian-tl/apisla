package com.dangdang.logtest;

import java.util.ArrayList;
import java.util.List;

public class JsonBean {

	private List<String> categories = new ArrayList<>();
	
	private List<SeriesBean> series = new ArrayList<>();
	
	private List<SeriesBean> slaSeries = new ArrayList<>();
	
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<SeriesBean> getSeries() {
		return series;
	}

	public void setSeries(List<SeriesBean> series) {
		this.series = series;
	}

	public List<SeriesBean> getSlaSeries() {
		return slaSeries;
	}

	public void setSlaSeries(List<SeriesBean> slaSeries) {
		this.slaSeries = slaSeries;
	}
	
	
}
