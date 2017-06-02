package zjm.cst.dhu.library.utils.ui

import android.support.design.widget.TabLayout
import zjm.cst.dhu.library.MyApplication

/**
 * Created by zjm on 2017/5/10.
 */

object ViewsUtil {

    fun dynamicSetTabLayoutMode(tabLayout: TabLayout) {
        var tabWidth = 0
        for (i in 0..tabLayout.childCount - 1) {
            val view = tabLayout.getChildAt(i)
            view.measure(0, 0) // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.measuredWidth
        }
        val screenWidth = MyApplication.Companion.context!!.resources.displayMetrics.widthPixels

        if (tabWidth <= screenWidth) {
            tabLayout.tabMode = TabLayout.MODE_FIXED
        } else {
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        }
    }
}
