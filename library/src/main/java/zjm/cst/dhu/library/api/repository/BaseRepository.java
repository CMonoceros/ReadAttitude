package zjm.cst.dhu.library.api.repository;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import zjm.cst.dhu.library.mvp.model.NewsData;
import zjm.cst.dhu.library.mvp.model.NewsInf;

/**
 * Created by zjm on 2017/5/10.
 */

public interface BaseRepository {

    Observable<Map<String, List<NewsData>>> getNewsList(String type, String id, int start);

    Observable<Map<String, NewsInf>> getNewInf(String postId);

    Observable<ResponseBody> getNewsInfImg(String path);
}
