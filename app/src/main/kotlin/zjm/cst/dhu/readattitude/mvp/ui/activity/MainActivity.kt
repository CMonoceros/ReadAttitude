package zjm.cst.dhu.readattitude.mvp.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import zjm.cst.dhu.library.Constant


import javax.inject.Inject

import zjm.cst.dhu.readattitude.mvp.presenter.MainPresenter
import zjm.cst.dhu.readattitude.mvp.ui.activity.base.BaseActivity
import zjm.cst.dhu.readattitude.mvp.ui.adapter.MainFragmentPagerAdapter
import zjm.cst.dhu.readattitude.mvp.view.MainContract
import zjm.cst.dhu.library.MyApplication
import zjm.cst.dhu.library.utils.others.Router
import zjm.cst.dhu.readattitude.mvp.ui.fragment.TestFragment1
import zjm.cst.dhu.readattitude.mvp.ui.fragment.TestFragment2
import zjm.cst.dhu.readattitude.R
import zjm.cst.dhu.readattitude.databinding.ActivityMainBinding
import zjm.cst.dhu.readattitude.databinding.ItemTablayoutBinding

/**
 * Created by zjm on 2017/5/9.
 */

class MainActivity
    : BaseActivity<MainPresenter>(), MainContract.View {

    @Inject
    lateinit var mainPresenter: MainPresenter

    private var activityMainBinding: ActivityMainBinding? = null
    private var vp_main: ViewPager? = null
    private var tl_main: TabLayout? = null

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitles = ArrayList<String>()
    private val itemTabLayoutBindingHashMap = HashMap<Int, ItemTablayoutBinding>()
    private var nowFragmentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTab()
    }

    override fun dataBinding() {
        activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)
        vp_main = activityMainBinding!!.vpMain
        tl_main = activityMainBinding!!.tlMain
    }

    override fun setupInjector() {
        myActivityComponent!!.inject(this)
    }

    override fun setupViews() {
        setupFragment()

        myPresenter = mainPresenter
        myPresenter!!.attachView(this)

        val mainFragmentPagerAdapter = MainFragmentPagerAdapter(
                supportFragmentManager, mFragmentTitles, mFragmentList
        )
        vp_main!!.adapter = mainFragmentPagerAdapter
        tl_main!!.setupWithViewPager(vp_main)
        vp_main!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                itemTabLayoutBindingHashMap[nowFragmentPosition]!!.setIsSelected(false)
                nowFragmentPosition = position
                itemTabLayoutBindingHashMap[position]!!.setIsSelected(true)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        vp_main!!.setCurrentItem(nowFragmentPosition, true)
    }

    private fun setupFragment() {
        mFragmentList.add(Router.getFragment(Constant.NEWS_BAR_FRAGMENT_NAME)!!)
        mFragmentTitles.add(Constant.NEWS_BAR_FRAGMENT_TITTLE)

//        mFragmentList.add(TestFragment1());
//        mFragmentList.add(TestFragment2());
//
//        mFragmentTitles.add("test1");
//        mFragmentTitles.add("test2");
    }

    private fun setupTab() {
        val tabTittleList = MyApplication.context!!.getResources()
                .getStringArray(R.array.tab_item_tittle)
        val tabResourceArray = MyApplication.context!!.getResources()
                .obtainTypedArray(R.array.tab_item_resource_id)
        for (i in mFragmentList.indices) {
            val tab = tl_main!!.getTabAt(i)
            if (tab != null) {
                tab.setCustomView(R.layout.item_tablayout)
                val itemTabLayoutBinding = DataBindingUtil.bind<ItemTablayoutBinding>(tab.customView)
                itemTabLayoutBinding.tvTabItem.text = tabTittleList.get(i)
                itemTabLayoutBinding.ivTabItem.setImageResource(tabResourceArray.getResourceId(i, 0))
                itemTabLayoutBinding.isSelected = false
                itemTabLayoutBindingHashMap.put(i, itemTabLayoutBinding)
            }
        }
        tabResourceArray.recycle()
        itemTabLayoutBindingHashMap[nowFragmentPosition]!!.isSelected = false
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
