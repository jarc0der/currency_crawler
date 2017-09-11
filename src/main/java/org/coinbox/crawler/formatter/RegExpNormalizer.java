package org.coinbox.crawler.formatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpNormalizer implements Normalizer{
	
	private Pattern pattern;
	private String replace;
	
	public RegExpNormalizer(String regExp) {
		if(regExp == null)
			throw new IllegalArgumentException("Invalid regExp " + regExp);
		
		initVariables(regExp);
	}
	
	private void initVariables(String regExp){
		String[] params = regExp.split("@");
		
		if(params.length != 2) 
			throw new IllegalArgumentException("Wrong params " + regExp);
		
		this.pattern = Pattern.compile(params[0]);
		this.replace = params[1];
	}
	
	@Override
	public String normalize(String input) {
//		Pattern p = Pattern.compile("");
		Matcher m = pattern.matcher(input);
		
		if(m.find()){
			input = m.replaceAll(this.replace);
		}
		
		return input;
	}

}
