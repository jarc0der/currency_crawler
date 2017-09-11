package org.coinbox.crawler.domain;

import java.util.ArrayList;
import java.util.List;

public class Table extends Data{
	
	private String name;
	
	public Table(List<String[]> data) {
		super(data);
	}
	
	public Table(){
		this(new ArrayList<>());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
