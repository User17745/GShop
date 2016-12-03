package com.rigassembler.hplap.gshop;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static com.rigassembler.hplap.gshop.MainActivity.code;

/**
 * Created by HP Lap on 19-Nov-16.
 */

public class Extractor {

    static int totalProducts;
    static List<String> productNames, productPrices, websiteNames;

    public Extractor(Context context){
        shortener();
        count();
        populate(context);
    }

    public void shortener(){
        code = code.substring(code.indexOf("<g-scrolling-carousel"),code.indexOf("</g-scrolling-carousel>") + "</g-scrolling-carousel>".length());
    }

    public void count() {
        totalProducts = 0;
        for (int i = 0; i < code.length(); i++)
            if (code.charAt(i) == '₹') {
                totalProducts++;
            }
    }

    public void populate(Context context) {

        int index;

        List<Integer> fromIndex = new ArrayList<Integer>();
        List<Integer> toIndex = new ArrayList<Integer>();

        productNames = new ArrayList<String>();
        productPrices = new ArrayList<String>();
        websiteNames = new ArrayList<String>();

        ArrayList<String> oTag = new ArrayList<>();
        oTag.add("<h4 class=\"_HLg\">");
        oTag.add("<div class=\"_XJg\"><div>");
        oTag.add("<div class=\"_FLg a\">");

        for (String ot : oTag) {
            index = code.indexOf(ot);
            while (index >= 0) {
                fromIndex.add(index + ot.length());
                index = code.indexOf(ot, index + ot.length());
            }
        }

        ArrayList<String> cTag = new ArrayList<>();
        cTag.add("</h4><div class=\"_XJg\">");
        cTag.add("</div></div><div class=\"_FLg a\">");
        cTag.add("</div></div></a></g-inner-card>");

        for (String ct : cTag) {
            index = code.indexOf(ct);
            while (index >= 0) {
                toIndex.add(index);
                index = code.indexOf(ct, index + ct.length());
            }
        }

        for(int i = 0 ; i < toIndex.size() ; i++)
        {
            if(i < totalProducts)
                productNames.add(code.substring(fromIndex.get(i),toIndex.get(i)));
            else
                if(i >= totalProducts && i < 2*totalProducts)
                    productPrices.add(code.substring(fromIndex.get(i)+7,toIndex.get(i)).replace(",",""));
                        else
                            websiteNames.add(code.substring(fromIndex.get(i),toIndex.get(i)));
        }

        new CSV(context);
    }
}
