package com.rigassembler.hplap.gshop;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.rigassembler.hplap.gshop.MainActivity.mainActContext;
import static com.rigassembler.hplap.gshop.MainActivity.searcher;
import static com.rigassembler.hplap.gshop.Searcher.currentKeyword;
import static com.rigassembler.hplap.gshop.VariableRepo.code;
import static com.rigassembler.hplap.gshop.VariableRepo.productNames;
import static com.rigassembler.hplap.gshop.VariableRepo.productPrices;
import static com.rigassembler.hplap.gshop.VariableRepo.searchKey;
import static com.rigassembler.hplap.gshop.VariableRepo.websiteNames;

/**
 * Created by HP Lap on 19-Nov-16.
 */

public class Extractor {

    static int totalProducts;
    String shortCode;

    public Extractor() {
        shortener();
        count();
        populate();
    }

    public void shortener() {
        shortCode = code.substring(code.indexOf("<g-scrolling-carousel"), code.indexOf("</g-scrolling-carousel>") + "</g-scrolling-carousel>".length());
    }

    public void count() {
        totalProducts = 0;
        for (int i = 0; i < shortCode.length(); i++)
            if (shortCode.charAt(i) == '₹') {
                totalProducts++;
            }
    }

    public void populate() {

        int index;

        List<Integer> fromIndex = new ArrayList<Integer>();
        List<Integer> toIndex = new ArrayList<Integer>();

        ArrayList<String> oTag = new ArrayList<>();
        oTag.add("<h4 class=\"_HLg\">");
        oTag.add("<div class=\"_XJg\"><div>");
        oTag.add("<div class=\"_FLg a\">");

        for (String ot : oTag) {
            index = shortCode.indexOf(ot);
            while (index >= 0) {
                fromIndex.add(index + ot.length());
                index = shortCode.indexOf(ot, index + ot.length());
            }
        }

        ArrayList<String> cTag = new ArrayList<>();
        cTag.add("</h4><div class=\"_XJg\">");
        cTag.add("</div></div><div class=\"_FLg a\">");
        cTag.add("</div></div></a></g-inner-card>");

        for (String ct : cTag) {
            index = shortCode.indexOf(ct);
            while (index >= 0) {
                toIndex.add(index);
                index = shortCode.indexOf(ct, index + ct.length());
            }
        }

        for (int i = 0; i < toIndex.size(); i++) {
            if (i < totalProducts)
                productNames.add(shortCode.substring(fromIndex.get(i), toIndex.get(i)));
            else if (i >= totalProducts && i < 2 * totalProducts)
                productPrices.add(shortCode.substring(fromIndex.get(i) + 7, toIndex.get(i)).replace(",", ""));
            else {
                websiteNames.add(shortCode.substring(fromIndex.get(i), toIndex.get(i)));
                searchKey.add(currentKeyword);
            }
        }
    }
}
