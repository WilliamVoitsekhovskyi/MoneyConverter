package com.example.moneyonverter;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.math.BigDecimal;

public class Viewer extends Thread {


   /*rename: tmp in getCoefficient method
   * try to optimize: "Currency.getCurrencyExchangeRate(currencyChoice, resultCurrencyChoice,  "rate")"
   * */


   public static String getCoefficient (String currencyChoice, String resultCurrencyChoice) throws IOException, ParseException{
      BigDecimal tmp;
      tmp = BigDecimal.valueOf(Currency.getCurrencyExchangeRate(currencyChoice, resultCurrencyChoice,  "rate"));
      return String.valueOf(tmp.setScale(2, BigDecimal.ROUND_HALF_UP));
   }

   public static String getResult(String amountOfMoney, String currencyChoice, String resultCurrencyChoice)throws IOException, ParseException{
      BigDecimal result;
      result = BigDecimal.valueOf(Double.valueOf(amountOfMoney) * Currency.getCurrencyExchangeRate(currencyChoice, resultCurrencyChoice, "rate"));
      return String.valueOf(result.setScale(2, BigDecimal.ROUND_HALF_UP));
   }

   public static void a(){}

}
