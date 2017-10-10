package org.coinbox.crawler.converter;

import com.opencsv.CSVWriter;
import org.coinbox.crawler.app.Config;
import org.coinbox.crawler.domain.Table;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVConverter {

    public void convertToCSV(Stream<Table> csvData) {
        csvData.parallel().forEach(currentTable ->{
            System.out.println("Process " + currentTable.getName());
            try (CSVWriter writer = new CSVWriter(new FileWriter(Config.CSV_FOLDER + currentTable.getName() + ".csv"))) {
                writer.writeAll(currentTable.getData());
            } catch (IOException e) {
                System.out.println("Convertor error " + e.getMessage());
            }
        });
    }

    public void parallelConvertToCSV(Stream<Table> csvData){
        ExecutorService exec = null;

        try {
            exec = Executors.newCachedThreadPool();

            List<Table> tableList = csvData.collect(Collectors.toList());

            for (Table table : tableList) {
                exec.submit(() -> new CSVConverter().convertSingleData(table));
            }
        }finally{
            exec.shutdown();
        }
    }

    private void convertSingleData(Table currentTable){
        System.out.println("Process " + currentTable.getName());
        try (CSVWriter writer = new CSVWriter(new FileWriter(Config.CSV_FOLDER + currentTable.getName() + ".csv"))) {
            writer.writeAll(currentTable.getData());
        } catch (IOException e) {
            System.out.println("Convertor error " + e.getMessage());
        }
    }
}
