package zjm.cst.dhu.library.utils;

import android.support.design.widget.TabLayout;
import android.view.View;

import zjm.cst.dhu.library.MyApplication;

/**
 * Created by zjm on 2017/5/10.
 */

public class ViewsUtil {

    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        int screenWidth = MyApplication.getContext().getResources().getDisplayMetrics().widthPixels;

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }
}
