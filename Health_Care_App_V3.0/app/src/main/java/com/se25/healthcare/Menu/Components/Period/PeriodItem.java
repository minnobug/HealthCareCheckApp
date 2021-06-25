package com.se25.healthcare.Menu.Components.Period;

import android.view.inputmethod.EditorInfo;

import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class PeriodItem extends Item {
    private final static InputForm listForm =
            new InputForm(EditorInfo.TYPE_CLASS_TEXT, R.string.header_period_start, R.string.hint_day, R.drawable.blood);
    private final static InputForm[] inputForms = {
            new InputForm(EditorInfo.TYPE_CLASS_TEXT, R.string.header_period_start, R.string.hint_date, R.drawable.schedule),
    };
    private final static Entry[][] entries = {
            {
                    new Entry(60, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(40, R.string.evaluate_period_very_bad_h, R.drawable.item_very_good),
                    new Entry(33, R.string.evaluate_period_bad_h, R.drawable.item_good),
                    new Entry(27, R.string.evaluate_period_normal, R.drawable.item_warning),
                    new Entry(20, R.string.evaluate_period_bad_l, R.drawable.item_bad),
                    new Entry(14, R.string.evaluate_period_very_bad_l, R.drawable.item_very_bad),
                    new Entry(0, R.string.evaluate_impossible, R.drawable.item_gray)
            }
    };

    public PeriodItem() {
        super();
    }

    public PeriodItem(LocalDateTime dateCreate, Object... data) {
        super(dateCreate, data);
    }

    @Override
    public String getData(int index) {
        return Tool.toString(Tool.from(super.getData(index)));
    }

    @Override
    public Item Instance() {
        return new PeriodItem();
    }

    @Override
    public Double getProcessData() {
        long PeriodOld = Tool.getTimeUntilNow(Tool.from(getData(1)),R.string.day);
        long PeriodNew = Tool.getTimeUntilNow(Tool.from(getData(0)),R.string.day);
        return (double) (PeriodOld - PeriodNew);
    }

    @Override
    public Double getProcessData(int index) {
        return getProcessData();
    }

    @Override
    public int getResTitleId() {
        return R.string.button_period;
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
