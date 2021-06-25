package com.se25.healthcare.Menu.Components.Period;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class PeriodPresenter extends BasePresenter {
    public PeriodPresenter(BaseContract.View view) {
        super(view);
    }

    @Override
    public Item.InputForm[] getInputForms() {
        final PeriodItem itemTemp = new PeriodItem();
        return itemTemp.getInputForms();
    }

    @Override
    public void fillItemToForm(Item item) {
        ((PageForm)getView().getPage(R.string.title_form)).setEditForm(item);
        getView().scrollTo(R.string.title_form);
    }

    @Override
    public int getResTitleId() {
        return new PeriodItem().getResTitleId();
    }

    @Override
    public Item getForm() {
        PageForm pgForm = (PageForm) getView().getPage(R.string.title_form);
        LocalDateTime ldtStart = Tool.from(pgForm.getData(0));
        String oldPeriod = Tool.toString(ldtStart.plusDays(-30));
        if(getDataAdapter().getItems().size() > 0)
            oldPeriod = getDataAdapter().getItems().get(0).getData(0);
        PeriodItem temp = new PeriodItem(ldtStart,pgForm.getData(0), oldPeriod);
        if(temp.getProcessData() <= 0)
            return null;
        return temp;
    }

}
