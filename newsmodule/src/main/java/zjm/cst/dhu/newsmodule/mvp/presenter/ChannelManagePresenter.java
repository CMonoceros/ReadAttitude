package zjm.cst.dhu.newsmodule.mvp.presenter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;
import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.event.ChannelChangeEvent;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.utils.DatabaseUtil;
import zjm.cst.dhu.library.utils.RxBus;
import zjm.cst.dhu.newsmodule.mvp.view.ChannelManageContract;

/**
 * Created by zjm on 2017/5/12.
 */

public class ChannelManagePresenter implements ChannelManageContract.Presenter {
    private ChannelManageContract.View myView;
    private CompositeSubscription myCompositeSubscription;

    @Inject
    public ChannelManagePresenter() {

    }

    @Override
    public void attachView(ChannelManageContract.View BaseView) {
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
    public void loadChannelList() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Map<Integer, List<NewsChannel>>>() {

            @Override
            public void call(Subscriber<? super Map<Integer, List<NewsChannel>>> subscriber) {
                Map<Integer, List<NewsChannel>> map = new HashMap<Integer, List<NewsChannel>>();
                map.put(Constant.CHANNEL_LIST_LIKE, DatabaseUtil.getLikeChannel());
                map.put(Constant.CHANNEL_LIST_UNLIKE, DatabaseUtil.getUnlikeChannel());
                subscriber.onNext(map);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<Integer, List<NewsChannel>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        myView.loadChannelError();
                    }

                    @Override
                    public void onNext(Map<Integer, List<NewsChannel>> map) {
                        myView.loadChannelSuccess(map.get(Constant.CHANNEL_LIST_LIKE),
                                map.get(Constant.CHANNEL_LIST_UNLIKE));
                        subscribeChannelChangeEvent();
                    }
                });
        myCompositeSubscription.add(subscription);
    }

    @Override
    public void updateChannelDB(final List<NewsChannel> like, final List<NewsChannel> unlike) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Map<Integer, List<NewsChannel>>>() {
            @Override
            public void call(Subscriber<? super Map<Integer, List<NewsChannel>>> subscriber) {
                Map<Integer, List<NewsChannel>> map = new HashMap<Integer, List<NewsChannel>>();
                map.put(Constant.CHANNEL_LIST_LIKE, like);
                map.put(Constant.CHANNEL_LIST_UNLIKE, unlike);
                subscriber.onNext(map);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Map<Integer, List<NewsChannel>>>() {
                    @Override
                    public void call(Map<Integer, List<NewsChannel>> integerListMap) {
                        DatabaseUtil.updateChannel(integerListMap);
                    }
                });
        myCompositeSubscription.add(subscription);
    }

    private void subscribeChannelChangeEvent() {
        Subscription subscription = RxBus.getDefault().toObservable(ChannelChangeEvent.class)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ChannelChangeEvent>() {
                    @Override
                    public void call(ChannelChangeEvent channelChangeEvent) {
                        myView.channelChange(channelChangeEvent);
                    }
                });
        myCompositeSubscription.add(subscription);
    }


}
