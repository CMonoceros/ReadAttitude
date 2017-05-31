package zjm.cst.dhu.newsmodule.mvp.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.mvp.model.NewsData;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentNewsListBinding;
import zjm.cst.dhu.newsmodule.mvp.presenter.NewsListPresenter;
import zjm.cst.dhu.newsmodule.mvp.ui.activity.NewsInfActivity;
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.NewsListAdapter;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment;
import zjm.cst.dhu.newsmodule.mvp.view.NewsListContract;

import static zjm.cst.dhu.library.Constant.NEWS_LIST_GRIDLAYOUT_SPAN;

/**
 * Created by zjm on 2017/5/10.
 */

public class NewsListFragment extends BaseFragment<NewsListPresenter> implements NewsListContract.View {
    @Inject
    NewsListPresenter newsListPresenter;

    private SwipeRefreshLayout module_news_srl_news_list;
    private RecyclerView module_news_rv_news_list;
    private TextView module_news_tv_news_list_empty;

    private ModuleNewsFragmentNewsListBinding moduleNewsFragmentNewsListBinding;
    private NewsListAdapter newsListAdapter;
    private String mNewsId;
    private String mNewsType;
    private List<NewsData> mNewsDataList = new ArrayList<>();
    private boolean isInit = false;
    private boolean isLoad = false;

    public static NewsListFragment newInstance(String id, String type) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.NEWS_LIST_FRAGMENT_BUNDLE_ID, id);
        bundle.putString(Constant.NEWS_LIST_FRAGMENT_BUNDLE_TYPE, type);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setupValue(String id, String type) {
        this.mNewsId = id;
        this.mNewsType = type;
    }

    @Override
    public void dataBinding(View rootView) {
        moduleNewsFragmentNewsListBinding = DataBindingUtil.bind(rootView);
        module_news_srl_news_list = moduleNewsFragmentNewsListBinding.moduleNewsSrlNewsList;
        module_news_rv_news_list = moduleNewsFragmentNewsListBinding.moduleNewsRvNewsList;
        module_news_tv_news_list_empty = moduleNewsFragmentNewsListBinding.moduleNewsTvNewsListEmpty;
        moduleNewsFragmentNewsListBinding.setLoadSuccess(true);
        module_news_tv_news_list_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPresenter.loadNewList();
                module_news_srl_news_list.setRefreshing(true);
                moduleNewsFragmentNewsListBinding.setLoadSuccess(true);
            }
        });
    }

    @Override
    public void setupInjector() {
        myFragmentComponent.inject(this);
    }

    @Override
    public void setupViews(View rootView) {
        myPresenter = newsListPresenter;
        myPresenter.attachView(this);
        myPresenter.setValue(mNewsId, mNewsType);

        module_news_srl_news_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myPresenter.loadNewList();
                module_news_srl_news_list.setRefreshing(true);
            }
        });

        module_news_rv_news_list.setHasFixedSize(true);
        module_news_rv_news_list.setLayoutManager(
                new GridLayoutManager(module_news_rv_news_list.getContext(), NEWS_LIST_GRIDLAYOUT_SPAN,
                        GridLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter(this, mNewsDataList);
        newsListAdapter.setmOnItemClickListener(new NewsListAdapter.OnNewsListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsInfActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.NEWS_INF_POST_ID, mNewsDataList.get(position).getPostid());
                bundle.putString(Constant.NEWS_INF_IMG_BASE, mNewsDataList.get(position).getImgsrc());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        module_news_rv_news_list.setAdapter(newsListAdapter);
        module_news_rv_news_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    myPresenter.loadNextList();
                    newsListAdapter.setShowFooter(true);
                    newsListAdapter.notifyItemChanged(mNewsDataList.size());
                }

            }
        });

        isInit = true;
        lazyLoadData();
    }

    private void lazyLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint() && !isLoad) {
            isLoad = true;
            myPresenter.loadNewList();
            module_news_srl_news_list.setRefreshing(true);
        } else {
            if (isLoad) {

            }
        }
    }

    @Override
    public int getFragmentId() {
        return R.layout.module_news_fragment_news_list;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoadData();
    }

    @Override
    public void loadNewListSuccess(List<NewsData> list) {
        module_news_srl_news_list.setRefreshing(false);
        moduleNewsFragmentNewsListBinding.setLoadSuccess(true);
        mNewsDataList.clear();
        mNewsDataList.addAll(list);
        newsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadNewListError() {
        moduleNewsFragmentNewsListBinding.setLoadSuccess(false);
        module_news_srl_news_list.setRefreshing(false);
        NewsBarFragment newsBarFragment = (NewsBarFragment) getParentFragment();
        newsBarFragment.showSnackbarMessage(Constant.NEWS_LIST_LOAD_ERROR);
    }

    @Override
    public void loadNextListSuccess(List<NewsData> list) {
        newsListAdapter.setShowFooter(false);
        newsListAdapter.notifyItemRemoved(mNewsDataList.size());
        mNewsDataList.addAll(list);
        newsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadNextListError() {
        newsListAdapter.setShowFooter(false);
        newsListAdapter.notifyItemRemoved(mNewsDataList.size());
        NewsBarFragment newsBarFragment = (NewsBarFragment) getParentFragment();
        newsBarFragment.showSnackbarMessage(Constant.NEWS_LIST_LOAD_ERROR);
    }

    @Override
    public void floatingActionButtonClick() {
        module_news_rv_news_list.scrollToPosition(0);
    }
}
