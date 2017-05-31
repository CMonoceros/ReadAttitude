package zjm.cst.dhu.newsmodule.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zjm.cst.dhu.library.utils.RestoreFragmentStatePagerAdapter;

/**
 * Created by zjm on 2017/5/10.
 */

public class NewsListFragmentPagerAdapter extends RestoreFragmentStatePagerAdapter {
    private List<Fragment> mNewsFragmentList = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public NewsListFragmentPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> list) {
        super(fm);
        mTitles = titles;
        mNewsFragmentList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mNewsFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mNewsFragmentList.size();
    }

}
