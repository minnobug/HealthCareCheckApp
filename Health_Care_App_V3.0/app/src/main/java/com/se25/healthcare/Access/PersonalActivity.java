package com.se25.healthcare.Access;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.Models.File.FileModel;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonalActivity extends AppCompatActivity {

    Button btnBackToMenu, btnLogout;
    EditText etxtUsername, etxtPassword, etxtBirthday;
    RadioGroup rdGender;

    private String username, password, birthday;
    private int genderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        username = FileModel.getValueFromTemp("username",this);
        password = FileModel.getValueFromTemp("password",this);
        birthday = FileModel.getValueFromTemp("birthday",this);
        String genderStr = FileModel.getValueFromTemp("gender",this);
        if(genderStr.length() != 0){
            genderId = Integer.parseInt(genderStr);
        }

        initView();
        etxtBirthday.setOnClickListener(v -> {
            LocalDateTime ld;
            if(etxtBirthday.getText().toString().length()>0 && birthday.length()>0)
                ld = Tool.date(birthday);
            else ld = LocalDateTime.now();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                LocalDate newLd = LocalDate.of(year,month+1,dayOfMonth);
                etxtBirthday.setText(Tool.toString(newLd));
            },ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth()).show();
        });
        btnBackToMenu.setOnClickListener(v -> checkData());

        btnLogout.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
    }

    private void initView(){
        btnBackToMenu = findViewById(R.id.btnMenu);
        btnLogout = findViewById(R.id.btnLogout);
        etxtUsername = findViewById(R.id.etxtUsername);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtBirthday = findViewById(R.id.etxtBirthday);
        rdGender = findViewById(R.id.rgGender);

        etxtUsername.setText(username);
        etxtPassword.setText(password);
        etxtBirthday.setText(birthday);
        rdGender.check(genderId);
    }

    private void checkData() {
        String newUsername = etxtUsername.getText().toString();
        String newPassword = etxtPassword.getText().toString();
        String newBirthday = etxtBirthday.getText().toString();
        int newGenderId = rdGender.getCheckedRadioButtonId();

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.app_icon);

        if(newUsername.length() == 0|| newPassword.length() == 0||newGenderId==0||newBirthday.length()==0){
            if(username.length()!=0){
                dialog.setTitle(R.string.dialog_personal_not_valid);
                dialog.setMessage(R.string.dialog_personal_back);
                dialog.setPositiveButton(R.string.dialog_okay, (dialog1, which) -> {
                   startActivity(new Intent(this, MainActivity.class));
                });
                dialog.setNegativeButton(R.string.dialog_cancle,null);
                dialog.show();
            } else {
                Toast.makeText(this,R.string.toast_you_need_create_personal_data,Toast.LENGTH_SHORT).show();
            }
        } else {
            if(username.length()!=0){
                if(!username.equals(newUsername)||!password.equals(newPassword)||genderId!=newGenderId||!birthday.equals(newBirthday)) {
                    dialog.setTitle(R.string.dialog_personal_data_change);
                    dialog.setMessage(R.string.dialog_ask_to_save);
                    dialog.setPositiveButton(R.string.dialog_okay, (dialog1, which) -> {
                        FileModel.setValueToTemp("username", newUsername, this);
                        FileModel.setValueToTemp("password", newPassword, this);
                        FileModel.setValueToTemp("gender", newGenderId, this);
                        FileModel.setValueToTemp("birthday", newBirthday, this);
                        Toast.makeText(this, R.string.toast_personal_data_save, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                    });
                    dialog.setNegativeButton(R.string.dialog_cancle,null);
                    dialog.show();
                } else {
                    super.onBackPressed();
                }
            } else {
                FileModel.setValueToTemp("username", newUsername, this);
                FileModel.setValueToTemp("password", newPassword, this);
                FileModel.setValueToTemp("gender", newGenderId, this);
                FileModel.setValueToTemp("birthday", newBirthday, this);
                Toast.makeText(this,R.string.toast_personal_data_save,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }

        }

    }

    @Override
    public void onBackPressed() {

    }
}