package com.rigassembler.hplap.gshop;

import java.util.ArrayList;

/**
 * Created by HP Lap on 07-Dec-16.
 */

public class VariableRepo {

    static String baseDirectory = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    static ArrayList<String> searchProducts, costPrices;
    static ArrayList<String> searchKey, productNames, productPrices, websiteNames;
    static String code;

    public VariableRepo()
    {
        searchProducts = new ArrayList<String>();
        costPrices= new ArrayList<String>();

        searchKey = new ArrayList<String>();
        productNames = new ArrayList<String>();
        productPrices = new ArrayList<String>();
        websiteNames = new ArrayList<String>();

    }
}
