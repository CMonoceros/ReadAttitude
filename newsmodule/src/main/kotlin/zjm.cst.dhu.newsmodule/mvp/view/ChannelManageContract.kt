package zjm.cst.dhu.newsmodule.mvp.view


import zjm.cst.dhu.library.event.ChannelChangeEvent
import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.mvp.presenter.BasePresenter
import zjm.cst.dhu.library.mvp.view.base.BaseView

/**
 * Created by zjm on 2017/5/12.
 */

interface ChannelManageContract {
    interface View
        : BaseView {
        fun loadChannelSuccess(like: List<NewsChannel>?, unlike: List<NewsChannel>?)

        fun loadChannelError()

        fun channelChange(channelChangeEvent: ChannelChangeEvent)
    }

    interface Presenter
        : BasePresenter<View> {
        fun loadChannelList()

        fun updateChannelDB(like: List<NewsChannel>?, unlike: List<NewsChannel>?)
    }
}
