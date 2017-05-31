package zjm.cst.dhu.library.inject.component;

import android.content.Context;

import dagger.Component;
import zjm.cst.dhu.library.inject.module.ApplicationModule;
import zjm.cst.dhu.library.inject.scope.PerApplication;
import zjm.cst.dhu.library.inject.scope.PerContext;

/**
 * Created by zjm on 2017/5/9.
 */

@PerApplication
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @PerContext("Application")
    Context getApplicationContext();
}
