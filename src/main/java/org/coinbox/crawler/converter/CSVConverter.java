package org.coinbox.crawler.converter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.coinbox.crawler.app.Config;
import org.coinbox.crawler.parser.Table;

import com.opencsv.CSVWriter;

public class CSVConverter {
	
	public void convertToCSV(List<Table> csvData){
		
		CSVWriter writer = null;
		try {
			for(Table currentTable : csvData){
				writer = new CSVWriter(new FileWriter(Config.CSV_FOLDER + currentTable.getName() + ".csv"));
				
				writer.writeAll(currentTable.getData());
			}
			
		} catch (IOException e) {
			System.out.println("Convertor error " + e.getMessage());
		} finally{
//			try {
//				writer.flush();
//				writer.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
	}
}
