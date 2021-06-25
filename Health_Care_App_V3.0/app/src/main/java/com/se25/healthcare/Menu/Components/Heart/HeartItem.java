package com.se25.healthcare.Menu.Components.Heart;

import android.content.Context;
import android.view.inputmethod.EditorInfo;

import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class HeartItem extends Item {
    private final static InputForm[] inputForms = {
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER, R.string.header_heart_rate, R.string.hint_BPM, R.drawable.heart_rate),
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER, R.string.header_heart_pressure, R.string.hint_mmHg, R.drawable.heart_pressure),
    };
    private final static Entry[][] entries = {
            {
                    new Entry(200, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(160, R.string.evaluate_heart_rate_bad_h, R.drawable.item_bad),
                    new Entry(100, R.string.evaluate_heart_rate_warning_h, R.drawable.item_warning),
                    new Entry(60, R.string.evaluate_heart_rate_normal, R.drawable.item_good),
                    new Entry(45, R.string.evaluate_heart_rate_good, R.drawable.item_very_good),
                    new Entry(30, R.string.evaluate_heart_rate_warning_l, R.drawable.item_warning),
                    new Entry(0, R.string.evaluate_impossible, R.drawable.item_gray),
            },
            {
                    new Entry(240, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(180, R.string.evaluate_heart_pressure_very_bad_h, R.drawable.item_very_bad),
                    new Entry(140, R.string.evaluate_heart_pressure_bad_h2, R.drawable.item_bad),
                    new Entry(130, R.string.evaluate_heart_pressure_bad_h1, R.drawable.item_bad),
                    new Entry(120, R.string.evaluate_heart_pressure_warning_h, R.drawable.item_warning),
                    new Entry(90, R.string.evaluate_heart_pressure_good, R.drawable.item_very_good),
                    new Entry(50, R.string.evaluate_heart_pressure_warning_l, R.drawable.item_good),
                    new Entry(0, R.string.evaluate_impossible, R.drawable.item_gray),
            }
    };

    public HeartItem() {
        super();
    }

    public HeartItem(LocalDateTime dateCreate, Object... data) {
        super(dateCreate, data);
    }

    @Override
    public Item Instance() {
        return new HeartItem();
    }

    @Override
    public Double getProcessData() {
        return null;
    }

    @Override
    public Double getProcessData(int index) {
        return Double.parseDouble(getData(index));
    }

    @Override
    public int getResTitleId() {
        return R.string.button_heart;
    }

    @Override
    public InputForm getListForm(int index) {
        return inputForms[index];
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
