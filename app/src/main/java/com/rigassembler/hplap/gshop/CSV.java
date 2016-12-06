package com.rigassembler.hplap.gshop;

import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rigassembler.hplap.gshop.Extractor.productNames;
import static com.rigassembler.hplap.gshop.Extractor.productPrices;
import static com.rigassembler.hplap.gshop.Extractor.websiteNames;
import static com.rigassembler.hplap.gshop.MainActivity.mainActContext;
import static com.rigassembler.hplap.gshop.Searcher.currentKeyword;

/**
 * Created by HP Lap on 21-Nov-16.
 */

public class CSV {

    static int serial = 1;

    static String currentDateAndTime = DateFormat.getDateTimeInstance().format(new Date());

    static String baseDirectory = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    File writeCsvFolder = new File(baseDirectory + File.separator + "G Shop" + File.separator + "Output CSV" + File.separator);
    static String writeFileName;
    static String writeFilePath;

    File readCsvFolder = new File(baseDirectory + File.separator + "G Shop" + File.separator + "Input CSV" + File.separator);
    static String readFileName;
    static String readFilePath;

    static List<String[]> writeData;
    static List<String[]> readData;

    CSV() {
        writeFileConfig();
        dataPrepare();
        writeCSV();
    }

    CSV(boolean read)
    {
        readFileConfig();
        readCSV();
    }

    public void writeFileConfig() {
        currentDateAndTime = currentDateAndTime.replace(',', ' ');
        currentDateAndTime = currentDateAndTime.replace(':', ' ');
        currentDateAndTime = currentDateAndTime.replace('.', ' ');

        writeCsvFolder.mkdirs();
        writeFileName = serial + " G Shop Price List (" + currentDateAndTime + ").csv";
        writeFilePath = writeCsvFolder.getPath() + File.separator + writeFileName;
    }

    public void dataPrepare() {
        writeData = new ArrayList<String[]>();
        writeData.add(new String[]{"Search Keyword",
                "Product Name",
                "Website",
                "Product Price"});
        if (websiteNames.size() > 0)
            for (int i = 0; i < websiteNames.size(); i++) {
                writeData.add(new String[]{currentKeyword,
                        productNames.get(i),
                        websiteNames.get(i),
                        productPrices.get(i)});
            }
        else
            Toast.makeText(mainActContext, "Extractor->dataPrepare():\nList is still building..", Toast.LENGTH_SHORT).show();
    }

    public static void writeCSV() {

        try {
            if (writeData.size() > 0) {
                CSVWriter writer = new CSVWriter(new FileWriter(writeFilePath));
                writer.writeAll(writeData);
                writer.close();
                serial++;
                Toast.makeText(mainActContext, "File saved as:\n" + writeFilePath, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(mainActContext, "Data is empty", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mainActContext, "Error while creating the file :/", Toast.LENGTH_SHORT).show();
        }
    }

    void readFileConfig(){
        readCsvFolder.mkdirs();

        readFileName = "products.csv";
        readFilePath = readCsvFolder.getPath() + File.separator + readFileName;
    }

    void readCSV() {
        try {
            ArrayList<String> searchProducts = new ArrayList<>();

            readData = new ArrayList<String[]>();
            CSVReader reader = new CSVReader(new FileReader(readFilePath));
            readData = reader.readAll();

            String[] row;
            while ((row = reader.readNext()) != null) {
                searchProducts.add(row[0]);
                Toast.makeText(mainActContext,"Saved : " + row[0],Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
