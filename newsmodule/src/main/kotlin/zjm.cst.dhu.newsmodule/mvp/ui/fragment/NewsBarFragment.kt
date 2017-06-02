package zjm.cst.dhu.newsmodule.mvp.ui.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.event.FloatingActionButtonClickEvent
import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.utils.others.RxBus
import zjm.cst.dhu.library.utils.ui.ViewsUtil

import javax.inject.Inject
import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentNewsBarBinding
import zjm.cst.dhu.newsmodule.mvp.presenter.NewsBarPresenter
import zjm.cst.dhu.newsmodule.mvp.ui.activity.ChannelManageActivity
import zjm.cst.dhu.newsmodule.mvp.ui.adapter.NewsListFragmentPagerAdapter
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment
import zjm.cst.dhu.newsmodule.mvp.view.NewsBarContract

/**
 * Created by zjm on 2017/5/10.
 */

class NewsBarFragment
    : BaseFragment<NewsBarPresenter>(), NewsBarContract.View {
    @Inject
    lateinit var newsBarPresenter: NewsBarPresenter

    private var moduleNewsFragmentNewsBarBinding: ModuleNewsFragmentNewsBarBinding? = null
    private var newsListFragmentPagerAdapter: NewsListFragmentPagerAdapter? = null

    private var module_news_tl_news_channel: TabLayout? = null
    private var module_news_iv_channel_add: ImageView? = null
    private var module_news_vp_news_list: ViewPager? = null
    private var module_news_tb_list: Toolbar? = null
    private var module_news_fab_list: FloatingActionButton? = null

    private val newsListTittleList = ArrayList<String>()
    private val newsChannelList = ArrayList<NewsChannel>()
    private val newsFragmentList = ArrayList<Fragment>()
    private var nowPosition = 0

    override fun dataBinding(rootView: View) {
        moduleNewsFragmentNewsBarBinding =
                DataBindingUtil.bind<ModuleNewsFragmentNewsBarBinding>(rootView)
        module_news_iv_channel_add = moduleNewsFragmentNewsBarBinding!!.moduleNewsIvChannelAdd
        module_news_tl_news_channel = moduleNewsFragmentNewsBarBinding!!.moduleNewsTlNewsChannel
        module_news_vp_news_list = moduleNewsFragmentNewsBarBinding!!.moduleNewsVpNewsList
        module_news_fab_list = moduleNewsFragmentNewsBarBinding!!.moduleNewsFabList
        module_news_tb_list = moduleNewsFragmentNewsBarBinding!!.moduleNewsTbList

        module_news_fab_list!!.setOnClickListener {
            RxBus.default.post(FloatingActionButtonClickEvent(isClick = true))
        }
        module_news_iv_channel_add!!.setOnClickListener {
            val intent = Intent(activity, ChannelManageActivity::class.java)
            startActivity(intent)
        }

    }

    override fun setupInjector() {
        myFragmentComponent!!.inject(this)
    }

    override fun setupViews(rootView: View) {
        myPresenter = newsBarPresenter
        myPresenter!!.attachView(this)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(module_news_tb_list)
    }

    override fun onResume() {
        super.onResume()
        myPresenter!!.loadChannel()
    }

    override val fragmentId: Int
        get() = R.layout.module_news_fragment_news_bar

    override fun loadChannelSuccess(list: List<NewsChannel>?) {
        if (list != null) {
            val restoreStateMap = setupRestoreStateMap(list)
            newsFragmentList.clear()
            newsChannelList.clear()
            newsListTittleList.clear()
            newsChannelList.addAll(list)
            addNewsListFragment(list)
            setupViewPaper(restoreStateMap)
        }
    }

    private fun setupRestoreStateMap(list: List<NewsChannel>):
            Map<Int, Int> {
        val restoreStateMap = HashMap<Int, Int>()
        if (newsChannelList.size != 0) {
            for (i in newsChannelList.indices) {
                list.indices
                        .filter { newsChannelList[i].channelName.equals(list[it].channelName) }
                        .forEach { restoreStateMap.put(i, it) }
            }
        }
        return restoreStateMap
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun loadChannelError() {
        showSnackbarMessage(Constant.NEWS_BAR_LOAD_CHANNEL_ERROR)
        myPresenter!!.loadChannel()
    }

    override fun showSnackbarMessage(message: String) {
        Snackbar.make(module_news_fab_list!!, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun uploadCrashLog(log: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, Constant.CRASH_LOG_UPLOAD_TITTLE)
        intent.putExtra(Intent.EXTRA_TEXT, log)
        startActivity(intent)
    }


    private fun addNewsListFragment(list: List<NewsChannel>) {
        for (i in list.indices) {
            val newsListFragment = NewsListFragment()
            newsListFragment.setupValue(list[i].channelId, list[i].channelType)
            newsFragmentList.add(newsListFragment)
            newsListTittleList.add(list[i].channelName)
        }
    }

    private fun setupViewPaper(restoreStateMap: Map<Int, Int>) {
        newsListFragmentPagerAdapter = NewsListFragmentPagerAdapter(childFragmentManager,
                newsFragmentList, newsListTittleList)
        newsListFragmentPagerAdapter!!.setRestoreStateMap(restoreStateMap)
        module_news_vp_news_list!!.adapter = newsListFragmentPagerAdapter
        module_news_vp_news_list!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                nowPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        module_news_tl_news_channel!!.setupWithViewPager(module_news_vp_news_list)
        ViewsUtil.dynamicSetTabLayoutMode(module_news_tl_news_channel!!)
        module_news_vp_news_list!!.setCurrentItem(nowPosition, true)
    }

    override fun onOptionsItemSelected(item: MenuItem?):
            Boolean {
        when (item!!.itemId) {
            R.id.module_news_menu_upload_crash -> {
                myPresenter!!.readCrashLog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.module_news_fragment_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
