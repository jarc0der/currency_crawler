package org.coinbox.crawler.converter;

import com.opencsv.CSVWriter;
import org.coinbox.crawler.app.Config;
import org.coinbox.crawler.domain.Table;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

public class CSVConverter {

    public void convertToCSV(Stream<Table> csvData) {
        csvData.forEach(currentTable ->{
            System.out.println("Process " + currentTable.getName());
            try (CSVWriter writer = new CSVWriter(new FileWriter(Config.CSV_FOLDER + currentTable.getName() + ".csv"))) { // exception nullP
                writer.writeAll(currentTable.getData());
            } catch (IOException e) {
                System.out.println("Convertor error " + e.getMessage());
            }
        });
    }
}//мене не чути? NO =(
