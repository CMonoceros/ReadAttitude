package zjm.cst.dhu.newsmodule.inject.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import zjm.cst.dhu.library.inject.component.ApplicationComponent;
import zjm.cst.dhu.library.inject.scope.PerActivity;
import zjm.cst.dhu.library.inject.scope.PerContext;
import zjm.cst.dhu.newsmodule.inject.module.ActivityModule;

/**
 * Created by zjm on 2017/5/9.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    @PerContext("Activity")
    Context getActivityContext();

    @PerContext("Application")
    Context getApplicationContext();

    Activity getActivity();

}
