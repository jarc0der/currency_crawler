package org.coinbox.crawler.normalizer;

@FunctionalInterface
public interface Normalizer {

	String normalize(String input);
	
}
