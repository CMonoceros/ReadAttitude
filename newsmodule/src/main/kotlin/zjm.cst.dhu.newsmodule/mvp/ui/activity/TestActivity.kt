package zjm.cst.dhu.newsmodule.mvp.ui.activity

import android.Manifest
import android.nfc.Tag
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import zjm.cst.dhu.library.utils.data.DatabaseUtil
import zjm.cst.dhu.library.utils.data.SharedPreferencesUtil
import zjm.cst.dhu.library.utils.others.PermissionUtil

import zjm.cst.dhu.newsmodule.R
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsBarFragment

/**
 * Created by zjm on 2017/5/10.
 */

class TestActivity
    : AppCompatActivity() {
    private var fm_menu_main: FragmentManager=supportFragmentManager
    private var ft_menu_main: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_news_activity)
        requestPermission()
        if (!SharedPreferencesUtil.isFirstTime) {
            Log.i(TAG,"Not first time")
        } else {
            Log.i(TAG,"First time")
            DatabaseUtil.setupDatabase()
            SharedPreferencesUtil.isFirstTime=false
        }
        ft_menu_main = fm_menu_main.beginTransaction()
        ft_menu_main!!.add(R.id.module_news_fl_activity, NewsBarFragment())
        ft_menu_main!!.commit()
    }

    private fun requestPermission() {
        val permissionList = ArrayList<String>()
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionList.add(Manifest.permission.READ_PHONE_STATE)
        PermissionUtil.requestPermission(this, permissionList)
    }

    companion object{
        private val TAG=TestActivity::class.java.name
    }
}
