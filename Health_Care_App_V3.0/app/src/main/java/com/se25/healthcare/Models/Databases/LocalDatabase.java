package com.se25.healthcare.Models.Databases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.se25.healthcare.Menu.Components.BMI.BMIItem;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Components.Breath.BreathItem;
import com.se25.healthcare.Menu.Components.Calories.CaloriesItem;
import com.se25.healthcare.Menu.Components.Heart.HeartItem;
import com.se25.healthcare.Menu.Components.Period.PeriodItem;
import com.se25.healthcare.Menu.Components.Phone.PhoneItem;
import com.se25.healthcare.Menu.Components.Sleep.SleepItem;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LocalDatabase extends SQLiteOpenHelper {
    public static String DatabaseName = "HealthCare.db";
    public LocalDatabase(@Nullable Context context, int version) {
        super(context, DatabaseName, null, version);
    }

    public static void init(Context context,int version){
        LocalDatabase db = new LocalDatabase(context,version);
        db.setData("CREATE TABLE IF NOT EXISTS Item(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DateCreate LONG," +
                "Data1 TEXT," +
                "Data2 TEXT," +
                "ResTitleId INTEGER)");
        db.close();
    }

    public static void createDataTest(Context context,int version){
        LocalDatabase db = new LocalDatabase(context,version);
        ArrayList<Item> data = new ArrayList<>();
        data.addAll(Tool.createDataTool(R.string.button_heart));
        data.addAll(Tool.createDataTool(R.string.button_BMI));
        data.addAll(Tool.createDataTool(R.string.button_calories));
        data.addAll(Tool.createDataTool(R.string.button_breath));
        data.addAll(Tool.createDataTool(R.string.button_sleep));
        data.addAll(Tool.createDataTool(R.string.button_period));
        data.addAll(Tool.createDataTool(R.string.button_phone));
        db.saveListData(data);
        db.close();
    }

    @SuppressLint("DefaultLocale")
    public void saveListData(ArrayList<Item> listData) {
        if(listData.size()==0)
            return;
        setData("DELETE FROM Item WHERE ResTitleId="+listData.get(0).getResTitleId());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO Item VALUES ");

        for (Item item: listData) {
            long datetime = Tool.getTime(item.getDateCreate(),R.string.second);
            sqlBuilder.append(String.format("(null, %d,'%s','%s',%d),",datetime,item.getData(0),item.getData(1),item.getResTitleId()));
        }
        String sql = sqlBuilder.toString();
        if(listData.size()>0)
            sql = sql.substring(0,sql.length()-1);
        sql+=";";

        setData(sql);
    }

    @SuppressLint("DefaultLocale")
    public void insertItem(Item item) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO Item VALUES ");

        long datetime = Tool.getTime(item.getDateCreate(),R.string.second);
        sqlBuilder.append(String.format("(null, %d,'%s','%s',%d)",datetime,item.getData(0),item.getData(1),item.getResTitleId()));

        String sql = sqlBuilder.toString();
        sql+=";";

        setData(sql);
    }

    @SuppressLint("DefaultLocale")
    public void updateItem(Item item) {
        setData(String.format("UPDATE Item SET Data1 = '%s', Data2 = '%s' WHERE DateCreate = %d AND ResTitleId = %d",
                item.getData(0),
                item.getData(1),
                Tool.getTime(item.getDateCreate(), R.string.second),
                item.getResTitleId()));
    }

    @SuppressLint("DefaultLocale")
    public void deleteItem(Item item) {
        setData(String.format("DELETE FROM Item WHERE DateCreate = %d AND ResTitleId = %d",
                Tool.getTime(item.getDateCreate(), R.string.second),
                item.getResTitleId()));
    }

    public void deleteData() {
        setData("DELETE FROM Item");
    }

    public ArrayList<Item> getListData(int resTitleId) {
        ArrayList<Item> listResult = new ArrayList<>();
        Cursor cursor = getData("SELECT * FROM Item WHERE ResTitleId="+resTitleId);
        if(cursor.moveToFirst())
            for(;!cursor.isAfterLast();cursor.moveToNext())
            {
                LocalDateTime dateCreate = Tool.setTime(cursor.getLong(1),R.string.second);
                String data1 = cursor.getString(2);
                String data2 = cursor.getString(3);
                listResult.add(typeConstructor(resTitleId,dateCreate,data1,data2));
            }
        listResult.sort((i1,i2)->i2.getDateCreate().compareTo(i1.getDateCreate()));
        return listResult;
    }

    @SuppressLint("NonConstantResourceId")
    private Item typeConstructor(int resTitleId,LocalDateTime dateCreate, String...data) {
        switch (resTitleId) {
            case R.string.button_heart: return new HeartItem(dateCreate,data[0],data[1]);
            case R.string.button_BMI:  return new BMIItem(dateCreate,data[0],data[1]);
            case R.string.button_calories: return new CaloriesItem(dateCreate,data[0],data[1]);
            case R.string.button_breath: return new BreathItem(dateCreate,data[0],data[1]);
            case R.string.button_sleep: return new SleepItem(dateCreate,data[0],data[1]);
            case R.string.button_period: return new PeriodItem(dateCreate,data[0],data[1]);
            case R.string.button_phone: return new PhoneItem(dateCreate,data[0],data[1]);
            default: return null;
        }
    }

    //Base method
    private void setData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    private Cursor getData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
