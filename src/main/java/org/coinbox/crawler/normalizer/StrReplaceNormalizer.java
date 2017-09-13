package org.coinbox.crawler.normalizer;

public class StrReplaceNormalizer implements Normalizer {

	private String targetString;
	private String replaceString;
	
	@Override
	public String normalize(String input) {
		
		String result = input.replace(targetString, replaceString);
				
		return result;
	}

	@Override
	public void setParams(String str) {
		String[] params = str.split("@");
		
		if(params.length != 2)
			throw new IllegalArgumentException("Wrong params " + str);
		
		targetString = params[0];
		replaceString = params[1];
	}

}
