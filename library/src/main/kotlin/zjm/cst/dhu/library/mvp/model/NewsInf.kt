package zjm.cst.dhu.library.mvp.model

/**
 * Created by zjm on 2017/5/13.
 */

data class NewsInf(var body: String? = null,
                   var replyCount: Int = 0,
                   var shareLink: String? = null,
                   var digest: String? = null,
                   var dkeys: String? = null,
                   var ec: String? = null,
                   var docid: String? = null,
                   var isPicnews: Boolean = false,
                   var title: String? = null,
                   var tid: String? = null,
                   var template: String? = null,
                   var threadVote: Int = 0,
                   var threadAgainst: Int = 0,
                   var replyBoard: String? = null,
                   var source: String? = null,
                   var isHasNext: Boolean = false,
                   var voicecomment: String? = null,
                   var ptime: String? = null,
                   var users: List<*>? = null,
                   var ydbaike: List<*>? = null,
                   var link: List<*>? = null,
                   var votes: List<*>? = null,
                   var img: List<ImgBean>? = null,
                   var topiclist_news: List<TopiclistNewsBean>? = null,
                   var topiclist: List<TopiclistBean>? = null,
                   var boboList: List<*>? = null,
                   var relative_sys: List<*>? = null,
                   var apps: List<*>? = null) {

    data class ImgBean(var ref: String? = null,
                       var pixel: String? = null,
                       var alt: String? = null,
                       var src: String? = null) {
    }

    data class TopiclistNewsBean(var isHasCover: Boolean = false,
                                 var subnum: String? = null,
                                 var alias: String? = null,
                                 var tname: String? = null,
                                 var ename: String? = null,
                                 var tid: String? = null,
                                 var cid: String? = null) {
    }

    data class TopiclistBean(var isHasCover: Boolean = false,
                             var subnum: String? = null,
                             var alias: String? = null,
                             var tname: String? = null,
                             var ename: String? = null,
                             var tid: String? = null,
                             var cid: String? = null) {
    }
}
