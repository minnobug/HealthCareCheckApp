package com.se25.healthcare.Menu.Components.Phone;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class PhoneItem extends Item {
    private final static InputForm[] inputForms = {
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_SIGNED | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, R.string.header_phone_use, R.string.hour, R.drawable.smartphone),
    };
    private final static Entry[][] entries = {
            {
                    new Entry(24, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(8, R.string.evaluate_phone_very_bad_h, R.drawable.item_very_good),
                    new Entry(4, R.string.evaluate_phone_bad_h, R.drawable.item_good),
                    new Entry(2, R.string.evaluate_phone_normal, R.drawable.item_warning),
                    new Entry(0, R.string.evaluate_phone_good, R.drawable.item_bad)
            }
    };

    public PhoneItem() {
        super();
    }

    public PhoneItem(LocalDateTime dateCreate, Object... data) {
        super(dateCreate, data);
    }

    @Override
    public Item Instance() {
        return new PhoneItem();
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
        return R.string.button_phone;
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
