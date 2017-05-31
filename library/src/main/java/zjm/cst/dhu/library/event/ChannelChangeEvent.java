package zjm.cst.dhu.library.event;

import zjm.cst.dhu.library.mvp.model.NewsChannel;

/**
 * Created by zjm on 2017/5/12.
 */

public class ChannelChangeEvent {
    private boolean isDelete = false;
    private int changeChannelPosition;
    private NewsChannel newsChannel;
    private boolean isMove = false;
    private int fromPosition;
    private int toPosition;

    public ChannelChangeEvent(boolean isDelete, NewsChannel newsChannel) {
        this.isDelete = isDelete;
        this.newsChannel = newsChannel;
    }

    public ChannelChangeEvent(boolean isDelete, int position, NewsChannel newsChannel) {
        this.isDelete = isDelete;
        this.changeChannelPosition = position;
        this.newsChannel = newsChannel;
    }

    public ChannelChangeEvent(boolean isMove, int fromPosition, int toPosition) {
        this.isMove = isMove;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public void setChangeChannelPosition(int position) {
        this.changeChannelPosition = position;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public int getChangeChannelPosition() {
        return changeChannelPosition;
    }

    public void setNewsChannel(NewsChannel newsChannel) {
        this.newsChannel = newsChannel;
    }

    public NewsChannel getNewsChannel() {
        return newsChannel;
    }

    public void setFromPosition(int fromPosition) {
        this.fromPosition = fromPosition;
    }

    public void setToPosition(int toPosition) {
        this.toPosition = toPosition;
    }

    public int getFromPosition() {
        return fromPosition;
    }

    public int getToPosition() {
        return toPosition;
    }

    public void setIsMove(boolean isMove) {
        this.isMove = isMove;
    }

    public boolean getIsMove() {
        return isMove;
    }
}
