package zjm.cst.dhu.library.api.repository;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import zjm.cst.dhu.library.api.ApiService;
import zjm.cst.dhu.library.mvp.model.NewsData;
import zjm.cst.dhu.library.mvp.model.NewsInf;

/**
 * Created by zjm on 2017/5/10.
 */

public class Repository implements BaseRepository {
    private ApiService mApiService;

    public Repository(Retrofit retrofit) {
        mApiService = retrofit.create(ApiService.class);
    }

    @Override
    public Observable<Map<String, List<NewsData>>> getNewsList(String type, String id, int start) {
        return mApiService.getNewsList(type, id, start);
    }

    @Override
    public Observable<Map<String, NewsInf>> getNewInf(String postId) {
        return mApiService.getNewInf(postId);
    }

    @Override
    public Observable<ResponseBody> getNewsInfImg(String path) {
        return mApiService.getNewsInfImg(path);
    }
}
