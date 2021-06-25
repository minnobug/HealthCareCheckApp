package com.se25.healthcare.Menu.Page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.R;

import java.util.ArrayList;

public class PageList extends Fragment implements View.OnClickListener {
    BasePresenter presenter;

    public PageList(BasePresenter presenter) {
        this.presenter = presenter;
    }

    Button btnEdit, btnStatistics, btnDelete;
    ImageButton imgBtnRefresh;
    RecyclerView rVwItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_component_list, container, false);
        initView(view);
        initAction();
        return view;
    }

    private void initView(View view) {
        btnDelete = view.findViewById(R.id.btnDelete);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnStatistics = view.findViewById(R.id.btnStatistics);
        rVwItems = view.findViewById(R.id.rVwItems);
        imgBtnRefresh = view.findViewById(R.id.btnRefresh);

        rVwItems.setAdapter(presenter.getDataAdapter());
        rVwItems.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void initAction() {
        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnStatistics.setOnClickListener(this);
        imgBtnRefresh.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Item itemEdit;
        switch (v.getId()) {
            case R.id.btnDelete:
                presenter.handleDelete();
                break;
            case R.id.btnEdit:
                itemEdit = presenter.getDataAdapter().getLastSelected();
                if(itemEdit!=null) {
                    presenter.fillItemToForm(itemEdit);
                } else {
                    presenter.getView().showMessage(R.string.toast_not_select_fail);
                }
                break;
            case R.id.btnStatistics:
                ArrayList<Item> selected = presenter.getDataAdapter().getSelected();
                if(selected.size() > 0) {
                    ((PageGraph) presenter.getView().getPage(R.string.title_graph)).setItems(selected,R.string.title_graph_selected_items);
                    presenter.getView().scrollTo(R.string.title_graph);
                } else {
                    presenter.getView().showMessage(R.string.toast_not_select_fail);
                }
                break;
            case R.id.btnRefresh:
                presenter.getDataAdapter().clearSelect();
                break;
        }
    }
}