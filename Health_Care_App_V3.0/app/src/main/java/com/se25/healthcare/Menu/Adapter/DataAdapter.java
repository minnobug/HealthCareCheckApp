package com.se25.healthcare.Menu.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ItemHolder> {

    private BasePresenter presenter;
    private Context context;
    private ArrayList<Item> items;
    private ArrayList<Item> selected;

    public DataAdapter(BasePresenter presenter) {
        this.presenter = presenter;
        this.context = presenter.getView().getContext();
        this.items = presenter.getDatabaseData();
        selected = new ArrayList<>();
    }

    public ArrayList<Item> getSelected() {
        return selected;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public Item getLastSelected() {
        if(selected.size() == 0)
            return  null;
        return selected.get(0);
    }

    public void updateLastSelect(Item item) {
        this.items.set(items.indexOf(selected.get(0)),item);
        selected.set(0,item);
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        this.items.add(0,item);
        selected.add(0,item);
        notifyDataSetChanged();
    }

    public void removeSelected() {
        this.items.removeAll(selected);
        clearSelect();
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        this.selected.remove(item);
        notifyDataSetChanged();
    }

    public void clearSelect() {
        this.selected.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ItemHolder(inflater.inflate(R.layout.layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.setItem(items.get(position));
        int index = selected.indexOf(holder.getItem());
        switch (index) {
            case -1: return;
            case 0: holder.setBackgroundResource(R.drawable.item_select);
                break;
            default:holder.setBackgroundResource(R.drawable.item_select_old);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onUpdate(Item item) {
        notifyItemChanged(items.indexOf(item));
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        RelativeLayout[] rlData;
        ImageView[] icon;
        TextView[] data;
        TextView[] unit;
        TextView dataCreate;
        TextView txtVwStatus;
        ImageView btnStatus;
        RelativeLayout rlItem;
        Item item;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            rlData = new RelativeLayout[] {
                    itemView.findViewById(R.id.rlData1),
                    itemView.findViewById(R.id.rlData2)
            };
            icon = new ImageView[] {
                    itemView.findViewById(R.id.imgVwIcon1),
                    itemView.findViewById(R.id.imgVwIcon2)
            };
            data = new TextView[] {

                    itemView.findViewById(R.id.txtVwItem1),
                    itemView.findViewById(R.id.txtVwItem2)
            };
            unit = new TextView[] {

                    itemView.findViewById(R.id.txtVwUnit1),
                    itemView.findViewById(R.id.txtVwUnit2)
            };

            txtVwStatus = itemView.findViewById(R.id.txtVwStatus);
            dataCreate = itemView.findViewById(R.id.txtVwDateCreate);
            btnStatus = itemView.findViewById(R.id.imgVwEvaluate);
            rlItem = itemView.findViewById(R.id.rlData);
        }

        //Need to run
        @SuppressLint("SetTextI18n")
        public void setItem(Item item) {
            this.item = item;
            for(int i = 0; i<item.getDataQuantity(false); i++) {
                rlData[i].setVisibility(View.VISIBLE);
                this.icon[i].setImageResource(item.getListForm(i).iconId);
                this.unit[i].setText(item.getListForm(i).unitId);
                this.data[i].setText(Tool.toString(item.getProcessData(i)));
                this.txtVwStatus.setText(item.getStatus(context));
                this.dataCreate.setText(Tool.toString(item.getDateCreate()));
                setBackgroundResource(item.getStatusColorId());
            }
            btnStatus.setOnClickListener(v->{
                String content = item.getEvaluate(context);
                presenter.getView().showDialog(R.string.dialog_evaluate_one,content);
            });
            itemView.setOnClickListener(v->onSelect());
        }

        public void onSelect() {
            int index = selected.indexOf(item);
            switch (index) {
                case -1:
                    selected.add(0,item);
                    if(selected.size() > 1)
                        onUpdate(selected.get(1));
                    break;
                case 0:
                    selected.remove(item);
                    if(selected.size() > 0)
                        onUpdate(selected.get(0));
                default:
                    selected.remove(item);
            }
            onUpdate(item);
        }

        public void setBackgroundResource(int redId) {
            this.rlItem.setBackgroundResource(redId);
        }

        public Item getItem(){
            return this.item;
        }
    }
}
