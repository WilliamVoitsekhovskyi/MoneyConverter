package com.example.moneyonverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText enteredValue;
    EditText convertedValue;
    TextView coefficient;
    TextView updateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 19)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        convertedValue = findViewById(R.id.resultView);
        convertedValue.setFocusable(false);                                       // to make EditText field(resultView) uneditable


    }

    public void setConvertedValue(View view){
        enteredValue = findViewById(R.id.enteredValue);
        coefficient = findViewById(R.id.coefficientView);
        updateInfo = findViewById(R.id.UpdateView);
        String number = "0";
        String UAH_USD = getString(R.string.UAH_USD);
        String UAH = getString(R.string.UAH);
        switch (view.getId())
        {
            case R.id.convertButton:
                if(isOnline(this)) {
                    if(enteredValue.getText().length() == 0) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "The field is empty!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        number = enteredValue.getText().toString();
                    }
                    updateInfo.setText(Informer.getUpdateTime());

                    String makeCoefficientString = Informer.getExchangeRate() + UAH_USD;
                    coefficient.setText(makeCoefficientString);
                    String makeResultValueString = Informer.getExchangeResult(Double.valueOf(number)) + UAH;
                    convertedValue.setText(makeResultValueString);

                }
                if(isOnline(this)) {
                    if(enteredValue.getText().length() == 0) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "The field is empty!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        number = enteredValue.getText().toString();
                    }
                    updateInfo.setText(Informer.getUpdateTime());
                    String currencyName = Informer.getExchangeRate() + UAH_USD;
                    coefficient.setText(currencyName);

                    convertedValue.setText(Informer.getExchangeResult(Double.valueOf(number)) + " UAH");

                }
     }
    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }






}