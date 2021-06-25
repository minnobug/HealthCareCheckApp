package com.se25.healthcare.Menu.Components.Breath;

import android.view.inputmethod.EditorInfo;

import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class BreathItem extends Item {
    private final static InputForm[] inputForms = {
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL | EditorInfo.TYPE_NUMBER_FLAG_SIGNED, R.string.header_breath_hold, R.string.hint_s, R.drawable.yoga),
    };
    private final static Entry[][] entries = {
            {
                    new Entry(200, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(60, R.string.evaluate_breath_very_good, R.drawable.item_very_good),
                    new Entry(30, R.string.evaluate_breath_good, R.drawable.item_good),
                    new Entry(25, R.string.evaluate_breath_normal, R.drawable.item_warning),
                    new Entry(15, R.string.evaluate_breath_bad, R.drawable.item_bad),
                    new Entry(0, R.string.evaluate_breath_very_bad, R.drawable.item_very_bad),
                    new Entry(-200, R.string.evaluate_impossible, R.drawable.item_gray),
            }
    };

    public BreathItem() {
        super();
    }

    public BreathItem(LocalDateTime dateCreate, Object... data) {
        super(dateCreate, data);
    }

    @Override
    public Item Instance() {
        return new BreathItem();
    }

    @Override
    public Double getProcessData() {
        return Double.parseDouble(getData(0));
    }

    @Override
    public Double getProcessData(int index) {
        return getProcessData();
    }

    @Override
    public int getResTitleId() {
        return R.string.button_breath;
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
        return 1;
    }

    @Override
    public Entry[] getEntries(int index) {
        return entries[index];
    }
}
