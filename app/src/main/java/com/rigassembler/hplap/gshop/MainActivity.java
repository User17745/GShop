package com.rigassembler.hplap.gshop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import static com.rigassembler.hplap.gshop.CodeSaver.writeToFile;
import static com.rigassembler.hplap.gshop.Extractor.totalProducts;
import static com.rigassembler.hplap.gshop.VariableRepo.code;

public class MainActivity extends AppCompatActivity {

    static Searcher searcher;
    static Context mainActContext;

    static WebView browser;
    TextView count;
    Button names, prices;
    static Button miningButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new VariableRepo();
        mainActContext = getApplicationContext();

        browser = (WebView) findViewById(R.id.browser);
        browser.getSettings().setJavaScriptEnabled(true);

        count = (TextView) findViewById(R.id.textView);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_code);
                TextView codeDis = (TextView) findViewById(R.id.code);
                codeDis.setText(code);

                writeToFile(code);
            }
        });

        miningButton = (Button) findViewById(R.id.mining_button);
        miningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* load a web page */
                searcher = new Searcher();
            }
        });

        /* An instance of this class will be registered as a JavaScriptInterface interface */
        class CodeCopyJavaScriptInterface {
            @JavascriptInterface
            @SuppressWarnings("unused")
            public void processHTML(String html) {
                // process the html as needed by the app
                code = html;

                new Extractor();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count.setText(String.valueOf(totalProducts));
                        searcher.search();
                    }
                });
            }
        }

            /* Register a new JavaScriptInterface interface called HTMLOUT */
        browser.addJavascriptInterface(new CodeCopyJavaScriptInterface(), "HTMLOUT");

            /* WebViewClient must be set BEFORE calling loadUrl! */
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                /* This call inject JavaScriptInterface into the page which just finished loading. */
                browser.loadUrl("javascript:HTMLOUT.processHTML(document.documentElement.outerHTML);");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.option_create_csv)
            new CSV();

        if (id == R.id.option_read_csv)
            new CSV(true);

        if (id == R.id.option_refresh) {
            browser.loadUrl(browser.getUrl());
        }

        return super.onOptionsItemSelected(item);
    }
}

/*

        names = (Button) findViewById(R.id.name_button);
        names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productNames.size() > 0)
                    for (String pName : productNames)
                        Toast.makeText(getApplicationContext(), pName, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "List is still building..", Toast.LENGTH_SHORT).show();
            }
        });

        prices = (Button) findViewById(R.id.price_button);
        prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productPrices.size() > 0)
                    for (String pPrice : productPrices)
                        Toast.makeText(getApplicationContext(), "₹ " + pPrice, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "List is still building..", Toast.LENGTH_SHORT).show();
            }
        });

        miningButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (websiteNames.size() > 0)
                    for (int i = 0; i < websiteNames.size(); i++)
                        Toast.makeText(getApplicationContext(),
                                "Product: " + productNames.get(i)
                                        + "\nPrice: ₹ " + productPrices.get(i)
                                        + "\nWebsite: " + websiteNames.get(i), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "List is still building..", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
*/