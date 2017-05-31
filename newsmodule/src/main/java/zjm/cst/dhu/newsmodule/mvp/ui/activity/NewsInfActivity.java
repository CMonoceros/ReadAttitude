package zjm.cst.dhu.newsmodule.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsInfFragment;

/**
 * Created by zjm on 2017/5/14.
 */

public class NewsInfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_news_activity);

        NewsInfFragment newsInfFragment = new NewsInfFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.NEWS_INF_POST_ID, getIntent().getExtras().getString(Constant.NEWS_INF_POST_ID));
        bundle.putString(Constant.NEWS_INF_IMG_BASE, getIntent().getExtras().getString(Constant.NEWS_INF_IMG_BASE));
        newsInfFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.module_news_fl_activity, newsInfFragment);
        fragmentTransaction.commit();
    }
}
