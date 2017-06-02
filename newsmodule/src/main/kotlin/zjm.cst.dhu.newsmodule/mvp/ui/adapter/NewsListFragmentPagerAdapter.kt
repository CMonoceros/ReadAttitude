package zjm.cst.dhu.newsmodule.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

import zjm.cst.dhu.library.utils.ui.RestoreFragmentStatePagerAdapter


/**
 * Created by zjm on 2017/5/10.
 */

class NewsListFragmentPagerAdapter(fm: FragmentManager, private val mNewsFragmentList: ArrayList<Fragment>,
                                   private val mTitles: ArrayList<String>)
    : RestoreFragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int):
            CharSequence = mTitles[position]

    override fun getItem(position: Int):
            Fragment = mNewsFragmentList[position]

    override fun getCount():
            Int = mNewsFragmentList.size
}
