package zjm.cst.dhu.newsmodule.mvp.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.mvp.model.NewsData
import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsCardBinding
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsFooterBinding
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsItemNewsHeaderBinding
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsListFragment

/**
 * Created by zjm on 2017/5/10.
 */

class NewsListAdapter(private val newsListFragment: NewsListFragment,
                      private val newsDataList: List<NewsData>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mOnItemClickListener: OnNewsListItemClickListener? = null
    private var headerOnPageChangeListener: ViewPager.OnPageChangeListener? = null
    private var headerNowPosition = 0
    private val headerViewPaperItemList = ArrayList<View>()
    private val headerGuideImageViewList = ArrayList<ImageView>()
    private var showFooter = false

    interface OnNewsListItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder? =
            when (viewType) {
                Constant.NEWS_LIST_TYPE_HEADER ->
                    HolderHeader(LayoutInflater.from(parent.context).
                            inflate(R.layout.module_news_item_news_header, parent, false))
                Constant.NEWS_LIST_TYPE_CARD ->
                    HolderCard(LayoutInflater.from(parent.context).
                            inflate(R.layout.module_news_item_news_card, parent, false))
                Constant.NEWS_LIST_TYPE_FOOTER ->
                    HolderFooter(LayoutInflater.from(parent.context).
                            inflate(R.layout.module_news_item_news_footer, parent, false))
                else -> null
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderHeader -> bindHeader(holder)
            is HolderCard -> bindCard(holder, position)
            is HolderFooter -> bindFooter(holder)
        }
    }

    private fun bindHeader(holderHeader: HolderHeader) {
        setupHeaderView(holderHeader, setupHeaderValue(holderHeader))
    }

    private fun setupHeaderValue(holderHeader: HolderHeader):
            List<NewsData.Ad> {
        holderHeader.module_news_vp_news_header.removeOnPageChangeListener(headerOnPageChangeListener)
        holderHeader.moduleNewsItemNewsHeaderBinding.moduleNewsLlGuidePoint.removeAllViews()
        headerGuideImageViewList.clear()
        headerViewPaperItemList.clear()
        val adList = newsDataList[0].ads
        headerNowPosition = 0
        val layoutInflater = LayoutInflater.from(newsListFragment.activity)
        for (i in adList!!.indices) {
            headerViewPaperItemList.add(layoutInflater.
                    inflate(R.layout.module_news_item_news_header_item, null))
        }
        return adList
    }

    private fun setupHeaderView(holderHeader: HolderHeader, adList: List<NewsData.Ad>) {
        headerOnPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                headerGuideImageViewList[position].setBackgroundResource(
                        R.drawable.module_news_guide_shape_retangle_select)
                headerGuideImageViewList[headerNowPosition].setBackgroundResource(
                        R.drawable.module_news_guide_shape_retangle_unselect)
                headerNowPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        }
        val newsListHeaderAdapter = NewsListHeaderAdapter(newsListFragment, headerViewPaperItemList, adList)
        holderHeader.module_news_vp_news_header.adapter = newsListHeaderAdapter
        holderHeader.module_news_vp_news_header.addOnPageChangeListener(headerOnPageChangeListener)
        setupHeaderGuide(holderHeader)
    }

    private fun setupHeaderGuide(holderHeader: HolderHeader) {
        val moduleNewsLlGuidePoint = holderHeader.moduleNewsItemNewsHeaderBinding.moduleNewsLlGuidePoint
        val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        for (i in headerViewPaperItemList.indices) {
            val imageView = ImageView(newsListFragment.activity)
            imageView.setBackgroundResource(R.drawable.module_news_guide_shape_retangle_unselect)
            params.leftMargin = Constant.GUIDE_POINT_MARGIN
            params.rightMargin = Constant.GUIDE_POINT_MARGIN
            moduleNewsLlGuidePoint.addView(imageView, params)
            headerGuideImageViewList.add(imageView)
        }
        headerGuideImageViewList[headerNowPosition].setBackgroundResource(
                R.drawable.module_news_guide_shape_retangle_select)
    }

    private fun bindCard(holderCard: HolderCard, position: Int) {
        holderCard.moduleNewsItemNewsCardBinding.newsData = newsDataList[position]
        Glide.with(newsListFragment).load(newsDataList[position].imgsrc)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holderCard.module_news_iv_news_card)
        holderCard.moduleNewsItemNewsCardBinding.onClick = View.OnClickListener { v ->
            mOnItemClickListener!!.onItemClick(v, position)
        }
    }

    private fun bindFooter(holderFooter: HolderFooter) {
        holderFooter.moduleNewsItemNewsFooterBinding.isVisibility = showFooter
    }

    fun setShowFooter(showFooter: Boolean) {
        this.showFooter = showFooter
    }

    fun setOnItemClickListener(onItemClickListener: OnNewsListItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }


    override fun getItemViewType(position: Int):
            Int = when {
        position == Constant.NEWS_LIST_HEADER_POSITION &&
                newsDataList.size > 0 && newsDataList[0].ads != null ->
            Constant.NEWS_LIST_TYPE_HEADER
        position == itemCount - 1 ->
            Constant.NEWS_LIST_TYPE_FOOTER
        else ->
            Constant.NEWS_LIST_TYPE_CARD
    }


    override fun getItemCount():
            Int = newsDataList.size + 1

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView!!.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int):
                        Int {
                    val type = getItemViewType(position)
                    when (type) {
                        Constant.NEWS_LIST_TYPE_HEADER, Constant.NEWS_LIST_TYPE_CARD,
                        Constant.NEWS_LIST_TYPE_FOOTER ->
                            return manager.spanCount
                        else ->
                            return manager.spanCount / 2
                    }
                }
            }
        }
    }


    inner class HolderHeader(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        var moduleNewsItemNewsHeaderBinding: ModuleNewsItemNewsHeaderBinding
                = DataBindingUtil.bind<ModuleNewsItemNewsHeaderBinding>(itemView)
        var module_news_vp_news_header: ViewPager

        init {
            module_news_vp_news_header = moduleNewsItemNewsHeaderBinding.moduleNewsVpNewsHeader
        }
    }

    inner class HolderCard(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        var moduleNewsItemNewsCardBinding: ModuleNewsItemNewsCardBinding
                = DataBindingUtil.bind<ModuleNewsItemNewsCardBinding>(itemView)
        var module_news_iv_news_card: ImageView

        init {
            module_news_iv_news_card = moduleNewsItemNewsCardBinding.moduleNewsIvNewsCard
        }
    }

    inner class HolderFooter(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        var moduleNewsItemNewsFooterBinding: ModuleNewsItemNewsFooterBinding
                = DataBindingUtil.bind<ModuleNewsItemNewsFooterBinding>(itemView)
    }
}
