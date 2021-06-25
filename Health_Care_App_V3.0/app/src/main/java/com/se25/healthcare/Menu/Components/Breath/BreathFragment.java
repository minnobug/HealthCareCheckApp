package com.se25.healthcare.Menu.Components.Breath;

import android.view.View;
import android.widget.EditText;

import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.Menu.Components.BMI.BMIPresenter;
import com.se25.healthcare.Menu.Components.Base.BaseContract;
import com.se25.healthcare.Menu.Components.Base.BaseFragment;
import com.se25.healthcare.Menu.Page.PageForm;
import com.se25.healthcare.R;

public class BreathFragment extends BaseFragment {
    @Override
    public void initPresenter(BaseContract.View view) {
        presenter = new BreathPresenter(view);
    }

    @Override
    public int getSpecialInput(int index) {
        return index == 0?1:-1;
    }
}
