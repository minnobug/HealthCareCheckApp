package com.se25.healthcare.Menu.Components.Breath;

import android.app.AlertDialog;
import android.view.View;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class BreathPresenter extends BasePresenter {
    public BreathPresenter(BaseContract.View view) {
        super(view);
    }

    @Override
    public Item.InputForm[] getInputForms() {
        final BreathItem itemTemp = new BreathItem();
        return itemTemp.getInputForms();
    }

    @Override
    public void fillItemToForm(Item item) {
        ((PageForm)getView().getPage(R.string.title_form)).setEditForm(item);
        getView().scrollTo(R.string.title_form);
    }

    @Override
    public int getResTitleId() {
        return new BreathItem().getResTitleId();
    }

    @Override
    public Item getForm() {
        PageForm pgForm = (PageForm) getView().getPage(R.string.title_form);
        if(pgForm.getData(0).length() == 0)
            return null;
        return new BreathItem(pgForm.getDataCreate(),pgForm.getData(0),"0");
    }

}
