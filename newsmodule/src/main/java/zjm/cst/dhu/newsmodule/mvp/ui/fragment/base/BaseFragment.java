package zjm.cst.dhu.newsmodule.mvp.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.MyApplication;
import zjm.cst.dhu.library.mvp.presenter.BasePresenter;
import zjm.cst.dhu.newsmodule.inject.component.DaggerFragmentComponent;
import zjm.cst.dhu.newsmodule.inject.component.FragmentComponent;
import zjm.cst.dhu.newsmodule.inject.module.FragmentModule;

/**
 * Created by zjm on 2017/5/10.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected T myPresenter;
    protected FragmentComponent myFragmentComponent;

    public abstract void dataBinding(View rootView);

    public abstract void setupInjector();

    public abstract void setupViews(View rootView);

    public abstract int getFragmentId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentComponent();
        setupInjector();
    }

    private void setupFragmentComponent() {
        myFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((MyApplication) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        if (myPresenter != null) {
            myPresenter.detachView();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentId(), container, false);
        dataBinding(rootView);
        setupViews(rootView);
        return rootView;
    }
}
