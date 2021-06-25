package com.se25.healthcare.Menu.Components.Period;

import android.view.View;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BaseFragment;
import com.se25.healthcare.Menu.Components.Sleep.SleepPresenter;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.R;

public class PeriodFragment extends BaseFragment {
    @Override
    public void initPresenter(BaseContract.View view) {
        presenter = new PeriodPresenter(view);
    }

    @Override
    public int getSpecialInput(int index) {
        return 2;
    }

    @Override
    public int getDateCreateVisible() {
        return View.GONE;
    }
}
