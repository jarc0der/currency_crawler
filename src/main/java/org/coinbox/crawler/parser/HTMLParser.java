package org.coinbox.crawler.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.coinbox.crawler.domain.Table;
import org.coinbox.crawler.normalizer.Normalizer;
import org.coinbox.crawler.normalizer.RegExpNormalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser implements Parser{

	public Table parserData(String url) {		
		Table table = null;
		
		try {
			
			table = parseTableData(url);
			
		} catch (IOException e) {
			System.out.println("Error while reading " + url);
		}
		
		return table;
	}

	
	private Table parseTableData(String url) throws IOException{
		Table tableObj = new Table();
		
		Document doc = Jsoup.connect(url).get();
		
		Elements table = doc.select("table tbody");
		
		Element tableNameElement = doc.select("h1 > small.bold").first();
		
		String currentName = tableNameElement.text();
		
		Elements tableRows = table.select("tr");
		
		List<String[]> parsedData = convertHTMLToString(tableRows);
		
		tableObj.setName(currentName);
		tableObj.setData(parsedData);
		
		return tableObj;
	}
	
	private List<String[]> convertHTMLToString(Elements elements){
		List<String[]> parsedData = new ArrayList<>();
		
		Normalizer normalizer = new RegExpNormalizer("\\w{3}\\s(\\d{2}),\\s(\\d{4})@$1/$2");
		
		for(Element tr : elements){

			String parsedRow = tr.text();
			
			parsedRow = normalizer.normalize(parsedRow);
			parsedRow = parsedRow.replaceAll("\\,", "\\.");
				
			parsedData.add(parsedRow.split(" "));
		}
		
		return parsedData;
		
	}
	
}
