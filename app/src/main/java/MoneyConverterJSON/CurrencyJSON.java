package MoneyConverterJSON;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class CurrencyJSON {

    private static JSONObject createJSONObject(String currencyCode) throws IOException, ParseException {
        // website with JSON http://www.floatrates.com/daily/usd.json

        currencyCode = currencyCode.toLowerCase();

        String data = Jsoup.connect("http://www.floatrates.com/daily/" + currencyCode + ".json").ignoreContentType(true).execute().body();

        Object object = new JSONParser().parse(data);
        return (JSONObject) object;
    }

    public static double getCurrencyExchangeRate(String currencyCode, String currencyResultCode) throws IOException, ParseException {
        String chosenRate = "rate";
        currencyCode = currencyCode.toLowerCase();
        currencyResultCode = currencyResultCode.toLowerCase();

        JSONObject jsonObject = createJSONObject(currencyCode);

        Map currency;
        currency = ((Map)jsonObject.get(currencyResultCode));
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
            //use usd because if you will use eur you can't parse information about eur
            jsonObject = createJSONObject("usd");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        assert jsonObject != null;
        Map currency = ((Map) jsonObject.get(currencyCode));

        for (Map.Entry pair : (Iterable<Map.Entry>) currency.entrySet()) {
            if(pair.getKey().equals(dateStr))
                return (String) pair.getValue();
        }
        return null;
    }


}
