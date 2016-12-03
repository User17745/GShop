package com.rigassembler.hplap.gshop;

import java.util.ArrayList;
import java.util.List;

import static com.rigassembler.hplap.gshop.MainActivity.browser;

/**
 * Created by HP Lap on 22-Nov-16.
 */

public class Searcher {
    static List<String> keywords;
    static String currentKeyword;
    static int keyIndex = 0;

    Searcher() {
        populate();
        initSearch();
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

    void initSearch() {
        currentKeyword = keywords.get(keyIndex);
        browser.loadUrl("https://www.google.co.in/search?q=" + "buy+" + currentKeyword.replace(' ', '+'));
    }

    void loadNext() {
        if (keyIndex < keywords.size()) {
            currentKeyword = keywords.get(keyIndex);
            browser.loadUrl("https://www.google.co.in/search?q=" + "buy+" + currentKeyword.replace(' ', '+'));
            keyIndex++;
        }
    }
}
