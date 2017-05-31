package zjm.cst.dhu.readattitude.mvp.view;

import zjm.cst.dhu.library.mvp.presenter.BasePresenter;
import zjm.cst.dhu.library.mvp.view.base.BaseView;

/**
 * Created by zjm on 2017/5/9.
 */

public interface MainContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

    }
}
