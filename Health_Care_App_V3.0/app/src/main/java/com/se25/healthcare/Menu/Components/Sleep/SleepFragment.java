package com.se25.healthcare.Menu.Components.Sleep;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BaseFragment;
import com.se25.healthcare.Menu.Components.Breath.BreathPresenter;

public class SleepFragment extends BaseFragment {
    @Override
    public void initPresenter(BaseContract.View view) {
        presenter = new SleepPresenter(view);
    }

    @Override
    public int getSpecialInput(int index) {
        return 0;
    }
}
