package com.se25.healthcare.Access;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.Models.Databases.LocalDatabase;
import com.se25.healthcare.Models.File.FileModel;
import com.se25.healthcare.R;

public class VerifyActivity extends AppCompatActivity {

    TextView txtVwHello;
    EditText etxtPassword;
    Button btnVerify;
    int count;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        String username = FileModel.getValueFromTemp("username",this);
        if(username.length() == 0) {
            startActivity(new Intent(this,PersonalActivity.class));
            return;
        }
        initView();

        txtVwHello.setText(getString(R.string.welcome_back)+", "+username);
        btnVerify.setOnClickListener(v -> {
            if(etxtPassword.getText().toString().equals(FileModel.getValueFromTemp("password",this)))
                super.onBackPressed();
            else {
                count++;
                if(count < 5) {
                    Toast.makeText(this,R.string.wrong_password,Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setIcon(R.drawable.app_icon);
                    dialog.setTitle(R.string.dialog_create_new_personal_data);
                    dialog.setMessage(R.string.dialog_ask_to_clear_data);
                    dialog.setPositiveButton(R.string.dialog_okay, (dialog1, which) -> {
                        FileModel.setValueToTemp("username","",this);
                        FileModel.setValueToTemp("password","",this);
                        FileModel.setValueToTemp("gender","",this);
                        LocalDatabase db = new LocalDatabase(this,1);
                        db.deleteData();
                        startActivity(new Intent(this, PersonalActivity.class));
                    });
                    dialog.setNegativeButton(R.string.dialog_cancle,null);
                    dialog.show();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        String username = FileModel.getValueFromTemp("username", this);
        if (username.length() == 0) {
            startActivity(new Intent(this, PersonalActivity.class));
            return;
        }
        txtVwHello.setText(getString(R.string.welcome_back) + ", " + username);
    }

    private void initView() {
        txtVwHello = findViewById(R.id.txtVwHello);
        etxtPassword = findViewById(R.id.edtxtPassword);
        btnVerify = findViewById(R.id.btnVerify);
    }
    @Override
    public void onBackPressed() {

    }
}