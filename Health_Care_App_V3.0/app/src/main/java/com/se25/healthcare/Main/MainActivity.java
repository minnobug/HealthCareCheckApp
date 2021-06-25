package com.se25.healthcare.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se25.healthcare.Access.PersonalActivity;
import com.se25.healthcare.Access.VerifyActivity;
import com.se25.healthcare.Menu.Components.BMI.BMIFragment;
import com.se25.healthcare.Menu.Components.Birthday.BirthdayFragment;
import com.se25.healthcare.Menu.Components.Breath.BreathFragment;
import com.se25.healthcare.Menu.Components.Calories.CaloriesFragment;
import com.se25.healthcare.Menu.Components.Calories.Exercise.ExerciseFragment;
import com.se25.healthcare.Menu.Components.Calories.Food.FoodFragment;
import com.se25.healthcare.Menu.Components.DoctorContact.DoctorFragment;
import com.se25.healthcare.Menu.Components.Heart.HeartFragment;
import com.se25.healthcare.Menu.Components.Period.PeriodFragment;
import com.se25.healthcare.Menu.Components.Phone.PhoneFragment;
import com.se25.healthcare.Menu.Components.Sleep.SleepFragment;
import com.se25.healthcare.Menu.MenuFragment;
import com.se25.healthcare.Models.Databases.LocalDatabase;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rlLoading;
    private ImageView imgVwActionIcon, imgVwActionSettings, imgVwIcon;
    private TextView txtVwTitle;
    FragmentManager fragmentManager;
    Toast mToast;
    Handler handler;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, VerifyActivity.class));

        LocalDatabase.init(this, 1);

        initView();
        initAction();
        handler = new Handler();

        mToast = Toast.makeText(this, R.string.welcome_back, Toast.LENGTH_SHORT);
        mToast.show();
        setFragmentContent(R.string.title_menu);
    }

    private void initView() {
        imgVwActionIcon = findViewById(R.id.imgVwActionIcon);
        imgVwActionSettings = findViewById(R.id.imgVwActionSettings);
        txtVwTitle = findViewById(R.id.txtVwActionTitle);
        rlLoading = findViewById(R.id.rlLoading);
        imgVwIcon = findViewById(R.id.imgVwIcon);
    }

    private void initAction() {
        imgVwActionIcon.setOnClickListener(this);
        imgVwActionSettings.setOnClickListener(this);
    }

    public void showMessage(int resId, Object a) {
        String temp = getString(resId) + " " + a;
        mToast.cancel();
        mToast = Toast.makeText(this, temp, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showMessage(int resId) {
        mToast.cancel();
        mToast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showDialog(int resTitleId, String content) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.app_icon);
        dialog.setTitle(resTitleId);
        dialog.setMessage(content);
        dialog.setPositiveButton(R.string.dialog_okay, null);
        dialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgVwActionIcon:
                setFragmentContent(R.string.title_menu);
                break;
            case R.id.imgVwActionSettings:
                setLoading(R.drawable.id_card);
                startActivity(new Intent(this, PersonalActivity.class));
                break;
            default:
                break;
        }
    }

    @SuppressLint({"NonConstantResourceId", "QueryPermissionsNeeded"})
    public void setFragmentContent(int resId) {
        Fragment fragment = null;
        String title = getResources().getString(resId);
        int resIdActionIcon = R.drawable.button_menu;
        switch (resId) {
            case R.string.title_menu:
                setLoading(R.drawable.button_menu);
                resIdActionIcon = R.drawable.button_app_icon;
                fragment = new MenuFragment();
                break;
            case R.string.button_heart:
                setLoading(R.drawable.heart);
                fragment = new HeartFragment();
                break;
            case R.string.button_BMI:
                setLoading(R.drawable.bmi);
                fragment = new BMIFragment();
                break;
            case R.string.button_breath:
                setLoading(R.drawable.breath);
                fragment = new BreathFragment();
                break;
            case R.string.button_sleep:
                setLoading(R.drawable.sleep);
                fragment = new SleepFragment();
                break;
            case R.string.button_period:
                setLoading(R.drawable.blood);
                fragment = new PeriodFragment();
                break;
            case R.string.button_phone:
                setLoading(R.drawable.smartphone);
                fragment = new PhoneFragment();
                break;
            case R.string.button_calories:
                setLoading(R.drawable.calories_calculator);
                fragment = new CaloriesFragment();
                break;
            case R.string.button_food:
                setLoading(R.drawable.fast_food);
                fragment = new FoodFragment();
                break;
            case R.string.button_exercise:
                setLoading(R.drawable.exercise);
                fragment = new ExerciseFragment();
                break;
            case R.string.button_news:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://suckhoedoisong.vn/"));
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                } else {
                    showMessage(R.string.toast_fail);
                }
                break;
            case R.string.button_doctor:
                fragment = new DoctorFragment();
                break;
            case R.string.button_birthday:
                fragment = new BirthdayFragment();
                break;
            default:
                showMessage(R.string.toast_testing);
                return;
        }
        imgVwActionIcon.setImageResource(resIdActionIcon);
        txtVwTitle.setText(title);
        if (fragment == null)
            return;
        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rlContent, fragment);
        fragmentTransaction.commit();
    }

    public void setLoading(int iconId) {
        runOnUiThread(() -> {
            imgVwIcon.setImageResource(iconId);
            rlLoading.setVisibility(View.VISIBLE);
        });
        handler.postDelayed(()->{
            rlLoading.setVisibility(View.GONE);
        },100);
    }

    int count = 0;

    @Override
    public void onBackPressed() {
        if(txtVwTitle.getText().toString().equals(getString(R.string.title_menu))){
            count++;
            if(count == 10){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.ask_to_create_data);
                dialog.setNegativeButton(R.string.dialog_cancle,null);
                dialog.setPositiveButton(R.string.dialog_okay, (dialog1, which) -> {
                    LocalDatabase.createDataTest(this,1);
                    showMessage(R.string.data_create);
                });
                dialog.show();
                count =0;
            }
        } else {
            setFragmentContent(R.string.title_menu);
        }
    }
}