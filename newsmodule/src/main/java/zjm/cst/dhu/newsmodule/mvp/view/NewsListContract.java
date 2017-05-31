package zjm.cst.dhu.newsmodule.mvp.view;

import java.util.List;

import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.mvp.model.NewsData;
import zjm.cst.dhu.library.mvp.presenter.BasePresenter;
import zjm.cst.dhu.library.mvp.view.base.BaseView;

/**
 * Created by zjm on 2017/5/10.
 */

public interface NewsListContract {
    interface View extends BaseView {
        void loadNewListSuccess(List<NewsData> list);

        void loadNewListError();

        void loadNextListSuccess(List<NewsData> list);

        void loadNextListError();

        void floatingActionButtonClick();
    }

    interface Presenter extends BasePresenter<View> {
        void setValue(String id,String type);

        void loadNewList();

        void loadNextList();
    }
}
