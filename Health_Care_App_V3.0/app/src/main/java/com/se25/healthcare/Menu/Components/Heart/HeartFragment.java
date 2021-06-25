package com.se25.healthcare.Menu.Components.Heart;

import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BaseFragment;

public class HeartFragment extends BaseFragment {
    @Override
    public void initPresenter(BaseContract.View view) {
        presenter = new HeartPresenter(view);
    }
}
