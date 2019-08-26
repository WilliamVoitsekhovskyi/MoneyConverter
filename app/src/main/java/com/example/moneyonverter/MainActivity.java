package com.example.moneyonverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void setConvertedValue(View view){
        EditText enteredValue = (EditText) findViewById(R.id.enteredValue);
        String number = enteredValue.getText().toString();

        TextView coefficient = (TextView) findViewById(R.id.coefficientView);
        coefficient.setText(Double.toString(Informer.getExchangeRate()) + " UAH = 1 USD");
        TextView convertedValue = (TextView) findViewById(R.id.resultView);
        convertedValue.setText(Double.toString(Informer.getExchangeResult(Integer.valueOf(number))) + " USD");
        //convertedValue.setText(buf);
    }
}
