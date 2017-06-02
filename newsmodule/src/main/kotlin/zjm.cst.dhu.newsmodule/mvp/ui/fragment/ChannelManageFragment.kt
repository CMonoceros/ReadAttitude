package zjm.cst.dhu.newsmodule.mvp.ui.fragment

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import android.view.View
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.event.ChannelChangeEvent
import zjm.cst.dhu.library.mvp.model.NewsChannel

import javax.inject.Inject
import zjm.cst.dhu.newsmodule.utils.ItemTouchHelperCallBack
import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentChannelManageBinding
import zjm.cst.dhu.newsmodule.mvp.presenter.ChannelManagePresenter
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.ChannelManageLikeAdapter
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.ChannelManageUnlikeAdapter
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment
import zjm.cst.dhu.newsmodule.mvp.view.ChannelManageContract
import java.util.*

/**
 * Created by zjm on 2017/5/12.
 */

class ChannelManageFragment
    : BaseFragment<ChannelManagePresenter>(), ChannelManageContract.View {
    @Inject
    lateinit var channelManagePresenter: ChannelManagePresenter

    private var moduleNewsFragmentChannelManageBinding: ModuleNewsFragmentChannelManageBinding? = null
    private var module_news_rv_channel_like: RecyclerView? = null
    private var module_news_rv_channel_unlike: RecyclerView? = null
    private var module_news_tl_channel_manage: Toolbar? = null
    private var channelManageLikeAdapter: ChannelManageLikeAdapter? = null
    private var channelManageUnlikeAdapter: ChannelManageUnlikeAdapter? = null

    private val newsLikeChannelList = ArrayList<NewsChannel>()
    private val newsUnlikeChannelList = ArrayList<NewsChannel>()

    override fun dataBinding(rootView: View) {
        moduleNewsFragmentChannelManageBinding = DataBindingUtil.
                bind<ModuleNewsFragmentChannelManageBinding>(rootView)
        module_news_rv_channel_like = moduleNewsFragmentChannelManageBinding!!.moduleNewsRvChannelLike
        module_news_rv_channel_unlike = moduleNewsFragmentChannelManageBinding!!.moduleNewsRvChannelUnlike
        module_news_tl_channel_manage = moduleNewsFragmentChannelManageBinding!!.moduleNewsTlChannelManage
    }

    override fun setupInjector() {
        myFragmentComponent!!.inject(this)
    }

    override fun setupViews(rootView: View) {
        myPresenter = channelManagePresenter
        myPresenter!!.attachView(this)
        myPresenter!!.loadChannelList()

        channelManageLikeAdapter = ChannelManageLikeAdapter(newsLikeChannelList)
        module_news_rv_channel_like!!.adapter = channelManageLikeAdapter
        module_news_rv_channel_like!!.layoutManager =
                GridLayoutManager(module_news_rv_channel_like!!.context,
                        Constant.CHANNEL_SHOW_LINE_COUNTS, LinearLayoutManager.VERTICAL, false)

        val itemTouchHelperCallBack = ItemTouchHelperCallBack()
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(module_news_rv_channel_like)

        channelManageUnlikeAdapter = ChannelManageUnlikeAdapter(newsUnlikeChannelList)
        module_news_rv_channel_unlike!!.adapter = channelManageUnlikeAdapter
        module_news_rv_channel_unlike!!.layoutManager =
                GridLayoutManager(module_news_rv_channel_unlike!!.context,
                        Constant.CHANNEL_SHOW_LINE_COUNTS, LinearLayoutManager.VERTICAL, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(module_news_tl_channel_manage)
    }

    override val fragmentId: Int
        get() = R.layout.module_news_fragment_channel_manage

    override fun onOptionsItemSelected(item: MenuItem?):
            Boolean {
        when (item!!.itemId) {
            android.R.id.home -> activity.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun loadChannelSuccess(like: List<NewsChannel>?, unlike: List<NewsChannel>?) {
        if (like != null) {
            newsLikeChannelList.clear()
            newsLikeChannelList.addAll(like)
            channelManageLikeAdapter!!.notifyDataSetChanged()
        }
        if (unlike != null) {
            newsUnlikeChannelList.clear()
            newsUnlikeChannelList.addAll(unlike)
            channelManageUnlikeAdapter!!.notifyDataSetChanged()
        }
    }

    override fun loadChannelError() {
        val newsBarFragment = parentFragment as NewsBarFragment
        newsBarFragment.showSnackbarMessage(Constant.CHANNEL_MANAGE_LOAD_CHANNEL_ERROR)
        myPresenter!!.loadChannelList()
    }

    override fun channelChange(channelChangeEvent: ChannelChangeEvent) {
        if (channelChangeEvent.isDelete) {
            deleteChannel(channelChangeEvent)
        } else if (channelChangeEvent.isMove) {
            moveChannel(channelChangeEvent)
        } else {
            addChannel(channelChangeEvent)
        }
        for (i in newsLikeChannelList.indices) {
            newsLikeChannelList[i].channelIndex = i
        }
        myPresenter!!.updateChannelDB(newsLikeChannelList, newsUnlikeChannelList)
    }

    private fun deleteChannel(channelChangeEvent: ChannelChangeEvent) {
        channelManageLikeAdapter!!.notifyDataSetChanged()
        val newsChannel = channelChangeEvent.newsChannel
        if (newsChannel != null) {
            newsUnlikeChannelList.add(newsChannel)
            channelManageUnlikeAdapter!!.notifyItemInserted(newsUnlikeChannelList.size - 1)
            channelManageUnlikeAdapter!!.notifyItemChanged(newsUnlikeChannelList.size - 1)
            channelManageLikeAdapter!!.notifyItemRemoved(newsChannel.channelIndex)
            channelManageLikeAdapter!!.notifyItemRangeChanged(
                    newsChannel.channelIndex,
                    newsLikeChannelList.size - newsChannel.channelIndex
            )
            newsLikeChannelList.removeAt(newsChannel.channelIndex)
        }
    }

    private fun moveChannel(channelChangeEvent: ChannelChangeEvent) {
        val fromPosition = channelChangeEvent.fromPosition
        val toPosition = channelChangeEvent.toPosition
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
                Collections.swap(newsLikeChannelList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(newsLikeChannelList, i, i - 1)
            }
        }
        channelManageLikeAdapter!!.notifyItemMoved(fromPosition, toPosition)
    }

    private fun addChannel(channelChangeEvent: ChannelChangeEvent) {
        var newsChannel = channelChangeEvent.newsChannel
        if (newsChannel != null) {
            newsLikeChannelList.add(newsChannel)
            channelManageLikeAdapter!!.notifyItemInserted(newsLikeChannelList.size - 1)
            channelManageLikeAdapter!!.notifyItemChanged(newsLikeChannelList.size - 1)
            channelManageUnlikeAdapter!!.notifyItemRemoved(channelChangeEvent.changeChannelPosition)
            channelManageUnlikeAdapter!!.notifyItemRangeChanged(
                    channelChangeEvent.changeChannelPosition,
                    newsUnlikeChannelList.size - channelChangeEvent.changeChannelPosition
            )
            newsUnlikeChannelList.removeAt(channelChangeEvent.changeChannelPosition)
        }
    }
}
