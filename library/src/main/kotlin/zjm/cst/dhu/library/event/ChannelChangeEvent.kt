package zjm.cst.dhu.library.event

import zjm.cst.dhu.library.mvp.model.NewsChannel

/**
 * Created by zjm on 2017/5/12.
 */

data class ChannelChangeEvent(var newsChannel: NewsChannel? = null,
                              var isDelete: Boolean = false,
                              var isMove: Boolean = false,
                              var changeChannelPosition: Int = 0,
                              var fromPosition: Int = 0,
                              var toPosition: Int = 0) {
}
