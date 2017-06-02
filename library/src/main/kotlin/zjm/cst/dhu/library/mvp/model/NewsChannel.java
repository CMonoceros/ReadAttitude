package zjm.cst.dhu.library.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zjm on 2017/5/10.
 */

@Entity
public class NewsChannel {

    @Id(autoincrement = true)
    private long id;

    @Unique
    @NotNull
    @Property(nameInDb = "CHANNEL_NAME")
    private String channelName;

    @NotNull
    @Property(nameInDb = "CHANNEL_ID")
    private String channelId;

    @Property(nameInDb = "CHANNEL_INDEX")
    private int channelIndex;

    @Property(nameInDb = "CHANNEL_TYPE")
    private String channelType;

    @Property(nameInDb = "CHANNEL_LIKE")
    private boolean isLike;

    @Generated(hash = 1871823925)
    public NewsChannel(long id, @NotNull String channelName, @NotNull String channelId,
            int channelIndex, String channelType, boolean isLike) {
        this.id = id;
        this.channelName = channelName;
        this.channelId = channelId;
        this.channelIndex = channelIndex;
        this.channelType = channelType;
        this.isLike = isLike;
    }

    @Generated(hash = 566079451)
    public NewsChannel() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getChannelIndex() {
        return this.channelIndex;
    }

    public void setChannelIndex(int channelIndex) {
        this.channelIndex = channelIndex;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public boolean getIsLike() {
        return this.isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }
}
