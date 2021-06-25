package com.se25.healthcare.Menu.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se25.healthcare.Menu.Components.Base.BasePresenter;
import com.se25.healthcare.Menu.Components.DoctorContact.Doctor;
import com.se25.healthcare.Models.Tool;
import com.se25.healthcare.R;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    protected Context context;
    protected ArrayList<Doctor> doctors;

    public DoctorAdapter(Context context,ArrayList<Doctor> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new DoctorHolder(inflater.inflate(R.layout.layout_doctor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        holder.setDoctor(doctors.get(position));
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class DoctorHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtPhone,txtAddress;
        ImageButton btnCall;
        public DoctorHolder(@NonNull View DoctorView) {
            super(DoctorView);
            initView(DoctorView);
            initAction();
        }

        private void initView(View DoctorView) {
            txtName = DoctorView.findViewById(R.id.txtVwName);
            txtPhone = DoctorView.findViewById(R.id.txtVwPhone);
            txtAddress = DoctorView.findViewById(R.id.txtVwAddress);
            btnCall = DoctorView.findViewById(R.id.btnCall);
        }

        @SuppressLint("QueryPermissionsNeeded")
        private void initAction() {

            btnCall.setOnClickListener(v -> {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+txtPhone.getText()));
                context.startActivity(callIntent);
            });
        }

        @SuppressLint("SetTextI18n")
        public void setDoctor(Doctor doctor) {
            txtName.setText(doctor.getName());
            txtPhone.setText(doctor.getPhone());
            txtAddress.setText(context.getString(R.string.address) +": "+ doctor.getAddress());
        }
    }
}
