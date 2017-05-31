package zjm.cst.dhu.library.api;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;
import zjm.cst.dhu.library.mvp.model.NewsData;
import zjm.cst.dhu.library.mvp.model.NewsInf;

/**
 * Created by zjm on 2017/5/10.
 */

public interface ApiService {

    @GET("{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsData>>> getNewsList(@Path("type") String type, @Path("id") String id
            , @Path("startPage") int start);

    @GET("{postId}/full.html")
    Observable<Map<String, NewsInf>> getNewInf(@Path("postId") String postId);

    @GET
    Observable<ResponseBody> getNewsInfImg(@Url String path);
}
