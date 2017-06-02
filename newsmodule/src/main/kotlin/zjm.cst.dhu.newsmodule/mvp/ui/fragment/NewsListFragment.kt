package zjm.cst.dhu.newsmodule.mvp.ui.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.mvp.model.NewsData


import javax.inject.Inject

import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentNewsListBinding
import zjm.cst.dhu.newsmodule.mvp.presenter.NewsListPresenter
import zjm.cst.dhu.newsmodule.mvp.ui.activity.NewsInfActivity
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.NewsListAdapter
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment
import zjm.cst.dhu.newsmodule.mvp.view.NewsListContract

/**
 * Created by zjm on 2017/5/10.
 */

class NewsListFragment
    : BaseFragment<NewsListPresenter>(), NewsListContract.View {
    @Inject
    lateinit var newsListPresenter: NewsListPresenter

    private var module_news_srl_news_list: SwipeRefreshLayout? = null
    private var module_news_rv_news_list: RecyclerView? = null
    private var module_news_tv_news_list_empty: TextView? = null

    private var moduleNewsFragmentNewsListBinding: ModuleNewsFragmentNewsListBinding? = null
    private var newsListAdapter: NewsListAdapter? = null
    private var mNewsId: String? = null
    private var mNewsType: String? = null
    private val mNewsDataList = ArrayList<NewsData>()
    private var isInit = false
    private var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setupValue(id: String, type: String) {
        this.mNewsId = id
        this.mNewsType = type
    }

    override fun dataBinding(rootView: View) {
        moduleNewsFragmentNewsListBinding = DataBindingUtil.bind<ModuleNewsFragmentNewsListBinding>(rootView)
        module_news_srl_news_list = moduleNewsFragmentNewsListBinding!!.moduleNewsSrlNewsList
        module_news_rv_news_list = moduleNewsFragmentNewsListBinding!!.moduleNewsRvNewsList
        module_news_tv_news_list_empty = moduleNewsFragmentNewsListBinding!!.moduleNewsTvNewsListEmpty
        moduleNewsFragmentNewsListBinding!!.loadSuccess = true
        module_news_tv_news_list_empty!!.setOnClickListener {
            myPresenter!!.loadNewList()
            module_news_srl_news_list!!.isRefreshing = true
            moduleNewsFragmentNewsListBinding!!.loadSuccess = true
        }
    }

    override fun setupInjector() {
        myFragmentComponent!!.inject(this)
    }

    override fun setupViews(rootView: View) {
        myPresenter = newsListPresenter
        myPresenter!!.attachView(this)
        myPresenter!!.setValue(mNewsId!!, mNewsType!!)

        module_news_srl_news_list!!.setOnRefreshListener {
            myPresenter!!.loadNewList()
            module_news_srl_news_list!!.isRefreshing = true
        }

        module_news_rv_news_list!!.setHasFixedSize(true)
        module_news_rv_news_list!!.layoutManager = GridLayoutManager(module_news_rv_news_list!!.context,
                Constant.NEWS_LIST_GRIDLAYOUT_SPAN, GridLayoutManager.VERTICAL, false)
        newsListAdapter = NewsListAdapter(this, mNewsDataList)
        newsListAdapter!!.setOnItemClickListener(object : NewsListAdapter.OnNewsListItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(activity, NewsInfActivity::class.java)
                val bundle = Bundle()
                bundle.putString(Constant.NEWS_INF_POST_ID, mNewsDataList[position].postid)
                bundle.putString(Constant.NEWS_INF_IMG_BASE, mNewsDataList[position].imgsrc)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        module_news_rv_news_list!!.adapter = newsListAdapter
        module_news_rv_news_list!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView!!.layoutManager
                val lastVisibleItemPosition = (layoutManager as LinearLayoutManager)
                        .findLastVisibleItemPosition()
                val visibleItemCount = layoutManager.getChildCount()
                val totalItemCount = layoutManager.getItemCount()
                if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    myPresenter!!.loadNextList()
                    newsListAdapter!!.setShowFooter(true)
                    newsListAdapter!!.notifyItemChanged(mNewsDataList.size)
                }
            }
        })
        isInit = true
        lazyLoadData()
    }

    private fun lazyLoadData() {
        if (!isInit) {
            return
        }
        if (userVisibleHint && !isLoad) {
            isLoad = true
            myPresenter!!.loadNewList()
            module_news_srl_news_list!!.isRefreshing = true
        } else {
            if (isLoad) {

            }
        }
    }

    override val fragmentId: Int
        get() = R.layout.module_news_fragment_news_list

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        lazyLoadData()
    }

    override fun loadNewListSuccess(list: List<NewsData>) {
        module_news_srl_news_list!!.isRefreshing = false
        moduleNewsFragmentNewsListBinding!!.loadSuccess = true
        mNewsDataList.clear()
        mNewsDataList.addAll(list)
        newsListAdapter!!.notifyDataSetChanged()
    }

    override fun loadNewListError() {
        moduleNewsFragmentNewsListBinding!!.loadSuccess = false
        module_news_srl_news_list!!.isRefreshing = false
        val newsBarFragment = parentFragment as NewsBarFragment
        newsBarFragment.showSnackbarMessage(Constant.NEWS_LIST_LOAD_ERROR)
    }

    override fun loadNextListSuccess(list: List<NewsData>) {
        newsListAdapter!!.setShowFooter(false)
        newsListAdapter!!.notifyItemRemoved(mNewsDataList.size)
        mNewsDataList.addAll(list)
        newsListAdapter!!.notifyDataSetChanged()
    }

    override fun loadNextListError() {
        newsListAdapter!!.setShowFooter(false)
        newsListAdapter!!.notifyItemRemoved(mNewsDataList.size)
        val newsBarFragment = parentFragment as NewsBarFragment
        newsBarFragment.showSnackbarMessage(Constant.NEWS_LIST_LOAD_ERROR)
    }

    override fun floatingActionButtonClick() {
        module_news_rv_news_list!!.scrollToPosition(0)
    }

    companion object {

        fun newInstance(id: String, type: String):
                NewsListFragment {
            val newsListFragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(Constant.NEWS_LIST_FRAGMENT_BUNDLE_ID, id)
            bundle.putString(Constant.NEWS_LIST_FRAGMENT_BUNDLE_TYPE, type)
            newsListFragment.arguments = bundle
            return newsListFragment
        }
    }
}
