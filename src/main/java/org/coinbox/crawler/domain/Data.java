package org.coinbox.crawler.domain;

import java.util.List;

public abstract class Data {

	public List<String[]> data;
	
	public Data(List<String[]> data) {
		this.data = data;
	}

	public List<String[]> getData() {
		return data;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}
	
}
