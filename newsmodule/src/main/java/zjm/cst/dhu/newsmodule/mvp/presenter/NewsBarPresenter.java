package zjm.cst.dhu.newsmodule.mvp.presenter;

import android.content.Intent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.utils.DatabaseUtil;
import zjm.cst.dhu.newsmodule.mvp.view.NewsBarContract;

/**
 * Created by zjm on 2017/5/10.
 */

public class NewsBarPresenter implements NewsBarContract.Presenter {
    private NewsBarContract.View myView;
    private CompositeSubscription myCompositeSubscription;

    @Inject
    public NewsBarPresenter() {

    }

    @Override
    public void attachView(NewsBarContract.View BaseView) {
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
    public void loadChannel() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<NewsChannel>>() {

            @Override
            public void call(Subscriber<? super List<NewsChannel>> subscriber) {
                subscriber.onNext(DatabaseUtil.getLikeChannel());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsChannel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        myView.loadChannelError();
                    }

                    @Override
                    public void onNext(List<NewsChannel> newsChannels) {
                        myView.loadChannelSuccess(newsChannels);
                    }
                });
        myCompositeSubscription.add(subscription);
    }

    @Override
    public void readCrashLog() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File path = new File(Constant.CRASH_LOG_FILE_PATH);
                if (path.exists()) {
                    File[] file = path.listFiles();
                    String str = "";
                    for (int i = 0; i < file.length; i++) {
                        try {
                            str += file[i].getName() + " ----------start---------- ";
                            FileInputStream in = new FileInputStream(file[i]);
                            int size = in.available();
                            byte[] buffer = new byte[size];
                            in.read(buffer);
                            in.close();
                            String s = new String(buffer, "GB2312");
                            s += " ----------end---------- ";
                            str += s;
                        } catch (IOException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                    subscriber.onNext(str);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        myView.uploadCrashLog(s);
                    }
                });
        myCompositeSubscription.add(subscription);
    }
}
