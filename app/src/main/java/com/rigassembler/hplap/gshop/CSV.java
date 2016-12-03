package com.rigassembler.hplap.gshop;

import android.content.Context;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rigassembler.hplap.gshop.Extractor.productNames;
import static com.rigassembler.hplap.gshop.Extractor.productPrices;
import static com.rigassembler.hplap.gshop.Extractor.websiteNames;
import static com.rigassembler.hplap.gshop.Searcher.currentKeyword;
import static com.rigassembler.hplap.gshop.Searcher.keywords;

/**
 * Created by HP Lap on 21-Nov-16.
 */

public class CSV {

    static int serial = 0;

    static String currentDateAndTime = DateFormat.getDateTimeInstance().format(new Date());;

    static String baseDirectory = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();;
    File csvFolder = new File(baseDirectory + File.separator + "G Shop" + File.separator + "Output CSV" + File.separator);

    static String fileName;
    static String filePath;

    static List<String[]> data;

    CSV(Context context)
    {
        fileConfig();
        dataPrepare(context);
        makeCSV(context);
    }

    public void fileConfig(){
            currentDateAndTime = currentDateAndTime.replace(',',' ');
            currentDateAndTime = currentDateAndTime.replace(':',' ');
            currentDateAndTime = currentDateAndTime.replace('.',' ');

        csvFolder.mkdirs();
        fileName = serial + " G Shop Price List (" + currentDateAndTime + ").csv";
        filePath = csvFolder.getPath() + File.separator + fileName;
    }

    public void dataPrepare(Context context) {
        data = new ArrayList<String[]>();
        data.add(new String[]{"Search Keyword",
                                    "Product Name",
                                        "Website",
                                            "Product Price"});
        if (websiteNames.size()>0)
            for (int i = 0; i < websiteNames.size(); i++) {
                data.add(new String[]{keywords.get(serial),
                                            productNames.get(i),
                                                websiteNames.get(i),
                                                    productPrices.get(i)});
            }
        else
            Toast.makeText(context, "Extractor->dataPrepare():\nList is still building..", Toast.LENGTH_SHORT).show();
    }

    public static void makeCSV(Context context) {

        try {
            if(data.size()>0) {
                CSVWriter writer = new CSVWriter(new FileWriter(filePath));
                writer.writeAll(data);
                writer.close();
                /*Batch Trial*/
                serial++;
                /*Batch Trial*/
                Toast.makeText(context, "File saved as:\n" + filePath, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(context,"Data is empty",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error while creating the file :/", Toast.LENGTH_SHORT).show();
        }
    }

}
