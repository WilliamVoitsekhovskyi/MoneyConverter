package com.example.moneyonverter;

public class CurrencyInfo {
    public String date;
    public String code;
    public double rate;
    public double reversedRate;

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setReversedRate(double reversedRate) {
        this.reversedRate = reversedRate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public double getReversedRate() {
        return reversedRate;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public CurrencyInfo(String date, String code){
        //   this.exchangeRate = exchangeRate;

        this.date = date;
        this.code = code;
    }


}
