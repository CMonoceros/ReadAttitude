package zjm.cst.dhu.newsmodule.mvp.view;

import zjm.cst.dhu.library.mvp.model.NewsInf;
import zjm.cst.dhu.library.mvp.presenter.BasePresenter;
import zjm.cst.dhu.library.mvp.view.base.BaseView;

/**
 * Created by zjm on 2017/5/14.
 */

public interface NewsInfContract {
    interface View extends BaseView {
        void loadNewsInfSuccess(NewsInf newsInf);

        void loadNewsInfError();
    }

    interface Presenter extends BasePresenter<View> {

        void loadNewsInf(String postId);
    }
}
