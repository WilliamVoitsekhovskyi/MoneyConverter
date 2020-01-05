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



}
