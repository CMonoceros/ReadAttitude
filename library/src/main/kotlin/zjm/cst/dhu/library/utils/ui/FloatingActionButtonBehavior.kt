package zjm.cst.dhu.library.utils.ui

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 * Created by zjm on 2017/5/11.
 */

class FloatingActionButtonBehavior(context: Context, attrs: AttributeSet)
    : FloatingActionButton.Behavior() {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
                                     directTargetChild: View?, target: View?, nestedScrollAxes: Int):
            Boolean = (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
            || super.onStartNestedScroll(coordinatorLayout, child,
            directTargetChild, target, nestedScrollAxes))

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
                                target: View?, dxConsumed: Int, dyConsumed: Int,
                                dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child,
                target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed)
        if (dyConsumed > 0 && child!!.visibility == android.view.View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            child.hide()
        } else if (dyConsumed < 0 && child!!.visibility != android.view.View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.show()
        }
    }
}
