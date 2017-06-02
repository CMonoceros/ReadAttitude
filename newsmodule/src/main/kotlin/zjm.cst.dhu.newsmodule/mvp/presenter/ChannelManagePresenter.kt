package zjm.cst.dhu.newsmodule.mvp.presenter

import javax.inject.Inject

import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.event.ChannelChangeEvent
import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.utils.data.DatabaseUtil
import zjm.cst.dhu.library.utils.others.RxBus
import zjm.cst.dhu.newsmodule.mvp.view.ChannelManageContract

/**
 * Created by zjm on 2017/5/12.
 */

class ChannelManagePresenter @Inject constructor()
    : ChannelManageContract.Presenter {
    private var myView: ChannelManageContract.View? = null
    private var myCompositeSubscription: CompositeSubscription? = null

    override fun attachView(BaseView: ChannelManageContract.View) {
        myView = BaseView
        myCompositeSubscription = CompositeSubscription()
    }

    override fun detachView() {
        if (myCompositeSubscription != null && myCompositeSubscription!!.isUnsubscribed) {
            myCompositeSubscription!!.unsubscribe()
        }
    }

    override fun loadChannelList() {
        val subscription = Observable.create<Map<Int, List<NewsChannel>?>> { subscriber ->
            val map = HashMap<Int, List<NewsChannel>?>()
            map.put(Constant.CHANNEL_LIST_LIKE, DatabaseUtil.likeChannel)
            map.put(Constant.CHANNEL_LIST_UNLIKE, DatabaseUtil.unlikeChannel)
            subscriber.onNext(map)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Map<Int, List<NewsChannel>?>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        myView!!.loadChannelError()
                    }

                    override fun onNext(map: Map<Int, List<NewsChannel>?>) {
                        myView!!.loadChannelSuccess(map[Constant.CHANNEL_LIST_LIKE],
                                map[Constant.CHANNEL_LIST_UNLIKE])
                        subscribeChannelChangeEvent()
                    }
                })
        myCompositeSubscription!!.add(subscription)
    }

    override fun updateChannelDB(like: List<NewsChannel>?, unlike: List<NewsChannel>?) {
        val subscription = Observable.create<Map<Int, List<NewsChannel>?>> { subscriber ->
            val map = HashMap<Int, List<NewsChannel>?>()
            map.put(Constant.CHANNEL_LIST_LIKE, like)
            map.put(Constant.CHANNEL_LIST_UNLIKE, unlike)
            subscriber.onNext(map)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { integerListMap ->
                    DatabaseUtil.updateChannel(integerListMap)
                }
        myCompositeSubscription!!.add(subscription)
    }

    private fun subscribeChannelChangeEvent() {
        val subscription = RxBus.default.toObservable(ChannelChangeEvent::class.java)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ channelChangeEvent ->
                    myView!!.channelChange(channelChangeEvent)
                })
        myCompositeSubscription!!.add(subscription)
    }


}
