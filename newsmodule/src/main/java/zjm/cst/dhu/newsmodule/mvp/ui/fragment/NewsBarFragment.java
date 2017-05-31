package zjm.cst.dhu.newsmodule.mvp.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import junit.framework.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.event.FloatingActionButtonClickEvent;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.utils.RxBus;
import zjm.cst.dhu.library.utils.ViewsUtil;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentNewsBarBinding;
import zjm.cst.dhu.newsmodule.mvp.presenter.NewsBarPresenter;
import zjm.cst.dhu.newsmodule.mvp.ui.activity.ChannelManageActivity;
import zjm.cst.dhu.newsmodule.mvp.ui.activity.TestActivity;
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.NewsListFragmentPagerAdapter;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment;
import zjm.cst.dhu.newsmodule.mvp.view.NewsBarContract;

/**
 * Created by zjm on 2017/5/10.
 */

public class NewsBarFragment extends BaseFragment<NewsBarPresenter> implements NewsBarContract.View {
    @Inject
    NewsBarPresenter newsBarPresenter;

    private ModuleNewsFragmentNewsBarBinding moduleNewsFragmentNewsBarBinding;
    private NewsListFragmentPagerAdapter newsListFragmentPagerAdapter;

    private TabLayout module_news_tl_news_channel;
    private ImageView module_news_iv_channel_add;
    private ViewPager module_news_vp_news_list;
    private Toolbar module_news_tb_list;
    private FloatingActionButton module_news_fab_list;

    private List<String> newsListTittleList = new ArrayList<>();
    private List<NewsChannel> newsChannelList = new ArrayList<>();
    private List<Fragment> newsFragmentList = new ArrayList<>();
    private int nowPosition = 0;

    @Override
    public void dataBinding(View rootView) {
        moduleNewsFragmentNewsBarBinding = DataBindingUtil.bind(rootView);
        module_news_iv_channel_add = moduleNewsFragmentNewsBarBinding.moduleNewsIvChannelAdd;
        module_news_tl_news_channel = moduleNewsFragmentNewsBarBinding.moduleNewsTlNewsChannel;
        module_news_vp_news_list = moduleNewsFragmentNewsBarBinding.moduleNewsVpNewsList;
        module_news_fab_list = moduleNewsFragmentNewsBarBinding.moduleNewsFabList;
        module_news_tb_list = moduleNewsFragmentNewsBarBinding.moduleNewsTbList;

        module_news_fab_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new FloatingActionButtonClickEvent());
            }
        });
        module_news_iv_channel_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChannelManageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void setupInjector() {
        myFragmentComponent.inject(this);
    }

    @Override
    public void setupViews(View rootView) {
        myPresenter = newsBarPresenter;
        myPresenter.attachView(this);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(module_news_tb_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        myPresenter.loadChannel();
    }

    @Override
    public int getFragmentId() {
        return R.layout.module_news_fragment_news_bar;
    }

    @Override
    public void loadChannelSuccess(List<NewsChannel> list) {
        Map<Integer, Integer> restoreStateMap = setupRestoreStateMap(list);
        newsFragmentList.clear();
        newsChannelList.clear();
        newsListTittleList.clear();
        newsChannelList.addAll(list);
        addNewsListFragment(list);
        setupViewPaper(restoreStateMap);
    }

    private Map<Integer, Integer> setupRestoreStateMap(List<NewsChannel> list) {
        Map<Integer, Integer> restoreStateMap = new HashMap<>();
        if (newsChannelList.size() != 0) {
            for (int i = 0; i < newsChannelList.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (newsChannelList.get(i).getChannelName().equals(list.get(j).getChannelName())) {
                        restoreStateMap.put(i, j);
                    }
                }
            }
        }
        return restoreStateMap;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void loadChannelError() {
        showSnackbarMessage(Constant.NEWS_BAR_LOAD_CHANNEL_ERROR);
        myPresenter.loadChannel();
    }

    @Override
    public void showSnackbarMessage(String message) {
        Snackbar.make(module_news_fab_list, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void uploadCrashLog(String log) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, Constant.CRASH_LOG_UPLOAD_TITTLE);
        intent.putExtra(Intent.EXTRA_TEXT, log);
        startActivity(intent);
    }


    private void addNewsListFragment(List<NewsChannel> list) {
        for (int i = 0; i < list.size(); i++) {
            NewsListFragment newsListFragment = new NewsListFragment();
            newsListFragment.setupValue(list.get(i).getChannelId(),
                    list.get(i).getChannelType());
            newsFragmentList.add(newsListFragment);
            newsListTittleList.add(list.get(i).getChannelName());
        }
    }

    private void setupViewPaper(Map<Integer, Integer> restoreStateMap) {
        newsListFragmentPagerAdapter = new NewsListFragmentPagerAdapter(getChildFragmentManager(),
                newsListTittleList, newsFragmentList);
        newsListFragmentPagerAdapter.setRestoreStateMap(restoreStateMap);
        module_news_vp_news_list.setAdapter(newsListFragmentPagerAdapter);
        module_news_vp_news_list.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                nowPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        module_news_tl_news_channel.setupWithViewPager(module_news_vp_news_list);
        ViewsUtil.dynamicSetTabLayoutMode(module_news_tl_news_channel);
        module_news_vp_news_list.setCurrentItem(nowPosition, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.module_news_menu_upload_crash) {
            myPresenter.readCrashLog();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.module_news_fragment_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
