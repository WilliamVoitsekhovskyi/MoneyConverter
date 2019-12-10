package com.example.moneyonverter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class Currency {

    private static JSONObject createJSONObject(String currencyCode) throws IOException, ParseException {
        //http://www.floatrates.com/daily/usd.json

        currencyCode = currencyCode.toLowerCase();

        String data = Jsoup.connect("http://www.floatrates.com/daily/" + currencyCode + ".json").ignoreContentType(true).execute().body();

        Object object = new JSONParser().parse(data);
        return (JSONObject) object;
    }

    public static double getCurrencyExchangeRate(String currencyCode, String currencyResultCode, String chosenRate) {

        currencyCode = currencyCode.toLowerCase();
        currencyResultCode = currencyResultCode.toLowerCase();

        JSONObject jsonObject = null;

        try {
            jsonObject = createJSONObject(currencyCode);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Map currency = ((Map)jsonObject.get(currencyResultCode));
        for (Map.Entry pair : (Iterable<Map.Entry>) currency.entrySet()) {
            if(pair.getKey().equals(chosenRate))
                return (double) pair.getValue();
        }
        return 0;
    }

    public static String getDateOfUpdateCurrency() {

        String dateStr = "date";
        //it's doesn't matter which code you'll use(they all update at the same time, but eur is the first currency in the list
        String currencyCode = "eur";

        JSONObject jsonObject = null;
        try {
            jsonObject = createJSONObject("usd");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }



        Map currency = ((Map) jsonObject.get(currencyCode));

        for (Map.Entry pair : (Iterable<Map.Entry>) currency.entrySet()) {
            if(pair.getKey().equals(dateStr))
                return (String) pair.getValue();
        }
        return null;
    }


}
