package zjm.cst.dhu.readattitude.mvp.presenter

import javax.inject.Inject

import zjm.cst.dhu.readattitude.mvp.view.MainContract

/**
 * Created by zjm on 2017/5/9.
 */

class MainPresenter @Inject constructor()
    : MainContract.Presenter {
    private var myView: MainContract.View? = null

    override fun attachView(BaseView: MainContract.View) {
        myView = BaseView
    }

    override fun detachView() {

    }
}
