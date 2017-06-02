package zjm.cst.dhu.readattitude.mvp.ui.activity

import android.Manifest
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import zjm.cst.dhu.readattitude.mvp.ui.adapter.GuidePagerAdapter
import zjm.cst.dhu.library.utils.data.DatabaseUtil
import zjm.cst.dhu.library.utils.data.SharedPreferencesUtil
import zjm.cst.dhu.library.utils.others.PermissionUtil
import zjm.cst.dhu.readattitude.R
import zjm.cst.dhu.readattitude.databinding.ActivityGuideMainBinding

import zjm.cst.dhu.library.Constant

/**
 * Created by zjm on 2017/5/9.
 */

class GuideActivity
    : AppCompatActivity() {

    private var activityGuideMainBinding: ActivityGuideMainBinding? = null
    private var vp_guide_main: ViewPager? = null
    private var guidePagerAdapter: GuidePagerAdapter? = null
    private var onPageChangeListener: ViewPager.OnPageChangeListener? = null
    private val viewPaperItemList = ArrayList<View>()
    private val guideImageViewList = ArrayList<ImageView>()
    private var nowPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        judgeFirstTime()
        dataBinding()
        setupView()
        addGuidePoint()
        requestPermission()
    }

    private fun judgeFirstTime() {
        if (!SharedPreferencesUtil.isFirstTime) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            DatabaseUtil.setupDatabase()
            SharedPreferencesUtil.isFirstTime = false
        }
    }

    private fun dataBinding() {
        activityGuideMainBinding = DataBindingUtil.setContentView<ActivityGuideMainBinding>(this,
                R.layout.activity_guide_main)
        vp_guide_main = activityGuideMainBinding!!.vpGuideMain
    }

    private fun setupView() {
        val layoutInflater = LayoutInflater.from(this)
        viewPaperItemList.add(layoutInflater.inflate(R.layout.item_guide_1, null))
        viewPaperItemList.add(layoutInflater.inflate(R.layout.item_guide_2, null))
        guidePagerAdapter = GuidePagerAdapter(viewPaperItemList,this)
        vp_guide_main!!.adapter = guidePagerAdapter
        onPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                guideImageViewList[position].setBackgroundResource(R.drawable.guide_shape_retangle_select)
                guideImageViewList[nowPosition].setBackgroundResource(R.drawable.guide_shape_retangle_unselect)
                nowPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        }
        vp_guide_main!!.addOnPageChangeListener(onPageChangeListener)
    }

    private fun requestPermission() {
        val permissionList = ArrayList<String>()
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionList.add(Manifest.permission.READ_PHONE_STATE)
        PermissionUtil.requestPermission(this, permissionList)
    }

    private fun addGuidePoint() {
        val linearLayout = findViewById(R.id.ll_guide_point) as LinearLayout
        val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        for (i in viewPaperItemList.indices) {
            val imageView = ImageView(this)
            imageView.setBackgroundResource(R.drawable.guide_shape_retangle_unselect)
            params.leftMargin = Constant.GUIDE_POINT_MARGIN
            params.rightMargin = Constant.GUIDE_POINT_MARGIN
            linearLayout.addView(imageView, params)
            guideImageViewList.add(imageView)
        }
        guideImageViewList[nowPosition].setBackgroundResource(R.drawable.guide_shape_retangle_select)
    }
}
