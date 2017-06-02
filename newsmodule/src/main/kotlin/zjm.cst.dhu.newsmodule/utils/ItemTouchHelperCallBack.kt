package zjm.cst.dhu.newsmodule.utils

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import zjm.cst.dhu.library.event.ChannelChangeEvent
import zjm.cst.dhu.library.utils.others.RxBus
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.ChannelManageLikeAdapter


/**
 * Created by zjm on 2017/5/12.
 */

class ItemTouchHelperCallBack
    : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder):
            Int = ItemTouchHelper.Callback.makeMovementFlags(ItemTouchHelper.LEFT
            or ItemTouchHelper.RIGHT or ItemTouchHelper.UP
            or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.DOWN, 0)


    override fun isItemViewSwipeEnabled():
            Boolean = false


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder):
            Boolean {
        val fromPosition = viewHolder.adapterPosition//得到拖动ViewHolder的position
        val toPosition = target.adapterPosition//得到目标ViewHolder的position
        RxBus.default.post(ChannelChangeEvent(isMove = true, fromPosition = fromPosition,
                toPosition = toPosition))
        val holderChannelLike = viewHolder as ChannelManageLikeAdapter.HolderChannelLike
        holderChannelLike.moduleNewsItemChannelLikeBinding.isManage = false
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
}
