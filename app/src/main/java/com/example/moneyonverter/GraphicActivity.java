package com.example.moneyonverter;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;



public class GraphicActivity extends AppCompatActivity {

    Spinner SP_currencySelected;
    Spinner SP_resultCurrencySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        SP_resultCurrencySelected = findViewById(R.id.changeResultCurrency);
        SP_resultCurrencySelected.setSelection(3);

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
