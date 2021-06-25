package com.se25.healthcare.Menu.Components.Heart;

import androidx.fragment.app.Fragment;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.Models.Databases.LocalDatabase;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HeartPresenter extends BasePresenter {
    public HeartPresenter(BaseContract.View view) {
        super(view);
    }

    @Override
    public ArrayList<Item> getDatabaseData() {
        LocalDatabase db = new LocalDatabase(getView().getContext(),1);
        ArrayList<Item> items = db.getListData(R.string.button_heart);
        db.close();

        //return Tool.createDataTool(R.string.button_heart);
        return db.getListData(R.string.button_heart);
    }

    @Override
    public Item.InputForm[] getInputForms() {
        final HeartItem itemTemp = new HeartItem();
        return itemTemp.getInputForms();
    }

    @Override
    public void fillItemToForm(Item item) {
        ((PageForm)getView().getPage(R.string.title_form)).setEditForm(item);
        getView().scrollTo(R.string.title_form);
    }

    @Override
    public int getResTitleId() {
        return new HeartItem().getResTitleId();
    }

    @Override
    public Item getForm() {
        PageForm pgForm = (PageForm) getView().getPage(R.string.title_form);
        if(pgForm.getData(0).length() == 0 || pgForm.getData(1).length() == 0)
            return null;
        return new HeartItem(pgForm.getDataCreate(),pgForm.getData(0),pgForm.getData(1));
    }
}
