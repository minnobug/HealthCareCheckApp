package com.se25.healthcare.Menu.Page;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.util.ArrayList;

public class PageGraph extends Fragment implements View.OnClickListener {
    BasePresenter presenter;

    public PageGraph(BasePresenter presenter) {
        this.presenter = presenter;
    }

    Button btnEvaluate, btnChangeGraph;
    TextView txtVwTitle;
    LineChart lineChart;
    ImageButton btnRefresh;
    ImageButton btnFit;

    ArrayList<Item> items;
    int index = 0;

    public void setItems(ArrayList<Item> items, int titleId) {
        if(items.size() == 0)
            return;
        txtVwTitle.setText(titleId);
        this.items = items;
        this.index = 0;
        if(items.get(0).getDataQuantity(false) == 1)
            btnChangeGraph.setVisibility(View.GONE);
        presenter.handleGraph(items,index);
        lineChart.removeAllViews();
    }

    public void changeGraph() {
        index++;
        if(index == items.get(0).getDataQuantity(false))
            index=0;
        presenter.handleGraph(items,index);
        lineChart.removeAllViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_component_graph, container, false);
        initView(view);
        initAction();
        setItems(presenter.getDataAdapter().getItems(),R.string.title_graph_all_items);
        return view;
    }

    private void initView(View view) {
        btnEvaluate = view.findViewById(R.id.btnEvaluate);
        btnChangeGraph = view.findViewById(R.id.btnChangeGraph);
        txtVwTitle = view.findViewById(R.id.txtVwTitle);
        lineChart = view.findViewById(R.id.lineChart);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        btnFit = view.findViewById(R.id.btnFit);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getAxisRight().setEnabled(false);
    }

    private void initAction() {
        btnEvaluate.setOnClickListener(this);
        btnChangeGraph.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        btnFit.setOnClickListener(this);
    }

    public void loadLineChart(Item.Entry[] entries, LineData lineData, int xUnitId) {
        Description description = new Description();
        description.setText(getString(xUnitId));

        lineChart.setDescription(description);
        lineChart.setData(lineData);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        float min = (float) entries[0].limitLine, max = (float) entries[0].limitLine;
        for(int i=0;i<entries.length;i++) {
            LimitLine ll = new LimitLine((float) entries[i].limitLine, getString(entries[i].evaluateId).substring(0, getString(entries[i].evaluateId).indexOf(":")));
            //ll.setLineColor(getResources().getColor(entries[i].colorId));
            ll.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
            ll.setLineWidth(1.5f);
            ll.setLineColor(ContextCompat.getColor(getContext(),Tool.getStatusColor(entries[i].colorId)));
            ll.enableDashedLine(2f,1f,1f);
            ll.setTextSize(8f);
            yAxis.addLimitLine(ll);

            min = Math.min(min,(float) entries[i].limitLine);
            max = Math.max(max,(float) entries[i].limitLine);
        }
        yAxis.setDrawLimitLinesBehindData(true);
        yAxis.setAxisMinimum(min);
        yAxis.setAxisMaximum(max);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEvaluate:
                if(items.size() == 0){
                    presenter.getView().showMessage(R.string.toast_evaluate_zero);
                    return;
                }
                presenter.handleEvaluate(items);
                break;
            case R.id.btnChangeGraph:
                changeGraph();
                break;
            case R.id.btnRefresh:
                setItems(presenter.getDataAdapter().getItems(),R.string.title_graph_all_items);
                presenter.getView().showMessage(R.string.toast_statistics_all_item);
                lineChart.fitScreen();
                break;
            case R.id.btnFit:
                lineChart.fitScreen();
                break;
        }
     }
}