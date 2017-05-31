package zjm.cst.dhu.newsmodule.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;

import java.util.List;

import zjm.cst.dhu.library.mvp.model.Ad;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsHeaderItemBinding;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsListFragment;

/**
 * Created by zjm on 2017/5/10.
 */

public class NewsListHeaderAdapter extends PagerAdapter {
    private List<View> viewList;
    private List<Ad> adList;
    private NewsListFragment newsListFragment;

    public NewsListHeaderAdapter(NewsListFragment newsListFragment, List<View> viewList, List<Ad> adList) {
        this.viewList = viewList;
        this.newsListFragment = newsListFragment;
        this.adList = adList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view, 0);
        ModuleNewsItemNewsHeaderItemBinding moduleNewsItemAdsBinding = DataBindingUtil.bind(view);
        moduleNewsItemAdsBinding.setAds(adList.get(position));
        Glide.with(newsListFragment).load(adList.get(position).getImgsrc())
                .into(moduleNewsItemAdsBinding.moduleNewsIvNewsHeader);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
