package com.se25.healthcare.Menu.Components.Phone;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Components.Period.PeriodItem;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class PhonePresenter extends BasePresenter {
    public PhonePresenter(BaseContract.View view) {
        super(view);
    }

    @Override
    public Item.InputForm[] getInputForms() {
        final PhoneItem itemTemp = new PhoneItem();
        return itemTemp.getInputForms();
    }

    @Override
    public void fillItemToForm(Item item) {
        ((PageForm)getView().getPage(R.string.title_form)).setEditForm(item);
        getView().scrollTo(R.string.title_form);
    }

    @Override
    public int getResTitleId() {
        return new PhoneItem().getResTitleId();
    }

    @Override
    public Item getForm() {
        PageForm pgForm = (PageForm) getView().getPage(R.string.title_form);
        String data = pgForm.getData(0);
        if(data.length() == 0 || Double.parseDouble(data) > 24 || Double.parseDouble(data) < 0)
            return null;

        return new PhoneItem(pgForm.getDataCreate(),pgForm.getData(0),"0");
    }
}
