package zjm.cst.dhu.library.inject.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by zjm on 2017/5/9.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerContext {
    String value() default "Application";
}
