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
import java.util.jar.Manifest;

import static com.rigassembler.hplap.gshop.VariableRepo.productNames;
import static com.rigassembler.hplap.gshop.VariableRepo.productPrices;
import static com.rigassembler.hplap.gshop.VariableRepo.searchKey;
import static com.rigassembler.hplap.gshop.VariableRepo.websiteNames;
import static com.rigassembler.hplap.gshop.MainActivity.mainActContext;
import static com.rigassembler.hplap.gshop.Searcher.currentKeyword;
import static com.rigassembler.hplap.gshop.VariableRepo.searchProducts;

/**
 * Created by HP Lap on 21-Nov-16.
 */

public class CSV {

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
        writeFileName = " G Shop Price List (" + currentDateAndTime + ").csv";
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
                writeData.add(new String[]{searchKey.get(i),
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

            readData = new ArrayList<String[]>();
            CSVReader reader = new CSVReader(new FileReader(readFilePath));

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
