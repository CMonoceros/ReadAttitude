package zjm.cst.dhu.newsmodule.mvp.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zjm.cst.dhu.library.event.ChannelChangeEvent;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.utils.RxBus;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemChannelUnlikeBinding;

/**
 * Created by zjm on 2017/5/12.
 */

public class ChannelManageUnlikeAdapter extends RecyclerView.Adapter<ChannelManageUnlikeAdapter.HolderChannelUnlike> {
    private List<NewsChannel> newsChannelList;

    public ChannelManageUnlikeAdapter(List<NewsChannel> list) {
        this.newsChannelList = list;
    }

    @Override
    public HolderChannelUnlike onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChannelManageUnlikeAdapter.HolderChannelUnlike(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.module_news_item_channel_unlike, parent, false));
    }

    @Override
    public void onBindViewHolder(HolderChannelUnlike holder, final int position) {
        holder.moduleNewsItemChannelUnlikeBinding.setChannel(newsChannelList.get(position));
        holder.moduleNewsItemChannelUnlikeBinding.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new ChannelChangeEvent(false, position, newsChannelList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsChannelList.size();
    }

    public class HolderChannelUnlike extends RecyclerView.ViewHolder {

        public ModuleNewsItemChannelUnlikeBinding moduleNewsItemChannelUnlikeBinding;


        public HolderChannelUnlike(View itemView) {
            super(itemView);
            moduleNewsItemChannelUnlikeBinding = DataBindingUtil.bind(itemView);
        }
    }
}
