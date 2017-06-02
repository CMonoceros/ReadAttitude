package zjm.cst.dhu.library.api.repository

import okhttp3.ResponseBody
import zjm.cst.dhu.library.mvp.model.NewsData
import zjm.cst.dhu.library.mvp.model.NewsInf
import rx.Observable

/**
 * Created by zjm on 2017/5/10.
 */

interface BaseRepository {

    fun getNewsList(type: String, id: String, start: Int):
            Observable<Map<String, List<NewsData>>>

    fun getNewInf(postId: String):
            Observable<Map<String, NewsInf>>

    fun getNewsInfImg(path: String):
            Observable<ResponseBody>
}