package com.rigassembler.hplap.gshop;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.rigassembler.hplap.gshop.MainActivity.mainActContext;

/**
 * Created by HP Lap on 21-Nov-16.
 */

public class CodeSaver {

    static void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mainActContext.openFileOutput("htmlCode.html", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Toast.makeText(mainActContext,"Code Saved :)",Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
