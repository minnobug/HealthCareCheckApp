package com.se25.healthcare.Menu.Components.BMI;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BaseFragment;
import com.se25.healthcare.Menu.Components.Heart.HeartPresenter;

public class BMIFragment extends BaseFragment {
    @Override
    public void initPresenter(BaseContract.View view) {
        presenter = new BMIPresenter(view);
    }
}
