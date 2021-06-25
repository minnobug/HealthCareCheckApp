package com.se25.healthcare.Menu.Components.Base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.se25.healthcare.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Item {
    private LocalDateTime dateCreate;
    private Object[] data;

    public Item() {

    }

    public Item(LocalDateTime dateCreate, Object...data) {
        this.dateCreate = dateCreate;
        this.data = data;
    }

    public String getData(int index) {
        return data[index].toString();
    }
    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public abstract Item Instance();
    public abstract Double getProcessData();
    public abstract Double getProcessData(int index);
    public String getEvaluate(Context context) {
        ArrayList<String> evaluates = new ArrayList<>();
        for(int i=0, max = getDataQuantity(false);i<max;i++){
            Entry temp = getEntry(i);
            evaluates.add(context.getString((temp == null)? R.string.evaluate_impossible: temp.evaluateId)+"\n");
        }
        return String.join("\n",evaluates);
    }
    public Entry getEntry(int index){
        Entry[] entries = getEntries(index);
        for(int i=0;i<entries.length;i++)
            if(getProcessData(index) > entries[i].limitLine)
                return i == 0?null:entries[i];
        return null;
    }
    public String getStatus(Context context) {
        String evaluate = getEvaluate(context);
        String[] evaluate_split = evaluate.split("\n");
        ArrayList<String> status = new ArrayList<>();
        for (String str: evaluate_split) {
            status.add(str.split(":")[0]);
        }
        return String.join("\n",status);
    }

    public int getStatusColorId(int index){
        final Entry e = getEntry(index);
        if(e==null)
            return R.drawable.item_gray;
        return e.colorId;
    }
    public int getStatusColorId(){
        int id = R.drawable.item_very_good;
        for(int i=0;i<getDataQuantity(false);i++) {
            int newid = getStatusColorId(i);
            if(colorRank(newid) < colorRank(id))
                id= newid;
        }
        return id;
    }
    @SuppressLint("NonConstantResourceId")
    public int colorRank(int id) {
        switch (id) {
            case R.drawable.item_gray: return -1;
            case R.drawable.item_very_bad: return 0;
            case R.drawable.item_bad: return 1;
            case R.drawable.item_warning: return 2;
            case R.drawable.item_good: return 3;
            case R.drawable.item_very_good: return 4;
            default: return 5;
        }
    }

    public abstract int getResTitleId();

    public abstract int getDataQuantity(boolean isInput);
    public abstract Entry[] getEntries(int index);
    public abstract InputForm getListForm(int index);
    public abstract InputForm[] getInputForms();

    public static class InputForm {
        public int iconId, unitId, headerId, type;

        public InputForm(int type, int headerId,int unitId, int iconId) {
            this.iconId = iconId;
            this.unitId = unitId;
            this.headerId = headerId;
            this.type = type;
        }
    }

    public static class Entry {
        public double limitLine;
        public int evaluateId;
        public int colorId;

        public Entry(double limitLine, int evaluateId, int colorId) {
            this.limitLine = limitLine;
            this.evaluateId = evaluateId;
            this.colorId = colorId;
        }
    }
}
