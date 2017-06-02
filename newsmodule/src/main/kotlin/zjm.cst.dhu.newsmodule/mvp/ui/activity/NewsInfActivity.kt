package zjm.cst.dhu.newsmodule.mvp.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import zjm.cst.dhu.library.Constant

import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsInfFragment

/**
 * Created by zjm on 2017/5/14.
 */

class NewsInfActivity
    : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_news_activity)
        val newsInfFragment = NewsInfFragment()
        val bundle = Bundle()
        bundle.putString(Constant.NEWS_INF_POST_ID, intent.extras.getString(Constant.NEWS_INF_POST_ID))
        bundle.putString(Constant.NEWS_INF_IMG_BASE, intent.extras.getString(Constant.NEWS_INF_IMG_BASE))
        newsInfFragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.module_news_fl_activity, newsInfFragment)
        fragmentTransaction.commit()
    }
}
