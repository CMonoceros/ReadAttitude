package zjm.cst.dhu.newsmodule.mvp.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.ChannelManageFragment;

/**
 * Created by zjm on 2017/5/14.
 */

public class ChannelManageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_news_activity);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.module_news_fl_activity, new ChannelManageFragment());
        fragmentTransaction.commit();
    }

}
