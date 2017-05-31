package zjm.cst.dhu.readattitude.inject.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import zjm.cst.dhu.library.inject.component.ApplicationComponent;
import zjm.cst.dhu.library.inject.scope.PerActivity;
import zjm.cst.dhu.library.inject.scope.PerContext;
import zjm.cst.dhu.readattitude.inject.module.ActivityModule;
import zjm.cst.dhu.readattitude.mvp.ui.activity.MainActivity;

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

    void inject(MainActivity activity);
}
