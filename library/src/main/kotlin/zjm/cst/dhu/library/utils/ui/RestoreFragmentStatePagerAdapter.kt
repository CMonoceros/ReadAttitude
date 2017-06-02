package zjm.cst.dhu.library.utils.ui

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup


/**
 * Created by zjm on 2017/5/12.
 */

abstract class RestoreFragmentStatePagerAdapter(private val mFragmentManager: FragmentManager)
    : PagerAdapter() {

    private var mCurTransaction: FragmentTransaction? = null
    private val mSavedState = ArrayList<Fragment.SavedState?>()
    private val mFragments = ArrayList<Fragment?>()
    private val restoreStateMap = HashMap<Int, Int>()
    private var mCurrentPrimaryItem: Fragment? = null

    /**
     * Return the Fragment associated with a specified position.
     */
    abstract fun getItem(position: Int):
            Fragment

    fun setRestoreStateMap(restoreStateMap: Map<Int, Int>) {
        this.restoreStateMap.putAll(restoreStateMap)
    }

    override fun startUpdate(container: ViewGroup) {
        if (container.id == View.NO_ID) {
            throw IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int):
            Any {
        // If we already have this item instantiated, there is nothing
        // to do.  This can happen when we are restoring the entire pager
        // from its saved state, where the fragment manager has already
        // taken care of restoring the fragments we previously had instantiated.
        if (mFragments.size > position) {
            val f = mFragments[position]
            if (f != null) {
                return f
            }
        }

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }

        val fragment = getItem(position)
        if (mSavedState.size > position) {
            val fss = mSavedState[position]
            if (fss != null) {
                fragment.setInitialSavedState(fss)
            }
        }
        while (mFragments.size <= position) {
            mFragments.add(null)
        }
        fragment.setMenuVisibility(false)
        fragment.userVisibleHint = false
        mFragments[position] = fragment
        mCurTransaction!!.add(container.id, fragment)

        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as Fragment

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }
        while (mSavedState.size <= position) {
            mSavedState.add(null)
        }
        mSavedState.set(position, if (fragment.isAdded)
            mFragmentManager.saveFragmentInstanceState(fragment)
        else
            null)
        mFragments.set(position, null)

        mCurTransaction!!.remove(fragment)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any?) {
        val fragment = `object` as Fragment?
        if (fragment !== mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem!!.setMenuVisibility(false)
                mCurrentPrimaryItem!!.userVisibleHint = false
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true)
                fragment.userVisibleHint = true
            }
            mCurrentPrimaryItem = fragment
        }
    }

    override fun finishUpdate(container: ViewGroup) {
        if (mCurTransaction != null) {
            mCurTransaction!!.commitNowAllowingStateLoss()
            mCurTransaction = null
        }
    }

    override fun isViewFromObject(view: View, `object`: Any):
            Boolean = ((`object` as Fragment).view === view)

    override fun saveState():
            Parcelable? {
        var state: Bundle? = null
        if (mSavedState.size > 0) {
            state = Bundle()
            val fss = arrayOfNulls<Fragment.SavedState>(mSavedState.size)
            mSavedState.toTypedArray<Fragment.SavedState?>()
            state.putParcelableArray("states", fss)
        }
        for (i in mFragments.indices) {
            val f = mFragments[i]
            if (f != null && f.isAdded) {
                if (state == null) {
                    state = Bundle()
                }
                val key = "f" + i
                mFragmentManager.putFragment(state, key, f)
            }
        }
        return state
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        if (state != null) {
            val bundle = state as Bundle?
            bundle!!.classLoader = loader
            val fss = bundle.getParcelableArray("states")
            mSavedState.clear()
            mFragments.clear()
            if (fss != null) {
                for (i in fss.indices) {
                    mSavedState.add(fss[i] as Fragment.SavedState)
                }
            }
            val keys = bundle.keySet()
            for (key in keys) {
                if (key.startsWith("f")) {
                    val oldIndex = Integer.parseInt(key.substring(1))
                    var newIndex = -1
                    if (restoreStateMap[oldIndex] != null) {
                        newIndex = restoreStateMap[oldIndex]!!
                    }
                    val f = mFragmentManager.getFragment(bundle, key)
                    if (f != null && newIndex != -1) {
                        while (mFragments.size <= newIndex) {
                            mFragments.add(null)
                        }
                        f.setMenuVisibility(false)
                        mFragments[newIndex] = f
                    } else {
                    }
                }
            }
            restoreStateMap.clear()
        }
    }

    companion object {
        private val TAG = "RestoreFragmentStatePagerAdapter"
        private val DEBUG = false
    }
}
