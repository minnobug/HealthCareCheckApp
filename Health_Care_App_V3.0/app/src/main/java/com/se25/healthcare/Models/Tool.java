package com.se25.healthcare.Models;

import android.annotation.SuppressLint;

import com.se25.healthcare.Menu.Components.BMI.BMIItem;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Components.Breath.BreathItem;
import com.se25.healthcare.Menu.Components.Calories.CaloriesItem;
import com.se25.healthcare.Menu.Components.Heart.HeartItem;
import com.se25.healthcare.Menu.Components.Period.PeriodItem;
import com.se25.healthcare.Menu.Components.Phone.PhoneItem;
import com.se25.healthcare.Menu.Components.Sleep.SleepItem;
import com.se25.healthcare.R;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public abstract class Tool {
    public static String pattern_date_time = "HH:mm dd/MM/yy";
    public static String pattern_date = "dd/MM/yyyy";
    @SuppressLint("NonConstantResourceId")
    public static ArrayList<Item> createDataTool(int redTitleId) {
        ArrayList<Item> dataTest = new ArrayList<>();
        Random r = new Random();
        switch (redTitleId) {
            case R.string.button_BMI:
                for (int i = 0; i <= 100; i++) {
                    int k = (int) (-0.005 * (i - 25) * (i - 25) + 0.5 * (i - 25) + 50);
                    LocalDateTime ldt = LocalDateTime.now();
                    ldt = ldt.plusDays(-i);
                    dataTest.add(new BMIItem(ldt,r.nextInt(2) + 25 + k, 170 ));
                }
                break;
            case R.string.button_heart:
                for (int i = 0; i <= 100; i++) {
                    int k = (int) (-0.05 * (i - 25) * (i - 25) + 0.5 * (i - 25) + 50);
                    LocalDateTime ldt = LocalDateTime.now();
                    ldt = ldt.plusDays(-i);
                    dataTest.add(new HeartItem(ldt, r.nextInt(2) + 90 + k, r.nextInt(2) + 90 + k));
                }
                break;
            case R.string.button_calories:
                for (int i = 0; i <= 100; i++) {
                    int k = (int) (-0.01 * (i - 25) * (i - 25) + 0.5 * (i - 25) + 50);
                    LocalDateTime ldt = LocalDateTime.now();
                    ldt = ldt.plusDays(-i);
                    dataTest.add(new CaloriesItem( ldt,1000 + k, 0));
                }
                break;
            case R.string.button_phone:
                for (int i = 0; i <= 100; i++) {
                    LocalDateTime ldt = LocalDateTime.now();
                    ldt = ldt.plusDays(-i);
                    dataTest.add(new PhoneItem(ldt,r.nextInt(5) + 10, 0));
                }
                break;
            case R.string.button_period:
                for(int i = 0;i <= 120;i++)
                {
                    LocalDateTime ldt = LocalDateTime.now();
                    LocalDateTime ldt_temp = ldt.plusMonths(-i).plusDays(r.nextInt(2));
                    dataTest.add(new PeriodItem(ldt.plusMonths(-i),Tool.toString(ldt),Tool.toString(ldt_temp)));
                    ldt = ldt_temp;
                }
                break;
            case R.string.button_sleep:
                for(int i = 0;i <= 100;i++)
                {
                    LocalDateTime ldt = LocalDateTime.now();
                    ldt = ldt.plusDays(-i);
                    SleepItem a = new SleepItem(ldt,Tool.toString(ldt.plusHours(-r.nextInt(10))),Tool.toString(ldt));
                    dataTest.add(a);
                }
                break;
            case R.string.button_breath:
                for (int i = 0; i <= 100; i++) {
                    LocalDateTime ldt = LocalDateTime.now();
                    ldt = ldt.plusDays(-i);
                    dataTest.add(new BreathItem(ldt,r.nextInt(10) + 20, 0));
                }
                break;
            default: break;
        }

        return dataTest;
    }

    public static long getTimeUntilNow(LocalDateTime ldt, int resIdDate) {
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long time = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return (now-time)/getDateTimeDivide(resIdDate);
    }

    @SuppressLint("NonConstantResourceId")
    public static long getTime(LocalDateTime ldt, int resIdDateDivide) {
        return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()/getMilisecond(resIdDateDivide);
    }

    @SuppressLint("NonConstantResourceId")
    public static long getMilisecond(int resIdDateDivide) {
        switch (resIdDateDivide) {
            case R.string.day:
                return 86400000;
            case R.string.hour:
                return 3600000;
            case R.string.minute:
                return 60000;
            case R.string.second:
                return 1000;
            default:
                return 1;
        }
    }

    @SuppressLint("DefaultLocale")
    public static String toString(double number){
        if(number > (int) number)
            return String.format("%.02f",number);
        return (int) number + "";
    }

    public static LocalDateTime setTime(long time, int resIdDate) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time*getDateTimeDivide(resIdDate)), ZoneId.systemDefault());
    }

    @SuppressLint("NonConstantResourceId")
    public static long getDateTimeDivide(int resIdDateDivide) {
        switch (resIdDateDivide) {
            case R.string.month: return (long)30*24*60*60*1000;
            case R.string.day: return (long)24*60*60*1000;
            case R.string.hour: return (long)60*60*1000;
            case R.string.minute: return (long)60*1000;
            case R.string.second: return (long)1000;
            default: return 1;
        }
    }
    public static int getDateTimeType(long ms) {
        if(ms >= (long)24*60*60*1000*30*20)
            return R.string.month;
        if(ms >= (long)24*60*60*1000*20)
            return  R.string.day;
        if(ms >= (long)60*60*1000*20)
            return  R.string.hour;
        if(ms >= (long)60*1000*20)
            return  R.string.minute;
        return  R.string.second;
    }
    public static String toString(LocalDateTime localDateTime) {
        if(localDateTime.getHour() == 0 && localDateTime.getMinute() == 0)
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern_date));
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern_date_time));
    }

    public static LocalDateTime from(String dateTime) {
        if(dateTime.length() == pattern_date.length())
            return date(dateTime);
        return LocalDateTime.parse(dateTime,DateTimeFormatter.ofPattern(pattern_date_time));
    }

    @SuppressLint("NonConstantResourceId")
    public static int getStatusColor(int drawableId) {
        switch (drawableId) {
            case R.drawable.item_very_bad: return R.color.very_bad;
            case R.drawable.item_bad: return R.color.bad;
            case R.drawable.item_warning: return R.color.warning;
            case R.drawable.item_good: return R.color.good;
            case R.drawable.item_very_good: return R.color.very_good;
            default: return R.color.light_gray;
        }
    }

    public static String toString(LocalDate newLd) {
        return newLd.format(DateTimeFormatter.ofPattern(pattern_date));
    }

    public static LocalDateTime date(String date) {
        LocalDate ld = LocalDate.parse(date,DateTimeFormatter.ofPattern(pattern_date));
        return LocalDateTime.of(ld.getYear(),ld.getMonthValue(),ld.getDayOfMonth(),0,0);
    }
}
