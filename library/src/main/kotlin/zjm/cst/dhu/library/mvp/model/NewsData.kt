package zjm.cst.dhu.library.mvp.model

/**
 * Created by zjm on 2017/5/10.
 */


data class NewsData(var articleType: String? = null,
                    var ads: List<Ad>? = null,
                    var postid: String? = null,
                    var hasCover: Boolean? = null,
                    var hasHead: Int? = null,
                    var replyCount: Int? = null,
                    var ltitle: String? = null,
                    var hasImg: Int? = null,
                    var digest: String? = null,
                    var hasIcon: Boolean? = null,
                    var docid: String? = null,
                    var title: String? = null,
                    var order: Int? = null,
                    var priority: Int? = null,
                    var lmodify: String? = null,
                    var boardid: String? = null,
                    var topicBackground: String? = null,
                    var url3w: String? = null,
                    var template: String? = null,
                    var votecount: Int? = null,
                    var alias: String? = null,
                    var cid: String? = null,
                    var url: String? = null,
                    var hasAD: Int? = null,
                    var source: String? = null,
                    var ename: String? = null,
                    var subtitle: String? = null,
                    var imgsrc: String? = null,
                    var tname: String? = null,
                    var ptime: String? = null,
                    var skipID: String? = null,
                    var skipType: String? = null,
                    var imgextra: List<Imgextra>? = null,
                    var photosetID: String? = null,
                    var imgsum: Int? = null,
                    var specialID: String? = null,
                    val additionalProperties: HashMap<String, Any>) {

    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }

    data class Imgextra(var imgsrc: String? = null,
                        val additionalProperties: HashMap<String, Any>) {

        fun setAdditionalProperty(name: String, value: Any) {
            this.additionalProperties[name] = value
        }
    }

    data class Ad(var docid: String? = null,
                  var title: String? = null,
                  var skipID: String? = null,
                  var tag: String? = null,
                  var imgsrc: String? = null,
                  var subtitle: String? = null,
                  var skipType: String? = null,
                  var url: String? = null,
                  val additionalProperties: HashMap<String, Any>) {

        fun setAdditionalProperty(name: String, value: Any) {
            this.additionalProperties[name] = value
        }
    }

}
