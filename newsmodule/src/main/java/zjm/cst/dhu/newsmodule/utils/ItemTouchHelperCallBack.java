package zjm.cst.dhu.newsmodule.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

import zjm.cst.dhu.library.event.ChannelChangeEvent;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.utils.RxBus;
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.ChannelManageLikeAdapter;

/**
 * Created by zjm on 2017/5/12.
 */

public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {


    public ItemTouchHelperCallBack() {
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.START | ItemTouchHelper.DOWN, 0);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
        RxBus.getDefault().post(new ChannelChangeEvent(true, fromPosition, toPosition));
        ChannelManageLikeAdapter.HolderChannelLike holderChannelLike =
                (ChannelManageLikeAdapter.HolderChannelLike) viewHolder;
        holderChannelLike.moduleNewsItemChannelLikeBinding.setIsManage(false);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
