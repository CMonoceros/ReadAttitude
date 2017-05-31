package zjm.cst.dhu.newsmodule.mvp.ui.fragment;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.event.ChannelChangeEvent;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.newsmodule.utils.ItemTouchHelperCallBack;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentChannelManageBinding;
import zjm.cst.dhu.newsmodule.mvp.presenter.ChannelManagePresenter;
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.ChannelManageLikeAdapter;
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.ChannelManageUnlikeAdapter;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment;
import zjm.cst.dhu.newsmodule.mvp.view.ChannelManageContract;

import static zjm.cst.dhu.library.Constant.CHANNEL_SHOW_LINE_COUNTS;

/**
 * Created by zjm on 2017/5/12.
 */

public class ChannelManageFragment extends BaseFragment<ChannelManagePresenter> implements ChannelManageContract.View {
    @Inject
    ChannelManagePresenter channelManagePresenter;

    private ModuleNewsFragmentChannelManageBinding moduleNewsFragmentChannelManageBinding;
    private RecyclerView module_news_rv_channel_like, module_news_rv_channel_unlike;
    private Toolbar module_news_tl_channel_manage;
    private ChannelManageLikeAdapter channelManageLikeAdapter;
    private ChannelManageUnlikeAdapter channelManageUnlikeAdapter;

    private List<NewsChannel> newsLikeChannelList = new ArrayList<>();
    private List<NewsChannel> newsUnlikeChannelList = new ArrayList<>();

    @Override
    public void dataBinding(View rootView) {
        moduleNewsFragmentChannelManageBinding = DataBindingUtil.bind(rootView);
        module_news_rv_channel_like = moduleNewsFragmentChannelManageBinding.moduleNewsRvChannelLike;
        module_news_rv_channel_unlike = moduleNewsFragmentChannelManageBinding.moduleNewsRvChannelUnlike;
        module_news_tl_channel_manage = moduleNewsFragmentChannelManageBinding.moduleNewsTlChannelManage;
    }

    @Override
    public void setupInjector() {
        myFragmentComponent.inject(this);
    }

    @Override
    public void setupViews(View rootView) {
        myPresenter = channelManagePresenter;
        myPresenter.attachView(this);
        myPresenter.loadChannelList();

        channelManageLikeAdapter = new ChannelManageLikeAdapter(newsLikeChannelList);
        module_news_rv_channel_like.setAdapter(channelManageLikeAdapter);
        module_news_rv_channel_like.setLayoutManager(
                new GridLayoutManager(module_news_rv_channel_like.getContext(), CHANNEL_SHOW_LINE_COUNTS, LinearLayoutManager.VERTICAL, false));

        ItemTouchHelperCallBack itemTouchHelperCallBack = new ItemTouchHelperCallBack();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(module_news_rv_channel_like);

        channelManageUnlikeAdapter = new ChannelManageUnlikeAdapter(newsUnlikeChannelList);
        module_news_rv_channel_unlike.setAdapter(channelManageUnlikeAdapter);
        module_news_rv_channel_unlike.setLayoutManager(
                new GridLayoutManager(module_news_rv_channel_unlike.getContext(), CHANNEL_SHOW_LINE_COUNTS, LinearLayoutManager.VERTICAL, false));

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(module_news_tl_channel_manage);
    }

    @Override
    public int getFragmentId() {
        return R.layout.module_news_fragment_channel_manage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadChannelSuccess(List<NewsChannel> like, List<NewsChannel> unlike) {
        newsLikeChannelList.clear();
        newsLikeChannelList.addAll(like);
        channelManageLikeAdapter.notifyDataSetChanged();

        newsUnlikeChannelList.clear();
        newsUnlikeChannelList.addAll(unlike);
        channelManageUnlikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadChannelError() {
        NewsBarFragment newsBarFragment = (NewsBarFragment) getParentFragment();
        newsBarFragment.showSnackbarMessage(Constant.CHANNEL_MANAGE_LOAD_CHANNEL_ERROR);
        myPresenter.loadChannelList();
    }

    @Override
    public void channelChange(ChannelChangeEvent channelChangeEvent) {
        if (channelChangeEvent.getIsDelete()) {
            deleteChannel(channelChangeEvent);
        } else if (channelChangeEvent.getIsMove()) {
            moveChannel(channelChangeEvent);
        } else {
            addChannel(channelChangeEvent);
        }
        for (int i = 0; i < newsLikeChannelList.size(); i++) {
            newsLikeChannelList.get(i).setChannelIndex(i);
        }
        myPresenter.updateChannelDB(newsLikeChannelList, newsUnlikeChannelList);
    }

    private void deleteChannel(ChannelChangeEvent channelChangeEvent) {
        channelManageLikeAdapter.notifyDataSetChanged();
        NewsChannel newsChannel = channelChangeEvent.getNewsChannel();
        newsUnlikeChannelList.add(newsChannel);
        channelManageUnlikeAdapter.notifyItemInserted(newsUnlikeChannelList.size() - 1);
        channelManageUnlikeAdapter.notifyItemChanged(newsUnlikeChannelList.size() - 1);
        channelManageLikeAdapter.notifyItemRemoved(newsChannel.getChannelIndex());
        channelManageLikeAdapter.notifyItemRangeChanged(
                newsChannel.getChannelIndex(),
                newsLikeChannelList.size() - newsChannel.getChannelIndex()
        );
        newsLikeChannelList.remove(newsChannel.getChannelIndex());
    }

    private void moveChannel(ChannelChangeEvent channelChangeEvent) {
        int fromPosition = channelChangeEvent.getFromPosition();
        int toPosition = channelChangeEvent.getToPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(newsLikeChannelList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(newsLikeChannelList, i, i - 1);
            }
        }
        channelManageLikeAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    private void addChannel(ChannelChangeEvent channelChangeEvent) {
        newsLikeChannelList.add(channelChangeEvent.getNewsChannel());
        channelManageLikeAdapter.notifyItemInserted(newsLikeChannelList.size() - 1);
        channelManageLikeAdapter.notifyItemChanged(newsLikeChannelList.size() - 1);
        channelManageUnlikeAdapter.notifyItemRemoved(channelChangeEvent.getChangeChannelPosition());
        channelManageUnlikeAdapter.notifyItemRangeChanged(
                channelChangeEvent.getChangeChannelPosition(),
                newsUnlikeChannelList.size() - channelChangeEvent.getChangeChannelPosition()
        );
        newsUnlikeChannelList.remove(channelChangeEvent.getChangeChannelPosition());
    }
}
