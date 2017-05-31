package zjm.cst.dhu.newsmodule.mvp.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.mvp.model.Ad;
import zjm.cst.dhu.library.mvp.model.NewsData;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsCardBinding;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsFooterBinding;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsHeaderBinding;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.ChannelManageFragment;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsInfFragment;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsListFragment;

import static zjm.cst.dhu.library.Constant.GUIDE_POINT_MARGIN;

/**
 * Created by zjm on 2017/5/10.
 */

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private NewsListFragment newsListFragment;
    private OnNewsListItemClickListener mOnItemClickListener;
    private ViewPager.OnPageChangeListener headerOnPageChangeListener;

    private List<NewsData> newsDataList;
    private int headerNowPosition = 0;
    private List<View> headerViewPaperItemList = new ArrayList<>();
    private List<ImageView> headerGuideImageViewList = new ArrayList<>();
    private boolean showFooter = false;

    public interface OnNewsListItemClickListener {
        void onItemClick(View view, int position);
    }

    public NewsListAdapter(NewsListFragment newsListFragment, List<NewsData> newsDataList) {
        this.newsListFragment = newsListFragment;
        this.newsDataList = newsDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constant.NEWS_LIST_TYPE_HEADER:
                return new HolderHeader(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.module_news_item_news_header, parent, false));
            case Constant.NEWS_LIST_TYPE_CARD:
                return new HolderCard(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.module_news_item_news_card, parent, false));
            case Constant.NEWS_LIST_TYPE_FOOTER:
                return new HolderFooter(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.module_news_item_news_footer, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderHeader) {
            bindHeader((HolderHeader) holder, position);
        } else if (holder instanceof HolderCard) {
            bindCard((HolderCard) holder, position);
        } else if (holder instanceof HolderFooter) {
            bindFooter((HolderFooter) holder, position);
        }
    }

    private void bindHeader(HolderHeader holderHeader, int position) {
        setupHeaderView(holderHeader, setupHeaderValue(holderHeader));
    }

    private List<Ad> setupHeaderValue(HolderHeader holderHeader) {
        holderHeader.module_news_vp_news_header.removeOnPageChangeListener(headerOnPageChangeListener);
        holderHeader.moduleNewsItemNewsHeaderBinding.moduleNewsLlGuidePoint.removeAllViews();
        headerGuideImageViewList.clear();
        headerViewPaperItemList.clear();
        List<Ad> adList = newsDataList.get(0).getAds();
        headerNowPosition = 0;
        LayoutInflater layoutInflater = LayoutInflater.from(newsListFragment.getActivity());
        for (int i = 0; i < adList.size(); i++) {
            headerViewPaperItemList.add(layoutInflater.inflate(R.layout.module_news_item_news_header_item, null));
        }
        return adList;
    }

    private void setupHeaderView(HolderHeader holderHeader, List<Ad> adList) {
        headerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                headerGuideImageViewList.get(position).setBackgroundResource(
                        R.drawable.module_news_guide_shape_retangle_select);
                headerGuideImageViewList.get(headerNowPosition).setBackgroundResource(
                        R.drawable.module_news_guide_shape_retangle_unselect);
                headerNowPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        NewsListHeaderAdapter newsListHeaderAdapter = new NewsListHeaderAdapter(newsListFragment, headerViewPaperItemList, adList);
        holderHeader.module_news_vp_news_header.setAdapter(newsListHeaderAdapter);
        holderHeader.module_news_vp_news_header.addOnPageChangeListener(headerOnPageChangeListener);
        LinearLayout moduleNewsLlGuidePoint = holderHeader.moduleNewsItemNewsHeaderBinding.moduleNewsLlGuidePoint;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < headerViewPaperItemList.size(); i++) {
            ImageView imageView = new ImageView(newsListFragment.getActivity());
            imageView.setBackgroundResource(R.drawable.module_news_guide_shape_retangle_unselect);
            params.leftMargin = GUIDE_POINT_MARGIN;
            params.rightMargin = GUIDE_POINT_MARGIN;
            moduleNewsLlGuidePoint.addView(imageView, params);
            headerGuideImageViewList.add(imageView);
        }
        headerGuideImageViewList.get(headerNowPosition).setBackgroundResource(
                R.drawable.module_news_guide_shape_retangle_select);

    }

    private void bindCard(HolderCard holderCard, final int position) {
        holderCard.moduleNewsItemNewsCardBinding.setNewsData(newsDataList.get(position));
        Glide.with(newsListFragment).load(newsDataList.get(position).getImgsrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holderCard.module_news_iv_news_card);
        holderCard.moduleNewsItemNewsCardBinding.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    private void bindFooter(HolderFooter holderFooter, int position) {
        holderFooter.moduleNewsItemNewsFooterBinding.setIsVisibility(showFooter);
    }

    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == Constant.NEWS_LIST_HEADER_POSITION &&
                newsDataList.size() > 0 && newsDataList.get(0).getAds() != null) {
            return Constant.NEWS_LIST_TYPE_HEADER;
        } else if (position == getItemCount() - 1) {
            return Constant.NEWS_LIST_TYPE_FOOTER;
        } else {
            return Constant.NEWS_LIST_TYPE_CARD;
        }
    }

    @Override
    public int getItemCount() {
        return newsDataList.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case Constant.NEWS_LIST_TYPE_HEADER:
                        case Constant.NEWS_LIST_TYPE_CARD:
                        case Constant.NEWS_LIST_TYPE_FOOTER:
                            return gridManager.getSpanCount();
                        default:
                            return gridManager.getSpanCount() / 2;
                    }
                }
            });
        }
    }

    public void setmOnItemClickListener(OnNewsListItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public class HolderHeader extends RecyclerView.ViewHolder {

        public ModuleNewsItemNewsHeaderBinding moduleNewsItemNewsHeaderBinding;
        public ViewPager module_news_vp_news_header;

        public HolderHeader(View itemView) {
            super(itemView);
            moduleNewsItemNewsHeaderBinding = DataBindingUtil.bind(itemView);
            module_news_vp_news_header = moduleNewsItemNewsHeaderBinding.moduleNewsVpNewsHeader;
        }
    }

    public class HolderCard extends RecyclerView.ViewHolder {

        public ModuleNewsItemNewsCardBinding moduleNewsItemNewsCardBinding;
        public ImageView module_news_iv_news_card;

        public HolderCard(View itemView) {
            super(itemView);
            moduleNewsItemNewsCardBinding = DataBindingUtil.bind(itemView);
            module_news_iv_news_card = moduleNewsItemNewsCardBinding.moduleNewsIvNewsCard;
        }
    }

    public class HolderFooter extends RecyclerView.ViewHolder {

        public ModuleNewsItemNewsFooterBinding moduleNewsItemNewsFooterBinding;

        public HolderFooter(View itemView) {
            super(itemView);
            moduleNewsItemNewsFooterBinding = DataBindingUtil.bind(itemView);
        }
    }
}
