package com.example.moneyonverter;

import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Spinner;

import MoneyConverterData.CurrencyDataBaseHelper;
import MoneyConverterJSON.WebViewer;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;



public class GraphicActivity extends AppCompatActivity {

    Spinner SP_currencySelected;
    Spinner SP_resultCurrencySelected;

    String currencyChoice;
    String resultCurrencyChoice;

    private WebView webView;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        initAllSpinners();
        SP_resultCurrencySelected.setSelection(3);

        final WebViewer webViewClient = new WebViewer();

        webView = findViewById(R.id.webView);

        webView.setWebViewClient(webViewClient);

        webViewClient.prepareWebPage(webView);

    //    init();

        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(this, "CurrencyRateDB", 1 );   // here should be (this) not (this, "", 1 )
        ContentValues contentValues = new ContentValues();
    }

    private void initAllSpinners() {
        SP_currencySelected = findViewById(R.id.changeCurrency);
        SP_resultCurrencySelected = findViewById(R.id.changeResultCurrency);
    }

    public void onClick_doReverse(View view){
        int buf;

        initAllSpinners();

        buf = SP_currencySelected.getSelectedItemPosition();

        SP_currencySelected.setSelection(SP_resultCurrencySelected.getSelectedItemPosition());
        SP_resultCurrencySelected.setSelection(buf);
    }

    public void onClick_showGraphic(View view){
        initAllSpinners();

        currencyChoice = String.valueOf(SP_currencySelected.getSelectedItem());
        resultCurrencyChoice = String.valueOf(SP_resultCurrencySelected.getSelectedItem());

        WebViewer.getGraphic(webView, currencyChoice, resultCurrencyChoice);

    }
}
