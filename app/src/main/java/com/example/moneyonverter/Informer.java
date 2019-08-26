package com.example.moneyonverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Informer {
    public static double getExchangeRate(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://freecurrencyrates.com/en/convert-USD-UAH").get();
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