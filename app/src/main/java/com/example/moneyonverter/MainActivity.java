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
        EditText enteredValue = (EditText) findViewById(R.id.enteredValue);
       String number = "0";
        if(enteredValue.getText().length() == 0) {
            TextView textView = (TextView) findViewById(R.id.tv_exeption);
            textView.setText("Введіть суму");
        }
        else {
            TextView textView = (TextView) findViewById(R.id.tv_exeption);
            textView.setText("");
            number = enteredValue.getText().toString();

        }
        TextView coefficient = (TextView) findViewById(R.id.coefficientView);
        if(isOnline(this)) {
            if(enteredValue.getText().length() == 0) {
                TextView textView = (TextView) findViewById(R.id.tv_exeption);
                textView.setText("Введіть суму");
            }
            else {
                TextView textView = (TextView) findViewById(R.id.tv_exeption);
                textView.setText("");
                number = enteredValue.getText().toString();

            }
            coefficient.setText(Double.toString(Informer.getExchangeRate()) + " UAH = 1 USD");
            TextView convertedValue = (TextView) findViewById(R.id.resultView);
            convertedValue.setText(Double.toString(Informer.getExchangeResult(Double.valueOf(number))) + " USD");
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
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

}
