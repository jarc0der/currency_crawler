package org.coinbox.crawler.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.coinbox.crawler.app.Config;
import org.coinbox.crawler.compressor.Compressor;
import org.coinbox.crawler.compressor.ZIPCompressor;
import org.coinbox.crawler.converter.CSVConverter;
import org.coinbox.crawler.domain.Table;
import org.coinbox.crawler.parser.HTMLParser;
import org.coinbox.crawler.parser.Parser;

public class FileCrawler implements Crawler, FileHandler{
	
	private Parser parser = new HTMLParser();
	private CSVConverter converter = new CSVConverter();
	private Compressor compressor = new ZIPCompressor();

	@Override
	public void crawlData(String source) {
		handleFiles(source);
		
	}

	@Override
	public void handleFiles(String fileName) {
		//read file and get all links
		List<String> dataLinks = readFile(fileName);
		
		//for each link execute parser and get necesary content
		List<Table> parsedData = processLinks(dataLinks);
		
		System.out.println("Tables count: " + parsedData.size());
		
		//convert parsed content to CSV files
		converter.convertToCSV(parsedData);
		
		//clear folder with csv
		
		//create ZIP from all csv files
		File dir = new File(Config.RESOURCE_FOLDER);
		
		List<File> files = (List<File>)FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		
		compressor.compress(new ArrayList<>(files));
	}
	
	public List<String> readFile(String fileName) {
		File fileWithLinks = new File(fileName);
		
		try{
			return FileUtils.readLines(fileWithLinks);
		}catch(IOException ex){
			System.out.println("Error while reading file " + fileName + ". Utils: " + ex.getMessage());
		}
		
		return null;
	}
	
	private List<Table> processLinks(List<String> dataLinks){
		List<Table> parsedTablesData = new ArrayList<>();
		
		int counter = 0;
		
		for(String currentLink : dataLinks){
			int percentage = (++counter * 100) / dataLinks.size();
			Table parsedContent = (Table)parser.parserData(currentLink);
			parsedTablesData.add(parsedContent);
			System.out.println("Parsed " + percentage + "%");
		}
		
		return parsedTablesData;
	}

}
