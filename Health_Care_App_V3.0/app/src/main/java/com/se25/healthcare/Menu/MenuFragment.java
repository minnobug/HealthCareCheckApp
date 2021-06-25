package com.se25.healthcare.Menu;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.Models.File.FileModel;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;
import com.se25.healthcare.Support.ItemSquare;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class MenuFragment extends Fragment implements View.OnClickListener {
    private final static int[] btnResId = {
            R.id.imgBtnNews, R.id.imgBtnSmartWatch, R.id.imgBtnDoctor,
            R.id.imgBtnHeart, R.id.imgBtnBreath, R.id.imgBtnBMI,
            R.id.imgBtnCalories, R.id.imgBtnFood, R.id.imgBtnExercise,
            R.id.imgBtnSleep, R.id.imgBtnPhone,R.id.imgBtnBirthday,
            R.id.imgBtnPeriod
    };
    private final static int[] btnTitleResId = {
            R.string.button_news, R.string.button_smartwatch, R.string.button_doctor,
            R.string.button_heart, R.string.button_breath, R.string.button_BMI,
            R.string.button_calories, R.string.button_food, R.string.button_exercise,
            R.string.button_sleep, R.string.button_phone, R.string.button_birthday,
            R.string.button_period
    };
    ImageButton[] imgBtn;
    ItemSquare iqBirthdayCount;
    TextView txtVwBirthdayCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initView(view);
        initAction();
        processBirthday();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        processBirthday();
    }

    public void processBirthday(){
        String birthday = FileModel.getValueFromTemp("birthday",getContext());
        if(birthday.length() == 0)
            return;
        LocalDateTime bd = Tool.date(birthday);
        LocalDateTime nextBd = LocalDateTime.of(LocalDateTime.now().getYear(),bd.getMonthValue(),bd.getDayOfMonth(),0,0);
        if(Tool.getTimeUntilNow(nextBd,R.string.day)>0)
            nextBd = nextBd.plusYears(1);
        else {
            iqBirthdayCount.setVisibility(View.GONE);
            imgBtn[11].setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.red));
            return;
        }

        long day = Math.round((float)Tool.getTimeUntilNow(nextBd,R.string.milis)/Tool.getMilisecond(R.string.day));
        if(day<0)
            txtVwBirthdayCount.setText(String.valueOf(-day));
        else if(day==0) {
        }
    }

    private void initView(View view) {
        imgBtn = new ImageButton[btnResId.length];
        for (int i = 0; i < imgBtn.length; i++) {
            imgBtn[i] = (ImageButton) view.findViewById(btnResId[i]);
        }
        iqBirthdayCount = view.findViewById(R.id.iqBirthdayCount);
        txtVwBirthdayCount = view.findViewById(R.id.txtVwBirthdayCount);
        if(FileModel.getValueFromTemp("gender",getContext()).equals(String.valueOf(R.id.rdMale)))
            imgBtn[imgBtn.length-1].setVisibility(View.GONE);
    }

    private void initAction() {
        for (int i = 0; i < imgBtn.length; i++) {
            imgBtn[i].setOnClickListener(this);
        }
    }

    private int findIndex(int x) {
        for (int i = 0; i < MenuFragment.btnResId.length; i++)
            if (x == MenuFragment.btnResId[i])
                return i;
        return -1;
    }

    @Override
    public void onClick(View v) {
        //btnResId & btnTitleResId có chiều dài bằng nhau nên sẽ không có trường hợp lỗi
        int titleResId = btnTitleResId[findIndex(v.getId())];
        ((MainActivity) requireActivity()).showMessage(titleResId);
        ((MainActivity) requireActivity()).setFragmentContent(titleResId);
    }
}
