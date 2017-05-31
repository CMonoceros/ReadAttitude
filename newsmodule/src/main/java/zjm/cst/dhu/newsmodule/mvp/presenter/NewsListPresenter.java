package zjm.cst.dhu.newsmodule.mvp.presenter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.api.repository.Repository;
import zjm.cst.dhu.library.event.FloatingActionButtonClickEvent;
import zjm.cst.dhu.library.mvp.model.NewsData;
import zjm.cst.dhu.library.utils.RetrofitUtil;
import zjm.cst.dhu.library.utils.RxBus;
import zjm.cst.dhu.newsmodule.mvp.view.NewsListContract;

/**
 * Created by zjm on 2017/5/10.
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    private NewsListContract.View myView;
    private CompositeSubscription myCompositeSubscription;
    private int nowPosition = 0;
    private String mNewsId;
    private String mNewsType;

    @Inject
    public NewsListPresenter() {

    }

    @Override
    public void attachView(NewsListContract.View BaseView) {
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
    public void setValue(String id, String type) {
        this.mNewsId = id;
        this.mNewsType = type;
        subscribeFloatingActionButtonClickEvent();
    }

    private void subscribeFloatingActionButtonClickEvent(){
        Subscription subscription= RxBus.getDefault().toObservable(FloatingActionButtonClickEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FloatingActionButtonClickEvent>() {
                    @Override
                    public void call(FloatingActionButtonClickEvent floatingActionButtonClickEvent) {
                        myView.floatingActionButtonClick();
                    }
                });
        myCompositeSubscription.add(subscription);
    }

    @Override
    public void loadNewList() {
        nowPosition = 0;
        Retrofit retrofit = RetrofitUtil.setupRetrofit(RetrofitUtil.setupOkHttpClient(), Constant.NETEASE_NEWS_BASE_URL);
        Repository repository = new Repository(retrofit);
        Subscription subscription = repository.getNewsList(mNewsType, mNewsId, nowPosition)
                .flatMap(new Func1<Map<String, List<NewsData>>, Observable<NewsData>>() {
                    @Override
                    public Observable<NewsData> call(Map<String, List<NewsData>> stringListMap) {
                        if (mNewsId.endsWith("5rex5Zyz")) {
                            return Observable.from(stringListMap.get("深圳"));
                        }
                        return Observable.from(stringListMap.get(mNewsId));
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsData, NewsData, Integer>() {
                    @Override
                    public Integer call(NewsData newsData, NewsData newsData2) {
                        if (newsData.getAds() != null) {
                            return -1;
                        } else if (newsData2.getAds() != null) {
                            return 1;
                        }
                        return newsData2.getPtime().compareTo(newsData.getPtime());
                    }
                })
                .compose(new Observable.Transformer<List<NewsData>, List<NewsData>>() {
                    @Override
                    public Observable<List<NewsData>> call(Observable<List<NewsData>> listObservable) {
                        return listObservable
                                .unsubscribeOn(Schedulers.io())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Observer<List<NewsData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        myView.loadNewListError();
                    }

                    @Override
                    public void onNext(List<NewsData> newsDataList) {
                        myView.loadNewListSuccess(newsDataList);
                        nowPosition+=Constant.NEWS_LIST_REFRESH_ITEM_MEDIUM_COUNTS;
                    }
                });
        myCompositeSubscription.add(subscription);
    }

    @Override
    public void loadNextList() {
        Retrofit retrofit = RetrofitUtil.setupRetrofit(RetrofitUtil.setupOkHttpClient(), Constant.NETEASE_NEWS_BASE_URL);
        Repository repository = new Repository(retrofit);
        Subscription subscription = repository.getNewsList(mNewsType, mNewsId, nowPosition)
                .flatMap(new Func1<Map<String, List<NewsData>>, Observable<NewsData>>() {
                    @Override
                    public Observable<NewsData> call(Map<String, List<NewsData>> stringListMap) {
                        if (mNewsId.endsWith("5rex5Zyz")) {
                            return Observable.from(stringListMap.get("深圳"));
                        }
                        return Observable.from(stringListMap.get(mNewsId));
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsData, NewsData, Integer>() {
                    @Override
                    public Integer call(NewsData newsData, NewsData newsData2) {
                        if (newsData.getAds() != null) {
                            return -1;
                        } else if (newsData2.getAds() != null) {
                            return 1;
                        }
                        return newsData2.getPtime().compareTo(newsData.getPtime());
                    }
                })
                .compose(new Observable.Transformer<List<NewsData>, List<NewsData>>() {
                    @Override
                    public Observable<List<NewsData>> call(Observable<List<NewsData>> listObservable) {
                        return listObservable
                                .unsubscribeOn(Schedulers.io())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Observer<List<NewsData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        myView.loadNextListError();
                    }

                    @Override
                    public void onNext(List<NewsData> newsDataList) {
                        myView.loadNextListSuccess(newsDataList);
                        nowPosition+=Constant.NEWS_LIST_REFRESH_ITEM_MEDIUM_COUNTS;
                    }
                });
        myCompositeSubscription.add(subscription);
    }
}
