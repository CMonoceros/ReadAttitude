package zjm.cst.dhu.newsmodule.mvp.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import zjm.cst.dhu.library.event.ChannelChangeEvent;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.utils.RxBus;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemChannelLikeBinding;

/**
 * Created by zjm on 2017/5/12.
 */

public class ChannelManageLikeAdapter extends RecyclerView.Adapter<ChannelManageLikeAdapter.HolderChannelLike> {

    private List<NewsChannel> newsChannelList;

    public ChannelManageLikeAdapter(List<NewsChannel> list) {
        this.newsChannelList = list;
    }

    @Override
    public ChannelManageLikeAdapter.HolderChannelLike onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderChannelLike(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.module_news_item_channel_like, parent, false));
    }

    @Override
    public void onBindViewHolder(final ChannelManageLikeAdapter.HolderChannelLike holder, final int position) {
        final int pos = position;
        holder.moduleNewsItemChannelLikeBinding.setIsManage(false);
        holder.moduleNewsItemChannelLikeBinding.setChannel(newsChannelList.get(position));
        holder.moduleNewsItemChannelLikeBinding.moduleNewsRlChannelLike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.moduleNewsItemChannelLikeBinding.getIsManage()) {
                    holder.moduleNewsItemChannelLikeBinding.setIsManage(false);
                } else {
                    holder.moduleNewsItemChannelLikeBinding.setIsManage(true);
                }
                return true;
            }
        });
        holder.module_news_iv_channel_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(new ChannelChangeEvent(true, newsChannelList.get(pos)));
                holder.moduleNewsItemChannelLikeBinding.setIsManage(false);

            }
        });
    }

    @Override
    public int getItemCount() {
        return newsChannelList.size();
    }


    public class HolderChannelLike extends RecyclerView.ViewHolder {

        public ModuleNewsItemChannelLikeBinding moduleNewsItemChannelLikeBinding;
        public ImageView module_news_iv_channel_delete;

        public HolderChannelLike(View itemView) {
            super(itemView);
            moduleNewsItemChannelLikeBinding = DataBindingUtil.bind(itemView);
            module_news_iv_channel_delete = moduleNewsItemChannelLikeBinding.moduleNewsIvChannelDelete;
        }
    }

}
