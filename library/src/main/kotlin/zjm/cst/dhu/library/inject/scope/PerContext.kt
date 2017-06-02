package zjm.cst.dhu.library.inject.scope


import javax.inject.Qualifier

/**
 * Created by zjm on 2017/5/9.
 */

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class PerContext(val value: String = "Application")
