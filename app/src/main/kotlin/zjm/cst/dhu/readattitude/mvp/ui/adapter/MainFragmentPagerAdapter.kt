package zjm.cst.dhu.readattitude.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by zjm on 2017/5/9.
 */

class MainFragmentPagerAdapter(fm: FragmentManager,
                               private val mTitles: List<String>,
                               private val mFragmentList: List<Fragment>)
    : FragmentPagerAdapter(fm) {


    override fun getPageTitle(position: Int):
            CharSequence {
        return mTitles[position]
    }

    override fun getItem(position: Int):
            Fragment {
        return mFragmentList[position]
    }

    override fun getCount():
            Int {
        return mFragmentList.size
    }
}
