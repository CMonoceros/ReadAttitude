package zjm.cst.dhu.library.api

import okhttp3.ResponseBody
import zjm.cst.dhu.library.mvp.model.NewsData
import zjm.cst.dhu.library.mvp.model.NewsInf
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import rx.Observable

/**
 * Created by zjm on 2017/5/10.
 */

interface ApiService {

    @GET("{type}/{id}/{startPage}-20.html")
    fun getNewsList(@Path("type") type: String,
                    @Path("id") id: String,
                    @Path("startPage") start: Int):
            Observable<Map<String, List<NewsData>>>

    @GET("{postId}/full.html")
    fun getNewInf(@Path("postId") postId: String):
            Observable<Map<String, NewsInf>>

    @GET
    fun getNewsInfImg(@Url path: String):
            Observable<ResponseBody>
}
