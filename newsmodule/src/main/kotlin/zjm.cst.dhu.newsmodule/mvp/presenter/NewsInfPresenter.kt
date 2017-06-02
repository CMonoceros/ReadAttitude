package zjm.cst.dhu.newsmodule.mvp.presenter

import javax.inject.Inject

import retrofit2.Retrofit
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.api.repository.Repository
import zjm.cst.dhu.library.mvp.model.NewsInf
import zjm.cst.dhu.library.utils.network.RetrofitUtil
import zjm.cst.dhu.newsmodule.mvp.view.NewsInfContract

/**
 * Created by zjm on 2017/5/14.
 */

class NewsInfPresenter @Inject constructor()
    : NewsInfContract.Presenter {

    private var myView: NewsInfContract.View? = null
    private var myCompositeSubscription: CompositeSubscription? = null

    private var postId: String? = null

    override fun attachView(BaseView: NewsInfContract.View) {
        myView = BaseView
        myCompositeSubscription = CompositeSubscription()
    }

    override fun detachView() {
        if (myCompositeSubscription != null && myCompositeSubscription!!.isUnsubscribed) {
            myCompositeSubscription!!.unsubscribe()
        }
    }

    override fun loadNewsInf(postId: String) {
        this.postId = postId
        val retrofit = RetrofitUtil.setupRetrofit(RetrofitUtil.setupOkHttpClient(),
                Constant.NETEASE_NEWS_BASE_URL)
        val repository = Repository(retrofit)
        val subscription = repository.getNewInf(postId)
                .map { stringNewsInfMap ->
                    val newsInf = stringNewsInfMap[postId]
                    if (newsInf != null) {
                        changeToHtml(newsInf)
                    }
                    newsInf
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NewsInf?> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        myView!!.loadNewsInfError()
                    }

                    override fun onNext(newsInf: NewsInf?) {
                        myView!!.loadNewsInfSuccess(newsInf)
                    }
                })
        myCompositeSubscription!!.add(subscription)
    }

    private fun changeToHtml(newsInf: NewsInf) {
        val imgList = newsInf.img
        if (imgList != null && imgList.size > 0) {
            var newsBody: String? = newsInf.body
            if (newsBody != null) {
                for (i in imgList.indices) {
                    val oldChars = "<!--IMG#$i-->"
                    val newChars: String = "<img src=\"" + imgList[i].src + "\" />"
                    newsBody = newsBody!!.replace(oldChars, newChars)
                }
                newsInf.body = newsBody
            }
        }
    }
}
