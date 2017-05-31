package zjm.cst.dhu.readattitude.mvp.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.MyApplication;
import zjm.cst.dhu.library.mvp.presenter.BasePresenter;
import zjm.cst.dhu.readattitude.inject.component.ActivityComponent;
import zjm.cst.dhu.readattitude.inject.component.DaggerActivityComponent;
import zjm.cst.dhu.readattitude.inject.module.ActivityModule;

/**
 * Created by zjm on 2017/5/9.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T myPresenter;
    protected ActivityComponent myActivityComponent;
    private long firstBackPressTime = 0;

    public abstract void dataBinding();

    public abstract void setupInjector();

    public abstract void setupViews();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent();
        dataBinding();
        setupInjector();
        setupViews();
    }

    private void setupActivityComponent() {
        myActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((MyApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
        if (myPresenter != null) {
            myPresenter.detachView();
        }
    }

    @Override
    public void onBackPressed() {
        long secondBackPressTime = System.currentTimeMillis();
        if (secondBackPressTime - firstBackPressTime > Constant.BACK_PRESS_TIME) {
            Toast.makeText(this, "Press the exit procedure again", Toast.LENGTH_SHORT).show();
            firstBackPressTime = secondBackPressTime;
        } else {
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
