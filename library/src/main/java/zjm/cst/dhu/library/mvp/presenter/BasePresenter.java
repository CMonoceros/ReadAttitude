package zjm.cst.dhu.library.mvp.presenter;

import rx.subscriptions.CompositeSubscription;
import zjm.cst.dhu.library.mvp.view.base.BaseView;

/**
 * Created by zjm on 2017/5/9.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T BaseView);

    void detachView();
}
