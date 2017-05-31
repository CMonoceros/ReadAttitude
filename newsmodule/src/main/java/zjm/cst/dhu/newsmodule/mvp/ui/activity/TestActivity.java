package zjm.cst.dhu.newsmodule.mvp.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import zjm.cst.dhu.library.utils.DatabaseUtil;
import zjm.cst.dhu.library.utils.PermissionUtil;
import zjm.cst.dhu.library.utils.SharedPreferencesUtil;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsBarFragment;

/**
 * Created by zjm on 2017/5/10.
 */

public class TestActivity extends AppCompatActivity {
    private FragmentManager fm_menu_main;
    private FragmentTransaction ft_menu_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_news_activity);
        requestPermission();
        if (!SharedPreferencesUtil.isFirstTime()) {
            System.out.println("!!!!not first time!!!!");
        } else {
            System.out.println("!!!!first time!!!!");
            DatabaseUtil.setupDatabase();
            SharedPreferencesUtil.setFirstTime(false);
        }
        fm_menu_main = getSupportFragmentManager();
        ft_menu_main = fm_menu_main.beginTransaction();
        NewsBarFragment newsBarFragment = new NewsBarFragment();
        ft_menu_main.add(R.id.module_news_fl_activity, newsBarFragment);
        ft_menu_main.commit();
    }

    private void requestPermission() {
        List<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        PermissionUtil.requestPermission(this, permissionList);
    }
}
