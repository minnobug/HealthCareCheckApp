package com.se25.healthcare.Menu.Components.Base;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.se25.healthcare.Menu.Adapter.DataAdapter;
import com.se25.healthcare.Menu.Page.PageGraph;
import com.se25.healthcare.Models.Databases.LocalDatabase;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.ArrayList;

public abstract class BasePresenter implements BaseContract.Presenter {
    BaseContract.View view;
    DataAdapter dataAdapter;

    public BasePresenter(BaseContract.View view) {
        this.view = view;
    }

    public BaseContract.View getView() {
        return this.view;
    }

    public ArrayList<Item> getDatabaseData() {
        LocalDatabase db = new LocalDatabase(getView().getContext(),1);
        ArrayList<Item> items = db.getListData(getResTitleId());
        db.close();

        return db.getListData(getResTitleId());
    }

    @Override
    public DataAdapter getDataAdapter() {
        if (dataAdapter == null)
            dataAdapter = new DataAdapter(this);
        return dataAdapter;
    }

    @Override
    public void handleSave(Item item) {
        if (item == null) {
            getView().showMessage(R.string.toast_item_save_fail);
            return;
        }
        LocalDatabase db = new LocalDatabase(getView().getContext(), 1);
        db.insertItem(item);
        dataAdapter.addItem(item);
        getView().showMessage(R.string.toast_item_add_success);
        db.close();
    }

    @Override
    public void handleDelete(Item item) {
        LocalDatabase db = new LocalDatabase(getView().getContext(), 1);
        db.deleteItem(item);
        dataAdapter.removeItem(item);
        getView().showMessage(R.string.toast_item_delete_success);
        db.close();
    }

    @Override
    public void handleDelete() {
        LocalDatabase db = new LocalDatabase(getView().getContext(), 1);
        for (Item item : getDataAdapter().getSelected()) {
            db.deleteItem(item);
        }
        getDataAdapter().removeSelected();
        getView().showMessage(R.string.toast_item_delete_success);
        db.close();
    }

    @Override
    public void handleEdit(Item item) {
        if (item == null) {
            getView().showMessage(R.string.toast_item_save_fail);
            return;
        }
        LocalDatabase db = new LocalDatabase(getView().getContext(), 1);
        db.updateItem(item);
        dataAdapter.updateLastSelect(item);
        getView().showMessage(R.string.toast_item_edit_success);
        db.close();
    }

    @Override
    public void handleEvaluate(Item item) {
        if (item == null) {
            getView().showMessage(R.string.toast_item_save_fail);
            return;
        }
        String content = item.getEvaluate(getView().getContext());
        getView().showDialog(R.string.dialog_evaluate_one, content);
    }

    @Override
    public void handleEvaluate(ArrayList<Item> items) {
        if (items.size() == 0) {
            getView().showMessage(R.string.toast_not_select_fail);
            return;
        }
        ArrayList<Item> data = new ArrayList<>(items);
        data.sort((i1, i2) -> i1.getDateCreate().compareTo(i2.getDateCreate()));

        long range = (long) Tool.getTimeUntilNow(data.get(0).getDateCreate(),R.string.milis)
                - Tool.getTimeUntilNow(data.get(data.size()-1).getDateCreate(),R.string.milis) ;
        int resIdDateDivide = Tool.getDateTimeType(range);

        StringBuilder stringBuilder = new StringBuilder()
                .append(getString(R.string.dialog_general).toUpperCase()).append(":");

        for (int i=0, max = items.get(0).getDataQuantity(false);i<max;i++)
            stringBuilder.append("\n+ ").append(processDataEvaluate(data,i,50f,resIdDateDivide));

        stringBuilder.append("\n\n").append(getString(R.string.dialog_recently).toUpperCase()).append(":");

        int from = data.size()*3/4, to = data.size();
        for (int i=0, max = items.get(0).getDataQuantity(false);i<max;i++)
            stringBuilder.append("\n+ ").append(processDataEvaluate(new ArrayList<Item>(data.subList(from,to)),i,75f,resIdDateDivide));

        stringBuilder.append("\n\n**").append(getString(R.string.dialog_README_FORMAT).replace("%s",getString(resIdDateDivide)).toLowerCase());

        getView().showDialog(R.string.dialog_evaluate_list,stringBuilder.toString());
    }

    @Override
    public void handleGraph(ArrayList<Item> items, int index) {
        if(items.size() == 0)
            return;
        ArrayList<Entry> entryArrayList = new ArrayList<>();
        for(Item item: items) {
            entryArrayList.add(new Entry(-Tool.getTimeUntilNow(item.getDateCreate(),R.string.milis),item.getProcessData(index).floatValue()));
        }
        entryArrayList.sort((i1,i2)->Float.compare(i1.getX(),i2.getX()));

        long range = (long) (entryArrayList.get(entryArrayList.size()-1).getX() - entryArrayList.get(0).getX());
        int resIdDateDivide = Tool.getDateTimeType(range);
        long divide = Tool.getDateTimeDivide(resIdDateDivide);

        for(int i=0;i<entryArrayList.size();i++){
            Entry entry = entryArrayList.get(i);
            entry.setX(entry.getX()/divide);
        }
        Item.InputForm form = items.get(index).getListForm(index);
        LineDataSet dataLine = new LineDataSet(entryArrayList,getString(form.headerId));
        dataLine.setColor(getView().getContext().getColor(R.color.red));
        dataLine.setDrawCircles(false);

        LineDataSet generalLine = processLineEvaluate(items,index,resIdDateDivide,R.string.dialog_general,R.color.teal_100);
        LineDataSet recentlyLine = processLineEvaluate(new ArrayList<Item>(items.subList(0,items.size()/4)),index,
                resIdDateDivide,R.string.dialog_recently,R.color.teal_200);

        Item.Entry[] entries = items.get(index).getEntries(index);
        ((PageGraph) getView().getPage(R.string.title_graph))
                .loadLineChart(entries,new LineData(dataLine,generalLine,recentlyLine),resIdDateDivide);
    }

    private LineDataSet processLineEvaluate(ArrayList<Item> items, int index, int resIdDateDivide, int resTitleId,int resColorId){
        if(items.size() == 0)
            return new LineDataSet(null,getString(resTitleId));
        ArrayList<Item> dataForProcess = new ArrayList<Item>(items);
        dataForProcess.sort((i1,i2)->i2.getDateCreate().compareTo(i1.getDateCreate()));
        SimpleRegression regression = processRegression(dataForProcess,index,resIdDateDivide);
        long xEnd = Tool.getTimeUntilNow(dataForProcess.get(0).getDateCreate(),resIdDateDivide);
        long xStart = Tool.getTimeUntilNow(dataForProcess.get(items.size()-1).getDateCreate(),resIdDateDivide);
        LineDataSet line = new LineDataSet(new ArrayList<Entry>(),getString(resTitleId));
        line.addEntry(new Entry(-xStart, (float) regression.predict(xStart)));
        line.addEntry(new Entry(-xEnd, (float) regression.predict(xEnd)));
        line.setColor(getView().getContext().getColor(resColorId));
        line.enableDashedLine(1f,2f,1f);
        line.setLineWidth(2f);
        line.setDrawCircles(false);
        return  line;
    }

    private String processDataEvaluate(ArrayList<Item> data, int index, float trust, int resIdDateDivide) {
        SimpleRegression regression = processRegression(data,index,resIdDateDivide);

        Item data_skeleton = data.get(0);
        StringBuilder sBuilder = new StringBuilder(getString(data_skeleton.getListForm(index).headerId)).append(":");


        if (regression.getRSquare() > trust/100) {
            double slope = regression.getSlope();
            sBuilder.append(getString(idSlope(slope)));
            double temp;
            String evaluate;

            temp = data_skeleton.getProcessData(index);
            evaluate = getString(idEvaluate(data_skeleton.getEntries(index),temp)).split(":")[0];
            sBuilder.append("\n - ").append(getString(R.string.dialog_from)).append(": ").append(evaluate);

            temp = data.get(data.size()-1).getProcessData(index);
            evaluate = getString(idEvaluate(data_skeleton.getEntries(index),temp)).split(":")[0];
            sBuilder.append("\n - ").append(getString(R.string.dialog_to)).append(": ").append(evaluate);

            temp = regression.predict(Tool.getTimeUntilNow(data.get(data.size()-1).getDateCreate().plusSeconds(Tool.getMilisecond(resIdDateDivide)/100), resIdDateDivide));
            evaluate = getString(idEvaluate(data_skeleton.getEntries(index),temp)).split(":")[0];
            sBuilder.append("\n - ").append(getString(R.string.dialog_predict)).append(": ").append(evaluate);

        } else {
            sBuilder.append(getString(R.string.dialog_unreliable_data)).append(" < ").append(trust).append("%");
        }

        return sBuilder.toString();
    }
    private SimpleRegression processRegression(ArrayList<Item> data, int index, int resIdDateDivide){
        SimpleRegression regression = new SimpleRegression(true);
        for (Item item : data)
            regression.addData(Tool.getTimeUntilNow(item.getDateCreate(), resIdDateDivide), item.getProcessData(index));
        return regression;
    }

    private int idSlope(double slope) {
        int deg = -(int) Math.toDegrees(Math.atan(slope));
        if(deg >= 60)
            return R.string.dialog_evaluate_fast_increase;
        if(deg >= 30)
            return R.string.dialog_evaluate_increase;
        if(deg > 0)
            return R.string.dialog_evaluate_low_increase;
        if(deg == 0)
            return R.string.dialog_evaluate_same;
        if(deg >= -30)
            return R.string.dialog_evaluate_decrease;
        return R.string.dialog_evaluate_fast_decrease;
    }

    private int idEvaluate(Item.Entry[] entries, double data) {
        for (Item.Entry entry : entries)
            if (data > entry.limitLine) {
                return entry.evaluateId;
            }
        return R.string.evaluate_impossible;
    }
    public String getString(int id) {
        return getView().getContext().getString(id);
    }

}
