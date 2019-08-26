package com.example.moneyonverter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Informer {
    public static double getExchangeRate(){
        Document doc = null;
        try {
            String url = "https://freecurrencyrates.com/en/convert-USD-UAH";
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element link = doc.select("p").get(2);//2 - number of line which starting with tag <p>
        String linkText = link.text();
        return Double.valueOf(linkText.substring(37,42));
    }
    public static double getExchangeResult(double number){
        return number * getExchangeRate();
    }
}