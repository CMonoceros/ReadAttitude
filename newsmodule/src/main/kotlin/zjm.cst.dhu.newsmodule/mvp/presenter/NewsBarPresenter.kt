package zjm.cst.dhu.newsmodule.mvp.presenter

import java.io.File
import java.io.FileInputStream
import java.io.IOException

import javax.inject.Inject

import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.utils.data.DatabaseUtil
import zjm.cst.dhu.newsmodule.mvp.view.NewsBarContract
import java.nio.charset.Charset

/**
 * Created by zjm on 2017/5/10.
 */

class NewsBarPresenter @Inject constructor()
    : NewsBarContract.Presenter {
    private var myView: NewsBarContract.View? = null
    private var myCompositeSubscription: CompositeSubscription? = null

    override fun attachView(BaseView: NewsBarContract.View) {
        myView = BaseView
        myCompositeSubscription = CompositeSubscription()
    }

    override fun detachView() {
        if (myCompositeSubscription != null && myCompositeSubscription!!.isUnsubscribed) {
            myCompositeSubscription!!.unsubscribe()
        }
    }

    override fun loadChannel() {
        val subscription = Observable.create<List<NewsChannel>?> { subscriber ->
            subscriber.onNext(DatabaseUtil.likeChannel)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<NewsChannel>?> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        myView!!.loadChannelError()
                    }

                    override fun onNext(newsChannels: List<NewsChannel>?) {
                        myView!!.loadChannelSuccess(newsChannels)
                    }
                })
        myCompositeSubscription!!.add(subscription)
    }

    override fun readCrashLog() {
        val subscription = Observable.create(Observable.OnSubscribe<String> { subscriber ->
            val path = File(Constant.CRASH_LOG_FILE_PATH)
            if (path.exists()) {
                val file = path.listFiles()
                var str = ""
                for (i in file!!.indices) {
                    try {
                        str += "${file[i].getName()}  ----------start---------- "
                        val `in` = FileInputStream(file[i])
                        val size = `in`.available()
                        val buffer = ByteArray(size)
                        `in`.read(buffer)
                        `in`.close()
                        str += "${String(bytes = buffer, charset = Charsets.UTF_8)} " +
                                "----------end---------- "
                    } catch (e: IOException) {
                        e.printStackTrace()
                        subscriber.onError(e)
                    }
                }
                subscriber.onNext(str)
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {}

                    override fun onNext(s: String) {
                        myView!!.uploadCrashLog(s)
                    }
                })
        myCompositeSubscription!!.add(subscription)
    }
}
