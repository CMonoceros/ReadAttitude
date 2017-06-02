package zjm.cst.dhu.library

/**
 * Created by zjm on 2017/5/9.
 */

object Constant {
    val NEWS_BAR_FRAGMENT_NAME = "zjm.cst.dhu.newsmodule.mvp.ui.fragment.NewsBarFragment"
    val NEWS_BAR_FRAGMENT_TITTLE = "News"

    val CACHE_NETWORK=0*60
    val CACHE_NONETWORK=2*24*60*60
    val CACHE_SIZE=1024 * 1024 * 100

    val CONNECT_TIMEOUT=6
    val READ_TIMEOUT=6
    val WRITE_TIMEOUT=6


    val CRASH_LOG_FILE_PATH = "/sdcard/ReadAttitude/daily/log/"
    val CRASH_LOG_UPLOAD_TITTLE = "Read Attitude Crash Log"

    val GUIDE_POINT_MARGIN = 10

    val BACK_PRESS_TIME: Long = 2000

    val SHARED_READ_ATTITUDE = "shared_read_attitude"
    val SHARED_FIRST_TIME = "shared_first_time"

    val DATABASE_NAME = "database_read_attitude"
    val DATABASE_PASSWORD="zjm_cst_dhu"

    val NEWS_CHANNEL_HEADLINE = "头条"
    val NEWS_CHANNEL_HOUSE = "房产"
    val NEWS_CHANNEL_LIKEINDEX = 6

    val NEWS_LIST_HEADER_POSITION = 0
    val NEWS_LIST_TYPE_HEADER = 0xff01
    val NEWS_LIST_TYPE_CARD = 0xff02
    val NEWS_LIST_TYPE_FOOTER = 0xff03

    val NEWS_LIST_FRAGMENT_BUNDLE_ID = "news_list_fragment_bundle_id"
    val NEWS_LIST_FRAGMENT_BUNDLE_TYPE = "news_list_fragment_bundle_type"

    val NEWS_LIST_REFRESH_ITEM_MEDIUM_COUNTS = 20
    val NEWS_LIST_REFRESH_ITEM_SMALL_COUNTS = 10
    val NETEASE_NEWS_BASE_URL = "http://c.m.163.com/nc/article/"

    val NEWS_LIST_GRIDLAYOUT_SPAN = 6

    val NEWS_LIST_LOAD_ERROR = "Load error,please try again!"
    val NEWS_BAR_LOAD_CHANNEL_ERROR = "Load channels error,please wait!"
    val CHANNEL_MANAGE_LOAD_CHANNEL_ERROR = "Load channels error,please wait!"

    val CHANNEL_LIST_LIKE = 1
    val CHANNEL_LIST_UNLIKE = 0

    val CHANNEL_SHOW_LINE_COUNTS = 3

    val NEWS_INF_POST_ID = "news_inf_post_id"
    val NEWS_INF_IMG_BASE = "news_inf_img_base"

    val NEWS_INF_LOAD_ERROR = "Load error,please try again!"

    val GLIDE_THUMBNAIL_SCALE = 0.5f

}
