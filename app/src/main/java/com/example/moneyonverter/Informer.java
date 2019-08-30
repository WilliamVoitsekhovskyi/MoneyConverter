package com.example.moneyonverter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;

public class Informer {
    private static String getInfoLine(){
        Document doc = null;
        try {
            String url = "https://freecurrencyrates.com/en/convert-USD-UAH";
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element link = doc.select("p").get(2);//2 - number of line which starting with tag <p>
        String linkText = link.text();
        return linkText;
    }
    public static String getUpdateTime(){ return getInfoLine().substring(45, 89);}

    public static double getExchangeRate(){ return Double.valueOf(getInfoLine().substring(37, 42)); }

    public static BigDecimal getExchangeResult(double number){
        BigDecimal result = BigDecimal.valueOf(number * getExchangeRate());
        return result.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

}