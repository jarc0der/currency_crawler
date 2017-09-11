package org.coinbox.crawler.app;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.coinbox.crawler.core.FileCrawler;

public class App 
{
    public static void main( String[] args )
    {
    	Scanner sc = new Scanner(System.in);
    	
    	String command = null;
    	
    	do{
    		System.out.print(">>");
    		command = sc.nextLine();
    		
    		switch(command){
	    		case "go": {
	    			long t1 = System.currentTimeMillis();
	    	        new FileCrawler().crawlData(Config.FILE_LINKS);
	    	        long t2 = System.currentTimeMillis();
	    	        
	    	        System.out.println((t2 - t1) / 1000 + " sec.");
	    	        
	    	        break;
	    		}
	    		case "clean":{
	    			clean(Config.CSV_FOLDER);
	    			clean(Config.ZIP_FOLDER);
	    			break;
	    		}
    		}
    	}while(!"exit".equals(command));
    }
    
    public static void clean(String folder){
    	File folderObject = new File(folder);
    	
    	if(!folderObject.isDirectory())
    		return;
    	
    	try {
			FileUtils.cleanDirectory(folderObject);
		} catch (IOException e) {
			System.out.println("Issue with cleaning directory " + folder + ". " + e.getMessage());
		}
    	
    	System.out.println("Clean folder " + folderObject);
    }
}
