package com.example.moneyonverter;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;



public class GraphicActivity extends AppCompatActivity {

    Spinner SP_currencySelected;
    Spinner SP_resultCurrencySelected;
    private WebView webView;

    private class MyWebViewClient extends WebViewClient {



        @Override
        public void onPageFinished(WebView view, String url)
        {
            webView.loadUrl ("javascript: document.getElementByClassName ('frame fm-middle');");
        }


        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        // Для старых устройств
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        SP_resultCurrencySelected = findViewById(R.id.changeResultCurrency);
        SP_resultCurrencySelected.setSelection(3);
        final MyWebViewClient MyWebViewClient = new MyWebViewClient();
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(MyWebViewClient);
        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.floatrates.com/chart/usd/eur/");
        webView.getSettings().setBuiltInZoomControls(true);
        webView.scrollTo(0,1050);
        //webView.clearFormData();

        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(this, "CurrencyRateDB", 1 );   // here should be (this) not (this, "", 1 )
        ContentValues contentValues = new ContentValues();


    }

    public void onClick_doReverse(View view){
        int buf;

        SP_currencySelected = findViewById(R.id.changeCurrency);
        SP_resultCurrencySelected = findViewById(R.id.changeResultCurrency);

        buf = SP_currencySelected.getSelectedItemPosition();

        SP_currencySelected.setSelection(SP_resultCurrencySelected.getSelectedItemPosition());
        SP_resultCurrencySelected.setSelection(buf);
    }
}
