package org.coinbox.crawler.normalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpNormalizer implements Normalizer{
	
	private Pattern pattern;
	private String replace;
	
	public RegExpNormalizer() {}
	
	@Override
	public String normalize(String input) {
		Matcher m = pattern.matcher(input);
		
		if(m.find()){
			input = m.replaceAll(this.replace);
		}
		
		return input;
	}

	@Override
	public void setParams(String str) {
		String[] params = str.split("@");
		
		if(params.length != 2) 
			throw new IllegalArgumentException("Wrong params " + str);
		
		this.pattern = Pattern.compile(params[0]);
		this.replace = params[1];
	}

}
