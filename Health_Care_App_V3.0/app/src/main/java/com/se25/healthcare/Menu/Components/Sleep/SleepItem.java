package com.se25.healthcare.Menu.Components.Sleep;

import android.view.inputmethod.EditorInfo;

import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class SleepItem extends Item {
    private final static InputForm listForm = new InputForm(EditorInfo.TYPE_CLASS_TEXT, R.string.header_sleep_time, R.string.hour, R.drawable.sleep);
    private final static InputForm[] inputForms = {
            new InputForm(EditorInfo.TYPE_CLASS_TEXT, R.string.header_sleep_time, R.string.hint_datetime, R.drawable.sleep),
            new InputForm(EditorInfo.TYPE_CLASS_TEXT,R.string.header_wake_up_time, R.string.hint_datetime, R.drawable.waking_up),
    };
    private final static Entry[][] entries = {
            {
                    new Entry(24, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(16, R.string.evaluate_sleep_very_bad_h, R.drawable.item_very_good),
                    new Entry(8, R.string.evaluate_sleep_bad_h, R.drawable.item_good),
                    new Entry(6, R.string.evaluate_sleep_normal, R.drawable.item_warning),
                    new Entry(4, R.string.evaluate_sleep_bad_l, R.drawable.item_bad),
                    new Entry(0, R.string.evaluate_sleep_very_bad_l, R.drawable.item_very_bad),
                    new Entry(-5, R.string.evaluate_impossible, R.drawable.item_gray)
            }
    };

    public SleepItem() {
        super();
    }

    public SleepItem(LocalDateTime dateCreate, Object... data) {
        super(dateCreate, data);
    }

    @Override
    public Item Instance() {
        return new SleepItem();
    }

    @Override
    public Double getProcessData() {
        long sleep = Tool.getTimeUntilNow(Tool.from(getData(0)),R.string.milis);
        long wake = Tool.getTimeUntilNow(Tool.from(getData(1)),R.string.milis);
        return (double) (sleep - wake)/Tool.getMilisecond(R.string.hour);
    }

    @Override
    public Double getProcessData(int index) {
        return getProcessData();
    }

    @Override
    public int getResTitleId() {
        return R.string.button_sleep;
    }

    @Override
    public InputForm getListForm(int index) {
        return listForm;
    }

    @Override
    public InputForm[] getInputForms() {
        return inputForms;
    }

    @Override
    public int getDataQuantity(boolean isInput) {
        return isInput?inputForms.length:entries.length;
    }

    @Override
    public Entry[] getEntries(int index) {
        return entries[index];
    }
}
