package com.se25.healthcare.Menu.Components.Calories;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.R;


public abstract class NutriFragment extends Fragment implements View.OnClickListener {

    protected ImageView imgVwIcon;
    protected TextView txtVwResult, txtVwData;
    protected Button btnRequest, btnAddData, btnWipeData;
    protected EditText etxtRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutri_request, container, false);
        // Inflate the layout for this fragment
        initView(view);
        initAction();
        return view;
    }

    @SuppressLint("DefaultLocale")
    private void initView(View view) {
        imgVwIcon = view.findViewById(R.id.imgVwIcon);
        txtVwResult = view.findViewById(R.id.txtResult);
        txtVwData = view.findViewById(R.id.txtTempData);
        btnRequest = view.findViewById(R.id.btnRequest);
        btnAddData = view.findViewById(R.id.btnAddData);
        btnWipeData = view.findViewById(R.id.btnWipeData);
        etxtRequest = view.findViewById(R.id.etxtRequest);

        imgVwIcon.setImageResource(getIcon());
        etxtRequest.setHint(getHint());
        txtVwData.setText(String.format("%.02f",getDataTemp()));
    }

    private void initAction() {
        btnAddData.setOnClickListener(this);
        btnWipeData.setOnClickListener(this);
        btnRequest.setOnClickListener(this);
    }

    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRequest:
                request();
                break;
            case R.id.btnAddData:
                try {
                    double temp = Double.parseDouble(txtVwResult.getText().toString());
                    setDataTemp(temp);
                    txtVwData.setText(String.format("%.02f",getDataTemp()));
                } catch (NumberFormatException e) {
                    ((MainActivity) requireActivity()).showMessage(R.string.evaluate_fail);
                }
                break;
            case R.id.btnWipeData:
                setDataTemp(0);
                txtVwData.setText(String.format("%.02f",getDataTemp()));
                break;
        }
    }

    protected abstract void request();

    protected abstract void setDataTemp(double value);

    protected abstract double getDataTemp();

    protected abstract int getHint();

    protected abstract int getIcon();
}