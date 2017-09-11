package org.coinbox.crawler.parser;

import org.coinbox.crawler.domain.Data;

public interface Parser {

	Data parserData(String url);
	
}
