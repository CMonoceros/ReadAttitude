package zjm.cst.dhu.library.utils.data

import zjm.cst.dhu.library.Constant
import zjm.cst.dhu.library.MyApplication
import zjm.cst.dhu.library.mvp.model.NewsChannel
import zjm.cst.dhu.library.mvp.model.NewsChannelDao
import zjm.cst.dhu.library.R

/**
 * Created by zjm on 2017/5/10.
 */

object DatabaseUtil {

    fun setupDatabase() {
        val newsChannelDao = MyApplication.Companion.newChannelDao
        val channelNameList = MyApplication.Companion.context!!.resources
                .getStringArray(R.array.news_channel_list_name)
        val channelIdList = MyApplication.Companion.context!!.resources
                .getStringArray(R.array.news_channel_list_id)
        val channelTypeList = MyApplication.Companion.context!!.resources
                .getStringArray(R.array.news_channel_list_type)
        for (i in channelNameList.indices) {
            val name = channelNameList[i]
            var type = channelTypeList[2]
            var isLike = false
            if (name == Constant.NEWS_CHANNEL_HEADLINE) {
                type = channelTypeList[0]
            } else if (name == Constant.NEWS_CHANNEL_HOUSE) {
                type = channelTypeList[1]
            }
            if (i < Constant.NEWS_CHANNEL_LIKEINDEX) {
                isLike = true
            }
            newsChannelDao.insert(NewsChannel(i.toLong(), name, channelIdList[i], i, type, isLike))
        }
    }

    val likeChannel: List<NewsChannel>?
        get() {
            val newsChannelQuery = MyApplication.Companion.newChannelDao.queryBuilder()
                    .where(NewsChannelDao.Properties.IsLike.eq(true))
                    .orderAsc(NewsChannelDao.Properties.ChannelIndex)
                    .build()
            return newsChannelQuery.list()
        }

    val unlikeChannel: List<NewsChannel>?
        get() {
            val newsChannelQuery = MyApplication.newChannelDao.queryBuilder()
                    .where(NewsChannelDao.Properties.IsLike.eq(false))
                    .orderAsc(NewsChannelDao.Properties.ChannelIndex)
                    .build()
            return newsChannelQuery.list()
        }

    fun updateChannel(newsChannelMap: Map<Int, List<NewsChannel>?>) {
        val like = newsChannelMap[Constant.CHANNEL_LIST_LIKE]
        val unlike = newsChannelMap[Constant.CHANNEL_LIST_UNLIKE]
        var newsChannel: NewsChannel
        if (like != null) {
            for (i in like.indices) {
                newsChannel = like.get(i)
                newsChannel.isLike = true
                DatabaseUtil.updateChannel(newsChannel)
            }
        }
        if (unlike != null) {
            for (i in unlike.indices) {
                newsChannel = unlike.get(i)
                newsChannel.isLike = false
                DatabaseUtil.updateChannel(newsChannel)
            }
        }
    }


    fun updateChannel(newsChannel: NewsChannel) {
        MyApplication.Companion.newChannelDao.update(newsChannel)
    }

}
