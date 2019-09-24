package com.example.moneyonverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText ED_enteredValue;
    EditText ED_convertedValue;

    TextView TV_coefficient;
    TextView TV_updateInfo;

    Spinner SP_currencySelected;
    Spinner SP_resultCurrencySelected;

    String number;                                         //
    String updateTime;                                    //
    String UAH_USD;                                       // should be global
    String UAH;                                            //
    String fieldEmpty = "Field is empty";                  //should be from string values
    String noInternet;                                     //
    String makeCoefficientString;                          //
    String makeResultValueString;
    String comparedChoice = "";

    ImageButton reverseImageButton;

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

//        SP_currencySelected.setSelection(1);              //used to set choice by default
//        SP_resultCurrencySelected.setSelection(4);        //but it doesn't work in onCreate. why??
    }

    public void onClick_setConvertedValue(View view){
        UAH = getString(R.string.UAH);
        UAH_USD = getString(R.string.UAH_USD);
        noInternet = getString(R.string.noInternet);

        ED_enteredValue = findViewById(R.id.enteredValue);
        ED_convertedValue = findViewById(R.id.resultView);
        ED_convertedValue.setFocusable(false);

        TV_coefficient = findViewById(R.id.coefficientView);
        TV_coefficient.setFocusable(false);
        TV_updateInfo = findViewById(R.id.UpdateView);

        reverseImageButton = findViewById(R.id.reverseButton);

        SP_currencySelected = findViewById(R.id.changeСurrency);
        SP_resultCurrencySelected = findViewById(R.id.changeResultСurrency);

        String currencyChoice = String.valueOf(SP_currencySelected.getSelectedItem());
        String resultCurrencyChoice = String.valueOf(SP_resultCurrencySelected.getSelectedItem());
        comparedChoice = currencyChoice + "-" + resultCurrencyChoice;

        try {
            number = ED_enteredValue.getText().toString();
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
                    makeCoefficientString = Informer.getExchangeRate(comparedChoice) + UAH_USD;
                    TV_coefficient.post(new Runnable() {
                        @Override
                        public void run() {
                            TV_coefficient.setText(makeCoefficientString);
                        }
                    });
                    updateTime = Informer.getUpdateTime();
                    TV_updateInfo.post(new Runnable() {
                        @Override
                        public void run() {
                            TV_updateInfo.setText(updateTime);
                        }
                    });
                    try {
                        makeResultValueString = Informer.getExchangeResult(Double.valueOf(number), comparedChoice) + " " + UAH;
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
                ED_convertedValue.post(new Runnable() {
                    @Override
                    public void run() {
                        ED_convertedValue.setText(makeResultValueString);
                    }
                });
            }
        }).start();
    }
    public void onClick_copyResult(View view) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", ED_convertedValue.getText());
                    clipboard.setPrimaryClip(clip);
                } catch (NullPointerException e) {
                    Toast.makeText(MainActivity.this, fieldEmpty, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onClick_copyCoefficient(View view){
        try{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", (TV_coefficient.getText()));
            clipboard.setPrimaryClip(clip);
        }
        catch (NullPointerException e) {
            Toast.makeText(MainActivity.this, fieldEmpty, Toast.LENGTH_SHORT).show();
        }

    }
    public void onClick_doReverse(View view){
        Toast.makeText(MainActivity.this,  "test", Toast.LENGTH_SHORT).show();//

    }
}