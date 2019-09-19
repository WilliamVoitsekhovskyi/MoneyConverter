package com.example.moneyonverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    String number;                                         //
    String updateTime = "0";                                    //
    String  UAH_USD;                                       // should be global
    String UAH;                                            //
    String fieldEmpty = "Field is empty";//should be from string values                                     //
    String noInternet = "";                                     //
    String makeCoefficientString = "";                          //
    String makeResultValueString = "";

    ImageButton reverseButton;

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
        noInternet = getString(R.string.noInternet);
        enteredValue = findViewById(R.id.enteredValue);
        coefficient = findViewById(R.id.coefficientView);
        coefficient.setFocusable(false);
        updateInfo = findViewById(R.id.UpdateView);
        reverseButton = findViewById(R.id.reverseButton);
        currencySelected = findViewById(R.id.changeСurrency);
        resultCurrencySelected = findViewById(R.id.changeResultСurrency);

            try {
                number = enteredValue.getText().toString();
            }
            catch (NumberFormatException e){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, fieldEmpty, Toast.LENGTH_SHORT).show();
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
                        makeResultValueString = Informer.getExchangeResult(Double.valueOf(number), "USD-UAH") + " " + UAH;
                    }
                    catch (NumberFormatException e){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, fieldEmpty, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }                    }
                    catch (NullPointerException e){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, noInternet, Toast.LENGTH_SHORT).show();
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
    public void copyResult(View view) {
        try {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", convertedValue.getText());
            clipboard.setPrimaryClip(clip);
        }
        catch (NullPointerException e) {
            Toast.makeText(MainActivity.this, fieldEmpty, Toast.LENGTH_SHORT).show();
                }

            }
    public void copyCoefficient(View view){
        try{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", coefficient.getText());
            clipboard.setPrimaryClip(clip);
        }
        catch (NullPointerException e) {
            Toast.makeText(MainActivity.this, fieldEmpty, Toast.LENGTH_SHORT).show();
    }

    }
    public void reverseClick(View view){
        Toast.makeText(MainActivity.this,  "test", Toast.LENGTH_SHORT).show();//

    }
}