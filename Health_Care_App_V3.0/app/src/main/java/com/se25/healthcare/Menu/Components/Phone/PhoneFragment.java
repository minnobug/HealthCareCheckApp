package com.se25.healthcare.Menu.Components.Phone;

import android.view.View;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BaseFragment;
import com.se25.healthcare.Menu.Components.Period.PeriodPresenter;

public class PhoneFragment extends BaseFragment {
    @Override
    public void initPresenter(BaseContract.View view) {
        presenter = new PhonePresenter(view);
    }

}
