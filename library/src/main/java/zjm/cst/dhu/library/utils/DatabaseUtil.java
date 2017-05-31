package zjm.cst.dhu.library.utils;

import org.greenrobot.greendao.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.MyApplication;
import zjm.cst.dhu.library.R;
import zjm.cst.dhu.library.mvp.model.NewsChannel;
import zjm.cst.dhu.library.mvp.model.NewsChannelDao;

/**
 * Created by zjm on 2017/5/10.
 */

public class DatabaseUtil {
    public static void setupDatabase() {
        NewsChannelDao newsChannelDao = MyApplication.getNewChannelDao();
        List<String> channelNameList = Arrays.asList(MyApplication.getContext().getResources()
                .getStringArray(R.array.news_channel_list_name));
        List<String> channelIdList = Arrays.asList(MyApplication.getContext().getResources()
                .getStringArray(R.array.news_channel_list_id));
        List<String> channelTypeList = Arrays.asList(MyApplication.getContext().getResources()
                .getStringArray(R.array.news_channel_list_type));
        for (int i = 0; i < channelNameList.size(); i++) {
            String name = channelNameList.get(i);
            String type = channelTypeList.get(2);
            boolean isLike = false;
            if (name.equals(Constant.NEWS_CHANNEL_HEADLINE)) {
                type = channelTypeList.get(0);
            } else if (name.equals(Constant.NEWS_CHANNEL_HOUSE)) {
                type = channelTypeList.get(1);
            }
            if (i < Constant.NEWS_CHANNEL_LIKEINDEX) {
                isLike = true;
            }
            newsChannelDao.insert(new NewsChannel(i, name, channelIdList.get(i), i, type, isLike));
        }
    }

    public static List<NewsChannel> getLikeChannel() {
        Query<NewsChannel> newsChannelQuery = MyApplication.getNewChannelDao().queryBuilder()
                .where(NewsChannelDao.Properties.IsLike.eq(true))
                .orderAsc(NewsChannelDao.Properties.ChannelIndex)
                .build();
        return newsChannelQuery.list();
    }

    public static List<NewsChannel> getUnlikeChannel() {
        Query<NewsChannel> newsChannelQuery = MyApplication.getNewChannelDao().queryBuilder()
                .where(NewsChannelDao.Properties.IsLike.eq(false))
                .orderAsc(NewsChannelDao.Properties.ChannelIndex)
                .build();
        return newsChannelQuery.list();
    }

    public static void updateChannel(Map<Integer, List<NewsChannel>> newsChannelMap) {
        List<NewsChannel> like = newsChannelMap.get(Constant.CHANNEL_LIST_LIKE);
        List<NewsChannel> unlike = newsChannelMap.get(Constant.CHANNEL_LIST_UNLIKE);
        NewsChannel newsChannel;
        for (int i = 0; i < like.size(); i++) {
            newsChannel = like.get(i);
            newsChannel.setIsLike(true);
            updateChannel(newsChannel);
        }
        for (int i = 0; i < unlike.size(); i++) {
            newsChannel = unlike.get(i);
            newsChannel.setIsLike(false);
            updateChannel(newsChannel);
        }
    }


    public static void updateChannel(NewsChannel newsChannel) {
        MyApplication.getNewChannelDao().update(newsChannel);
    }

}
