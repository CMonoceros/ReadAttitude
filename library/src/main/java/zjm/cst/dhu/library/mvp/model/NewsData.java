package zjm.cst.dhu.library.mvp.model;

/**
 * Created by zjm on 2017/5/10.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsData {
    private String articleType;
    private List<Ad> ads = null;
    private String postid;
    private Boolean hasCover;
    private Integer hasHead;
    private Integer replyCount;
    private String ltitle;
    private Integer hasImg;
    private String digest;
    private Boolean hasIcon;
    private String docid;
    private String title;
    private Integer order;
    private Integer priority;
    private String lmodify;
    private String boardid;
    private String topicBackground;
    private String url3w;
    private String template;
    private Integer votecount;
    private String alias;
    private String cid;
    private String url;
    private Integer hasAD;
    private String source;
    private String ename;
    private String subtitle;
    private String imgsrc;
    private String tname;
    private String ptime;
    private String skipID;
    private String skipType;
    private List<Imgextra> imgextra = null;
    private String photosetID;
    private Integer imgsum;
    private String specialID;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public Boolean getHasCover() {
        return hasCover;
    }

    public void setHasCover(Boolean hasCover) {
        this.hasCover = hasCover;
    }

    public Integer getHasHead() {
        return hasHead;
    }

    public void setHasHead(Integer hasHead) {
        this.hasHead = hasHead;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getHasImg() {
        return hasImg;
    }

    public void setHasImg(Integer hasImg) {
        this.hasImg = hasImg;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Boolean getHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(Boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public Integer getImgsum() {
        return imgsum;
    }

    public void setImgsum(Integer imgsum) {
        this.imgsum = imgsum;
    }

    public String getTopicBackground() {
        return topicBackground;
    }

    public void setTopicBackground(String topicBackground) {
        this.topicBackground = topicBackground;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getVotecount() {
        return votecount;
    }

    public void setVotecount(Integer votecount) {
        this.votecount = votecount;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getHasAD() {
        return hasAD;
    }

    public void setHasAD(Integer hasAD) {
        this.hasAD = hasAD;
    }

    public List<Imgextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<Imgextra> imgextra) {
        this.imgextra = imgextra;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getUrl3w() {
        return url3w;
    }

    public void setUrl3w(String url3w) {
        this.url3w = url3w;
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSpecialID() {
        return specialID;
    }

    public void setSpecialID(String specialID) {
        this.specialID = specialID;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
