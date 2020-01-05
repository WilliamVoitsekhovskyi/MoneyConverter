package MoneyConverterData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import MoneyConverterJSON.CurrencyJSON;

public class DataBaseWorker {
    private static int scaleForBigDecimal = 2;

    public static void setRateIntoDateBase(Context context) throws IOException, ParseException {
        context.deleteDatabase(CurrencyDataBaseHelper.DB_Name);
        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(context, CurrencyDataBaseHelper.DB_Name, 1 );
        SQLiteDatabase database = currencyDataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String[] currency = new String[]{"USD", "EUR", "RUB", "UAH", "PLN", "GBP", "CZK", "BYN"};
        String currencyChoice;
        String currencyResultChoice;

        System.out.println("создав базу");

        for(int i = 0; i < currency.length; i++ ){
            currencyChoice = currency[i];
            for(int j = 0; j < currency.length; j++){
                if(j != i) {
                    currencyResultChoice = currency[j];
                    System.out.println("почав получати данні");
                    contentValues.put(CurrencyDataBaseHelper.COL2_CURRENCY_NAME, currencyChoice + "-" + currencyResultChoice);
                    contentValues.put(CurrencyDataBaseHelper.COL3_RATE, CurrencyJSON.getCurrencyExchangeRate(currency[i],currency[j]));
                    contentValues.put(CurrencyDataBaseHelper.COL4_TIME, dateFormat.format(date));
                    database.insert(currencyDataBaseHelper.TABLE_NAME, null, contentValues);
                    System.out.println("вставив данні");
                }
            }
        }
        System.out.println("закінчив заповнення бази");
        currencyDataBaseHelper.close();
        database.close();
    }

    public static String DB_test (Context context){
        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(context, CurrencyDataBaseHelper.DB_Name, 1 );
        SQLiteDatabase database = currencyDataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(CurrencyDataBaseHelper.TABLE_NAME,null,null, null,null,null,null);
        String test = "test";

        System.out.println("зайшов в метод");


        if (cursor.moveToFirst()){
            do {
                int idIndex = cursor.getColumnIndex(CurrencyDataBaseHelper.COL1_ID);
                int idCurrencyName = cursor.getColumnIndex(CurrencyDataBaseHelper.COL2_CURRENCY_NAME);
                int idRate = cursor.getColumnIndex(CurrencyDataBaseHelper.COL3_RATE);
                int idDate = cursor.getColumnIndex(CurrencyDataBaseHelper.COL4_TIME);

                System.out.println("получив індекси");



                test += "\n" + "ID - " + cursor.getInt(idIndex) + "\n"
                        + "NAME - " + cursor.getString(idCurrencyName) + "\n"
                        + "RATE - " + cursor.getString(idRate) + "\n"
                        + "DATE - " + cursor.getString(idDate) + "\n";

                System.out.println("почав получати данні з бази");

            }while (cursor.moveToNext());

        }
//        String currencyChoice = "UAH";
//        String resultCurrencyChoice = "USD";
//        String wishedCurrency = currencyChoice + "-" + resultCurrencyChoice;
//        String[] selectionArgs = {wishedCurrency};
//        cursor = database.query(CurrencyDataBaseHelper.TABLE_NAME, null, CurrencyDataBaseHelper.COL2_CURRENCY_NAME +" = ?", selectionArgs, null,null,null );
//        if (cursor.moveToNext()){
//            test = cursor.getString(cursor.getColumnIndex(CurrencyDataBaseHelper.COL1_ID)) +"\n" +
//                    cursor.getString(cursor.getColumnIndex(CurrencyDataBaseHelper.COL2_CURRENCY_NAME)) +"\n" +
//                    cursor.getString(cursor.getColumnIndex(CurrencyDataBaseHelper.COL3_RATE)) +"\n" +
//                    cursor.getString(cursor.getColumnIndex(CurrencyDataBaseHelper.COL4_TIME)) +"\n";
//        }

        System.out.println("вивів");
        currencyDataBaseHelper.close();
        database.close();
        return test;

    }

    public static Boolean IsTimeForUpdate(Context context){
        String dateOfLastUpdate = "";
        String currentDate = "";
        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(context, CurrencyDataBaseHelper.DB_Name, 1 );
        SQLiteDatabase database = currencyDataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query(CurrencyDataBaseHelper.TABLE_NAME,null,null, null,null,null,null);
        if(cursor.moveToNext()){
            int idDate = cursor.getColumnIndex(CurrencyDataBaseHelper.COL4_TIME);
            dateOfLastUpdate = cursor.getString(idDate);
        }
        currencyDataBaseHelper.close();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        currentDate = dateFormat.format(date);
        return !currentDate.equals(dateOfLastUpdate);
    }

    public static String getCoefficient(String currencyChoice, String resultCurrencyChoice, Context context){
        String coefficient = "";
        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(context, CurrencyDataBaseHelper.DB_Name, 1 );
        SQLiteDatabase database = currencyDataBaseHelper.getReadableDatabase();
        String[] selectionArgs = {currencyChoice + "-" + resultCurrencyChoice};
        Cursor cursor = database.query(CurrencyDataBaseHelper.TABLE_NAME, null, CurrencyDataBaseHelper.COL2_CURRENCY_NAME +" = ?", selectionArgs, null,null,null );
        if (cursor.moveToNext()){
            coefficient = cursor.getString(cursor.getColumnIndex(CurrencyDataBaseHelper.COL3_RATE));
        }
        BigDecimal bigDecimal = new BigDecimal(coefficient);
        return String.valueOf(bigDecimal.setScale(scaleForBigDecimal, BigDecimal.ROUND_HALF_UP));
    }

    public static String getResult(String amountOfMoney, String currencyChoice, String resultCurrencyChoice, Context context){
        BigDecimal result;
        BigDecimal coefficient = new BigDecimal(getCoefficient(currencyChoice, resultCurrencyChoice, context));
        BigDecimal amount = new BigDecimal(amountOfMoney);
        result = coefficient.multiply(amount);
        return String.valueOf(result.setScale(scaleForBigDecimal, BigDecimal.ROUND_HALF_UP));
    }

    public static String getDateOfUpdateCurrency(String currencyChoice, String resultCurrencyChoice, Context context){
        String timeOfUpdate = "";
        CurrencyDataBaseHelper currencyDataBaseHelper = new CurrencyDataBaseHelper(context, CurrencyDataBaseHelper.DB_Name, 1 );
        SQLiteDatabase database = currencyDataBaseHelper.getReadableDatabase();
        String[] selectionArgs = {currencyChoice + "-" + resultCurrencyChoice};
        Cursor cursor = database.query(CurrencyDataBaseHelper.TABLE_NAME, null, CurrencyDataBaseHelper.COL2_CURRENCY_NAME +" = ?", selectionArgs, null,null,null );
        if (cursor.moveToNext()){
            timeOfUpdate = cursor.getString(cursor.getColumnIndex(CurrencyDataBaseHelper.COL4_TIME));
        }
        return timeOfUpdate;
    }
}
