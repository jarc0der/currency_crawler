package org.coinbox.crawler.converter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.coinbox.crawler.app.Config;
import org.coinbox.crawler.domain.Table;

import com.opencsv.CSVWriter;

public class CSVConverter {

    public void convertToCSV(List<Table> csvData) {


        for (Table currentTable : csvData) {
            System.out.println("Process " + currentTable.getName());

            try (CSVWriter writer = new CSVWriter(new FileWriter(Config.CSV_FOLDER + currentTable.getName() + ".csv"))) {
                writer.writeAll(currentTable.getData());
            } catch (IOException e) {
                System.out.println("Convertor error " + e.getMessage());
            }
        }
    }
}
