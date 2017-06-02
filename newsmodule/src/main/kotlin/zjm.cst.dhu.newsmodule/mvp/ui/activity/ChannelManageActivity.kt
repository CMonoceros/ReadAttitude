package zjm.cst.dhu.newsmodule.mvp.ui.activity

import android.support.v4.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.ChannelManageFragment

/**
 * Created by zjm on 2017/5/14.
 */

class ChannelManageActivity
    : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_news_activity)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.module_news_fl_activity, ChannelManageFragment())
        fragmentTransaction.commit()
    }

}
