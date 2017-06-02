package zjm.cst.dhu.library.utils.others

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

/**
 * Created by zjm on 2017/5/11.
 */

class RxBus {

    private val bus: Subject<Any, Any>

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    init {
        bus = SerializedSubject(PublishSubject.create<Any>())
    }

    // 发送一个新的事件
    fun post(o: Any) {
        bus.onNext(o)
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    fun <T> toObservable(eventType: Class<T>):
            Observable<T> = bus.ofType(eventType)

    companion object {
        @Volatile private var defaultInstance: RxBus? = null

        // 单例RxBus
        val default: RxBus
            get() {
                if (defaultInstance == null) {
                    synchronized(RxBus::class.java) {
                        if (defaultInstance == null) {
                            defaultInstance = RxBus()
                        }
                    }
                }
                return defaultInstance!!
            }
    }
}
