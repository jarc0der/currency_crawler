package org.coinbox.crawler.normalizer;

public interface Normalizer {

	String normalize(String input);
	
	void setParams(String str);
	
}
