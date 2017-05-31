package zjm.cst.dhu.newsmodule.inject.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import zjm.cst.dhu.library.inject.component.ApplicationComponent;
import zjm.cst.dhu.library.inject.scope.PerContext;
import zjm.cst.dhu.library.inject.scope.PerFragment;
import zjm.cst.dhu.newsmodule.inject.module.FragmentModule;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.ChannelManageFragment;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsBarFragment;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsInfFragment;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsListFragment;

/**
 * Created by zjm on 2017/5/10.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @PerContext("Activity")
    Context getActivityContext();

    @PerContext("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsBarFragment newsBarFragment);

    void inject(NewsListFragment newsListFragment);

    void inject(ChannelManageFragment channelManageFragment);

    void inject(NewsInfFragment newsInfFragment);
}
