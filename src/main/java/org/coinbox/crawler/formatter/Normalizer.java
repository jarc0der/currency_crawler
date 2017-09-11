package org.coinbox.crawler.formatter;

@FunctionalInterface
public interface Normalizer {

	String normalize(String input);
	
}
