package com.example.MoneyConverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Spinner;
import android.widget.TextView;

import MoneyConverterData.DataBaseWorker;
import MoneyConverterJSON.WebViewer;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



public class GraphicActivity extends AppCompatActivity {

    private Spinner SP_currencySelected;
    private Spinner SP_resultCurrencySelected;
    public TextView TV_test;


    private ActionBar actionBar;

    private WebView webView;

    Context context = this;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        setActionBarBackButton();

        initAllSpinners();
        SP_resultCurrencySelected.setSelection(3);

        final WebViewer webViewClient = new WebViewer();

        webView = findViewById(R.id.webView);

        webView.setWebViewClient(webViewClient);

        webViewClient.prepareWebPage(webView);

        TV_test = findViewById(R.id.textView);


        new Thread(new Runnable() {
            @Override
            public void run() {
                TV_test.setText(DataBaseWorker.DB_test(context));
            }
        }).start();
        TV_test.setMovementMethod(new ScrollingMovementMethod());

    }

    private void initAllSpinners() {
        SP_currencySelected = findViewById(R.id.changeCurrency);
        SP_resultCurrencySelected = findViewById(R.id.changeResultCurrency);
    }

    private void setActionBarBackButton(){
        actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClick_doReverse(View view){
        int buf;

        initAllSpinners();

        buf = SP_currencySelected.getSelectedItemPosition();

        SP_currencySelected.setSelection(SP_resultCurrencySelected.getSelectedItemPosition());
        SP_resultCurrencySelected.setSelection(buf);
    }

    public void onClick_showGraphic(View view){
        String currencyChoice;
        String resultCurrencyChoice;

        initAllSpinners();

        currencyChoice = String.valueOf(SP_currencySelected.getSelectedItem());
        resultCurrencyChoice = String.valueOf(SP_resultCurrencySelected.getSelectedItem());

        WebViewer.getGraphic(webView, currencyChoice, resultCurrencyChoice);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }




}
