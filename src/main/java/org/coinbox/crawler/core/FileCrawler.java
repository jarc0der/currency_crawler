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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
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
		Instant i1 = Instant.now();
		Stream<Table> parsedData = parallelProcessLinks(dataLinks, 20);
		Instant i2 = Instant.now();

		System.out.println("Parrallel time: " + Duration.between(i1, i2));
		
		//System.out.println("Tables count: " + parsedData.size());

		System.out.println("Here 1");
		//convert parsed content to CSV files
		Instant i3 = Instant.now();
		converter.parallelConvertToCSV(parsedData);
		Instant i4 = Instant.now();

		System.out.println("Here 2");
		System.out.println("CSV time: " + Duration.between(i3, i4));
		
		//clear folder with csv
		
		//create ZIP from all csv files
		System.out.println("Here 3");
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

	private Stream<Table> parallelProcessLinks(List<String> dataLinks, int nThread){
		//init executor
		ExecutorService exec = null;
		Stream<Table> stream = null;

		try{
			exec = Executors.newFixedThreadPool(nThread);
			//create tasks
			List<Future<Table>> futures = new ArrayList<>();

			for(String link : dataLinks){
				System.out.println("add task");
				futures.add(exec.submit(() -> (Table)parser.parserData(link)));
			}

			//retrieve Data from Future objects
			stream = futures.stream().map(FileCrawler::dataExtractor);

		} finally {
			exec.shutdown();
		}
		//return Stream<Table>

		return stream;
	}

	private static Table dataExtractor(Future<Table> tableFuture){
		Table table = null;
		try {
			table = tableFuture.get();
		} catch (Exception e){
			e.printStackTrace();
		}

		//bug, some times we get null tables
		if(table == null)
			table = new Table();

		return table;
	}

}
