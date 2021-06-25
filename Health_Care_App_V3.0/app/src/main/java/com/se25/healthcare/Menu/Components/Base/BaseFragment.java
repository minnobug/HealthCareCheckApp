package com.se25.healthcare.Menu.Components.Base;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.Menu.Adapter.PageAdapter;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.Menu.Page.PageGraph;
import com.se25.healthcare.Menu.Page.PageList;
import com.se25.healthcare.R;

import java.util.Objects;

public abstract class BaseFragment extends Fragment implements BaseContract.View, View.OnClickListener {
    public BasePresenter presenter;
    ViewPager vwPage;
    TabLayout tlTab;

    PageAdapter pageAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_component,container,false);
        initPresenter(this);
        presenter.getDataAdapter();
        initView(view);
        return view;
    }

    @Override
    public int getDateCreateVisible() {
        return View.VISIBLE;
    }

    @Override
    public void scrollTo(int resId) {
        vwPage.setCurrentItem(pageAdapter.getPositiveById(resId),true);
    }


    private void initView(View view){
        tlTab = view.findViewById(R.id.tlTab);
        vwPage = view.findViewById(R.id.vwPgPage);

        pageAdapter = new PageAdapter(getContext(), getChildFragmentManager());
        pageAdapter.addFragment(new PageForm(presenter),R.string.title_form);
        pageAdapter.addFragment(new PageList(presenter),R.string.title_list);
        pageAdapter.addFragment(new PageGraph(presenter),R.string.title_graph);
        vwPage.setOffscreenPageLimit(3);
        vwPage.setAdapter(pageAdapter);
        vwPage.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position!=0)
                    ((PageForm)getPage(R.string.title_form)).setEditForm(null);
            }
        });
        tlTab.setupWithViewPager(vwPage,true);
    }

    public Fragment getPage(int id) {
        return pageAdapter.getItemById(id);
    }

    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void showMessage(int resId) {
        ((MainActivity) requireActivity()).showMessage(resId);
    }

    @Override
    public void showDialog(int resTitleId, String message) {
        ((MainActivity) requireActivity()).showDialog(resTitleId,message);
    }

    @Override
    public int getSpecialInput(int index) {
        return -1;
    }


}
