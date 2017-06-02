package zjm.cst.dhu.library.utils.others

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment

/**
 * Created by zjm on 2017/5/11.
 */

object Router {

    fun getFragment(name: String):
            Fragment? {
        val fragment: Fragment?
        try {
            val fragmentClass = Class.forName(name)
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return fragment
    }

    fun startActivityForName(context: Context, name: String) {
        try {
            val clazz = Class.forName(name)
            val intent = Intent(context, clazz)
            context.startActivity(intent)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }

    }
}
