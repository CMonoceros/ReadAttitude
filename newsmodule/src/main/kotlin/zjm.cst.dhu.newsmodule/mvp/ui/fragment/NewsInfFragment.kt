package zjm.cst.dhu.newsmodule.mvp.ui.fragment

import android.databinding.DataBindingUtil
import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


import javax.inject.Inject

import zjm.cst.dhu.newsmodule.utils.UrlImageGetter
import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentNewsInfBinding
import zjm.cst.dhu.newsmodule.mvp.presenter.NewsInfPresenter
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment
import zjm.cst.dhu.newsmodule.mvp.view.NewsInfContract

import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.Html.FROM_HTML_MODE_LEGACY
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.mvp.model.NewsInf

/**
 * Created by zjm on 2017/5/14.
 */

class NewsInfFragment
    : BaseFragment<NewsInfPresenter>(), NewsInfContract.View {

    @Inject
    lateinit var newsInfPresenter: NewsInfPresenter

    private var moduleNewsFragmentNewsInfBinding: ModuleNewsFragmentNewsInfBinding? = null
    private var urlImageGetter: UrlImageGetter? = null

    private var module_news_tv_news_inf: TextView? = null
    private var module_news_tv_news_inf_date: TextView? = null
    private var module_news_iv_news_inf: ImageView? = null
    private var module_news_abl_inf: AppBarLayout? = null
    private var module_news_tb_inf: Toolbar? = null
    private var module_news_ctl_inf: CollapsingToolbarLayout? = null

    private var newsInf: NewsInf? = null
    private var postId: String? = null


    override fun dataBinding(rootView: View) {
        moduleNewsFragmentNewsInfBinding = DataBindingUtil.bind<ModuleNewsFragmentNewsInfBinding>(rootView)
        module_news_tv_news_inf = moduleNewsFragmentNewsInfBinding!!.moduleNewsTvNewsInf
        module_news_tv_news_inf_date = moduleNewsFragmentNewsInfBinding!!.moduleNewsTvNewsInfDate
        module_news_iv_news_inf = moduleNewsFragmentNewsInfBinding!!.moduleNewsIvNewsInf
        module_news_ctl_inf = moduleNewsFragmentNewsInfBinding!!.moduleNewsCtlInf
        module_news_abl_inf = moduleNewsFragmentNewsInfBinding!!.moduleNewsAblInf
        module_news_tb_inf = moduleNewsFragmentNewsInfBinding!!.moduleNewsTbInf
        moduleNewsFragmentNewsInfBinding!!.isLoading = true
        moduleNewsFragmentNewsInfBinding!!.isSuccess = true
        moduleNewsFragmentNewsInfBinding!!.reloadClickListener = View.OnClickListener {
            myPresenter!!.loadNewsInf(postId!!)
        }
    }

    override fun setupInjector() {
        myFragmentComponent!!.inject(this)
    }

    override fun setupViews(rootView: View) {
        postId = arguments.getString(Constant.NEWS_INF_POST_ID)
        myPresenter = newsInfPresenter
        myPresenter!!.attachView(this)
        myPresenter!!.loadNewsInf(postId!!)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(module_news_tb_inf)
    }

    override val fragmentId: Int
        get() = R.layout.module_news_fragment_news_inf

    override fun loadNewsInfSuccess(newsInf: NewsInf?) {
        moduleNewsFragmentNewsInfBinding!!.isLoading = false
        moduleNewsFragmentNewsInfBinding!!.isSuccess = true
        this.newsInf = newsInf
        module_news_ctl_inf!!.title = this.newsInf!!.title
        module_news_ctl_inf!!.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.colorWhite))
        module_news_ctl_inf!!.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.colorWhite))
        module_news_tv_news_inf_date!!.text = this.newsInf!!.source + " " + this.newsInf!!.ptime
        setupImageView()
        val imgList = this.newsInf!!.img
        setupTextView(imgList)
    }

    private fun setupImageView() {
        val imgSrc = arguments.getString(Constant.NEWS_INF_IMG_BASE)
        Glide.with(this@NewsInfFragment).load(imgSrc)
                .asBitmap()
                .placeholder(R.drawable.module_news_ic_loading)
                .error(R.drawable.module_news_ic_loading_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(module_news_iv_news_inf!!)
    }

    private fun setupTextView(imgList: List<NewsInf.ImgBean>?) {
        if (imgList != null && imgList.size > 1) {
            urlImageGetter = UrlImageGetter(module_news_tv_news_inf!!, newsInf!!.body!!, imgList.size, this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                module_news_tv_news_inf!!.text = Html.fromHtml(newsInf!!.body!!,
                        FROM_HTML_MODE_LEGACY, urlImageGetter, null)
            } else {
                module_news_tv_news_inf!!.text = Html.fromHtml(newsInf!!.body!!, urlImageGetter, null)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                module_news_tv_news_inf!!.text = Html.fromHtml(newsInf!!.body!!, FROM_HTML_MODE_COMPACT)
            } else {
                module_news_tv_news_inf!!.text = Html.fromHtml(newsInf!!.body!!)
            }
        }
    }

    override fun loadNewsInfError() {
        moduleNewsFragmentNewsInfBinding!!.isLoading = false
        moduleNewsFragmentNewsInfBinding!!.isSuccess = false
        showSnakeBarMessage(Constant.NEWS_INF_LOAD_ERROR)
    }

    private fun showSnakeBarMessage(message: String) {
        Snackbar.make(module_news_abl_inf!!, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?):
            Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
