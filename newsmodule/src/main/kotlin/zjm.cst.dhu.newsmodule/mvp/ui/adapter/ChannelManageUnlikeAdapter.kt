package zjm.cst.dhu.newsmodule.mvp.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import zjm.cst.dhu.library.event.ChannelChangeEvent
import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.utils.others.RxBus
import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemChannelUnlikeBinding

/**
 * Created by zjm on 2017/5/12.
 */

class ChannelManageUnlikeAdapter(private val newsChannelList: List<NewsChannel>)
    : RecyclerView.Adapter<ChannelManageUnlikeAdapter.HolderChannelUnlike>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HolderChannelUnlike = HolderChannelUnlike(LayoutInflater.from(parent.context).
            inflate(R.layout.module_news_item_channel_unlike, parent, false))


    override fun onBindViewHolder(holder: HolderChannelUnlike, position: Int) {
        holder.moduleNewsItemChannelUnlikeBinding.channel = newsChannelList[position]
        holder.moduleNewsItemChannelUnlikeBinding.onClick = View.OnClickListener {
            RxBus.default.post(ChannelChangeEvent(isMove = false, changeChannelPosition = position,
                    newsChannel = newsChannelList[position]))
        }
    }

    override fun getItemCount():
            Int = newsChannelList.size


    inner class HolderChannelUnlike(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        var moduleNewsItemChannelUnlikeBinding: ModuleNewsItemChannelUnlikeBinding
                = DataBindingUtil.bind<ModuleNewsItemChannelUnlikeBinding>(itemView)
    }
}
