package com.example.moneyonverter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;

public class Informer {
    public static void main(String[] args){
    }
    //<span xmlns:fo="http://www.w3.org/1999/XSL/Format" style="float: right;font-size: 12px; ;color: #2d2d2d;padding:0;margin-bottom:30px">updated 13:00:11(EEST) 8/09/2019</span>
    private static Document getDocument() {
        Document doc = null;
        try {
            String url = "https://fx-rate.net/UAH/";
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static String getUpdateTime(){
        Element link = getDocument().select("div #toptencurrencies").select("span").get(3);//3 - number of line which starting with tag <span>
        return link.text();
    }

    public static double getExchangeRate(String currencyChoice){
        String attribute = "";
        System.out.println(currencyChoice);
        switch (currencyChoice) {
            case "UAH-EUR":
                attribute = "a[href=/UAH/EUR/]";
                break;
            case "UAH-USD":
                attribute = "a[href=/UAH/USD/]";
                break;
            case "UAH-RUB":
                attribute = "a[href=/UAH/RUB/]";
                break;
            case "EUR-USD":
                attribute = "a[href=/EUR/USD/]";
                break;
            case "EUR-RUB":
                attribute = "a[href=/EUR/RUB/]";
                break;
            case "EUR-UAH":
                attribute = "a[href=/EUR/UAH/]";
                break;
            case "RUB-USD":
                attribute = "a[href=/RUB/USD/]";
                break;
            case "RUB-EUR":
                attribute = "a[href=/RUB/EUR/]";
                break;
            case "RUB-UAH":
                attribute = "a[href=/RUB/UAH/]";
                break;
            case "USD-UAH":
                attribute = "a[href=/USD/UAH/]";
                break;
            case "USD-EUR":
                attribute = "a[href=/USD/EUR/]";
                break;
            case "USD-RUB":
                attribute = "a[href=/USD/RUB/]";
                break;
            default:
                System.out.println("ERROR");
                break;
        }
        Element link = getDocument().select(attribute).first();//2 - number of line which starting with tag <p>
        String linkText = link.text();
        return Double.parseDouble(linkText);
    }

    public static BigDecimal getExchangeResult(double number, String currencyChoice){
        BigDecimal result = BigDecimal.valueOf(number * getExchangeRate(currencyChoice));
        return result.setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}