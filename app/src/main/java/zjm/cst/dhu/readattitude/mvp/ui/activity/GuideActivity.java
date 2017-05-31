package zjm.cst.dhu.readattitude.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import zjm.cst.dhu.library.utils.DatabaseUtil;
import zjm.cst.dhu.library.utils.PermissionUtil;
import zjm.cst.dhu.library.utils.SharedPreferencesUtil;
import zjm.cst.dhu.readattitude.R;
import zjm.cst.dhu.readattitude.databinding.ActivityGuideMainBinding;
import zjm.cst.dhu.readattitude.mvp.ui.adapter.GuidePagerAdapter;

import static zjm.cst.dhu.library.Constant.GUIDE_POINT_MARGIN;

/**
 * Created by zjm on 2017/5/9.
 */

public class GuideActivity extends AppCompatActivity {

    private ActivityGuideMainBinding activityGuideMainBinding;
    private ViewPager vp_guide_main;
    private GuidePagerAdapter guidePagerAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private List<View> viewPaperItemList = new ArrayList<>();
    private List<ImageView> guideImageViewList = new ArrayList<>();
    private int nowPosition = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        judgeFirstTime();
        dataBinding();
        setupView();
        addGuidePoint();
        requestPermission();
    }

    private void judgeFirstTime() {
        if (!SharedPreferencesUtil.isFirstTime()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            DatabaseUtil.setupDatabase();
            SharedPreferencesUtil.setFirstTime(false);
        }
    }

    private void dataBinding() {
        activityGuideMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_guide_main);
        vp_guide_main = activityGuideMainBinding.vpGuideMain;
    }

    private void setupView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        viewPaperItemList.add(layoutInflater.inflate(R.layout.item_guide_1, null));
        viewPaperItemList.add(layoutInflater.inflate(R.layout.item_guide_2, null));
        guidePagerAdapter = new GuidePagerAdapter(viewPaperItemList, this);
        vp_guide_main.setAdapter(guidePagerAdapter);
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                guideImageViewList.get(position).setBackgroundResource(R.drawable.guide_shape_retangle_select);
                guideImageViewList.get(nowPosition).setBackgroundResource(R.drawable.guide_shape_retangle_unselect);
                nowPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        vp_guide_main.addOnPageChangeListener(onPageChangeListener);
    }

    private void requestPermission() {
        List<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        PermissionUtil.requestPermission(this, permissionList);
    }

    private void addGuidePoint() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_guide_point);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < viewPaperItemList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.guide_shape_retangle_unselect);
            params.leftMargin = GUIDE_POINT_MARGIN;
            params.rightMargin = GUIDE_POINT_MARGIN;
            linearLayout.addView(imageView, params);
            guideImageViewList.add(imageView);
        }
        guideImageViewList.get(nowPosition).setBackgroundResource(R.drawable.guide_shape_retangle_select);
    }
}
