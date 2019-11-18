package com.example.moneyonverter;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import java.io.IOException;



public class GraphicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        Gson gson = new Gson();

        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(this, "CurrencyRateDB", 1 );   // here should be (this) not (this, "", 1 )
        ContentValues contentValues = new ContentValues();

        try {
            String url = "http://www.floatrates.com/daily/uah.json";
            String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
            CurrencyInfo currencyInfo = gson.fromJson(json, CurrencyInfo.class);
            System.out.println(gson.toJson(currencyInfo));
            TextView textView = findViewById(R.id.textView);
            textView.setText(currencyInfo.date);
            TextView textView5 = findViewById(R.id.textView5);
            textView5.setText(currencyInfo.code);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
