package zjm.cst.dhu.readattitude.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import zjm.cst.dhu.readattitude.R

/**
 * Created by zjm on 2017/5/9.
 */

class TestFragment1
    : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = inflater!!.inflate(R.layout.item_guide_1, container, false)
}
