package com.rigassembler.hplap.gshop;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.rigassembler.hplap.gshop.MainActivity.mainActContext;
import static com.rigassembler.hplap.gshop.Searcher.currentKeyword;
import static com.rigassembler.hplap.gshop.VariableRepo.baseDirectory;

/**
 * Created by HP Lap on 21-Nov-16.
 */

public class CodeSaver {

    static File folder = new File(baseDirectory + File.separator + "G Shop" + File.separator + "HTML" + File.separator);
    static String fileName = currentKeyword + " pageCode.html";
    static String filePath = folder.getPath() + File.separator + fileName;

    static void writeToFile(String data) {
        try {
            folder.mkdirs();

            FileOutputStream output = new FileOutputStream(new File(filePath));
            output.write(data.getBytes());
            output.close();
            Toast.makeText(mainActContext,"Code Saved :)",Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}