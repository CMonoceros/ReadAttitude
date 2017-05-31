package zjm.cst.dhu.newsmodule.inject.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import zjm.cst.dhu.library.inject.scope.PerActivity;
import zjm.cst.dhu.library.inject.scope.PerContext;
import zjm.cst.dhu.library.inject.scope.PerFragment;

/**
 * Created by zjm on 2017/5/10.
 */
@Module
public class FragmentModule {
    private Fragment myFragment;

    public FragmentModule(Fragment fragment) {
        this.myFragment = fragment;
    }

    @Provides
    @PerFragment
    @PerContext("Activity")
    public Context provideActivityContext() {
        return myFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return myFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return myFragment;
    }
}
