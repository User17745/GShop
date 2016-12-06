package com.rigassembler.hplap.gshop;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.rigassembler.hplap.gshop.MainActivity.browser;
import static com.rigassembler.hplap.gshop.MainActivity.mainActContext;
import static com.rigassembler.hplap.gshop.MainActivity.miningButton;

/**
 * Created by HP Lap on 22-Nov-16.
 */

public class Searcher {
    static List<String> keywords;
    static String currentKeyword;
    static int keyIndex = 0;

    Searcher() {
        populate();
        search();
    }

    void populate() {
        keywords = new ArrayList<String>();
        keywords.add("i3 processor");
        keywords.add("i5 processor");
        keywords.add("teddy bear");
        keywords.add("chocolate");
        keywords.add("magnets");
        keywords.add("pens");
    }

    void search() {
        if (keyIndex < keywords.size()) {
            currentKeyword = keywords.get(keyIndex++);
            browser.loadUrl("https://www.google.co.in/search?q=" + "buy+" + currentKeyword.replace(' ', '+'));
        } else {
            Toast.makeText(mainActContext, "Last Search Reached, resetting to the first one.\nPress again to restart :)", Toast.LENGTH_LONG).show();
            keyIndex = 0;
            miningButton.setText("Start Mining Again!");
        }
    }
}
