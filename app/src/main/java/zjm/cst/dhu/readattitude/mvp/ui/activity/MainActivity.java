package zjm.cst.dhu.readattitude.mvp.ui.activity;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.MyApplication;
import zjm.cst.dhu.library.utils.Router;
import zjm.cst.dhu.readattitude.databinding.ActivityMainBinding;
import zjm.cst.dhu.readattitude.databinding.ItemTablayoutBinding;
import zjm.cst.dhu.readattitude.mvp.ui.activity.base.BaseActivity;
import zjm.cst.dhu.readattitude.R;
import zjm.cst.dhu.readattitude.mvp.presenter.MainPresenter;
import zjm.cst.dhu.readattitude.mvp.ui.adapter.MainFragmentPagerAdapter;
import zjm.cst.dhu.readattitude.mvp.ui.fragment.TestFragment1;
import zjm.cst.dhu.readattitude.mvp.ui.fragment.TestFragment2;
import zjm.cst.dhu.readattitude.mvp.view.MainContract;

/**
 * Created by zjm on 2017/5/9.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Inject
    MainPresenter mainPresenter;

    private ActivityMainBinding activityMainBinding;
    private ViewPager vp_main;
    private TabLayout tl_main;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();
    private final Map<Integer, ItemTablayoutBinding> itemTablayoutBindingHashMap = new HashMap<>();
    private int nowFragmentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTab();
    }

    @Override
    public void dataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vp_main = activityMainBinding.vpMain;
        tl_main = activityMainBinding.tlMain;
    }

    @Override
    public void setupInjector() {
        myActivityComponent.inject(this);
    }

    @Override
    public void setupViews() {
        setupFragment();

        myPresenter = mainPresenter;
        myPresenter.attachView(this);

        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(
                getSupportFragmentManager(), mFragmentTitles, mFragmentList
        );
        vp_main.setAdapter(mainFragmentPagerAdapter);
        tl_main.setupWithViewPager(vp_main);
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                itemTablayoutBindingHashMap.get(nowFragmentPosition).setIsSelected(false);
                nowFragmentPosition = position;
                itemTablayoutBindingHashMap.get(position).setIsSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_main.setCurrentItem(nowFragmentPosition, true);
    }

    private void setupFragment() {
        mFragmentList.add(Router.getFragment(Constant.NEWS_BAR_FRAGMENT_NAME));
        mFragmentTitles.add(Constant.NEWS_BAR_FRAGMENT_TITTLE);
//        mFragmentList.add(Router.getFragment(Constant.NEWS_BAR_FRAGMENT_NAME));
//        mFragmentTitles.add(Constant.NEWS_BAR_FRAGMENT_TITTLE);


//        mFragmentList.add(new TestFragment1());
//        mFragmentList.add(new TestFragment2());
//
//        mFragmentTitles.add("test1");
//        mFragmentTitles.add("test2");
    }

    private void setupTab() {
        List<String> tabTittleList = Arrays.asList(MyApplication.getContext().getResources()
                .getStringArray(R.array.tab_item_tittle));
        TypedArray tabResourceArray = MyApplication.getContext().getResources()
                .obtainTypedArray(R.array.tab_item_resource_id);
        for (int i = 0; i < mFragmentList.size(); i++) {
            TabLayout.Tab tab = tl_main.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(R.layout.item_tablayout);
                ItemTablayoutBinding itemTablayoutBinding = DataBindingUtil.bind(tab.getCustomView());
                itemTablayoutBinding.tvTabItem.setText(tabTittleList.get(i));
                itemTablayoutBinding.ivTabItem.setImageResource(tabResourceArray.getResourceId(i, 0));
                itemTablayoutBinding.setIsSelected(false);
                itemTablayoutBindingHashMap.put(i, itemTablayoutBinding);
            }
        }
        tabResourceArray.recycle();
        itemTablayoutBindingHashMap.get(nowFragmentPosition).setIsSelected(false);
    }

}
