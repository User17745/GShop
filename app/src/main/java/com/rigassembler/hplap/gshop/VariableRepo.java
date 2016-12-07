package com.rigassembler.hplap.gshop;

import java.util.ArrayList;

/**
 * Created by HP Lap on 07-Dec-16.
 */

public class VariableRepo {
    static ArrayList<String> searchProducts, costPrices;
    static ArrayList<String> productNames, productPrices, websiteNames;

    public VariableRepo()
    {
        searchProducts = new ArrayList<String>();
        costPrices= new ArrayList<String>();

        /*searchProducts.add("i3 processor");
        searchProducts.add("i5 processor");
        searchProducts.add("i7 processor");
        searchProducts.add("fx 8320 processor");
        searchProducts.add("fx 8350 processor");*/

    }
}
