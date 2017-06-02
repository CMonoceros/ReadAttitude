package zjm.cst.dhu.library.api.repository

import okhttp3.ResponseBody
import zjm.cst.dhu.library.api.ApiService
import zjm.cst.dhu.library.mvp.model.NewsData
import zjm.cst.dhu.library.mvp.model.NewsInf
import retrofit2.Retrofit
import rx.Observable

/**
 * Created by zjm on 2017/5/10.
 */

class Repository(retrofit: Retrofit)
    : BaseRepository {

    private val mApiService: ApiService = retrofit.create(ApiService::class.java)

    override fun getNewsList(type: String, id: String, start: Int):
            Observable<Map<String, List<NewsData>>> = mApiService.getNewsList(type, id, start)

    override fun getNewInf(postId: String):
            Observable<Map<String, NewsInf>> = mApiService.getNewInf(postId)

    override fun getNewsInfImg(path: String):
            Observable<ResponseBody> = mApiService.getNewsInfImg(path)

}
