package com.example.MoneyConverter;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.parser.ParseException;

import java.io.IOException;

import MoneyConverterData.DataBaseWorker;


public class MainActivity extends AppCompatActivity {

    EditText ED_enteredValue;
    EditText ED_convertedValue;

    TextView TV_coefficient;
    TextView TV_updateInfo;

    Spinner SP_currencySelected;
    Spinner SP_resultCurrencySelected;

    String amountOfMoney;
    String fieldEmpty = "Field is empty";
    String noInternet;
    String dataBaseNotUpdated;
    String dataBaseUpdated;
    String waitForRateUpdate;
    String coefficientString;
    String resultValueString;
    String currencyChoice = "";
    String resultCurrencyChoice = "";

    ImageButton reverseImageButton;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaultCurrencyChoice();

        if (android.os.Build.VERSION.SDK_INT > 19)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(DataBaseWorker.IsTimeForUpdate(context)){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, waitForRateUpdate, Toast.LENGTH_SHORT).show();
                        }
                    });
                    try {
                        DataBaseWorker.setRateIntoDateBase(context);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "DataBase Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

    }

    public void setDefaultCurrencyChoice(){
        SP_resultCurrencySelected = findViewById(R.id.changeResultCurrency);
        SP_resultCurrencySelected.setSelection(3);
    }

    public void init(){
        noInternet = getString(R.string.noInternet);
        dataBaseNotUpdated = getString(R.string.dataBaseNotUpdated);
        dataBaseUpdated = getString(R.string.dataBaseUpdated);
        waitForRateUpdate = getString(R.string.waitForRateUpdate);

        initAllEditTexts();
        initAllTextViewers();
        initAllSpinners();

        currencyChoice = String.valueOf(SP_currencySelected.getSelectedItem());
        resultCurrencyChoice = String.valueOf(SP_resultCurrencySelected.getSelectedItem());

        reverseImageButton = findViewById(R.id.reverseButton);

    }

    private void initAllEditTexts(){
        ED_enteredValue = findViewById(R.id.enteredValue);
        ED_convertedValue = findViewById(R.id.resultView);
        ED_convertedValue.setFocusable(false);


    }

    private void initAllTextViewers(){
        TV_coefficient = findViewById(R.id.coefficientView);
        TV_coefficient.setFocusable(false);
        TV_updateInfo = findViewById(R.id.UpdateView);
    }

    private void initAllSpinners(){
        SP_currencySelected = findViewById(R.id.changeCurrency);
        SP_resultCurrencySelected = findViewById(R.id.changeResultCurrency);
    }

    public void onClick_setConvertedValue(final View view){
        init();
        try {
            amountOfMoney = ED_enteredValue.getText().toString();
        }
        catch (NumberFormatException e){
            runOnUiThread(new Runnable() {
                public void run() {
                    showError();
                }
            });
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    coefficientString = DataBaseWorker.getCoefficient(currencyChoice, resultCurrencyChoice, context);
                    TV_coefficient.post(new Runnable() {
                        @Override
                        public void run() {
                            TV_coefficient.setText(coefficientString);
                        }
                    });
                    TV_updateInfo.post(new Runnable() {
                        @Override
                        public void run() {
                            TV_updateInfo.setText(DataBaseWorker.getDateOfUpdateCurrency(currencyChoice, resultCurrencyChoice, context));
                        }
                    });
                    try {

                        resultValueString = DataBaseWorker.getResult(amountOfMoney, currencyChoice, resultCurrencyChoice, context);
                    }
                    catch (NumberFormatException e){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                showError();
                            }
                        });
                    }
                }
                catch (NullPointerException e){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, noInternet, Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (NumberFormatException e){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, dataBaseNotUpdated + " - " +
                                    getString(R.string.updateRateInformation), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                ED_convertedValue.post(new Runnable() {
                    @Override
                    public void run() {
                        ED_convertedValue.setText(resultValueString);
                    }
                });
            }
        }).start();
    }

    private void showError(){
        Toast.makeText(MainActivity.this, fieldEmpty, Toast.LENGTH_SHORT).show();
    }

    public void onClick_copyResult(View view) {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", ED_convertedValue.getText());
                    clipboard.setPrimaryClip(clip);
                } catch (NullPointerException e) {
                    showError();
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
            showError();
        }

    }
    public void onClick_doReverse(View view){
        int buf;

        initAllSpinners();

        buf = SP_currencySelected.getSelectedItemPosition();
        SP_currencySelected.setSelection(SP_resultCurrencySelected.getSelectedItemPosition());
        SP_resultCurrencySelected.setSelection(buf);
    }

    public void goToGraphic(View view){
        Intent intent = new Intent(this, GraphicActivity.class);
        startActivity(intent);
    }

    public void onClick_updateRateInformation (View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, waitForRateUpdate, Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    DataBaseWorker.setRateIntoDateBase(context);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, dataBaseUpdated, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }




}