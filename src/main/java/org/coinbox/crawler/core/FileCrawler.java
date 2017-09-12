package org.coinbox.crawler.core;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.coinbox.crawler.app.Config;
import org.coinbox.crawler.compressor.Compressor;
import org.coinbox.crawler.compressor.ZIPCompressor;
import org.coinbox.crawler.converter.CSVConverter;
import org.coinbox.crawler.domain.Table;
import org.coinbox.crawler.parser.HTMLParser;
import org.coinbox.crawler.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
		Stream<Table> parsedData = processLinks(dataLinks);
		
		//System.out.println("Tables count: " + parsedData.size());
		
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
	
	private Stream<Table> processLinks(List<String> dataLinks){
		return dataLinks.parallelStream().map(link -> (Table)parser.parserData(link));
	}

}
