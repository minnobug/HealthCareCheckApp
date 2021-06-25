package com.se25.healthcare.Menu.Components.DoctorContact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.se25.healthcare.Menu.Adapter.DoctorAdapter;
import com.se25.healthcare.R;

import java.util.ArrayList;

public class DoctorFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor,container,false);

        RecyclerView rVwDoctors = view.findViewById(R.id.rVwDoctors);
        ArrayList<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Hà Văn Thiệu","0367697788","579 Đường Đỗ Xuân Hợp, Phước Long B, Quận 9, Thành phố Hồ Chí Minh"));
        doctors.add(new Doctor("Nguyễn Hữu Chung","0981630068","313a Hoà Hảo, Phường 4, Quận 10, Thành phố Hồ Chí Minh"));
        doctors.add(new Doctor("Hoàng Quốc Hòa","02838991129","395 Điện Biên Phủ, Phường 25, Bình Thạnh, Thành phố Hồ Chí Minh"));
        DoctorAdapter doctorAdapter = new DoctorAdapter(getContext(),doctors);
        rVwDoctors.setAdapter(doctorAdapter);
        rVwDoctors.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

}
