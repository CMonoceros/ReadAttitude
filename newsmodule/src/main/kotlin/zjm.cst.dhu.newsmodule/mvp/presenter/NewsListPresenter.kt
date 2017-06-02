package zjm.cst.dhu.newsmodule.mvp.presenter

import javax.inject.Inject

import retrofit2.Retrofit
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func1
import rx.functions.Func2
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.api.repository.Repository
import zjm.cst.dhu.library.event.FloatingActionButtonClickEvent
import zjm.cst.dhu.library.mvp.model.NewsData
import zjm.cst.dhu.library.utils.network.RetrofitUtil
import zjm.cst.dhu.library.utils.others.RxBus
import zjm.cst.dhu.newsmodule.mvp.view.NewsListContract

/**
 * Created by zjm on 2017/5/10.
 */

class NewsListPresenter @Inject constructor()
    : NewsListContract.Presenter {
    private var myView: NewsListContract.View? = null
    private var myCompositeSubscription: CompositeSubscription? = null
    private var nowPosition = 0
    private var mNewsId: String? = null
    private var mNewsType: String? = null

    override fun attachView(BaseView: NewsListContract.View) {
        myView = BaseView
        myCompositeSubscription = CompositeSubscription()
    }

    override fun detachView() {
        if (myCompositeSubscription != null && myCompositeSubscription!!.isUnsubscribed) {
            myCompositeSubscription!!.unsubscribe()
        }
    }

    override fun setValue(id: String, type: String) {
        this.mNewsId = id
        this.mNewsType = type
        subscribeFloatingActionButtonClickEvent()
    }

    private fun subscribeFloatingActionButtonClickEvent() {
        val subscription = RxBus.default.toObservable(FloatingActionButtonClickEvent::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    myView!!.floatingActionButtonClick()
                })
        myCompositeSubscription!!.add(subscription)
    }

    override fun loadNewList() {
        nowPosition = 0
        if (mNewsId != null && mNewsType != null) {
            myCompositeSubscription!!.add(
                    prepareLoadList()
                            .subscribe(object : Observer<List<NewsData>> {
                                override fun onCompleted() {

                                }

                                override fun onError(e: Throwable) {
                                    e.printStackTrace()
                                    myView!!.loadNewListError()
                                }

                                override fun onNext(newsDataList: List<NewsData>) {
                                    myView!!.loadNewListSuccess(newsDataList)
                                    nowPosition += Constant.NEWS_LIST_REFRESH_ITEM_MEDIUM_COUNTS
                                }
                            })
            )
        }
    }

    override fun loadNextList() {
        if (mNewsId != null && mNewsType != null) {
            myCompositeSubscription!!.add(
                    prepareLoadList()
                            .subscribe(object : Observer<List<NewsData>> {
                                override fun onCompleted() {

                                }

                                override fun onError(e: Throwable) {
                                    e.printStackTrace()
                                    myView!!.loadNextListError()
                                }

                                override fun onNext(newsDataList: List<NewsData>) {
                                    myView!!.loadNextListSuccess(newsDataList)
                                    nowPosition += Constant.NEWS_LIST_REFRESH_ITEM_MEDIUM_COUNTS
                                }
                            })
            )
        }
    }

    private fun prepareLoadList():
            Observable<List<NewsData>> {
        val retrofit = RetrofitUtil.setupRetrofit(RetrofitUtil.setupOkHttpClient(),
                Constant.NETEASE_NEWS_BASE_URL)
        val repository = Repository(retrofit)
        val subscription = repository.getNewsList(mNewsType!!, mNewsId!!, nowPosition)
                .flatMap(Func1<Map<String, List<NewsData>>, Observable<NewsData>> { stringListMap ->
                    if (mNewsId!!.endsWith("5rex5Zyz")) {
                        return@Func1 Observable.from<NewsData>(stringListMap["深圳"])
                    }
                    Observable.from<NewsData>(stringListMap[mNewsId!!])
                })
                .distinct()
                .toSortedList(Func2<NewsData, NewsData, Int> { newsData, newsData2 ->
                    if (newsData.ads != null) {
                        return@Func2 -1
                    } else if (newsData2.ads != null) {
                        return@Func2 1
                    }
                    val t2 = newsData2.ptime
                    val t1 = newsData.ptime
                    if (t1 != null && t2 != null) {
                        t2.compareTo(t1)
                    } else {
                        0
                    }
                })
                .compose({ listObservable ->
                    listObservable
                            .unsubscribeOn(Schedulers.io())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                })
        return subscription
    }
}
