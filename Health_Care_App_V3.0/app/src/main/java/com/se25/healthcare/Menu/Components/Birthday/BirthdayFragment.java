package com.se25.healthcare.Menu.Components.Birthday;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.se25.healthcare.Models.File.FileModel;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class BirthdayFragment extends Fragment {

    TextView hour, minute, dayleft;
    RelativeLayout rlHPBD, rlTimeCount;
    LocalDateTime birthday;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_birthday,container,false);
        initView(view);
        initBirthday();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                processTime();
            }

            @Override
            public void onFinish() {
                start();
            }
        }).start();
    }

    public void initView(View view) {
        hour = view.findViewById(R.id.txtVwHour);
        minute = view.findViewById(R.id.txtVwMinute);
        dayleft = view.findViewById(R.id.txtVwDayLeft);

        rlHPBD = view.findViewById(R.id.rlHPBD);
        rlTimeCount = view.findViewById(R.id.rlTimeCount);
    }

    public void initBirthday() {
        birthday = Tool.date(FileModel.getValueFromTemp("birthday",getContext()));
        birthday = LocalDateTime.of(LocalDateTime.now().getYear(),birthday.getMonthValue(),birthday.getDayOfMonth(),0,0);
        if(Tool.getTimeUntilNow(birthday,R.string.day)>0)
            birthday = birthday.plusYears(1);
    }

    public void processTime() {
        LocalDateTime now = LocalDateTime.now();
        long milis = Tool.getTimeUntilNow(birthday,R.string.milis);
        long day_l = milis/Tool.getMilisecond(R.string.day);
        milis -= day_l*Tool.getMilisecond(R.string.day);
        long hour_l = milis/Tool.getMilisecond(R.string.hour);
        milis -= hour_l*Tool.getMilisecond(R.string.hour);
        long minute_l = Math.round((float)milis/Tool.getMilisecond(R.string.minute));
        if(day_l<0){
            if(rlTimeCount.getVisibility()==View.GONE){
                rlTimeCount.setVisibility(View.VISIBLE);
                rlHPBD.setVisibility(View.GONE);
            }
            dayleft.setText(String.valueOf(-day_l));
            hour.setText(String.valueOf(-hour_l));
            minute.setText(String.valueOf(-minute_l));
        }
        else if(day_l==0) {
            if(rlHPBD.getVisibility()==View.GONE){
                dayleft.setText(String.valueOf(0));
                hour.setText(String.valueOf(0));
                minute.setText(String.valueOf(0));
                rlHPBD.setVisibility(View.VISIBLE);
                rlTimeCount.setVisibility(View.GONE);
            }
        }
    }
}
