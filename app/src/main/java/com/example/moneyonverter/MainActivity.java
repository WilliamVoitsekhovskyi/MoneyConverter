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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    EditText enteredValue;
    EditText convertedValue;

    TextView coefficient;
    TextView updateInfo;

    Spinner currencySelected;
    Spinner resultCurrencySelected;

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
        currencySelected = findViewById(R.id.changeСurrency);
        resultCurrencySelected = findViewById(R.id.changeResultСurrency);
        String number = "0";
        String UAH_USD = getString(R.string.UAH_USD);
        String UAH = getString(R.string.UAH);

                if(isOnline(this)) {
                    if(enteredValue.getText().length() == 0) {
                        Toast toastEmptyField = Toast.makeText(getApplicationContext(),
                                "The field is empty!", Toast.LENGTH_SHORT);
                        toastEmptyField.show();
                    }
                    else {
                        number = enteredValue.getText().toString();
                    }
                    updateInfo.setText(Informer.getUpdateTime());
                    String makeCoefficientString = Informer.getExchangeRate("USD-UAH") + UAH_USD;
                    coefficient.setText(makeCoefficientString);
                    String makeResultValueString = Informer.getExchangeResult(Double.valueOf(number), "USD-UAH") + UAH;
                    convertedValue.setText(makeResultValueString);
                }
                else {
                    Toast toastNoInternet = Toast.makeText(getApplicationContext(),
                            "No Internet connection", Toast.LENGTH_SHORT);
                    toastNoInternet.show();
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