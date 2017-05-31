package zjm.cst.dhu.newsmodule.mvp.presenter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.api.repository.Repository;
import zjm.cst.dhu.library.mvp.model.NewsInf;
import zjm.cst.dhu.library.utils.RetrofitUtil;
import zjm.cst.dhu.newsmodule.mvp.view.NewsInfContract;

/**
 * Created by zjm on 2017/5/14.
 */

public class NewsInfPresenter implements NewsInfContract.Presenter {

    private NewsInfContract.View myView;
    private CompositeSubscription myCompositeSubscription;

    private String postId;

    @Inject
    public NewsInfPresenter() {

    }

    @Override
    public void attachView(NewsInfContract.View BaseView) {
        myView = BaseView;
        myCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        if (myCompositeSubscription != null && myCompositeSubscription.isUnsubscribed()) {
            myCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void loadNewsInf(final String postId) {
        this.postId = postId;
        Retrofit retrofit = RetrofitUtil.setupRetrofit(RetrofitUtil.setupOkHttpClient(), Constant.NETEASE_NEWS_BASE_URL);
        Repository repository = new Repository(retrofit);
        Subscription subscription = repository.getNewInf(postId)
                .map(new Func1<Map<String, NewsInf>, NewsInf>() {
                    @Override
                    public NewsInf call(Map<String, NewsInf> stringNewsInfMap) {
                        NewsInf newsInf = stringNewsInfMap.get(postId);
                        changeToHtml(newsInf);
                        return newsInf;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsInf>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        myView.loadNewsInfError();
                    }

                    @Override
                    public void onNext(NewsInf newsInf) {
                        myView.loadNewsInfSuccess(newsInf);
                    }
                });
        myCompositeSubscription.add(subscription);
    }

    private void changeToHtml(NewsInf newsInf) {
        List<NewsInf.ImgBean> imgList = newsInf.getImg();
        if (imgList != null && imgList.size() > 1) {
            String newsBody = newsInf.getBody();
            for (int i = 0; i < imgList.size(); i++) {
                String oldChars = "<!--IMG#" + i + "-->";
                String newChars;
                newChars = "<img src=\"" + imgList.get(i).getSrc() + "\" />";
                newsBody = newsBody.replace(oldChars, newChars);
            }
            newsInf.setBody(newsBody);
        }
    }
}
