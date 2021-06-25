package com.se25.healthcare.Menu.Page;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Models.File.FileModel;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class PageForm extends Fragment implements View.OnClickListener {
    BasePresenter presenter;
    public PageForm(BasePresenter presenter) {
        this.presenter = presenter;
    }

    TextView txtTitle, txtDateCreate, txtTimeCount;
    RelativeLayout rlFormInput, rlTimeCount, rlDateCreate;
    TableRow tbrEdit;
    RelativeLayout[] rlInput;
    TextView[] txtHeader;
    EditText[] edtxtInput;
    ImageView[] imgVwIcon;

    Button btnSave;
    Button btnEvaluate;
    Button btnDelete;


    Button btnStopEdit;
    Item itemHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_component_form, container, false);
        initView(view);
        initAction();
        return view;
    }

    public boolean isEdit() {
        return itemHolder == null;
    }

    public void setEditForm(Item item) {
        this.itemHolder = item;
        if(this.itemHolder!=null) {
            rlFormInput.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.warning)));
            tbrEdit.setVisibility(View.VISIBLE);
            txtTitle.setText(getString(R.string.form_title_edit));
            edtxtInput[0].setText(item.getData(0));
            edtxtInput[1].setText(item.getData(1));
            txtDateCreate.setText(Tool.toString(itemHolder.getDateCreate()));
        } else {
            rlFormInput.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_gray_2)));
            txtTitle.setText(getString(R.string.form_title_add));
            txtDateCreate.setText(R.string.date_now);
            edtxtInput[0].setText("");
            edtxtInput[1].setText("");
            tbrEdit.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        rlDateCreate = view.findViewById(R.id.rlDateCreate);
        rlFormInput = view.findViewById(R.id.rlFormInput);
        rlTimeCount = view.findViewById(R.id.rlTimeCount);
        txtTimeCount = view.findViewById(R.id.txtVwTimeCount);
        tbrEdit = view.findViewById(R.id.tbrEdit);
        txtDateCreate = view.findViewById(R.id.txtVwDateCreate);
        txtTitle = view.findViewById(R.id.txtVwFormTitle);
        rlInput = new RelativeLayout[]{
                view.findViewById(R.id.rlInput1),
                view.findViewById(R.id.rlInput2)
        };
        txtHeader = new TextView[]{
                view.findViewById(R.id.txtVwHeader1),
                view.findViewById(R.id.txtVwHeader2)
        };
        edtxtInput = new EditText[]{
                view.findViewById(R.id.edtxtInput1),
                view.findViewById(R.id.edtxtInput2)
        };
        imgVwIcon = new ImageView[]{
                view.findViewById(R.id.imgVwIcon1),
                view.findViewById(R.id.imgVwIcon2)
        };

        btnSave = view.findViewById(R.id.btnSave);
        btnEvaluate = view.findViewById(R.id.btnEvaluate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnStopEdit = view.findViewById(R.id.btnStopEdit);

        Item.InputForm[] inputForms = presenter.getInputForms();
        for (int i = 0; i < inputForms.length; i++) {
            rlInput[i].setVisibility(View.VISIBLE);
            txtHeader[i].setText(inputForms[i].headerId);
            edtxtInput[i].setHint(inputForms[i].unitId);
            edtxtInput[i].setInputType(inputForms[i].type);
            imgVwIcon[i].setImageResource(inputForms[i].iconId);
        }
        setEditForm(null);

        setSpecialInput(0,presenter.getView().getSpecialInput(0));
        setSpecialInput(1,presenter.getView().getSpecialInput(1));

        if(presenter.getResTitleId() == R.string.button_calories){
            String temp = FileModel.getValueFromTemp("food",getContext());
            if(temp.length() != 0 && !temp.equals("0"))
                edtxtInput[0].setText(temp);
            temp = FileModel.getValueFromTemp("exercise",getContext());
            if(temp.length() != 0 && !temp.equals("0"))
                edtxtInput[1].setText(temp);
        }

        rlDateCreate.setVisibility(presenter.getView().getDateCreateVisible());
    }

    public void setSpecialInput(int index, int type) {
        LocalDateTime ldt = LocalDateTime.now();
        switch (type){
            case 0:
                edtxtInput[index].setFocusable(false);
                edtxtInput[index].setOnClickListener(v->{
                    new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                        edtxtInput[index].setText(Tool.toString(LocalDateTime.of(year,month+1,dayOfMonth,hourOfDay,minute)));
                    },ldt.getHour(),ldt.getMinute(),true).show(), ldt.getYear(),ldt.getMonthValue(),ldt.getDayOfMonth()).show();
                });
                break;
            case 1:
                edtxtInput[index].setFocusable(false);
                edtxtInput[index].setOnClickListener(v->runTimeCount(0));
                break;
            case 2:
                edtxtInput[index].setFocusable(false);
                edtxtInput[index].setOnClickListener(v->{
                    new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                        edtxtInput[index].setText(Tool.toString(LocalDateTime.of(year,month+1,dayOfMonth,0,0)));
                    }, ldt.getYear(),ldt.getMonthValue(),ldt.getDayOfMonth()).show();
                });
                break;
        }
    }

    public void runTimeCount(int index) {
        LocalDateTime start = LocalDateTime.now();
        rlTimeCount.setVisibility(View.VISIBLE);
        edtxtInput[index].setText(R.string.input_waiting);
        txtTimeCount.setText("");
        CountDownTimer cdt = new CountDownTimer(100,50) {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimeCount.setText(String.format("%.02f", (float) Tool.getTimeUntilNow(start,R.string.milis)/1000));
            }

            @Override
            public void onFinish() {
                start();
            }
        };
        rlTimeCount.setOnClickListener(v -> {
            edtxtInput[index].setText(txtTimeCount.getText());
            cdt.cancel();
            rlTimeCount.setVisibility(View.GONE);
        });
        cdt.start();
    }


    private void initAction() {
        btnSave.setOnClickListener(this);
        btnEvaluate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnStopEdit.setOnClickListener(this);
    }
    public EditText getEdtxtInput(int index){
        return edtxtInput[index];
    }

    public String getData(int index) {
        return edtxtInput[index].getText().toString();
    }

    public LocalDateTime getDataCreate() {
        String dateCreate =  txtDateCreate.getText().toString();
        if(dateCreate.equals(getString(R.string.date_now)))
            return LocalDateTime.now();
        return Tool.from(dateCreate);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                presenter.getDataAdapter().removeItem(itemHolder);
                setEditForm(null);
                break;
            case R.id.btnSave:
                if(itemHolder!=null)
                    presenter.handleEdit(presenter.getForm());
                else
                    presenter.handleSave(presenter.getForm());
                setEditForm(null);
                break;
            case R.id.btnEvaluate:
                presenter.handleEvaluate(presenter.getForm());
                break;
            case R.id.btnStopEdit:
                setEditForm(null);
                break;
        }
    }
}