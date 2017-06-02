package zjm.cst.dhu.newsmodule.mvp.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import zjm.cst.dhu.library.event.ChannelChangeEvent
import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.utils.others.RxBus

import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemChannelLikeBinding

/**
 * Created by zjm on 2017/5/12.
 */

class ChannelManageLikeAdapter(private val newsChannelList: List<NewsChannel>)
    : RecyclerView.Adapter<ChannelManageLikeAdapter.HolderChannelLike>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ChannelManageLikeAdapter.HolderChannelLike = HolderChannelLike(LayoutInflater.from(parent.context)
            .inflate(R.layout.module_news_item_channel_like, parent, false))


    override fun onBindViewHolder(holder: ChannelManageLikeAdapter.HolderChannelLike, position: Int) {
        holder.moduleNewsItemChannelLikeBinding.isManage = false
        holder.moduleNewsItemChannelLikeBinding.channel = newsChannelList[position]
        holder.moduleNewsItemChannelLikeBinding.moduleNewsRlChannelLike.setOnLongClickListener {
            holder.moduleNewsItemChannelLikeBinding.isManage =
                    !holder.moduleNewsItemChannelLikeBinding.isManage
            true
        }
        holder.module_news_iv_channel_delete.setOnClickListener {
            RxBus.default.post(ChannelChangeEvent(isDelete = true, newsChannel = newsChannelList[position]))
            holder.moduleNewsItemChannelLikeBinding.isManage = false
        }
    }

    override fun getItemCount():
            Int = newsChannelList.size


    inner class HolderChannelLike(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        var moduleNewsItemChannelLikeBinding: ModuleNewsItemChannelLikeBinding
                = DataBindingUtil.bind<ModuleNewsItemChannelLikeBinding>(itemView)
        var module_news_iv_channel_delete: ImageView

        init {
            module_news_iv_channel_delete = moduleNewsItemChannelLikeBinding.moduleNewsIvChannelDelete
        }
    }

}
