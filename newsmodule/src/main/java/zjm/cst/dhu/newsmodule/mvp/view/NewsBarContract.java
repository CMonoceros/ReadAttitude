package zjm.cst.dhu.newsmodule.mvp.view;

import android.view.View;

import java.util.List;

import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.mvp.model.NewsChannelDao;
import zjm.cst.dhu.library.mvp.presenter.BasePresenter;
import zjm.cst.dhu.library.mvp.view.base.BaseView;

/**
 * Created by zjm on 2017/5/10.
 */

public interface NewsBarContract {
    interface View extends BaseView {
        void loadChannelSuccess(List<NewsChannel> list);

        void loadChannelError();

        void showSnackbarMessage(String message);

        void uploadCrashLog(String log);
    }

    interface Presenter extends BasePresenter<View> {
        void loadChannel();

        void readCrashLog();
    }
}
