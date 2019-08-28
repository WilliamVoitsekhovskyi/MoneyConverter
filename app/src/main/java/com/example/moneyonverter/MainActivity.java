package com.example.moneyonverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        EditText enteredValue = findViewById(R.id.enteredValue);
        TextView convertedValue = findViewById(R.id.resultView);
        TextView coefficient = findViewById(R.id.coefficientView);
        TextView textView = findViewById(R.id.tv_exeption);
        TextView updateInfo = findViewById(R.id.UpdateView);
        String number = "0";
        if(isOnline(this)) {
            if(enteredValue.getText().length() == 0) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "The field is empty!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                textView.setText("");
                number = enteredValue.getText().toString();

            }
            updateInfo.setText(Informer.getUpdateTime());
            coefficient.setText(Informer.getExchangeRate() + " UAH = 1 USD");
            convertedValue.setText(Informer.getExchangeResult(Double.valueOf(number)) + " USD");

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No internet connection", Toast.LENGTH_SHORT);
            toast.show();
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