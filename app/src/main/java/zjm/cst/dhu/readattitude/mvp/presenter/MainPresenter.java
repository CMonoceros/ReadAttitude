package zjm.cst.dhu.readattitude.mvp.presenter;

import javax.inject.Inject;

import zjm.cst.dhu.readattitude.mvp.view.MainContract;

/**
 * Created by zjm on 2017/5/9.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View myView;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainContract.View BaseView) {
        myView = BaseView;
    }

    @Override
    public void detachView() {

    }
}
