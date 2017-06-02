package zjm.cst.dhu.readattitude.mvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import zjm.cst.dhu.readattitude.mvp.ui.activity.MainActivity
import zjm.cst.dhu.readattitude.R

/**
 * Created by zjm on 2017/5/9.
 */

class GuidePagerAdapter(private val list: List<View>, private val context: Context)
    : PagerAdapter() {

    override fun getCount():
            Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int):
            Any {
        container.addView(list[position], 0)
        if (position == list.size - 1) {
            val button = container.findViewById(R.id.b_guide_start) as Button
            button.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }
        return list[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(list[position])
    }

    override fun isViewFromObject(view: View, `object`: Any):
            Boolean = (view === `object`)
}
