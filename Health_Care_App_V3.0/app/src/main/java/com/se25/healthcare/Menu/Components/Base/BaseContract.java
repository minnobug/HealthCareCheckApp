package com.se25.healthcare.Menu.Components.Base;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.se25.healthcare.Menu.Adapter.DataAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface BaseContract {
    interface View{
        void initPresenter(BaseContract.View view);
        void showMessage(int resId);
        void showDialog(int resTitleId, String message);
        void scrollTo(int resId);
        Fragment getPage(int id);
        Context getContext();
        int getSpecialInput(int index);
        int getDateCreateVisible();
    }
    interface Presenter{
        ArrayList<Item> getDatabaseData();
        DataAdapter getDataAdapter();
        Item.InputForm[] getInputForms();
        void handleSave(Item item);
        void handleEdit(Item item);
        void handleGraph(ArrayList<Item> items, int index);
        void handleDelete(Item item);
        void handleDelete(); // remove all selected item
        void handleEvaluate(Item item);
        void handleEvaluate(ArrayList<Item> items);
        void fillItemToForm(Item item);

        int getResTitleId();
        Item getForm();
    }
}
