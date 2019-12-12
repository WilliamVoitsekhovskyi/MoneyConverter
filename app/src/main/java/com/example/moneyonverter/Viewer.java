package com.example.moneyonverter;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.math.BigDecimal;

public class Viewer extends Thread {

   private static int scale = 2;
   /*rename: tmp in getCoefficient method
   * try to optimize: "Currency.getCurrencyExchangeRate(currencyChoice, resultCurrencyChoice,  "rate")"
   * */


   public static String getCoefficient (String currencyChoice, String resultCurrencyChoice) throws IOException, ParseException{
      BigDecimal tmp;
      tmp = BigDecimal.valueOf(CurrencyJSON.getCurrencyExchangeRate(currencyChoice, resultCurrencyChoice));
      return String.valueOf(tmp.setScale(scale, BigDecimal.ROUND_HALF_UP));
   }

   public static String getResult(String amountOfMoney, String currencyChoice, String resultCurrencyChoice)throws IOException, ParseException{
      BigDecimal result;
      result = BigDecimal.valueOf(Double.valueOf(amountOfMoney) * CurrencyJSON.getCurrencyExchangeRate(currencyChoice, resultCurrencyChoice));
      return String.valueOf(result.setScale(scale, BigDecimal.ROUND_HALF_UP));
   }

}
