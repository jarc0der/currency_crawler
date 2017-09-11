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
import org.coinbox.crawler.parser.HTMLParser;
import org.coinbox.crawler.parser.Parser;
import org.coinbox.crawler.parser.Table;

public class ProcessorImpl implements  FileHandler{
	
	private Parser parser = new HTMLParser();
	private CSVConverter converter = new CSVConverter();
	private Compressor compressor = new ZIPCompressor();

	public List<String> readFile(String fileName) {
		File fileWithLinks = new File(fileName);
		
		try{
			return FileUtils.readLines(fileWithLinks);
		}catch(IOException ex){
			System.out.println("Error while reading file " + fileName + ". Utils: " + ex.getMessage());
		}
		
		return null;
	}
	
	public void process(String source){
				//read file and get all links
				List<String> dataLinks = readFile(source);
				
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
	
	private List<Table> processLinks(List<String> dataLinks){
		List<Table> parsedTablesData = new ArrayList<>();
		
		int counter = 0;
		
		for(String currentLink : dataLinks){
			int percentage = (++counter * 100) / dataLinks.size();
			Table parsedContent = parser.parserData(currentLink);
			parsedTablesData.add(parsedContent);
			System.out.println("Parsed " + percentage + "%");
		}
		
		return parsedTablesData;
	}

}
