package zjm.cst.dhu.newsmodule.mvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.bumptech.glide.Glide

import zjm.cst.dhu.library.mvp.model.NewsData
import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsHeaderItemBinding
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsListFragment

/**
 * Created by zjm on 2017/5/10.
 */

class NewsListHeaderAdapter(private val newsListFragment: NewsListFragment,
                            private val viewList: List<View>, private val adList: List<NewsData.Ad>)
    : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int):
            Any {
        val view = viewList[position]
        container.addView(view, 0)
        val moduleNewsItemAdsBinding = DataBindingUtil.bind<ModuleNewsItemNewsHeaderItemBinding>(view)
        moduleNewsItemAdsBinding.ads = adList[position]
        Glide.with(newsListFragment).load(adList[position].imgsrc)
                .into(moduleNewsItemAdsBinding.moduleNewsIvNewsHeader)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewList[position])
    }

    override fun getCount():
            Int = viewList.size


    override fun isViewFromObject(view: View, `object`: Any):
            Boolean = (view === `object`)

}
