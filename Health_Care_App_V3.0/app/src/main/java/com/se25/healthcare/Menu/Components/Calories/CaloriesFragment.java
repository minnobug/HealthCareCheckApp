package com.se25.healthcare.Menu.Components.Calories;

import com.se25.healthcare.Menu.Components.Calories.CaloriesPresenter;
import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BaseFragment;

public class CaloriesFragment extends BaseFragment {
    @Override
    public void initPresenter(BaseContract.View view) {
        presenter = new CaloriesPresenter(view);
    }

}
