package zjm.cst.dhu.readattitude.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zjm.cst.dhu.readattitude.R;

/**
 * Created by zjm on 2017/5/9.
 */

public class TestFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_guide_2,container,false);
        return view;
    }
}
