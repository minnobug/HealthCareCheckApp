package com.se25.healthcare.Menu.Components.BMI;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.R;

public class BMIPresenter extends BasePresenter {
    public BMIPresenter(BaseContract.View view) {
        super(view);
    }

    @Override
    public Item.InputForm[] getInputForms() {
        final BMIItem itemTemp = new BMIItem();
        return itemTemp.getInputForms();
    }

    @Override
    public void fillItemToForm(Item item) {
        ((PageForm)getView().getPage(R.string.title_form)).setEditForm(item);
        getView().scrollTo(R.string.title_form);
    }

    @Override
    public int getResTitleId() {
        return new BMIItem().getResTitleId();
    }

    @Override
    public Item getForm() {
        PageForm pgForm = (PageForm) getView().getPage(R.string.title_form);
        if(pgForm.getData(0).length() == 0 || pgForm.getData(1).length() == 0)
            return null;
        return new BMIItem(pgForm.getDataCreate(),pgForm.getData(0),pgForm.getData(1));
    }
}
