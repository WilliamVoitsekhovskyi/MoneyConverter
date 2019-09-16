package com.example.moneyonverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
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
    String number = "";                                         //
    String updateTime = "0";                                    //
    String  UAH_USD = "";                                       // should be global
    String UAH = "";                                            //
    String makeCoefficientString = "";                          //
    String makeResultValueString = "";                          //


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
        UAH = getString(R.string.UAH);
        UAH_USD = getString(R.string.UAH_USD);
        enteredValue = findViewById(R.id.enteredValue);
        coefficient = findViewById(R.id.coefficientView);
        updateInfo = findViewById(R.id.UpdateView);
        currencySelected = findViewById(R.id.changeСurrency);
        resultCurrencySelected = findViewById(R.id.changeResultСurrency);
            try {
                number = enteredValue.getText().toString();
            }
            catch (NumberFormatException e){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "The field is empty!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        makeCoefficientString = Informer.getExchangeRate("USD-UAH") + UAH_USD;
                        coefficient.post(new Runnable() {
                            @Override
                            public void run() {
                                coefficient.setText(makeCoefficientString);
                            }
                        });
                        updateTime = Informer.getUpdateTime();
                        updateInfo.post(new Runnable() {
                            @Override
                            public void run() {
                                updateInfo.setText(updateTime);
                            }
                        });
                        try {
                        makeResultValueString = Informer.getExchangeResult(Double.valueOf(number), "USD-UAH") + UAH;
                    }
                    catch (NumberFormatException e){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "The field is empty!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }                    }
                    catch (NullPointerException e){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    convertedValue.post(new Runnable() {
                        @Override
                        public void run() {
                            convertedValue.setText(makeResultValueString);
                        }
                    });
                }
            }).start();
    }

//    public static boolean isOnline(Context context)
//    {
//        ConnectivityManager cm =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }
}