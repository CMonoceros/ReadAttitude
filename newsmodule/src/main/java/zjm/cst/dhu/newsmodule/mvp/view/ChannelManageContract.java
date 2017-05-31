package zjm.cst.dhu.newsmodule.mvp.view;

import java.util.List;

import zjm.cst.dhu.library.event.ChannelChangeEvent;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.mvp.presenter.BasePresenter;
import zjm.cst.dhu.library.mvp.view.base.BaseView;

/**
 * Created by zjm on 2017/5/12.
 */

public interface ChannelManageContract {
    interface View extends BaseView {
        void loadChannelSuccess(List<NewsChannel> like, List<NewsChannel> unlike);

        void loadChannelError();

        void channelChange(ChannelChangeEvent channelChangeEvent);
    }

    interface Presenter extends BasePresenter<View> {
        void loadChannelList();

        void updateChannelDB(List<NewsChannel> like, List<NewsChannel> unlike);
    }
}
